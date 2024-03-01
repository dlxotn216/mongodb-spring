package io.taesu.springdatamongodb.app.domain

import io.taesu.springdatamongodb.app.extensions.resolveZonedDateTime
import org.bson.types.ObjectId
import org.springframework.data.annotation.*
import org.springframework.data.domain.Persistable
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
abstract class Auditable(
    @CreatedDate
    var createdAtMillis: Instant? = null,
    @CreatedBy
    var createdBy: ObjectId? = null,
    @LastModifiedDate
    var modifiedAtMillis: Instant? = null,
    @LastModifiedBy
    var modifiedBy: ObjectId? = null,
): Persistable<ObjectId> {
    override fun isNew() = createdAtMillis == null

    val createdAt: ZonedDateTime?
        get() = createdAtMillis.resolveZonedDateTime
    val modifiedAt: ZonedDateTime?
        get() = modifiedAtMillis.resolveZonedDateTime

}
