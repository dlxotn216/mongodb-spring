package io.taesu.springdatamongodb.student.application

import io.taesu.springdatamongodb.student.document.Score
import io.taesu.springdatamongodb.student.document.Student
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.random.Random
import kotlin.streams.asStream

/**
 * Created by itaesu on 2024/02/21.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class StudentCreateService(
    private val mongoTemplate: MongoTemplate,
) {
    fun prepareLarge() {
        mongoTemplate.dropCollection(Student::class.java)
        (1..500000)
            .asSequence()
            .asStream()
            .parallel()
            .map {
                mongoTemplate.insert(
                    Student(
                        studentKey = it.toLong(),
                        scores = listOf(
                            Score(
                                type = "math",
                                score = Random.nextDouble(0.0, 100.0)
                            ),
                            Score(
                                type = "english",
                                score = Random.nextDouble(0.0, 100.0)
                            ),
                            Score(
                                type = "korean",
                                score = Random.nextDouble(0.0, 100.0)
                            ),
                        ),
                        classKey = Random.nextLong(1, 1000)
                    )
                )
            }
            .collect(Collectors.toList())
    }

}
