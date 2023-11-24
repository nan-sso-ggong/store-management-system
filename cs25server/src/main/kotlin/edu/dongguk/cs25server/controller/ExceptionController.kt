package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.exception.GlobalException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {
    @ExceptionHandler(GlobalException::class)
    fun cs25Exception(e: GlobalException): RestResponse<*> {
        return RestResponse.errorResponse(e.getErrorCode())
    }

}