package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.app.extensions.findById
import io.taesu.springdatamongodb.app.extensions.findByIdNotNull
import io.taesu.springdatamongodb.app.extensions.toResponse
import io.taesu.springdatamongodb.post.domain.Post
import io.taesu.springdatamongodb.post.domain.PostRepository
import io.taesu.springdatamongodb.post.interfaces.HashTagDto
import io.taesu.springdatamongodb.post.interfaces.MentionDto
import io.taesu.springdatamongodb.post.interfaces.PostRetrieveResponse
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class PostRetrieveService(val postRepository: PostRepository) {
    fun retrieve(id: ObjectId): PostRetrieveResponse {
        return postRepository.findByIdNotNull(id).toResponse()
    }
}

fun Post.toResponse() = PostRetrieveResponse(
    id = this.id,
    title = this.title,
    type = this.category.name,
    content = this.contentValue,
    author = PostRetrieveResponse.Author(
        id = this.author.id,
        name = this.author.displayName
    ),
    comments = this.comments,
    hashTags = this.hashTags.map { hashTag -> HashTagDto(hashTag.label) },
    mentions = this.mentions.map { mention ->
        MentionDto(
            label = mention.label,
            targetId = mention.targetId,
            targetType = mention.targetType
        )
    },
    createdAt = this.createdAt.toResponse(),
    modifiedAt = this.modifiedAt.toResponse(),
)
