package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.Post
import io.taesu.springdatamongodb.post.interfaces.dtos.PostCreateRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class PostCreateService(
    private val mongoTemplate: MongoTemplate,
) {
    fun create(request: PostCreateRequest): Post {
        return mongoTemplate.insert(request.toDocument(), "posts")
    }
}

fun PostCreateRequest.toDocument() = Post(
    title = this.title,
    content = this.content
)
