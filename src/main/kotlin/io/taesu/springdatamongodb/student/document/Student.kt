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
    val classKey: Long,
) {
    val finalGrade = scores.map { it.score }.average()
}

class Score(
    val type: String,
    val score: Double,
)

@Document("studentClasses")
class StudentClass(
    val _id: ObjectId = ObjectId(),
    val studentId: ObjectId = ObjectId(),
    val classes: List<ObjectId> = listOf(),
)


@Document("students")
class Student2(
    val _id: ObjectId = ObjectId(),
    val name: String,
    val classes: List<ObjectId>,
)

@Document("students")
class Student3(
    val _id: ObjectId = ObjectId(),
    val name: String,
    val classes: List<Student3Class>,
) {
    class Student3Class(
        val _id: ObjectId = ObjectId(),
        val name: String,
        val room: String,
        val credits: Double,
    )
}

@Document("students")
class Student4(
    val _id: ObjectId = ObjectId(),
    val name: String,
    val classes: List<Student4Class>
) {
    class Student4Class(
        val _id: ObjectId = ObjectId(),
        val name: String
    )
}
