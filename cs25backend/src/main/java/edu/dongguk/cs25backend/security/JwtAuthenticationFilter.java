package edu.dongguk.cs25backend.security;


import edu.dongguk.cs25backend.domain.type.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    final private JwtProvider jwtProvider;
    final private CustomUserDetailService customUserDetailService;

    private final String[] urls = { "/favicon.ico",
            "/api/v1/auth/naver/callback",
            "/api/v1/auth/kakao/callback",
            "/api/v1/auth/google/callback"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = JwtProvider.refineToken(request);
        Claims claims = jwtProvider.validateToken(token);

        String userId = claims.get("id").toString();
        String userRole = claims.get("role").toString();

        log.info("userId = {}, userRole = {}", userId, userRole);

//        CustomUserDetail userDetails = (CustomUserDetail) customUserDetailService.loadUserByUsername(userId);
        CustomUserDetail userDetails = (CustomUserDetail) customUserDetailService.loadUserByUsernameAndUserRole(userId, userRole);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(urls).filter(url -> url.equals(request.getRequestURI())).count() > 0;
    }
}
