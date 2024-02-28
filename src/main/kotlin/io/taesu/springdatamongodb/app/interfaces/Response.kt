package io.taesu.springdatamongodb.app.interfaces

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
open class Response(
    val result: Result,
)

class SuccessResponse<T>(
    val data: T,
): Response(Result(ResultType.SUCCESS))

class Result(
    val type: ResultType,
)

enum class ResultType {
    SUCCESS,
    FAIL
}
