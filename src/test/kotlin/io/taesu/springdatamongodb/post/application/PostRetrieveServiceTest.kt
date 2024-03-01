package io.taesu.springdatamongodb.post.application

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import io.taesu.springdatamongodb.mock.giveMeOne
import io.taesu.springdatamongodb.post.domain.Post
import io.taesu.springdatamongodb.post.domain.PostRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class PostRetrieveServiceTest {
    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var postRetrieveService: PostRetrieveService

    var post: Post? = null

    @BeforeEach
    fun init() {
        postRepository.deleteAll()
        this.post = postRepository.save(giveMeOne())
    }

    @Test
    fun `Post 조회 서비스 테스트`() {
        // given
        // when
        val response = postRetrieveService.retrieve(post!!.id)

        // then
        response.run {
            assertThat(this.id).isEqualTo(post!!.id.toString())
            assertThat(this.title).isEqualTo(post!!.title)
            assertThat(this.type).isEqualTo(post!!.category.name)
            assertThat(this.content).isEqualTo(post!!.contentValue)
            assertThat(this.author.id).isEqualTo(post!!.author.id.toString())
            assertThat(this.author.name).isEqualTo(post!!.author.displayName)
            assertThat(this.hashTags.size).isEqualTo(post!!.hashTags.size)
            assertThat(this.mentions.size).isEqualTo(post!!.mentions.size)
        }
    }
}

