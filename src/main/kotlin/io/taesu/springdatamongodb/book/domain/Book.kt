package io.taesu.springdatamongodb.book.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime
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
    var year: Year,

    @CreatedDate
    val createdDate: LocalDateTime? = null,

    @LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,

    // 없으면 @CreatedDate 설정이 안됨
    // 조회 후 수정하는 형태로 해야됨. 조회가 아까우니 MongoTemplate으로?
    @Version
    var version: Long? = null,
) {
    fun update(author: String, title: String, year: Year) {
        this.author = author
        this.title = title
        this.year = year
    }
}

interface BookRepository: MongoRepository<Book, ObjectId>
