package io.taesu.springdatamongodb.post.domain.vo

import io.taesu.springdatamongodb.app.domain.Auditable
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Unwrapped
import java.time.Instant

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
class Comments(
    val comments: List<Comment> = emptyList(),
    val commentsCount: Int = comments.size,
)

class Comment(
    val commentId: ObjectId = ObjectId(),
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    var content: PostContent,
    val author: Author,
): Auditable(
    createdBy = author.id,
    modifiedBy = author.id,
) {
    override fun getId(): ObjectId = commentId
}
