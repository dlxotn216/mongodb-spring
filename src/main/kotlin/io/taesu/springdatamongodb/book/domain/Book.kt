package io.taesu.springdatamongodb.book.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Year

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("books")
class Book(
    @Id
    val _id: ObjectId = ObjectId(),
    var title: String,
    var author: String,
    val year: Year,
)

interface BookRepository: MongoRepository<Book, ObjectId>
