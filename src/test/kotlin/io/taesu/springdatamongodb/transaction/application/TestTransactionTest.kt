package io.taesu.springdatamongodb.transaction.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

/**
 * Created by taesu on 2024/02/22.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class TestTransactionTest {
    @Autowired
    private lateinit var testTransaction: TestTransaction

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(Group::class.java)
        mongoTemplate.dropCollection(GroupUser::class.java)
    }

    @Test
    fun `트랜잭션 작업 성공 테스트`() {
        // when
        testTransaction.save()

        // then
        val groups = mongoTemplate.findAll(Group::class.java)
        assertThat(groups.size).isEqualTo(1)
        val group = groups[0]
        assertThat(group.name).isEqualTo("Dev")

        val user = mongoTemplate.findOne(Query().apply {
            this.addCriteria(Criteria.where("_id").`is`(group.userIds[0]))
        }, GroupUser::class.java)!!
        assertThat(user.name).isEqualTo("lee")
    }

    @Test
    fun `트랜잭션 작업 실패 테스트`() {
        // when
        try {
            testTransaction.throwException()
        } catch (e: Exception) {
            // ignore
        }

        // then
        val groups = mongoTemplate.findAll(Group::class.java)
        assertThat(groups).isEmpty()
        val users = mongoTemplate.findAll(GroupUser::class.java)
        assertThat(users).isEmpty()
    }
}
