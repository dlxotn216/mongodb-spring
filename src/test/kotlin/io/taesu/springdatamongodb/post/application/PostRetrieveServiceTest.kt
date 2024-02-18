package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.Comment
import io.taesu.springdatamongodb.post.domain.Post
import org.junit.jupiter.api.Assertions.*
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
                Post(title = "title1", content = "content1", comments = listOf(
                    Comment("comment1", "taesu"),
                    Comment("comment2", "taesu"),
                    Comment("comment3", "taesu"),
                    Comment("comment4", "taesu"),
                    Comment("comment5", "taesu"),
                )),
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
}
