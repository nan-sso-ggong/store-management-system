package edu.dongguk.cs25backend.controller;

import edu.dongguk.cs25backend.domain.type.LoginProvider;
import edu.dongguk.cs25backend.dto.response.LoginResponse;
import edu.dongguk.cs25backend.dto.response.RestResponse;
import edu.dongguk.cs25backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google/callback")
    public RestResponse<LoginResponse> byGoogle(@RequestParam("code") String code) {
        return new RestResponse<>(authService.login(code, LoginProvider.GOOGLE));
    }

    @PostMapping("/kakao/callback")
    public RestResponse<LoginResponse> byKakao(@RequestParam("code") String code) {
        return new RestResponse<>(authService.login(code, LoginProvider.KAKAO));
    }

    @PostMapping("/naver/callback")
    public RestResponse<LoginResponse> byNaver(@RequestParam("code") String code) {
        return new RestResponse<>(authService.login(code, LoginProvider.NAVER));
    }

}
