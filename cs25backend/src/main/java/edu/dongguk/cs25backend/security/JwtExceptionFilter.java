package edu.dongguk.cs25backend.security;

import edu.dongguk.cs25backend.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final String[] urls = { "/favicon.ico",
            "/api/v1/auth/naver/callback",
            "/api/v1/auth/kakao/callback",
            "/api/v1/auth/google/callback"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        log.info("JwtExceptionFilter Request doFilterInternal");

        boolean isException = false;
        try {
            filterChain.doFilter(request, response);

            log.info("JwtExceptionFilter Response doFilterInternal");
        } catch (SecurityException e) {
            log.error("FilterException throw SecurityException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.ACCESS_DENIED_ERROR);
            isException = true;
        }
        catch (MalformedJwtException e) {
            log.error("FilterException throw MalformedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_MALFORMED_ERROR);
            isException = true;
        } catch (IllegalArgumentException e) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_TYPE_ERROR);
            isException = true;
        } catch (ExpiredJwtException e) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_EXPIRED_ERROR);
            isException = true;
        } catch (UnsupportedJwtException e) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_UNSUPPORTED_ERROR);
            isException = true;
        } catch (JwtException e) {
            log.error("FilterException throw JwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_UNKNOWN_ERROR);
            isException = true;
        } catch (Exception e) {
            log.error("FilterException throw Exception Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.NOT_FOUND_ERROR);
            isException = true;
        }

        if (isException) {
            log.info("JwtExceptionFilter Error doFilterInternal");
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(urls).filter(url -> url.equals(request.getRequestURI())).count() > 0;
    }
}
