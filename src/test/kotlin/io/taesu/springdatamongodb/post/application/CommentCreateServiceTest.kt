package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.PostRepository
import io.taesu.springdatamongodb.post.interfaces.dtos.CommentCreateRequest
import io.taesu.springdatamongodb.post.interfaces.dtos.PostCreateRequest
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
}
