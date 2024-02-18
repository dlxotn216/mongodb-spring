package io.taesu.springdatamongodb.user.application

import io.taesu.springdatamongodb.user.document.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class UserRetrieveService(
    private val mongoTemplate: MongoTemplate,
) {
    fun findByName(name: String): List<User> {
        return mongoTemplate.find(
            query(
                Criteria.where("name").`is`(name)
            ),
            User::class.java
        )
    }

    fun findByAge(age: Int): List<User> {
        return mongoTemplate.find(
            query(
                Criteria.where("age").`is`(age)
            ),
            User::class.java
        )
    }

    fun findByNameAge(name: String, age: Int): List<User> {
        return mongoTemplate.find(
            query(
                Criteria.where("age").`is`(age)
                    .and("name").`is`(name)
            ),
            User::class.java
        )
    }

    fun findWithProjection(): MutableList<UserIdName> {
        return mongoTemplate.find(
            Query().apply {
                // find using query: {} fields: Document{{name=1, _id=1}}
                // _id는 항상 반환한다.
                this.fields().include("name")
                // .exclude("_id") 제외하고 싶으면 명시적으로 제외해야 함

                // 없으면 다 들고와서 전달한 클래스에 매핑 함
                // find using query: {} fields: Document{{}}
            },
            UserIdName::class.java,
            "users"
        )
    }

    fun findWithRange(joinedAt: LocalDateTime): MutableList<User> {
        // { "joinedAt" : { "$gte" : { "$date" : "2020-12-31T15:00:00Z"}}}
        return mongoTemplate.find(
            query(
                Criteria.where("joinedAt").gte(joinedAt)
            ),
            User::class.java
        )
    }

    fun findNe(age: Int): MutableList<User> {
        return mongoTemplate.find(
            query(
                Criteria.where("age").ne(age)
            ),
            User::class.java
        )
    }

    fun findByAgeIn(age: List<Int>): MutableList<User> {
        // { "age" : { "$in" : [20, 22]}, "name" : { "$not" : { "$in" : ["yoon"]}}, "$or" : [{ "name" : "kim"}]}
        Criteria().andOperator(
            Criteria.where("age").`in`(age),
            Criteria.where("name").not().`in`(listOf("yoon"))
        ).orOperator(
            Criteria.where("name").`is`("kim")
        )
        return mongoTemplate.find(
            query(
                // { "$or" : [{ "name" : "kim"}, { "$and" : [{ "age" : { "$in" : [20, 22]}}, { "name" : { "$not" : { "$in" : ["yoon"]}}}]}]}
                Criteria().orOperator(
                    Criteria.where("name").`is`("kim"),
                    Criteria().andOperator(
                        Criteria.where("age").`in`(age),
                        Criteria.where("name").not().`in`(listOf("yoon"))
                    )
                )
            ),
            User::class.java
        )
    }

    fun retrieveAddressIsNullOrNotSet(): MutableList<User> {
        // { "address" : null}
        // null이거나 없는 경우
        return mongoTemplate.find(
            query(
                Criteria.where("address").isNull // .`is`(null) 도 가능
            ),
            User::class.java
        )
    }

    fun retrieveAddressIsNull(): MutableList<User> {
        // { "address" : null}
        return mongoTemplate.find(
            query(
                Criteria.where("address").isNullValue
            ),
            User::class.java
        )
    }
}

data class UserIdName(
    val id: ObjectId,
    val name: String,
)
