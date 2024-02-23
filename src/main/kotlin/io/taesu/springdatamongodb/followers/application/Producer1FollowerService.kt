package io.taesu.springdatamongodb.followers.application

import io.taesu.springdatamongodb.followers.document.Activity
import io.taesu.springdatamongodb.followers.document.Producer1
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/23.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class Producer1FollowerService(
    private val mongoTemplate: MongoTemplate,
) {
    fun retrieveActivities(username: String): List<Activity> {
        val user = mongoTemplate.findOne(
            Query().apply {
                this.addCriteria(Criteria.where("username").`is`(username))
            },
            Producer1::class.java
        ) ?: return emptyList()

        return mongoTemplate.find(Query().apply {
            addCriteria(Criteria.where("userId").`in`(user.following))
        }, Activity::class.java)
    }

    fun retrieveFollowers(username: String): List<Producer1> {
        val user = mongoTemplate.findOne(
            Query().apply {
                this.addCriteria(Criteria.where("username").`is`(username))
            },
            Producer1::class.java
        ) ?: return emptyList()

        return mongoTemplate.find(
            Query().apply {
                this.addCriteria(Criteria.where("following").`is`(user._id))
            },
            Producer1::class.java
        )
    }
}

