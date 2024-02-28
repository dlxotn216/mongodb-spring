package io.taesu.springdatamongodb.app.context

import org.bson.types.ObjectId

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
data class HttpRequestContext(
    val contextUser: ContextUser
)

class ContextUser(
    val id: ObjectId,
    val name: String
)
