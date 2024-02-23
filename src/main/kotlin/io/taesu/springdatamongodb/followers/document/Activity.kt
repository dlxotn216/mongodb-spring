package io.taesu.springdatamongodb.followers.document

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by itaesu on 2024/02/23.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("activities")
class Activity(
    val _id: ObjectId = ObjectId(),
    val userId: ObjectId,
    val type: String,
    val title: String
)
