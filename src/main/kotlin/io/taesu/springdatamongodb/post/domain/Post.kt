package io.taesu.springdatamongodb.post.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("posts")
class Post(
    @Id
    val _id: ObjectId = ObjectId(),
    var title: String,
    var content: String,
    val comments: List<Comment> = listOf(),
    val votes1: List<String> = listOf(),
    val votes2: List<String> = listOf(),
) {
}

class Comment(
    val content: String,
    val author: String,
    val likes: List<String> = listOf(),
)

interface PostRepository: MongoRepository<Post, ObjectId>
