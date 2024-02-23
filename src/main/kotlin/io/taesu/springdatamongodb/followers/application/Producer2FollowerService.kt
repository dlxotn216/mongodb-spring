package io.taesu.springdatamongodb.followers.application

import io.taesu.springdatamongodb.followers.document.Activity
import io.taesu.springdatamongodb.followers.document.Producer1
import io.taesu.springdatamongodb.followers.document.Producer2
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class Producer2FollowerService(
    private val mongoTemplate: MongoTemplate,
) {
    fun retrieveActivities(username: String): List<Activity> {
        val user = mongoTemplate.findOne(
            Query().apply {
                this.addCriteria(Criteria.where("username").`is`(username))
            },
            Producer2::class.java
        ) ?: return emptyList()

        val followings = mongoTemplate.find(
            Query().apply {
                this.addCriteria(Criteria.where("followers").`is`(user._id))
            },
            Producer2::class.java
        ).map { it._id }.takeIf { it.isNotEmpty() } ?: return emptyList()

        return mongoTemplate.find(Query().apply {
            addCriteria(Criteria.where("userId").`in`(followings))
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
