package io.taesu.springdatamongodb.numbers.application

import io.taesu.springdatamongodb.numbers.Numbers
import io.taesu.springdatamongodb.user.application.UserRetrieveService
import io.taesu.springdatamongodb.user.document.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime

/**
 * Created by itaesu on 2024/02/18.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class NumberRetrieveServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var numberRetrieveService: NumberRetrieveService

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(Numbers::class.java)
        mongoTemplate.insertAll(
            listOf(
                Numbers(x = listOf(5)),
                Numbers(x = listOf(15)),
                Numbers(x = listOf(25)),
                Numbers(x = 12),
                Numbers(x = listOf(5, 25)),
                Numbers(x = listOf(1235, 35)),
                Numbers(x = listOf(5, 35)),
            )
        )
    }

    @Test
    fun `Range 검색`() {
        // given
        // when
        val numbers = numberRetrieveService.findNumbers(10, 20)
        // 모든 요소가 조건에 하나라도 걸리면 나온다
        // (15) 15는 10보다 크고 20보다 작아서 포함
        // 12 12는 10보다 크고 20보다 작아서 포함
        // (5, 25) 5는 20보다 작아서 포함, 25는 10보다 크니 포함
        // (5, 35) 5는 20보다 작아서 포함, 35는 10보다 크니 포함

        // then
        assertEquals(4, numbers.size)
    }

    @Test
    fun `모든 검색 조건에 만족하는 Range 검색`() {
        // given
        // when
        val numbers = numberRetrieveService.findNumbersCompareAllCondition(10, 20)
        // 모든 요소가 조건에 다 일치해야 반환.
        // (15) 15는 10보다 크고 20보다 작아서 포함
        // 12는 배열이 아니므로 쿼리하지 않음

        // then
        assertEquals(1, numbers.size)
    }

    @Test
    fun `인덱스 활용을 위한 min, max를 사용한 검색`() {
        // given
        // when
        val numbers = numberRetrieveService.findNumbersWithMinMax(10, 20)
        // 모든 요소가 조건에 다 일치해야 반환.
        // (15) 15는 10보다 크고 20보다 작아서 포함
        // 12는 배열이 아니므로 쿼리하지 않음

        // then
        assertEquals(1, numbers.size)
    }
}
