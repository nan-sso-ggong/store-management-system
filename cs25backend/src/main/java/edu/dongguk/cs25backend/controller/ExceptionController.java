package edu.dongguk.cs25backend.controller;

import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CS25Exception.class)
    public RestResponse<?> notEnoughStockException(CS25Exception e) {
        return RestResponse.errorResponse(e.getErrorCode());
    }
}
