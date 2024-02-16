package io.taesu.springdatamongodb.book.application

import io.taesu.springdatamongodb.book.domain.BookRepository
import io.taesu.springdatamongodb.book.interfaces.dtos.BookUpdateRequest
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class BookUpdateService(
    private val bookRepository: BookRepository,
    private val mongoTemplate: MongoTemplate,
) {
    fun update(id: String, request: BookUpdateRequest) {
        request.run {
            bookRepository.findByIdOrNull(ObjectId(id))?.let {
                it.update(
                    author = author,
                    title = title,
                    year = year,
                )
                bookRepository.save(it)
            }
        }
    }

    fun updateByMongoTemplate(id: String, request: BookUpdateRequest) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(id)))
        }
        val update = Update().apply {
            set("title", request.title)
            set("author", request.author)
            set("year", request.year)
        }

        mongoTemplate.updateFirst(query, update, "books")
    }

    fun updateTitleByMongoTemplate(id: String, title: String) {
        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(id)))
        }
        val update = Update().apply {
            set("title", title)
            inc("year", 1)
            set("test.user", "taesu")   // 새 필드가 생성되기도 함
            setOnInsert("createdDate", "2024-02-15T12:20:26.015Z")
        }

        mongoTemplate.updateFirst(query, update, "books")
    }
}
