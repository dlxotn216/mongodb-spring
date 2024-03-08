package io.taesu.springdatamongodb.app.exception

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
open class BusinessException(val errorCode: ErrorCode): RuntimeException()

class ResourceNotFoundException(val identifier: Any)
    : BusinessException(ErrorCode.RESOURCE_NOT_FOUND)

class UnexpectedException: RuntimeException() {
    val errorCode: ErrorCode = ErrorCode.UNEXPECTED
}
