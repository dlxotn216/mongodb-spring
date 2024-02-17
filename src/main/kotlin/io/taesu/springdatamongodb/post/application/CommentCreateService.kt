package io.taesu.springdatamongodb.post.application

import com.mongodb.BasicDBObject
import io.taesu.springdatamongodb.post.domain.Comment
import io.taesu.springdatamongodb.post.interfaces.dtos.CommentCreateRequest
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service


/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class CommentCreateService(
    private val mongoTemplate: MongoTemplate,
) {
    /**
     * Calling update using query: { "_id" : { "$oid" : "65cf56c1e726c806031a6943"}} and
     * update: { "$push" : { "comments" : { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"},
     * "otherComments" : [{ "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}],
     * "queuedComments" : { "$each" : [{ "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}, { "content" : "content", "author" : "author", "_class" : "io.taesu.springdatamongodb.post.domain.Comment"}], "$sort" : -1, "$slice" : 5}}} in collection: posts
     */
    fun create(postId: String, request: CommentCreateRequest) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(postId)))
        }
        val update = Update().apply {
            push("comments", request.toDocument())
            push("otherComments", listOf(request.toDocument(), request.toDocument()))
            push(
                "queuedComments", BasicDBObject("\$each", (1..100).map { request.toDocument() })
                    .append("\$sort", -1)
                    .append("\$slice", 5)
            )
        }
        mongoTemplate.updateFirst(query, update, "posts")
    }

    fun addVotes(postId: String, users: List<String>) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(postId)))
        }
        val update = Update().apply {
            addToSet("votes1", users) // "taesu,lee,taesu,kim"
            addToSet("votes2", BasicDBObject("\$each", users)) // ["taesu", "lee", "kim"]
        }
        mongoTemplate.updateFirst(query, update, "posts")
    }

    /**
     * { $pop: { votes1: -1 } }
     * { $pull: { votes2: "taesu" } }
     */
    fun removeVotes(postId: String, user: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(postId)))
        }
        val update = Update().apply {
            pop("votes1", Update.Position.LAST)      // 배열의 양쪽에서 제거
            pull("votes2", user)                     // 배열에서 특정 값 "모두" 제거
        }
        mongoTemplate.updateFirst(query, update, "posts")
    }

    /**
     * index로 특정 comment에 좋아요 투표 추가
     */
    fun addVotes(postId: String, commentIndex: Int, users: List<String>) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(postId)))
        }
        val update = Update().apply {
            addToSet("comments.$commentIndex.likes", BasicDBObject("\$each", users))
        }
        mongoTemplate.updateFirst(query, update, "posts")
    }

    /**
     * 배열 위치 연산자 $를 사용하여 코멘트 작성자 이름 변경
     * 단, 일치하는 첫 번째만 변경 됨
     */
    fun updateFirstCommentAuthor(postId: String, author: String, newAuthor: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(postId)))
            addCriteria(Criteria.where("comments.author").`is`(author))
        }
        val update = Update().apply {
            set("comments.$.author", newAuthor)
        }
        mongoTemplate.updateFirst(query, update, "posts")
    }

    /**
     * 필터 된 개별 요소를 갱신 함
     */
    fun updateAllCommentAuthor(postId: String, author: String, newAuthor: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(postId)))
            addCriteria(Criteria.where("comments.author").`is`(author))
        }
        val update = Update().apply {
            set("comments.$[elem].author", newAuthor)
            filterArray(Criteria.where("elem.author").`is`(author)) // elem == comment object
        }
        mongoTemplate.updateFirst(query, update, "posts")
    }
}

fun CommentCreateRequest.toDocument() = Comment(
    author = this.author,
    content = this.content
)
