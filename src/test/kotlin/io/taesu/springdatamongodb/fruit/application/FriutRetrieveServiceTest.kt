package io.taesu.springdatamongodb.fruit.application

import io.taesu.springdatamongodb.fruit.document.Fruit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class FriutRetrieveServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var friutRetrieveService: FriutRetrieveService

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(Fruit::class.java)
        mongoTemplate.insert(Fruit(listOf("apple", "banana", "orange")))
        mongoTemplate.insert(Fruit(listOf("banana", "apple", "orange")))
        mongoTemplate.insert(Fruit(listOf("apple", "kumquat", "orange")))
        mongoTemplate.insert(Fruit(listOf("cherry", "banana", "apple")))
    }

    @Test
    fun retrieveIsAll() {
        // given
        // when
        val fruits = friutRetrieveService.retrieveIsAll(listOf("apple", "banana", "orange"))

        // then
        assertThat(fruits.size).isEqualTo(2)
        assertThat(fruits[0].fruits.toSet()).isEqualTo(listOf("apple", "banana", "orange").toSet())
        assertThat(fruits[1].fruits.toSet()).isEqualTo(listOf("apple", "banana", "orange").toSet())
    }

    @Test
    fun retrieveWithIndex() {
        // given
        // when
        val fruits = friutRetrieveService.retrieveIndexEq(
            mapOf(
                0 to "apple",
                2 to "orange")
        )

        // then
        assertThat(fruits.size).isEqualTo(2)
    }
}
