package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.util.Log.Companion.log
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(AuthenticationException::class)
    fun authenticationException(e: AuthenticationException, request: HttpServletRequest): RestResponse<*> {

        val errorCode = request.getAttribute("exception")
            ?: return RestResponse.errorResponse(ErrorCode.NOT_END_POINT)

//        val errorCode: ErrorCode = request.getAttribute("exception") as ErrorCode
//            ?: return RestResponse.errorResponse(ErrorCode.NOT_END_POINT)

        return RestResponse.errorResponse(errorCode as ErrorCode)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(e: AccessDeniedException, request: HttpServletRequest): RestResponse<*> {
        return RestResponse.errorResponse(ErrorCode.ACCESS_DENIED_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun invalidRequestException(e: MethodArgumentNotValidException): RestResponse<*> {
        val validation: MutableMap<String, String?> = HashMap()
        for (fieldError in e.fieldErrors) {
            validation[fieldError.field] = fieldError.defaultMessage
        }
        return RestResponse(success = false, data = validation, ErrorCode.INVALID_ARGUMENT)
    }

    @ExceptionHandler(GlobalException::class)
    fun cs25Exception(e: GlobalException): RestResponse<*> {
        return RestResponse.errorResponse(e.getErrorCode())
    }

}