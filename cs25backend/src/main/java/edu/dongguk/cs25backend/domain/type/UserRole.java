package edu.dongguk.cs25backend.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    CUSTOMER("CUSTOMER"), MANAGER("MANAGER"), HQ("HQ");

    private final String userRole;

    @Override
    public String toString() {
        return userRole;
    }
}
