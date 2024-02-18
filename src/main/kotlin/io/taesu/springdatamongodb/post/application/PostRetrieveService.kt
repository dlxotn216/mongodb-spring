package io.taesu.springdatamongodb.post.application

import io.taesu.springdatamongodb.post.domain.Post
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.ArrayOperators
import org.springframework.data.mongodb.core.aggregation.TypedAggregation
import org.springframework.data.mongodb.core.query.Criteria
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
}

class PostWithComments(
    val _id: ObjectId = ObjectId(),
    val title: String,
    val comments: List<String>
)
