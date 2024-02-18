package io.taesu.springdatamongodb.jobs.application

import io.taesu.springdatamongodb.jobs.document.Job
import io.taesu.springdatamongodb.jobs.document.JobStatus
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class JobRetrieveService(
    private val mongoTemplate: MongoTemplate,
) {
    fun findAndUpdateToRunning(): Job? {
        return mongoTemplate.findAndModify(
            Query().apply {
                addCriteria(Criteria.where("status").`is`(JobStatus.WAITING))
            },
            Update().apply {
                set("status", JobStatus.RUNNING)
            },
            FindAndModifyOptions.options().returnNew(true),
            Job::class.java
        )
    }
}
