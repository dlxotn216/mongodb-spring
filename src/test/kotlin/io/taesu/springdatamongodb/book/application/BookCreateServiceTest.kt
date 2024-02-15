package io.taesu.springdatamongodb.book.application

import io.taesu.springdatamongodb.book.domain.BookRepository
import io.taesu.springdatamongodb.book.interfaces.dtos.BookCreateRequest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
class BookCreateServiceTest {
    @Autowired
    private lateinit var bookCreateService: BookCreateService

    @Autowired
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun init() {
        bookRepository.deleteAll()
    }

    @Test
    fun `Book 생성 테스트`() {
        // given
        val request = BookCreateRequest(
            title = "Spring Data MongoDB",
            author = "Lee Tae Su",
            year = Year.of(2024)
        )

        // when
        val book = bookCreateService.crate(request)

        // then
        assertNotNull(book._id)
        assertNotNull(book.createdDate)
        assertNotNull(book.lastModifiedDate)
        assertNotNull(book.version)
    }

    @Test
    fun `MongoTemplate으로 Book 생성 테스트`() {
        // given
        val request = BookCreateRequest(
            title = "Spring Data MongoDB",
            author = "Lee Tae Su",
            year = Year.of(2024)
        )

        // when
        val book = bookCreateService.createByMongoTemplate(request)

        // then
        assertNotNull(book._id)
        assertNotNull(book.createdDate)         // 업데이트와 달리 컨트롤 됨
        assertNotNull(book.lastModifiedDate)    // 업데이트와 달리 컨트롤 됨
        assertNotNull(book.version)             // 업데이트와 달리 컨트롤 됨
    }
}
