package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.util.Log.Companion.log
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtExceptionFilter : OncePerRequestFilter() {
    private val urls = listOf(
        "/favicon.ico",
        "/oauth2/authorization/kakao", "/oauth2/authorization/naver", "/oauth2/authorization/google",
        "/api/v1/auth/naver/callback", "/api/v1/auth/kakao/callback", "/api/v1/auth/google/callback",
        "/api/v1/auth/managers/join", "/api/v1/auth/managers/login", "/api/v1/auth/headquarters/join", "/api/v1/auth/headquarters/login"
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        response.characterEncoding = "utf-8"
        log.info("JwtExceptionFilter Request doFilterInternal")

        var isException = false
        try {
            filterChain.doFilter(request, response)
            log.info("JwtExceptionFilter Response doFilterInternal")
        } catch (e: SecurityException) {
            log.error("FilterException throw SecurityException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.ACCESS_DENIED_ERROR)
            isException = true
        } catch (e: MalformedJwtException) {
            log.error("FilterException throw MalformedJwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_MALFORMED_ERROR)
            isException = true
        } catch (e: IllegalArgumentException) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_TYPE_ERROR)
            isException = true
        } catch (e: ExpiredJwtException) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_EXPIRED_ERROR)
            isException = true
        } catch (e: UnsupportedJwtException) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_UNSUPPORTED_ERROR)
            isException = true
        } catch (e: JwtException) {
            log.error("FilterException throw JwtException Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.TOKEN_UNKNOWN_ERROR)
            isException = true
        } catch (e: Exception) {
            log.error("FilterException throw Exception : {}", e.message)
            request.setAttribute("exception", ErrorCode.NOT_FOUND_ERROR)
            isException = true
        }

        if (isException) {
            log.info("JwtExceptionFilter Error doFilterInternal")
            filterChain.doFilter(request, response)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI in urls
    }
}
