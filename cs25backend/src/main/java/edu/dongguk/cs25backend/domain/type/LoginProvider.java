package edu.dongguk.cs25backend.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LoginProvider {
    KAKAO("KAKAO"), NAVER("NAVER"), GOOGLE("GOOGLE");

    private final String provider;

    @Override
    public String toString() {
        return provider;
    }
}
