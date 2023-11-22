package edu.dongguk.cs25server.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val customUserDetailService: CustomUserDetailService
) : OncePerRequestFilter() {
    private val urls = listOf(
        "/favicon.ico",
        "/oauth2/authorization/kakao", "/oauth2/authorization/naver", "/oauth2/authorization/google",
        "/api/v1/auth/naver/callback", "/api/v1/auth/kakao/callback", "/api/v1/auth/google/callback"
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token : String = JwtProvider.refineToken(request)
        val claims = jwtProvider.validateToken(token)

        val userId = claims["id"].toString()
        val role = claims["role"].toString()

        val userDetails : CustomUserDetail = customUserDetailService.loadUserByUsernameAndUserRole(userId, role)

        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI in urls
    }
}