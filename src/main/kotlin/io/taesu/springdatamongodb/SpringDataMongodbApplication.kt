package io.taesu.springdatamongodb

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@SpringBootApplication
class SpringDataMongodbApplication {
    @PostConstruct
    fun onConstruct() {
        TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_ZONE_ID))
    }

    companion object {
        const val DEFAULT_ZONE_ID = "Asia/Seoul"
    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataMongodbApplication>(*args)
}
