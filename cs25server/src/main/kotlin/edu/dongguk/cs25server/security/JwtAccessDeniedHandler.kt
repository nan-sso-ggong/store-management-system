package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.util.Log.Companion.log
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONValue
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        sendErrorResponse(response, ErrorCode.ACCESS_DENIED_ERROR)
//        log.info("handle")
//        response.contentType = "application/json;charset=UTF-8"
//        response.status = HttpServletResponse.SC_UNAUTHORIZED
//
//        val map = mapOf("success" to false, "data" to null, "message" to ErrorCode.ACCESS_DENIED_ERROR)
//        response.writer.print(JSONValue.toJSONString(map))
    }

    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        log.info("handle")

        val map = mapOf("success" to false, "data" to null, "message" to ErrorCode.ACCESS_DENIED_ERROR)
        response.writer.print(JSONValue.toJSONString(map))
    }

}