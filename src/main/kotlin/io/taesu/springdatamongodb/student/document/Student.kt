package io.taesu.springdatamongodb.student.document

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by itaesu on 2024/02/21.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("students")
class Student(
    val _id: ObjectId = ObjectId(),
    val studentKey: Long,
    val scores: List<Score>,
    val classKey: Long
) {
    val finalGrade = scores.map { it.score }.average()
}

class Score(
    val type: String,
    val score: Double,
)
