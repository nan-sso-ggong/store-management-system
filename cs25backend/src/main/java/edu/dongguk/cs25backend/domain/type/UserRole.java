package edu.dongguk.cs25backend.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ROLE_CUSTOMER("ROLE_CUSTOMER"), ROLE_MANAGER("ROLE_MANAGER"), ROLE_HQ("ROLE_HQ");

    private final String role;

    @Override
    public String toString() {
        return role;
    }

//    @JsonCreator
//    public static UserRole from(String value) {
//        for (UserRole userRole : UserRole.values()) {
//            if (userRole.toString().equals(value)) {
//                return userRole;
//            }
//        }
//        return null;
//    }
}
