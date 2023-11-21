package edu.dongguk.cs25backend.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
