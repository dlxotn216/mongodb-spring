package io.taesu.springdatamongodb.book.application

import io.taesu.springdatamongodb.book.domain.BookRepository
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class BookDeleteService(
    private val bookRepository: BookRepository,
    private val mongoTemplate: MongoTemplate,
) {
    fun delete(id: String) = bookRepository.deleteById(ObjectId(id))

    fun deleteByMongoTemplate(id: String) {
        Query().apply {
            addCriteria(Criteria.where("_id").`is`(ObjectId(id)))
        }.let {
            mongoTemplate.remove(it, "books")
        }
    }
}
