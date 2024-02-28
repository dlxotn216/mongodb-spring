package io.taesu.springdatamongodb.app.resolver

import io.taesu.springdatamongodb.app.context.ContextUser
import io.taesu.springdatamongodb.app.context.HttpRequestContext
import org.bson.types.ObjectId
import org.springframework.context.annotation.Profile
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Profile("!test")
@Component
class HttpRequestContextResolver:  HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return HttpRequestContext::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        return HttpRequestContext(ContextUser(ObjectId(), "Lee Tae Su"))
    }

}
