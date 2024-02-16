package io.taesu.springdatamongodb.book.application

import io.taesu.springdatamongodb.book.domain.Book
import io.taesu.springdatamongodb.book.domain.BookRepository
import io.taesu.springdatamongodb.book.interfaces.dtos.BookUpdateRequest
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestPropertySource
import java.time.Year
import java.time.temporal.ChronoUnit

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
@TestPropertySource(properties = ["logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG"])
class BookUpdateServiceTest {

    @Autowired
    private lateinit var bookUpdateService: BookUpdateService

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

    /*
     * { "_id" : { "$oid" : "65ce018adc540c4e631337cb"}, "version" : 0} and update: { "title" : "Spring Data MongoDB changed", "author" : "Lee Tae Su author", "year" : 2021, "createdDate" : { "$date" : "2024-02-15T12:20:26.015Z"}, "lastModifiedDate" : { "$date" : "2024-02-15T12:20:26.024Z"}, "version" : 1, "_class" : "io.taesu.springdatamongodb.book.domain.Book"}
     */
    @Test
    fun `Book 수정 테스트`() {
        // when
        bookUpdateService.update(
            _id.toString(),
            BookUpdateRequest(
                title = "Spring Data MongoDB changed",
                author = "Lee Tae Su author",
                year = Year.of(2021)
            )
        )

        // then
        bookRepository.findByIdOrNull(_id!!)?.let {
            assert(it._id == _id)
            assert(it.title == "Spring Data MongoDB changed")
            assert(it.author == "Lee Tae Su author")
            assert(it.year == Year.of(2021))
            assert(it.lastModifiedDate != saved!!.lastModifiedDate!!.truncatedTo(ChronoUnit.MILLIS))
            assert(it.version != saved!!.version)
        }
    }

    @Test
    fun `Book 변경없이 수정  테스트`() {
        // when
        bookUpdateService.update(
            _id.toString(),
            BookUpdateRequest(
                title = saved!!.title,
                author = saved!!.author,
                year = saved!!.year
            )
        )

        // then
        bookRepository.findByIdOrNull(_id!!)?.let {
            assert(it._id == _id)
            assert(it.title == saved!!.title)
            assert(it.author == saved!!.author)
            assert(it.year == saved!!.year)
            assert(it.lastModifiedDate != saved!!.lastModifiedDate!!.truncatedTo(ChronoUnit.MILLIS))
            assert(it.version != saved!!.version)
        }
    }

    /*
     * { "_id" : { "$oid" : "65ce018adc540c4e631337cd"}} and update: { "$set" : { "title" : "Spring Data MongoDB changed", "author" : "Lee Tae Su author", "year" : 2021}}
     */
    @Test
    fun `MongoTemplate으로 Book 수정 테스트`() {
        bookUpdateService.updateByMongoTemplate(
            _id.toString(),
            BookUpdateRequest(
                title = "Spring Data MongoDB changed",
                author = "Lee Tae Su author",
                year = Year.of(2021)
            )
        )

        bookRepository.findByIdOrNull(_id!!)?.let {
            assert(it._id == _id)
            assert(it.title == "Spring Data MongoDB changed")
            assert(it.author == "Lee Tae Su author")
            assert(it.year == Year.of(2021))
            assert(it.lastModifiedDate == saved!!.lastModifiedDate!!.truncatedTo(ChronoUnit.MILLIS)) // mongodb의 경우 날짜는 밀리초까지 정밀도를 가지고 있음. 따라서 나노초가 절삭 됨
            assert(it.version == saved!!.version)
        }
    }

    /**
     *  "_id" : { "$oid" : "65ce0189dc540c4e631337ca"}} and update: { "$set" : { "title" : "Spring Data MongoDB changed"}}
     */
    @Test
    fun `MongoTemplate으로 Book 부분 수정 테스트`() {
        bookUpdateService.updateTitleByMongoTemplate(
            _id.toString(),
            "Spring Data MongoDB changed",
        )

        bookRepository.findByIdOrNull(_id!!)?.let {
            assert(it._id == _id)
            assert(it.title == "Spring Data MongoDB changed")
            assert(it.author == "Lee Tae Su")
            assert(it.year == Year.of(2025))
            assert(it.createdDate == saved!!.createdDate!!.truncatedTo(ChronoUnit.MILLIS)) // 생성 시간이 변경되지 않음
            assert(it.lastModifiedDate == saved!!.lastModifiedDate!!.truncatedTo(ChronoUnit.MILLIS))  // 업데이트 시간이 변경되지 않음. saved가 더 정밀한 숫자 표현임
            assert(it.version == saved!!.version)
        }
    }
}
