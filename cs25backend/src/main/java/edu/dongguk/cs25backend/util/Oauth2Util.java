package edu.dongguk.cs25backend.util;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2Util {

    // kakao
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String KAKAO_TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String KAKAO_USERINFO_URL;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URL;

    //naver
    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String NAVER_TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String NAVER_USERINFO_URL;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String NAVER_REDIRECT_URL;

    //google
    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String GOOGLE_TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String GOOGLE_USERINFO_URL;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

    private final RestTemplate restTemplate;

    public String getKakaoAccessToken(String authCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("client_secret", KAKAO_CLIENT_SECRET);
        params.add("redirect_uri", KAKAO_REDIRECT_URL);
        params.add("code", authCode);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return JsonParser.parseString(Objects.requireNonNull(response.getBody()))
                .getAsJsonObject().get("access_token").getAsString();
    }

    public Oauth2CustomerInfo getKakaoUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+ accessToken);
        httpHeaders.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest= new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_USERINFO_URL,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());
        return Oauth2CustomerInfo.builder()
                .socialId(element.getAsJsonObject().get("id").getAsString())
                .socialName(element.getAsJsonObject().getAsJsonObject("properties").get("nickname").getAsString())
                .build();
    }

    public String getNaverAccessToken(String authCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("redirect_uri", NAVER_REDIRECT_URL);
        params.add("code", authCode);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params,httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                NAVER_TOKEN_URL,
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );

        return JsonParser.parseString(Objects.requireNonNull(response.getBody()))
                .getAsJsonObject().get("access_token").getAsString();
    }

    public Oauth2CustomerInfo getNaverUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+ accessToken);
        httpHeaders.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverProfileRequest= new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                NAVER_USERINFO_URL,
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());
        return Oauth2CustomerInfo.builder()
                .socialId(element.getAsJsonObject().getAsJsonObject("response").get("id").getAsString())
                .socialName(element.getAsJsonObject().getAsJsonObject("response").get("name").getAsString())
                .build();
    }


    public String getGoogleAccessToken(String authCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", GOOGLE_REDIRECT_URL);
        params.add("code", authCode);

        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(params,httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_TOKEN_URL,
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        return JsonParser.parseString(Objects.requireNonNull(response.getBody()))
                .getAsJsonObject().get("access_token").getAsString();
    }

    public Oauth2CustomerInfo getGoogleUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+ accessToken);
        httpHeaders.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> googleProfileRequest= new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_USERINFO_URL,
                HttpMethod.GET,
                googleProfileRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());
        return Oauth2CustomerInfo.builder()
                .socialId(element.getAsJsonObject().get("id").getAsString())
                .socialName(element.getAsJsonObject().get("name").getAsString())
                .build();
    }
}
