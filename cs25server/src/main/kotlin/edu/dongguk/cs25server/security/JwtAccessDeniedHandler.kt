package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.util.Log.Companion.log
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONValue
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAccessDeniedHandler (
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        resolver.resolveException(request, response, null, accessDeniedException)
    }
}