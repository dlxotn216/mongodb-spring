package io.taesu.springdatamongodb.post.domain.vo

import org.springframework.data.mongodb.core.mapping.Field

class PostContent(
    @Field("content")
    val value: String
)
