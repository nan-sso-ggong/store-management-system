package edu.dongguk.cs25backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/kakao")
    public void getKakaoRedirectUrl() {

    }

    @PostMapping("/kakao/customer")
    public void loginByKakao(@RequestParam("code") String code) {

    }

    @GetMapping("/naver")
    public void getNaverRedirectUrl() {

    }

    @PostMapping("/naver/customer")
    public void loginByNaver(@RequestParam("code") String code) {

    }

    @GetMapping("/google")
    public void getGoogleRedirectUrl() {

    }

    @PostMapping("/google/customer")
    public void loginByGoogle(@RequestParam("code") String code) {

    }
}
