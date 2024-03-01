package io.taesu.springdatamongodb.app.extensions

import io.taesu.springdatamongodb.app.exception.ResourceNotFoundException
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
fun <T> MongoRepository<T, ObjectId>.findByIdOrNull(id: String) = findByIdOrNull(ObjectId(id))
fun <T> MongoRepository<T, ObjectId>.findById(id: String) = findByIdOrNull(id) ?: throw ResourceNotFoundException(id)
fun <T> MongoRepository<T, ObjectId>.findByIdNotNull(id: ObjectId) = findByIdOrNull(id) ?: throw ResourceNotFoundException(id)
