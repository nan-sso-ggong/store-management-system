package edu.dongguk.cs25server.controller


import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.dto.response.LoginResponse
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val authService: AuthService
) {

    @PostMapping("/kakao/callback")
    fun byKakao(@RequestParam("code") code: String) : RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.KAKAO))
    }

    @PostMapping("/naver/callback")
    fun byNaver(@RequestParam("code") code: String) : RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.NAVER))
    }

    @PostMapping("/google/callback")
    fun byGoogle(@RequestParam("code") code: String) : RestResponse<LoginResponse>  {
        return RestResponse(authService.socialLogin(code, LoginProvider.GOOGLE))
    }


}