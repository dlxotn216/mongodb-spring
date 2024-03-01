package io.taesu.springdatamongodb.post.interfaces

import io.taesu.springdatamongodb.app.interfaces.SuccessResponse
import io.taesu.springdatamongodb.post.application.PostRetrieveService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@RestController
class PostRetrieveHttpController(private val postRetrieveService: PostRetrieveService) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/posts/{id}")
    fun retrieve(@PathVariable id: ObjectId) = SuccessResponse(
        postRetrieveService.retrieve(id)
    )
}
