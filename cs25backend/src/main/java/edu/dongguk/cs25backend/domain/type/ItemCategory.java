package edu.dongguk.cs25backend.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategory {
    ICE_CREAM("아이스크림"), SNACK("과자"), NOODLE("라면"), BEVERAGE("음료");
    private final String category;

    @Override
    public String toString() {
        return category;
    }
}
