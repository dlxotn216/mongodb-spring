package io.taesu.springdatamongodb.transaction.application

import org.assertj.core.api.Assertions.assertThat
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

    @Test
    fun `Test to success transaction`() {
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

}
