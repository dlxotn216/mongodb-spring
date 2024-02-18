package io.taesu.springdatamongodb.jobs.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("jobs")
class Job(
    @Id
    val _id: ObjectId = ObjectId(),
    val jobName: String,
    val status: JobStatus,
)

enum class JobStatus {
    WAITING, RUNNING, FINISHED, FAILED
}
