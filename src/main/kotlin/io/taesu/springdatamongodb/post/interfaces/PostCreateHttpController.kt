package io.taesu.springdatamongodb.post.interfaces

import io.taesu.springdatamongodb.app.context.HttpRequestContext
import io.taesu.springdatamongodb.app.interfaces.SuccessResponse
import io.taesu.springdatamongodb.post.application.PostCreateService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import io.taesu.springdatamongodb.app.interfaces.SuccessResponse as SuccessResponse1

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@RestController
class PostCreateHttpController(
    private val postCreateService: PostCreateService,
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/posts")
    fun create(
        @RequestBody request: PostCreateHttpRequest,
        context: HttpRequestContext,
    ): SuccessResponse<PostRetrieveResponse> {
        return SuccessResponse1(postCreateService.create(request, context))
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/posts/{id}/comments")
    fun createComment(
        @PathVariable id: String,
        @RequestBody request: PostCommentCreateHttpRequest,
        context: HttpRequestContext,
    ) {
        SuccessResponse1(postCreateService.createComment(id, request, context))
    }
}
