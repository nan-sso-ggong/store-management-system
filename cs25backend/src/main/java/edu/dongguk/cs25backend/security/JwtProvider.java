package edu.dongguk.cs25backend.security;


import edu.dongguk.cs25backend.domain.type.UserRole;
import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import edu.dongguk.cs25backend.repository.CustomerRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {
    private final CustomerRepository customerRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.accessExpiredMs}")
    private Long accessExpiredMs;
    @Value("${jwt.refreshExpiredMs}")
    private Long refreshExpiredMs;
    private Key key;


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(Long id, UserRole userRole, boolean isAccess) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        if(isAccess) {
            claims.put("role", userRole);
        }

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (isAccess ? accessExpiredMs : refreshExpiredMs)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public JwtToken createTotalToken(Long id, UserRole userRole) {
        String accessToken = createToken(id, userRole, true);
        String refreshToken = createToken(id, userRole, false);

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String validRefreshToken(HttpServletRequest request) throws JwtException {
        String refreshToken = refineToken(request);
        Claims claims = validateToken(refreshToken);
        UserLoginForm customer = customerRepository.findByIdAndRefreshToken(Long.valueOf(claims.get("id").toString())).get();

        return createToken(customer.getId(), customer.getRole(), true);
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id").toString();
    }

    public Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String refineToken(HttpServletRequest request) throws JwtException {
        String beforeToken = request.getHeader("Authorization");

        String afterToken = null;
        if (StringUtils.hasText(beforeToken) && beforeToken.startsWith("Bearer ")) {
            afterToken =  beforeToken.substring(7);
        } else {
            throw new CS25Exception(ErrorCode.TOKEN_INVALID_ERROR);
        }
        return afterToken;
    }
}