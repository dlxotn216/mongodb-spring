package io.taesu.springdatamongodb.user.document

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("users")
class User(
    val _id: ObjectId = ObjectId(),
    val name: String,
    val age: Int,
    val address: String? = null,
    val joinedAt: LocalDateTime = LocalDateTime.now(),
)

@Document("people")
class Person(
    val _id: ObjectId = ObjectId(),
    val name: Name,
)

class Name(
    val last: String,
    val first: String,
)

@Document("large_users")
class LargeUser(
    val _id: String,
    val name: String,
    val age: Int,
    val address: String? = null,
    val joinedAt: LocalDateTime = LocalDateTime.now(),
)
