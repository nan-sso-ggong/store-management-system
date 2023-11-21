package edu.dongguk.cs25backend.security;

import edu.dongguk.cs25backend.domain.type.UserRole;

public interface UserLoginForm {
    Long getId();
    UserRole getRole();
}
