package edu.dongguk.cs25backend.dto.request;

import edu.dongguk.cs25backend.domain.type.MemberShip;
import edu.dongguk.cs25backend.domain.type.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private MemberShip memberShip;
}
