package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.app.context.HttpRequestContext
import io.taesu.springdatamongodb.post.domain.Post
import io.taesu.springdatamongodb.post.domain.PostRepository
import io.taesu.springdatamongodb.post.domain.type.PostCategory
import io.taesu.springdatamongodb.post.domain.vo.*
import io.taesu.springdatamongodb.post.interfaces.PostCommentCreateHttpRequest
import io.taesu.springdatamongodb.post.interfaces.PostCreateHttpRequest
import io.taesu.springdatamongodb.post.interfaces.PostRetrieveResponse
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class PostCreateService(
    private val postRepository: PostRepository,
    private val mongoTemplate: MongoTemplate,
) {
    fun create(
        request: PostCreateHttpRequest,
        context: HttpRequestContext,
    ): PostRetrieveResponse {
        return toPostDocument(request, context)
            .let(postRepository::insert)
            .let(Post::toResponse)
    }

    private fun toPostDocument(
        request: PostCreateHttpRequest,
        context: HttpRequestContext,
    ) = Post(
        category = PostCategory.valueOf(request.type),
        title = request.title,
        content = PostContent(request.content),
        author = Author(context.contextUser.id, context.contextUser.name)
    ).apply {
        request.mentions.forEach {
            this.addMention(Mention(it.label, it.targetId, it.targetType))
        }
        request.hashTags.forEach {
            this.addHashTag(HashTag(it.label))
        }
    }

    fun createComment(
        postId: String,
        request: PostCommentCreateHttpRequest,
        context: HttpRequestContext,
    ) {
        mongoTemplate.updateFirst(
            Query().apply {
                Criteria().andOperator(
                    Post::postId isEqualTo postId
                )
            },
            Update().apply {
                this.push(
                    Comments::comments.name,
                    Comment(
                        content = PostContent(request.content),
                        author = Author(context.contextUser.id, context.contextUser.name)
                    ).apply {
                        val now = Instant.now()
                        this.createdAtMillis = now
                        this.modifiedAtMillis = now
                    }
                )
                this.inc(Comments::commentsCount.name)
            },
            Post::class.java
        )
    }
}
