package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONValue
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        val errorCode: ErrorCode = request.getAttribute("exception") as ErrorCode

        if (errorCode == null) {
            sendErrorResponse(response, ErrorCode.NOT_END_POINT)
        } else {
            when (errorCode) {
                ErrorCode.NOT_FOUND_ERROR -> { sendErrorResponse(response, ErrorCode.NOT_FOUND_ERROR) }

                ErrorCode.ACCESS_DENIED_ERROR -> { sendErrorResponse(response, ErrorCode.ACCESS_DENIED_ERROR) }

                ErrorCode.TOKEN_MALFORMED_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_MALFORMED_ERROR) }

                ErrorCode.TOKEN_EXPIRED_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED_ERROR) }

                ErrorCode.TOKEN_TYPE_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_TYPE_ERROR) }

                ErrorCode.TOKEN_UNSUPPORTED_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_UNSUPPORTED_ERROR) }

                ErrorCode.TOKEN_UNKNOWN_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_UNKNOWN_ERROR) }

                else -> {}
            }
        }
    }

    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val map = mapOf("success" to false, "data" to null, "message" to ErrorCode.ACCESS_DENIED_ERROR)
        response.writer.print(JSONValue.toJSONString(map))
    }
}