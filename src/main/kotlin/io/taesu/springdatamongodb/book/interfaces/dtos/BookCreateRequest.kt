package io.taesu.springdatamongodb.book.interfaces.dtos

import io.taesu.springdatamongodb.book.domain.Book
import java.time.Year

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
class BookCreateRequest(
    val title: String,
    val author: String,
    val year: Year,
) {
    fun toEntity(): Book {
        return Book(
            title = title,
            author = author,
            year = year
        )
    }
}
class BookUpdateRequest(
    val title: String,
    val author: String,
    val year: Year,
)
