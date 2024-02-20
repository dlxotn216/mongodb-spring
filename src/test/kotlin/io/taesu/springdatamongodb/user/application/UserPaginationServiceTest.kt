package io.taesu.springdatamongodb.user.application

import io.taesu.springdatamongodb.user.document.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Sort
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
class UserPaginationServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var userPaginationService: UserPaginationService

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(User::class.java)
        mongoTemplate.insertAll(
            (1..1000).map {
                User(
                    name = "taesu$it",
                    age = 20 + it,
                    joinedAt = LocalDateTime.of(2021, 1, 1, 0, 0, 0).plusDays(it.toLong())
                )
            }
        )
    }

    @Test
    fun `skip, limit`() {
        // given, when
        val users = userPaginationService.findUsers(2, 10)

        // then
        assertThat(users.size).isEqualTo(10)
        assertThat(users[0].name).isEqualTo("taesu11")
    }

    @Test
    fun `skip, limit with sort`() {
        // given, when
        val users = userPaginationService.findUsersWithSort(2, 10, Sort.by(Sort.Order.desc("age")))

        // then
        assertThat(users.size).isEqualTo(10)
        assertThat(users[0].name).isEqualTo("taesu990")
    }

    @Test
    fun `nooffset`() {
        val users = userPaginationService.findUsersWithNoOffset(10)
        assertThat(users.size).isEqualTo(10)

        userPaginationService.findUsersWithNoOffset(10, users.last().name).also {
            val page2Users = userPaginationService.findUsersWithSort(2, 10, Sort.by(Sort.Order.desc("name")))

            assertThat(it.size).isEqualTo(10)
            assertThat(it[0].name).isEqualTo(page2Users.first().name)
        }
    }

}
