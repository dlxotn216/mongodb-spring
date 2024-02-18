package io.taesu.springdatamongodb.fruit.application

import io.taesu.springdatamongodb.fruit.document.Fruit
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class FriutRetrieveService(private val mongoTemplate: MongoTemplate) {
    fun retrieveIsAll(targets: List<String>): MutableList<Fruit> {
        // { "fruits" : { "$all" : ["apple", "banana", "orange"]}}
        return mongoTemplate.find(
            query(
                Criteria.where("fruits").all(targets)
            ),
            Fruit::class.java
        )
    }

    fun retrieveIndexEq(targetMap: Map<Int, String>): MutableList<Fruit> {
        // { "fruits.0" : "apple", "fruits.2" : "orange"}
        val criteria = Criteria().apply {
            targetMap.forEach { (index, keyword) ->
                and("fruits.$index").`is`(keyword)
            }
        }
        return mongoTemplate.find(
            query(
                criteria
            ),
            Fruit::class.java
        )
    }
}
