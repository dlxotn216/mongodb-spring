package io.taesu.springdatamongodb.book.application

import io.taesu.springdatamongodb.book.domain.Book
import io.taesu.springdatamongodb.book.domain.BookRepository
import io.taesu.springdatamongodb.book.interfaces.dtos.BookCreateRequest
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class BookCreateService(
    private val bookRepository: BookRepository,
    private val mongoTemplate: MongoTemplate,
) {
    fun crate(request: BookCreateRequest): Book {
        return bookRepository.insert(request.toEntity())
    }

    fun createByMongoTemplate(request: BookCreateRequest): Book {
        return mongoTemplate.insert(request.toEntity(), "books")
    }
}
