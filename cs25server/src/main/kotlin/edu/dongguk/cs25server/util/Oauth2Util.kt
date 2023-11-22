package edu.dongguk.cs25server.util

import com.nimbusds.jose.shaded.gson.JsonParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.util.*

@Component
class Oauth2Util (
    // kakao
    @Value("\${spring.security.oauth2.client.provider.kakao.token-uri}")
    private val KAKAO_TOKEN_URL: String,

    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private val KAKAO_USERINFO_URL: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val KAKAO_CLIENT_ID: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}")
    private val KAKAO_CLIENT_SECRET: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val KAKAO_REDIRECT_URL: String,

    //naver
    @Value("\${spring.security.oauth2.client.provider.naver.token-uri}")
    private val NAVER_TOKEN_URL: String,

    @Value("\${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private val NAVER_USERINFO_URL: String,

    @Value("\${spring.security.oauth2.client.registration.naver.client-id}")
    private val NAVER_CLIENT_ID: String,

    @Value("\${spring.security.oauth2.client.registration.naver.client-secret}")
    private val NAVER_CLIENT_SECRET: String,

    @Value("\${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private val NAVER_REDIRECT_URL: String,

    //google
    @Value("\${spring.security.oauth2.client.provider.google.token-uri}")
    private val GOOGLE_TOKEN_URL: String,

    @Value("\${spring.security.oauth2.client.provider.google.user-info-uri}")
    private val GOOGLE_USERINFO_URL: String,

    @Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val GOOGLE_CLIENT_ID: String,

    @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val GOOGLE_CLIENT_SECRET: String,

    @Value("\${spring.security.oauth2.client.registration.google.redirect-uri}")
    private val GOOGLE_REDIRECT_URL: String,

    private val restTemplate: RestTemplate
) {

    fun getKakaoAccessToken(authCode: String): String {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", KAKAO_CLIENT_ID)
        params.add("client_secret", KAKAO_CLIENT_SECRET)
        params.add("redirect_uri", KAKAO_REDIRECT_URL)
        params.add("code", authCode)

        val tokenRequest = HttpEntity(params, httpHeaders)
        val response: ResponseEntity<String> = restTemplate.exchange(
            KAKAO_TOKEN_URL,
            HttpMethod.POST,
            tokenRequest,
            String::class
        )

        return JsonParser.parseString(Objects.requireNonNull(response.body))
            .asJsonObject["access_token"].asString
    }

    fun getNaverAccessToken(authCode: String): String {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", NAVER_CLIENT_ID)
        params.add("client_secret", NAVER_CLIENT_SECRET)
        params.add("redirect_uri", NAVER_REDIRECT_URL)
        params.add("code", authCode)

        val tokenRequest = HttpEntity(params, httpHeaders)
        val response: ResponseEntity<String> = restTemplate.exchange(
            NAVER_TOKEN_URL,
            HttpMethod.POST,
            tokenRequest,
            String::class
        )

        return JsonParser.parseString(Objects.requireNonNull(response.body))
            .asJsonObject["access_token"].asString
    }

    fun getGoogleAccessToken(authCode: String): String {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", GOOGLE_CLIENT_ID)
        params.add("client_secret", GOOGLE_CLIENT_SECRET)
        params.add("redirect_uri", GOOGLE_REDIRECT_URL)
        params.add("code", authCode)

        val tokenRequest = HttpEntity(params, httpHeaders)
        val response: ResponseEntity<String> = restTemplate.exchange(
            GOOGLE_TOKEN_URL,
            HttpMethod.POST,
            tokenRequest,
            String::class
        )

        return JsonParser.parseString(Objects.requireNonNull(response.body))
            .asJsonObject["access_token"].asString
    }

    fun getKakaoUserInfo(accessToken: String): Oauth2Info {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer $accessToken")
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val profileRequest = HttpEntity<MultiValueMap<String, String>>(httpHeaders)
        val response: ResponseEntity<String> = restTemplate.exchange(
            KAKAO_USERINFO_URL,
            HttpMethod.POST,
            profileRequest,
            String::class
        )

        val element = JsonParser.parseString(response.body)
        return Oauth2Info (
            socialId = element.asJsonObject["id"].asString,
            socialName = element.asJsonObject.getAsJsonObject("properties")["nickname"].asString,
        )
    }

    fun getNaverUserInfo(accessToken: String): Oauth2Info {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer $accessToken")
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val profileRequest = HttpEntity<MultiValueMap<String, String>>(httpHeaders)
        val response: ResponseEntity<String> = restTemplate.exchange(
            NAVER_USERINFO_URL,
            HttpMethod.POST,
            profileRequest,
            String::class
        )

        val element = JsonParser.parseString(response.body)
        return Oauth2Info (
            socialId = element.asJsonObject.getAsJsonObject("response")["id"].asString,
            socialName = element.asJsonObject.getAsJsonObject("response")["name"].asString
        )
    }

    fun getGoogleUserInfo(accessToken: String): Oauth2Info {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer $accessToken")
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val profileRequest = HttpEntity<MultiValueMap<String, String>>(httpHeaders)
        val response: ResponseEntity<String> = restTemplate.exchange(
            GOOGLE_USERINFO_URL,
            HttpMethod.GET,
            profileRequest,
            String::class
        )

        val element = JsonParser.parseString(response.body)
        return Oauth2Info (
            socialId = element.asJsonObject["id"].asString,
            socialName = element.asJsonObject["name"].asString,
        )
    }

}
