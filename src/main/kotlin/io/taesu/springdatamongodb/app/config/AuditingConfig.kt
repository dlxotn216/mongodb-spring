package io.taesu.springdatamongodb.app.config

import io.taesu.springdatamongodb.app.context.ContextUser
import io.taesu.springdatamongodb.app.domain.Auditable
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import java.lang.reflect.Field
import java.time.Instant
import java.util.*
import java.util.function.Consumer


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


@Configuration
class NestedObjectAuditorListener: ApplicationListener<BeforeConvertEvent<Any?>> {
    override fun onApplicationEvent(event: BeforeConvertEvent<Any?>) {
        val source = event.source
        audit(source)
    }

    private fun audit(source: Any) {
        if (source is Auditable) {
            processEachFields(source)
            val contextUser = ContextUser(ObjectId(), "Lee Tae Su")
            val now = Instant.now()
            if (source.isNew()) {
                source.createdBy = contextUser.id
                source.createdAtMillis = now
            }
            source.modifiedBy = contextUser.id
            source.modifiedAtMillis = now
        }
    }

    private fun processEachFields(source: Any) {
        val fields: Array<Field> = source.javaClass.getDeclaredFields()
        try {
            for (field in fields) {
                field.setAccessible(true)
                auditSelectedObjectClass(field.get(source))
            }
        } catch (e: Exception) {
            log.error("fail to access field", e)
        }
    }

    private fun auditSelectedObjectClass(fieldObj: Any) {
        if (fieldObj is Map<*, *>) {
            val map = fieldObj as? Map<String, Any> ?: return
            map.values
                .forEach(Consumer { source: Any -> audit(source) })
        } else {
            (fieldObj as? Auditable)?.let { audit(it) }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
