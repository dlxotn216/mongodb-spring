package io.taesu.springdatamongodb.app.config

import io.taesu.springdatamongodb.app.resolver.HttpRequestContextResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Created by itaesu on 2024/02/28.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@Configuration
class RealConfig: WebMvcConfigurer {
    @Autowired
    private lateinit var httpRequestContextResolver: HttpRequestContextResolver
    
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(httpRequestContextResolver)
    }
}
