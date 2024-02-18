package io.taesu.springdatamongodb.user.application

import io.taesu.springdatamongodb.user.document.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import java.time.LocalDateTime

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class UserRetrieveServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var userRetrieveService: UserRetrieveService

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(User::class.java)
        mongoTemplate.insertAll(
            listOf(
                User(name = "taesu", age = 20, joinedAt = LocalDateTime.of(2021, 1, 1, 0, 0, 0)),
                User(name = "lee", age = 22, joinedAt = LocalDateTime.of(2022, 1, 1, 0, 0, 0)),
                User(name = "kim", age = 52, address = null, joinedAt = LocalDateTime.of(2023, 1, 1, 0, 0, 0)),
            )
        )
    }

    @Test
    fun `이름으로 찾기`() {
        // given
        val name = "taesu"

        // when
        val users = userRetrieveService.findByName(name)

        // then
        assertEquals(1, users.size)
        assertEquals(name, users[0].name)
    }

    @Test
    fun `나이로 찾기`() {
        // given
        val age = 22

        // when
        val users = userRetrieveService.findByAge(age)

        // then
        assertEquals(1, users.size)
        assertEquals(age, users[0].age)
    }

    @Test
    fun `복합조건으로 찾기`() {
        // given
        val age = 22
        val name = "lee"

        // when
        val users = userRetrieveService.findByNameAge(name, age)

        // then
        assertEquals(1, users.size)
        assertEquals(age, users[0].age)
        assertEquals(name, users[0].name)
    }

    @Test
    fun `Projection으로 찾기`() {
        // given
        // when
        val users = userRetrieveService.findWithProjection()

        // then
        assertEquals(3, users.size)
        assertEquals("taesu", users[0].name)
        assertEquals("lee", users[1].name)
        assertEquals("kim", users[2].name)
    }

    @Test
    fun `범위로 찾기`() {
        // given
        // when
        val users = userRetrieveService.findWithRange(LocalDateTime.of(2021, 1, 1, 0, 0, 0))

        // then
        assertEquals(3, users.size)
        assertEquals("taesu", users[0].name)
        assertEquals("lee", users[1].name)
        assertEquals("kim", users[2].name)
    }

    @Test
    fun `Ne로 찾기`() {
        // given
        // when
        val users = userRetrieveService.findNe(22)

        // then
        assertEquals(2, users.size)
        assertEquals("taesu", users[0].name)
        assertEquals("kim", users[1].name)
    }

    @Test
    fun `In으로 찾기`() {
        // given
        // when
        val users = userRetrieveService.findByAgeIn(listOf(20, 22))

        // then
        assertEquals(3, users.size)
        assertEquals("taesu", users[0].name)
        assertEquals("lee", users[1].name)
        assertEquals("kim", users[2].name)
    }

    @Test
    fun `Is null 찾기`() {
        // given
        mongoTemplate.updateFirst(
            query(Criteria.where("name").`is`("kim")),
            Update().set("address", null),
            User::class.java
        )

        // when
        val usersAddressIsNullOrNotSet = userRetrieveService.retrieveAddressIsNullOrNotSet()

        // then
        assertEquals(3, usersAddressIsNullOrNotSet.size)
        assertEquals("taesu", usersAddressIsNullOrNotSet[0].name)
        assertEquals("lee", usersAddressIsNullOrNotSet[1].name)
        assertEquals("kim", usersAddressIsNullOrNotSet[2].name)

        // when
        val usersAddressIsNull = userRetrieveService.retrieveAddressIsNull()

        // then
        assertEquals(1, usersAddressIsNull.size)
        assertEquals("kim", usersAddressIsNull[0].name)
    }

}
