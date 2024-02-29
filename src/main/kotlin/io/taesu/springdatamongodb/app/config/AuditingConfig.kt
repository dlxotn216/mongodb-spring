package io.taesu.springdatamongodb.app.config

import io.taesu.springdatamongodb.app.context.ContextUser
import org.bson.types.ObjectId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import java.util.*


/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@EnableMongoAuditing
@Configuration
class AuditingConfig {
    @Bean
    fun auditorAware(): AuditorAware<ObjectId> {
        return AuditorAware {
            val contextUser = ContextUser(ObjectId(), "Lee Tae Su")
            Optional.of(contextUser.id)
        }
    }
}
