package edu.dongguk.cs25backend.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberShip {
    // TODO 적립률 각각 몇 퍼센트?
    NORMAL("NORMAL"), BRONZE("BRONZE"), SILVER("SILVER"), GOLD("GOLD");

    private final String level;
}
