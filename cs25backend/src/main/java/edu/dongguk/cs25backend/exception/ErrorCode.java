package edu.dongguk.cs25backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    NOT_ENOUGH_ERROR("400", "주문하신 상품의 재고가 부족합니다.");

    private final String code;
    private final String message;
}
