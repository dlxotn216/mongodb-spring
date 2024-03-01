package io.taesu.springdatamongodb.app.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
fun ZonedDateTime?.toResponse(zoneId: ZoneId = ZoneId.of("Asia/Seoul")): String {
    this ?: return ""
    return this.withZoneSameInstant(zoneId)
        .toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

val Instant?.resolveZonedDateTime: ZonedDateTime?
    get() = this?.let { ZonedDateTime.ofInstant(it, ZoneId.systemDefault()) }
