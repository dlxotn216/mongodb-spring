package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.PostRepository
import io.taesu.springdatamongodb.post.interfaces.dtos.CommentCreateRequest
import io.taesu.springdatamongodb.post.interfaces.dtos.PostCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class CommentCreateServiceTest {
    @Autowired
    private lateinit var postCreateService: PostCreateService

    @Autowired
    private lateinit var commentCreateService: CommentCreateService

    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun `코멘트 추가 테스트`() {
        // given
        val post = postCreateService.create(PostCreateRequest("title", "content"))

        // when
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content", "author"))

        // then
        postRepository.findByIdOrNull(post._id).let {
            assertNotNull(it)
            assertEquals(1, it!!.comments.size)
            assertEquals("content", it.comments[0].content)
            assertEquals("author", it.comments[0].author)
        }
    }

    @Test
    fun `Post에 좋아요 투표 추가 테스트`() {
        // given
        val post = postCreateService.create(PostCreateRequest("title2", "content"))

        // when
        commentCreateService.addVotes(
            post._id.toHexString(), listOf(
                "taesu", "lee", "taesu", "kim"
            )
        )

        // then
        postRepository.findByIdOrNull(post._id)!!.let {
            assertNotNull(it)
            assertEquals(1, it.votes1.size)     // "taesu,lee,taesu,kim"
            assertEquals(3, it.votes2.size)     // ["taesu", "lee", "kim"]
            assertThat(it.votes2.toSet()).isEqualTo(setOf("taesu", "lee", "kim"))
        }
    }

    @Test
    fun `Post에 좋아요 투표 제거 테스트`() {
        // given
        val post = postCreateService.create(PostCreateRequest("title2", "content"))
        commentCreateService.addVotes(
            post._id.toHexString(), listOf(
                "taesu", "lee", "taesu", "kim"
            )
        )

        // when
        commentCreateService.removeVotes(post._id.toHexString(), "taesu")

        // then
        postRepository.findByIdOrNull(post._id)!!.let {
            assertNotNull(it)
            assertEquals(0, it.votes1.size)
            assertEquals(2, it.votes2.size)
            assertThat(it.votes2.toSet()).isEqualTo(setOf("lee", "kim"))
        }
    }

    @Test
    fun `Comment에 좋아요 투표 추가 테스트`() {
        // given
        val post = postCreateService.create(PostCreateRequest("title2", "content"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content1", "taesu"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content2", "lee"))

        // when
        commentCreateService.addVotes(post._id.toHexString(), 1, listOf("kim", "choi"))

        // then
        postRepository.findByIdOrNull(post._id)!!.let {
            assertNotNull(it)
            assertThat(it.comments[1].likes.toSet()).isEqualTo(setOf("kim", "choi"))
        }
    }

    @Test
    fun `Comment 작성자 이름 변경 테스트`() {
        // given
        val post = postCreateService.create(PostCreateRequest("title2", "content"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content1", "taesu"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content2", "lee"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content3", "lee"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content4", "taesu"))

        // when
        commentCreateService.updateFirstCommentAuthor(post._id.toHexString(), "taesu", "taesu changed")

        // then
        postRepository.findByIdOrNull(post._id)!!.let {
            assertNotNull(it)
            assertThat(it.comments[0].author).isEqualTo("taesu changed")
            assertThat(it.comments[3].author).isEqualTo("taesu")
        }
    }

    @Test
    fun `Comment 전체 작성자 이름 변경 테스트`() {
        // given
        val post = postCreateService.create(PostCreateRequest("title2", "content"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content1", "taesu"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content2", "lee"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content3", "lee"))
        commentCreateService.create(post._id.toHexString(), CommentCreateRequest("content4", "taesu"))

        // when
        commentCreateService.updateAllCommentAuthor(post._id.toHexString(), "taesu", "taesu changed")

        // then
        postRepository.findByIdOrNull(post._id)!!.let {
            assertNotNull(it)
            assertThat(it.comments[0].author).isEqualTo("taesu changed")
            assertThat(it.comments[3].author).isEqualTo("taesu changed")
        }
    }
}
