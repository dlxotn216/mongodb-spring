package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.Comment
import io.taesu.springdatamongodb.post.domain.Post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class PostRetrieveServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(Post::class.java)
        mongoTemplate.insertAll(
            listOf(
                Post(
                    title = "title1", content = "content1", comments = listOf(
                        Comment("comment1-1", "taesu"),
                        Comment("comment1-2", "kim"),
                        Comment("comment1-3", "lee", likes = listOf("kim", "lee", "park")),
                        Comment("comment1-4", "park"),
                        Comment("comment1-5", "taesu", likes = listOf("kim", "lee", "park")),
                    )
                ),
                Post(
                    title = "title2", content = "content1", comments = listOf(
                        Comment("comment2-1", "taesu"),
                        Comment("comment2-2", "lee"),
                        Comment("comment2-3", "kim"),
                        Comment("comment2-4", "bob"),
                        Comment("comment2-5", "taesu"),
                    )
                ),
            )
        )
    }

    @Test
    fun `3개의 댓글만 조회`() {
        // given
        val title = "title1"

        // when
        val post = PostRetrieveService(mongoTemplate).retrieveWith3Comments(title)

        // then
        assertNotNull(post)
        assertEquals(3, post!!.comments.size)
    }

    @Test
    fun `도큐먼트 별로 1개의 댓글만 조회`() {
        // given
        // when
        val posts = PostRetrieveService(mongoTemplate).retrieveAllWithFirstComment()

        // then
        assertThat(posts[0].comments.size).isEqualTo(1)
        assertThat(posts[1].comments.size).isEqualTo(1)
    }

    @Test
    fun `개별로 조건을 전달한 경우`() {
        // given
        // when
        val posts = PostRetrieveService(mongoTemplate).retrieveCommentByAuthorAndLikeCountGte("taesu", 1)

        // then
        assertThat(posts[0].comments.size).isEqualTo(1)
        // 일치하는 조건 중 아무거나에 걸려 첫 번째 도큐먼트가 반환 됨. author ! = 'taesu'
        assertThat(posts[0].comments[0].author).isEqualTo("lee")
    }

    @Test
    fun `그룹으로 조건을 전달한 경우`() {
        // given
        // when
        val posts = PostRetrieveService(mongoTemplate).retrieveCommentByAuthorAndLikeCountGteWithElemMatch("taesu", 1)

        // then
        assertThat(posts[0].comments.size).isEqualTo(1)
        assertThat(posts[0].comments[0].author).isEqualTo("taesu")
    }
}
