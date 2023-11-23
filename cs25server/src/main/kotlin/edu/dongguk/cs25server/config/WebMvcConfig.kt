package edu.dongguk.cs25server.config

import edu.dongguk.cs25server.security.UserIdArgumentResolver
import edu.dongguk.cs25server.security.UserIdInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig (
    private val userIdArgumentResolver: UserIdArgumentResolver,
    private val userIdInterceptor: UserIdInterceptor
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userIdArgumentResolver)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userIdInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/api/v1/auth/**")
    }

}