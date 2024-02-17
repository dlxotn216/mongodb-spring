package io.taesu.springdatamongodb.website.application

import io.taesu.springdatamongodb.website.document.WebSite
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
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
class WebsiteVisitCountIncrService(private val mongoTemplate: MongoTemplate) {
    fun incrementVisitorsCount(url: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("url").`is`(url))
        }
        val update = Update().apply {
            inc("visitorsCount")
        }
        mongoTemplate.upsert(query, update, WebSite::class.java)
    }

    fun createWithSetOnInsert(url: String, createBy: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("url").`is`(url))
        }
        val update = Update().apply {
            inc("visitorsCount")
            setOnInsert("createdBy", createBy)
            setOnInsert("createdAt", LocalDateTime.now())
        }
        mongoTemplate.upsert(query, update, WebSite::class.java)
    }

    fun setCreateByMany(createBy: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("createBy").isNull)
        }
        val update = Update().apply {
            set("createdBy", createBy)
        }
        mongoTemplate.updateMulti(query, update, WebSite::class.java)
    }

}
