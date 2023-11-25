package edu.dongguk.cs25server.controller


import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.dto.request.JoinRequest
import edu.dongguk.cs25server.dto.request.JoinRequestForManager
import edu.dongguk.cs25server.dto.request.LoginRequest
import edu.dongguk.cs25server.dto.response.LoginResponse
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val authService: AuthService
) {
    // 소셜 로그인 - 소비자
    @PostMapping("/customers/kakao")
    fun byKakao(@RequestParam("code") code: String) : RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.KAKAO))
    }

    @PostMapping("/customers/naver")
    fun byNaver(@RequestParam("code") code: String) : RestResponse<LoginResponse> {
        return RestResponse(authService.socialLogin(code, LoginProvider.NAVER))
    }

    @PostMapping("/customers/google")
    fun byGoogle(@RequestParam("code") code: String) : RestResponse<LoginResponse>  {
        return RestResponse(authService.socialLogin(code, LoginProvider.GOOGLE))
    }

    // 사설 로그인 - 점주
    @PostMapping("/managers/join")
    fun joinManagers(@RequestBody @Valid joinRequest : JoinRequestForManager) : RestResponse<Any> {
        return RestResponse(success = authService.localJoinForManager(joinRequest, UserRole.MANAGER))
    }

    @PostMapping("/managers/login")
    fun loginManagers(@RequestBody loginRequest: LoginRequest) : RestResponse<LoginResponse> {
        return RestResponse(authService.managerLogin(loginRequest))
    }

    // 사설 로그인 - 본사 담당자
    @PostMapping("/headquarters/join")
    fun joinHeadquarters(@RequestBody @Valid joinRequest : JoinRequest) : RestResponse<Any> {
        return RestResponse(success = authService.localJoin(joinRequest, UserRole.HQ))
    }

    @PostMapping("/headquarters/login")
    fun loginHeadquarters(@RequestBody loginRequest: LoginRequest) : RestResponse<LoginResponse> {
        return RestResponse(authService.headquartersLogin(loginRequest))
    }

    // 로그 아웃
    @PostMapping("/customers/logout")
    fun logoutCustomer(@UserId customerId: Long) : RestResponse<Any> {
        return RestResponse(success = authService.logout(customerId, UserRole.CUSTOMER))
    }

    @PostMapping("/managers/logout")
    fun logoutManager(@UserId managerId: Long) : RestResponse<Any> {
        return RestResponse(success = authService.logout(managerId, UserRole.MANAGER))
    }

    @PostMapping("/headquarters/logout")
    fun logoutHeadquarters(@UserId headquartersId: Long) : RestResponse<Any> {
        return RestResponse(success = authService.logout(headquartersId, UserRole.HQ))
    }

}