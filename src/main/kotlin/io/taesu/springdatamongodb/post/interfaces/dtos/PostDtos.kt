package io.taesu.springdatamongodb.post.interfaces.dtos

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */

class PostCreateRequest(
    val title: String,
    val content: String
)
class CommentCreateRequest(
    val content: String,
    val author: String
)
