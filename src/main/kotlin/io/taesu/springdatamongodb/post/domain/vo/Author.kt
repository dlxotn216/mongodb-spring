package io.taesu.springdatamongodb.post.domain.vo

import org.bson.types.ObjectId

class Author(
    val id: ObjectId,
    val displayName: String
)
