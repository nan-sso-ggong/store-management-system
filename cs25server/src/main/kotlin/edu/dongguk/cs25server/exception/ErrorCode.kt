package edu.dongguk.cs25server.exception

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    private val code: String,
    private val message: String
) {

    NOT_END_POINT("400", "잘못된 요청입니다."),

    // Not Found Error
    NOT_FOUND_CUSTOMER("401", "사용자를 찾을 수 없습니다."),
    NOT_FOUND_MANAGER("402", "점주를 찾을 수 없습니다."),
    NOT_FOUND_HQ("403", "본사 담당자를 찾을 수 없습니다."),
    NOT_FOUND_ERROR("404", "찾을 수 없습니다."),
    NOT_FOUND_ITEMCS("405", "상품을 찾을 수 않습니다."),
    NOT_FOUND_STORE("406", "점포를 찾을 수 않습니다."),
    NOT_FOUND_ITEMHS("407", "상품을 찾을 수 없습니다."),
    NOT_FOUND_ORDER("408", "주문 내역을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_APPLICATION("409", "발주 신청 내역을 찾을 수 없습니다."),

    // Bad Request Error
    NOT_ENOUGH_STOCK_ERROR("410", "주문하신 상품의 재고가 부족합니다."),
    INVALID_ARGUMENT("411", "잘못된 값 입니다."),
    WRONG_CATEGORY_ERROR("412", "알 수 없는 카테고리입니다."),
    WRONG_SUPPLIER_ERROR("413", "알 수 없는 공급처입니다."),
    WRONG_PAYMENT_TYPE_ERROR("414", "알 수 없는 결제 수단입니다."),
    WRONG_ORDER_STATUS_ERROR("415", "알 수 없는 주문 수단입니다."),
    ALREADY_PICKUP_ERROR("416", "이미 픽업된 상품은 취소가 불가능합니다."),
    NOT_ENOUGH_BALANCE_ERROR("417", "잔고가 부족합니다."),
    NOT_ENOUGH_POINT_BALANCE_ERROR("418", "포인트 잔고가 부족합니다."),
    EMPTY_IMAGE_ERROR("419", "상품 등록시에는 반드시 이미지 파일이 등록되어야 합니다."),
    NOT_ENOUGH_RELEASE_ERROR("420", "출고하려는 상품의 재고가 부족합니다."),


    // Security Error
    ACCESS_DENIED_ERROR("430", "접근 권한이 없습니다."),
    TOKEN_INVALID_ERROR("431", "유효하지 않은 토큰입니다."),
    TOKEN_MALFORMED_ERROR("432", "잘못된 토큰 입니다."),
    TOKEN_EXPIRED_ERROR("433", "만료된 토큰입니다."),
    TOKEN_TYPE_ERROR("434", "잘못된 형식의 토큰 입니다."),
    TOKEN_UNSUPPORTED_ERROR("435", "지원되지 않는 토큰입니다."),
    TOKEN_UNKNOWN_ERROR("436", "토큰 에러입니다."),


    // Server Error
    SERVER_ERROR("500", "서버 내부 오류입니다."),
    DUPLICATION_MANAGER("505", "이미 존재하는 점주입니다"),
    DUPLICATION_STORE("506", "이미 존재하는 점포입니다"),
    DUPLICATION_HEADQUARTER("507", "이미 존재하는 본사입니다"),
    MANAGER_NOT_ALLOW("508", "아직 승인나지 않은 점주입니다"),
    STORE_NOT_ALLOW("509", "아직 승인나지 않은 점포입니다"),

    //Image Error
    IMAGE_SAVING_ERROR("510", "이미지 저장에 실패하였습니다."),
    IMAGE_DELETE_ERROR("511", "이미지 삭제에 실패하였습니다.");

    fun getMessage() = this.message
}