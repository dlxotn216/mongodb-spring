package io.taesu.springdatamongodb.numbers.application

import io.taesu.springdatamongodb.numbers.Numbers
import org.bson.Document
import org.springframework.data.domain.Pageable
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
class NumberRetrieveService(private val mongoTemplate: MongoTemplate) {
    fun findNumbers(start: Int, end: Int): MutableList<Numbers> {
        // { "x" : { "$gt" : 10, "$lt" : 20}}
        return mongoTemplate.find(
            Query().apply {
                this.addCriteria(Criteria.where("x").gt(start).lt(end))
            },
            Numbers::class.java
        )
    }

    fun findNumbersCompareAllCondition(start: Int, end: Int): MutableList<Numbers> {
        // { "x" : { "$elemMatch" : { "$gt" : 10, "$lt" : 20}}
        return mongoTemplate.find(
            Query().apply {
                this.addCriteria(Criteria("x").elemMatch(Criteria().gt(start).lt(end)))
            },
            Numbers::class.java
        )
    }

    fun findNumbersWithMinMax(start: Int, end: Int): MutableList<Numbers> {
        // db.numbers.createIndex({"x": 1})
        // db.numbers.find({"x": {"$gt": 10, "$lt": 20}}).min({"x": 10}).max({"x": 20}).hint({x: 1})
        return mongoTemplate.find(
            Query().apply {
                addCriteria(Criteria.where("x").gt(start).lt(end))
                    .withHint("{x: 1}")
            },
            Numbers::class.java
        )
    }
}
