package io.taesu.springdatamongodb.transaction.application

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by taesu on 2024/02/22.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class TestTransaction(private val mongoTemplate: MongoTemplate) {
    @Transactional
    fun save() {
        mongoTemplate.dropCollection("groups")
        mongoTemplate.dropCollection("group_users")
        val user = mongoTemplate.insert(GroupUser(name = "lee"))
        mongoTemplate.insert(Group(name = "Dev", userIds = listOf(user._id)))
    }

    @Transactional
    fun throwException() {
        mongoTemplate.dropCollection("groups")
        mongoTemplate.dropCollection("group_users")
        val user = mongoTemplate.insert(GroupUser(name = "lee"))
        mongoTemplate.insert(Group(name = "Dev", userIds = listOf(user._id)))

        if (1 == 1) {
            throw IllegalStateException("test")
        }
    }
}

@Document("groups")
class Group(
    val _id: ObjectId = ObjectId(),
    val name: String,
    val userIds: List<ObjectId>
)

@Document("group_users")
class GroupUser(
    val _id: ObjectId = ObjectId(),
    val name: String,
)
