package edu.dongguk.cs25backend.util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Oauth2CustomerInfo {
    private String socialId;
    private String socialName;

    @Builder
    public Oauth2CustomerInfo(String socialId, String socialName) {
        this.socialId = socialId;
        this.socialName = socialName;
    }
}
