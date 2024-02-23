package io.taesu.springdatamongodb.followers.application

import io.taesu.springdatamongodb.followers.document.Activity
import io.taesu.springdatamongodb.followers.document.Producer1
import io.taesu.springdatamongodb.followers.document.Producer2
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * Created by itaesu on 2024/02/23.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class Producer2FollowerServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var followerService: Producer2FollowerService

    @BeforeEach
    fun init() {
        mongoTemplate.dropCollection(Producer1::class.java)
        mongoTemplate.dropCollection(Activity::class.java)
    }

    @Test
    fun `Followers를 내장한 경우 Activity 조회 유스케이스`() {
        // given
        // taesu가 user1, user2를 팔로우 함
        val taesu = mongoTemplate.insert(Producer2(username = "taesu", email = "taesu@crscube.co.kr"))
        val user1 = mongoTemplate.insert(Producer2(username = "user1", email = "user1@crscube.co.kr", followers = listOf(taesu._id)))
        val user2 = mongoTemplate.insert(Producer2(username = "user2", email = "user2@crscube.co.kr", followers = listOf(taesu._id)))
        val user3 = mongoTemplate.insert(Producer2(username = "user3", email = "user2@crscube.co.kr", followers = listOf()))


        mongoTemplate.insertAll(
            listOf(
                Activity(userId = user1._id, type = "POST", title = "Hello everyone"),
                Activity(userId = user2._id, type = "POST", title = "Engines are ready"),
                Activity(userId = taesu._id, type = "POST", title = "Hello everyone"),
                Activity(userId = user3._id, type = "POST", title = "Hello everyone"),
            ),
        )

        // when
        val activities = followerService.retrieveActivities("taesu")

        // then
        Assertions.assertThat(activities.size).isEqualTo(2)
    }
}
