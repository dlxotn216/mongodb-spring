package io.taesu.springdatamongodb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@EnableMongoAuditing
@SpringBootApplication
class SpringDataMongodbApplication

fun main(args: Array<String>) {
    runApplication<SpringDataMongodbApplication>(*args)
}
