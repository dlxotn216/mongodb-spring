package io.taesu.springdatamongodb.post.domain

import io.taesu.springdatamongodb.app.domain.Auditable
import io.taesu.springdatamongodb.post.domain.type.PostCategory
import io.taesu.springdatamongodb.post.domain.vo.*
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.Unwrapped
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("posts")
class Post(
    @Id
    @Field("id")
    val postId: ObjectId = ObjectId(),
    val category: PostCategory,
    var title: String,
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_NULL)
    var content: PostContent,
    val author: Author,
): Auditable() {
    override fun getId(): ObjectId = postId

    val contentValue get() = content.value

    @Field("mentions")
    private var _mentions: MutableList<Mention> = mutableListOf()
    val mentions get() = _mentions.toList()

    @Field("hashTags")
    private var _hashTags: MutableList<HashTag> = mutableListOf()
    val hashTags get() = _hashTags.toList()

    @Field("comments")
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    private var _comments: Comments = Comments()
    val comments: Comments get() = _comments

    fun addMention(mention: Mention) {
        this._mentions += mention
    }

    fun addHashTag(hashTag: HashTag) {
        _hashTags += hashTag
    }
}

interface PostRepository: MongoRepository<Post, ObjectId>
