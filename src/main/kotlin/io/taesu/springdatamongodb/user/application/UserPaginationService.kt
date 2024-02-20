package io.taesu.springdatamongodb.user.application

import io.taesu.springdatamongodb.user.document.User
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/18.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class UserPaginationService(
    private val mongoTemplate: MongoTemplate,
) {
    fun findUsers(page: Int, size: Int): MutableList<User> {
        return mongoTemplate.find(
            Query().skip(((page - 1) * size).toLong()).limit(size),
            User::class.java
        )
    }

    fun findUsersWithSort(
        page: Int,
        size: Int,
        sort: Sort,
    ): MutableList<User> {
        return mongoTemplate.find(
            Query().skip(((page - 1) * size).toLong()).limit(size).with(sort),
            User::class.java
        )
    }

    fun findUsersWithNoOffset(
        size: Int,
        lastKey: String? = null,
    ): MutableList<User> {
        return mongoTemplate.find(
            Query().apply {
                if (lastKey != null) {
                    addCriteria(Criteria.where("name").lt(lastKey))
                }
            }.limit(size).with(Sort.by(Sort.Order.desc("name"))),
            User::class.java
        )
    }
}
