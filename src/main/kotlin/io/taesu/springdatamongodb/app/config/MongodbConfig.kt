package io.taesu.springdatamongodb.app.config

import io.taesu.springdatamongodb.app.converters.AppConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions


/**
 * Created by itaesu on 2024/02/15.
 *
 * AbstractMongoClientConfiguration를 상속받는 경우 application.yml에서 설정한 인증 정보가 무효화 됨.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Configuration
class MongodbConfig {
    @Bean
    fun mongoCustomConversions(appConverters: AppConverters): MongoCustomConversions {
        return MongoCustomConversions(appConverters.mongodbConverters)
    }
}
