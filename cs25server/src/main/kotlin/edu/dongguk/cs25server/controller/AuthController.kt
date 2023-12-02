package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.dto.request.JoinRequest
import edu.dongguk.cs25server.dto.request.JoinRequestForManager
import edu.dongguk.cs25server.dto.request.LoginRequest
import edu.dongguk.cs25server.dto.response.LoginResponse
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    // 소셜 로그인 - 소비자
    @PostMapping("/customers/kakao")
    fun byKakao(@RequestParam("code") code: String): RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.KAKAO))
    }

    @PostMapping("/customers/naver")
    fun byNaver(@RequestParam("code") code: String): RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.NAVER))
    }

    @PostMapping("/customers/google")
    fun byGoogle(@RequestParam("code") code: String): RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.GOOGLE))
    }

    // 사설 로그인 - 점주
    @PostMapping("/managers/join")
    fun joinManagers(
        @RequestPart @Valid joinRequest: JoinRequestForManager,
        @RequestPart imageFile: MultipartFile
    ): RestResponse<Any> {
        return RestResponse(success = authService.localJoinForManager(joinRequest, imageFile, UserRole.MANAGER))
    }

    @PostMapping("/managers/login")
    fun loginManagers(@RequestBody loginRequest: LoginRequest): RestResponse<LoginResponse> {
        return RestResponse(authService.managerLogin(loginRequest))
    }

    // 사설 로그인 - 본사 담당자
    @PostMapping("/headquarters/join")
    fun joinHeadquarters(@RequestBody @Valid joinRequest: JoinRequest): RestResponse<Any> {
        return RestResponse(success = authService.localJoin(joinRequest, UserRole.HQ))
    }

    @PostMapping("/headquarters/login")
    fun loginHeadquarters(@RequestBody loginRequest: LoginRequest): RestResponse<LoginResponse> {
        return RestResponse(authService.headquartersLogin(loginRequest))
    }

    @PostMapping("/customers/reissue")
    fun reissueCustomer(request: HttpServletRequest): RestResponse<Map<String, String>> {
        return RestResponse(authService.reissueToken(request, UserRole.CUSTOMER))
    }

    @PostMapping("/managers/reissue")
    fun reissueManager(request: HttpServletRequest): RestResponse<Map<String, String>> {
        return RestResponse(authService.reissueToken(request, UserRole.MANAGER))
    }

    @PostMapping("/headquarters/reissue")
    fun reissueHeadquarters(request: HttpServletRequest): RestResponse<Map<String, String>> {
        return RestResponse(authService.reissueToken(request, UserRole.HQ))
    }

}