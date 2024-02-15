package io.taesu.springdatamongodb.book.application

import io.taesu.springdatamongodb.book.domain.Book
import io.taesu.springdatamongodb.book.domain.BookRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestPropertySource
import java.time.Year

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
@TestPropertySource(properties = ["logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG"])
class BookDeleteServiceTest {

    @Autowired
    private lateinit var bookDeleteService: BookDeleteService

    @Autowired
    private lateinit var bookRepository: BookRepository

    var saved: Book? = null
    var _id: ObjectId? = null

    @BeforeEach
    fun init() {
        bookRepository.deleteAll()
        this.saved = bookRepository.save(
            Book(
                title = "Spring Data MongoDB",
                author = "Lee Tae Su",
                year = Year.of(2024)
            )
        )
        this._id = saved!!._id
    }

    @Test
    fun `Book 삭제 테스트`() {
        // when
        bookDeleteService.delete(_id.toString())

        // then
        assertNull(bookRepository.findByIdOrNull(_id!!))
    }

    @Test
    fun `MongoTemplate으로 Book 삭제 테스트`() {
        // when
        bookDeleteService.deleteByMongoTemplate(_id.toString())

        // then
        assertNull(bookRepository.findByIdOrNull(_id!!))
    }
}
