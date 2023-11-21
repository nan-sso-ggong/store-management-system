package edu.dongguk.cs25backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CS25Exception extends RuntimeException {
    private final ErrorCode errorCode;

    public CS25Exception(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
