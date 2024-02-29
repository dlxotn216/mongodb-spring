package io.taesu.springdatamongodb.app.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.*
import org.springframework.data.domain.Persistable
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
    @Id
    val _id: ObjectId = ObjectId(),
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
    override fun getId(): ObjectId = _id

    val createdAt: ZonedDateTime?
        get() = resolveZonedDateTime(createdAtMillis)
    val modifiedAt: ZonedDateTime?
        get() = resolveZonedDateTime(modifiedAtMillis)

    private fun resolveZonedDateTime(instant: Instant?) =
        instant?.let { ZonedDateTime.ofInstant(it, ZoneId.systemDefault()) }
}
