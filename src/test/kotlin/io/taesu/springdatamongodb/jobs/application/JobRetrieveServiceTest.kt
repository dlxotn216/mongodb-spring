package io.taesu.springdatamongodb.jobs.application

import io.taesu.springdatamongodb.jobs.document.Job
import io.taesu.springdatamongodb.jobs.document.JobStatus
import org.junit.jupiter.api.Assertions.*
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
class JobRetrieveServiceTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var jobRetrieveService: JobRetrieveService

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(Job::class.java)
    }

    @Test
    fun `findOneAndUpdate 테스트`() {
        // given
        mongoTemplate.save(Job(jobName="sync-scheduler", status = JobStatus.WAITING))
        mongoTemplate.save(Job(jobName="daily-scheduler", status = JobStatus.WAITING))
        mongoTemplate.save(Job(jobName="migration-scheduler", status = JobStatus.WAITING))

        // when
        val any = jobRetrieveService.findAndUpdateToRunning()

        // then
        assertNotNull(any)
        assertEquals(JobStatus.RUNNING, any!!.status)
    }

}
