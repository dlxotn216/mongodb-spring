package io.taesu.springdatamongodb.app.config

import io.taesu.springdatamongodb.app.config.jackson.ObjectIdToStringModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Created by itaesu on 2024/03/01.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Configuration
class AppConfig: WebMvcConfigurer {
    @Bean
    fun objectIdToStringModule() = ObjectIdToStringModule()
}
