package io.taesu.springdatamongodb.website.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("websites")
class WebSite(
    @Id
    val _id: ObjectId = ObjectId(),
    val url: String,
    val visitorsCount: Long,
    val createdBy: String = "unknown",
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    // 생성자에 있어야 매핑 된다.
    // val createdBy: String = "unknown"
    // val createdAt: LocalDateTime = LocalDateTime.now()
}
