package io.taesu.springdatamongodb.post.domain.vo

import org.bson.types.ObjectId

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
class Mention(
    val label: String,
    val targetId: ObjectId,
    val targetType: String,
)

class HashTag(
    val label: String,
)
