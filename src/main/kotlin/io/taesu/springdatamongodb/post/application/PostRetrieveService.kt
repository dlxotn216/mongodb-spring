package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.Post
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.ArrayOperators
import org.springframework.data.mongodb.core.aggregation.TypedAggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service


/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class PostRetrieveService(
    private val mongoTemplate: MongoTemplate,
) {
    fun retrieveWith3Comments(title: String): Post? {
        // [{ "$match" : { "title" : "title1"}}, { "$project" : { "title" : 1, "content" : 1, "comments" : { "$slice" : ["$comments", 3]}}}]
        val aggregation: TypedAggregation<Post> = Aggregation.newAggregation(
            Post::class.java,
            Aggregation.match(Criteria.where("title").`is`(title)),
            Aggregation.project()
                .andInclude("title", "content")
                .and(ArrayOperators.Slice.sliceArrayOf("comments").itemCount(3)).`as`("comments")
        )

        return mongoTemplate.aggregate(aggregation, Post::class.java).uniqueMappedResult
    }

    fun retrieveAllWithFirstComment(): MutableList<PostWithComments> {
        /*
            db.posts.find({
                'comments.author': 'taesu'
            }, {
                'comments.$': 1
            })
         */
        return mongoTemplate.find(
            Query().apply {
                this.addCriteria(Criteria.where("comments.author").`is`("taesu"))

                // 일치하는 배열 요소의 반환이므로 반드시 배열에 적용한 위 조건이 있어야 함
                this.fields().include("title", "comments.$")
            }, PostWithComments::class.java, "posts"
        )
    }

    fun retrieveCommentByAuthorAndLikeCountGte(author: String, likeCount: Int): MutableList<PostWithComments> {
        // { "comments.author" : "taesu", "comments.likeCount" : { "$gte" : 1}}
        return mongoTemplate.find(
            Query().apply {
                this.addCriteria(
                    Criteria.where("comments.author").`is`(author)
                        .and("comments.likeCount").gte(likeCount)
                )

                this.fields().include("title", "comments.$")
            }, PostWithComments::class.java, "posts"
        )
    }

    fun retrieveCommentByAuthorAndLikeCountGteWithElemMatch(
        author: String,
        likeCount: Int,
    ): MutableList<PostWithComments> {
        // { "comments" : { "$elemMatch" : { "author" : "taesu", "likeCount" : { "$gte" : 1}}}}
        return mongoTemplate.find(
            Query().apply {
                this.addCriteria(
                    Criteria.where("comments").elemMatch(
                        Criteria.where("author").`is`(author)
                            .and("likeCount").gte(likeCount)
                    )
                )

                this.fields().include("title", "comments.$")
            }, PostWithComments::class.java, "posts"
        )
    }
}

class PostWithComments(
    val _id: ObjectId = ObjectId(),
    val title: String,
    val comments: List<Comment>,
) {
    class Comment(
        val content: String,
        val author: String,
    )
}
