package edu.dongguk.cs25backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@ToString
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Not Found Error
    NOT_FOUND_ERROR("404", "찾을 수 없습니다."),

    // Bad Request Error
    NOT_ENOUGH_ERROR("400", "주문하신 상품의 재고가 부족합니다."),

    SERVER_ERROR("500", "서버 내부 오류입니다."),

    NOT_END_POINT("400", "잘못된 요청입니다."),

    ACCESS_DENIED_ERROR("401", "접근 권한이 없습니다."),

    TOKEN_INVALID_ERROR("401", "유효하지 않은 토큰입니다."),

    TOKEN_MALFORMED_ERROR("401", "잘못된 토큰 입니다."),

    TOKEN_EXPIRED_ERROR("401", "만료된 토큰입니다."),

    TOKEN_TYPE_ERROR("401", "잘못된 형식의 토큰 입니다."),

    TOKEN_UNSUPPORTED_ERROR("401", "지원되지 않는 토큰입니다."),

    TOKEN_UNKNOWN_ERROR("401", "토큰 에러입니다."),

    DUPLICATION_MANAGER("405","이미 존재하는 점주입니다");


    private final String code;
    private final String message;
}
