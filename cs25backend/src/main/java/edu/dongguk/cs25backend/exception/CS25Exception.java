package edu.dongguk.cs25backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CS25Exception extends RuntimeException {
    private final ErrorCode errorCode;
}
