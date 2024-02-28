package io.taesu.springdatamongodb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDataMongodbApplication

fun main(args: Array<String>) {
    runApplication<SpringDataMongodbApplication>(*args)
}
