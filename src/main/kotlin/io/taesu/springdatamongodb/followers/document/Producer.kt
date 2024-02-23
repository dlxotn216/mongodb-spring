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
@Document("producers")
class Producer1(
    val _id: ObjectId = ObjectId(),
    val username: String,
    val email: String,
    val following: List<ObjectId> = listOf(),
)

@Document("producers")
class Producer2(
    val _id: ObjectId = ObjectId(),
    val username: String,
    val email: String,
    val followers: List<ObjectId> = listOf(),
)

@Document("userFollowers")
class UserFollowers(
    val _id: ObjectId = ObjectId(),
    val followers: List<ObjectId> = listOf(),
)

@Document("producers")
class Producer3(
    val _id: ObjectId = ObjectId(),
    val username: String,
    val email: String,
    val followers: List<ObjectId> = listOf(),
    val tbc: List<ObjectId> // UserFollowersTbc의 id 참조
)

@Document("userFollowersTbc")
class UserFollowersTbc(
    val _id: ObjectId = ObjectId(),
    val followers: List<ObjectId> = listOf(),
)

@Document("recentActivities")
class RecentActivity(
    val _id: ObjectId = ObjectId(),
    val owner: ObjectId, // 누구에게 보여줄 Activity인가
    val type: String,
    val originsCount: Int,
    val origins: List<ActivityOrigin>,
    val createdTimeMillis: Long
)
class ActivityOrigin(
    val _id: ObjectId = ObjectId(),
    val userId: ObjectId,
    val type: String,
    val title: String
)
