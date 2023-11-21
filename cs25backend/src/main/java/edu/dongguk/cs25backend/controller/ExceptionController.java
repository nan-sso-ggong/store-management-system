package edu.dongguk.cs25backend.controller;

import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.dto.response.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CS25Exception.class)
    public RestResponse<?> cs25Exception(CS25Exception e) {
        return RestResponse.errorResponse(e.getErrorCode());
    }

}
