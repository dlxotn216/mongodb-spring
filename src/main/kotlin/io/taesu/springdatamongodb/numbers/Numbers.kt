package io.taesu.springdatamongodb.numbers

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by itaesu on 2024/02/18.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("numbers")
class Numbers(
    val _id: ObjectId = ObjectId(),
    val x: Any
)
