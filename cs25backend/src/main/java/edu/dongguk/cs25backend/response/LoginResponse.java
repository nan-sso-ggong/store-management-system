package edu.dongguk.cs25backend.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String name;
    private String access_token;
    private String refresh_token;
}
