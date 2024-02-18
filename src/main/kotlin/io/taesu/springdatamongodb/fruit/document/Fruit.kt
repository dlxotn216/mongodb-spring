package io.taesu.springdatamongodb.fruit.document

import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by itaesu on 2024/02/17.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Document("fruits")
class Fruit(
    val fruits: List<String>
)
