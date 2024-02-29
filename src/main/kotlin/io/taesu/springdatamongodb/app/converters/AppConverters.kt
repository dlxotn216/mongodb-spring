package io.taesu.springdatamongodb.app.converters

import io.taesu.springdatamongodb.app.annotation.MongodbReadingConverter
import io.taesu.springdatamongodb.app.annotation.MongodbWritingConverter
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.Year
import kotlin.reflect.full.hasAnnotation

/**
 * Created by itaesu on 2024/02/15.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Component
class AppConverters(converters: List<Converter<*, *>>) {
    final var mongodbConverters: List<Converter<*, *>>

    init {
        this.mongodbConverters = converters.filter(::hasMongodbConverterAnnotation)
    }

    private fun hasMongodbConverterAnnotation(it: Converter<*, *>) =
        (it::class).hasAnnotation<MongodbReadingConverter>()
            || (it::class).hasAnnotation<MongodbWritingConverter>()
}

@MongodbReadingConverter
class YearReadConverter: Converter<Long, Year> {
    override fun convert(source: Long): Year = Year.of(source.toInt())
}

@MongodbWritingConverter
class YearWriteConverter: Converter<Year, Long> {
    override fun convert(source: Year): Long = source.value.toLong()
}

@MongodbReadingConverter
class InstantReadConverter: Converter<Long, Instant> {
    override fun convert(source: Long): Instant = Instant.ofEpochMilli(source)
}

@MongodbWritingConverter
class InstantWriteConverter: Converter<Instant, Long> {
    override fun convert(source: Instant): Long = source.toEpochMilli()
}
