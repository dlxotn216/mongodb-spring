package io.taesu.springdatamongodb.user.application

import io.taesu.springdatamongodb.user.document.LargeUser
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import kotlin.random.Random

/**
 * Created by itaesu on 2024/02/21.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Service
class UserCreateService(
    private val mongoTemplate: MongoTemplate,
) {
    fun prepareLarge() {
        (1..1000000).map {
            mongoTemplate.insert(
                LargeUser(
                    _id = it.toString(),
                    name = "User $it",
                    age = Random.nextInt(10, 100),
                    address = "Seoul"
                )
            )
        }
    }

}
