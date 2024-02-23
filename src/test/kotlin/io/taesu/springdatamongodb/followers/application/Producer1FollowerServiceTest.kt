package io.taesu.springdatamongodb.followers.application

import io.taesu.springdatamongodb.followers.document.Activity
import io.taesu.springdatamongodb.followers.document.Producer1
import org.assertj.core.api.Assertions.assertThat
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
class Producer1FollowerServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var followerService: Producer1FollowerService

    @BeforeEach
    fun init() {
        mongoTemplate.dropCollection(Producer1::class.java)
        mongoTemplate.dropCollection(Activity::class.java)
    }

    @Test
    fun `Following을 내장한 경우 Activity 조회  유스케이스`() {
        // given
        val followings = mongoTemplate.insertAll(
            listOf(
                Producer1(username = "user1", email = "user1@crscube.co.kr"),
                Producer1(username = "user2", email = "user2@crscube.co.kr"),
            ),
        ).map { it._id }
        mongoTemplate.insert(Producer1(username = "taesu", email = "taesu@crscube.co.kr", following = followings))
        val any = mongoTemplate.insert(Producer1(username = "any", email = "taesu@crscube.co.kr", following = followings))

        mongoTemplate.insertAll(
            listOf(
                Activity(userId = followings[0], type = "POST", title = "Hello everyone"),
                Activity(userId = followings[1], type = "POST", title = "Engines are ready"),
                Activity(userId = any._id, type = "POST", title = "Hello everyone"),
            ),
        )

        // when
        val activities = followerService.retrieveActivities("taesu")

        // then
        assertThat(activities.size).isEqualTo(2)
    }


    @Test
    fun `Following을 내장한 경우 Followers 찾기 유스케이스`() {
        // given
        val user1 = mongoTemplate.insert(Producer1(username = "user1", email = "user1@crscube.co.kr"))
        val user2 = mongoTemplate.insert(Producer1(username = "user2", email = "user2@crscube.co.kr"))

        mongoTemplate.insert(Producer1(username = "taesu", email = "taesu@crscube.co.kr", following = listOf(user1._id, user2._id)))
        mongoTemplate.insert(Producer1(username = "lee", email = "taesu@crscube.co.kr", following = listOf(user2._id)))

        // when
        val followers = followerService.retrieveFollowers("user2")

        // then
        assertThat(followers.size).isEqualTo(2)
        assertThat(followers.map { it.username }).containsExactlyInAnyOrder("taesu", "lee")
    }

}
