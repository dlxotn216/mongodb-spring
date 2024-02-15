package io.taesu.springdatamongodb.app.annotation

import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@ReadingConverter
@Component
annotation class MongodbReadingConverter

@WritingConverter
@Component
annotation class MongodbWritingConverter
