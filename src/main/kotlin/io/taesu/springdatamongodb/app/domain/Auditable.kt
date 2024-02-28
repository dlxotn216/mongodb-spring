package io.taesu.springdatamongodb.app.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

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
)
