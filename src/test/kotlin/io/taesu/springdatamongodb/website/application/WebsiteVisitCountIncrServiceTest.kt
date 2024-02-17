package io.taesu.springdatamongodb.website.application

import io.taesu.springdatamongodb.website.document.WebSite
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class WebsiteVisitCountIncrServiceTest {
    @Autowired
    private lateinit var sebsiteVisitCountIncrService: WebsiteVisitCountIncrService

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(WebSite::class.java)
    }

    @Test
    fun `Upsert 테스트`() {
        // given, when
        sebsiteVisitCountIncrService.incrementVisitorsCount("https://taesu.io")

        // then
        val webSite = mongoTemplate.findOne(
            Query().apply { addCriteria(Criteria.where("url").`is`("https://taesu.io")) },
            WebSite::class.java
        )
        assertThat(webSite!!.visitorsCount).isEqualTo(1L)
    }

    @Test
    fun `SetOnInsert 테스트`() {
        // given, when
        val url = "https://taesu.io/set/on/insert"
        sebsiteVisitCountIncrService.createWithSetOnInsert(url, "taesu")
        sebsiteVisitCountIncrService.createWithSetOnInsert(url, "taesu1")
        sebsiteVisitCountIncrService.createWithSetOnInsert(url, "taesu2")

        // then
        val webSite = mongoTemplate.findOne(
            Query().apply { addCriteria(Criteria.where("url").`is`(url)) },
            WebSite::class.java
        )
        assertThat(webSite!!.visitorsCount).isEqualTo(3L)
        assertThat(webSite.createdBy).isEqualTo("taesu")
        assertThat(webSite.createdAt).isNotNull
    }

    @Test
    fun `UpdateMany 테스트`() {
        // given
        // create by is not exists
        val urls = listOf(
            "https://taesu1.io",
            "https://taesu2.io",
            "https://taesu3.io",
        )
        urls.forEach {
            sebsiteVisitCountIncrService.incrementVisitorsCount(it)
        }

        // when
        sebsiteVisitCountIncrService.setCreateByMany("taesu")

        // then
        urls.forEach {
            val webSite = mongoTemplate.findOne(
                Query().apply { addCriteria(Criteria.where("url").`is`(it)) },
                WebSite::class.java
            )
            assertThat(webSite!!.createdBy).isEqualTo("taesu")
        }
    }

}
