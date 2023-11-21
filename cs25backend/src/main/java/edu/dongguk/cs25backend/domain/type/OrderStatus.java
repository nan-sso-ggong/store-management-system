package edu.dongguk.cs25backend.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    //픽업대기, 픽업완료, 환불 처리
    READY("READY"), PICKUP("PICKUO"), CANCEL("CANCEL");

    private final String status;

    @Override
    public String toString() {
        return status;
    }
}
