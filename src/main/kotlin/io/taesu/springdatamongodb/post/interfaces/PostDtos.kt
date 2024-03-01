package io.taesu.springdatamongodb.post.interfaces

import io.taesu.springdatamongodb.post.domain.vo.Author
import io.taesu.springdatamongodb.post.domain.vo.Comments
import org.bson.types.ObjectId

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
class PostCreateHttpRequest(
    val title: String,
    val type: String,
    val content: String,
    val hashTags: List<HashTagDto> = emptyList(),
    val mentions: List<MentionDto> = emptyList(),
)

class MentionDto(
    val label: String,
    val targetId: ObjectId,
    val targetType: String,
)

class HashTagDto(
    val label: String,
)

class PostRetrieveResponse(
    val id: ObjectId,
    val title: String,
    val type: String,
    val content: String,
    val author: Author,
    val hashTags: List<HashTagDto>,
    val mentions: List<MentionDto>,
    val comments: Comments,
    val createdAt: String,
    val modifiedAt: String,
) {
    class Author(
        val id: ObjectId,
        val name: String,
    )
}
