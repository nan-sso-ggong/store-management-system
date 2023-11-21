package edu.dongguk.cs25backend.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    CUSTOMER("CUSTOMER"), MANAGER("MANAGER"), HQ("HQ");

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
