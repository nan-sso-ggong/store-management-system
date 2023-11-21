package edu.dongguk.cs25backend.security;

import edu.dongguk.cs25backend.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute("exception");

        if (errorCode == null) {
            sendErrorResponse(response, ErrorCode.NOT_END_POINT);
        } else {
            switch (errorCode) {
                case NOT_FOUND_ERROR -> sendErrorResponse(response, ErrorCode.NOT_FOUND_ERROR);
                case ACCESS_DENIED_ERROR -> { sendErrorResponse(response, ErrorCode.ACCESS_DENIED_ERROR); }
                case TOKEN_MALFORMED_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_MALFORMED_ERROR); }
                case TOKEN_EXPIRED_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED_ERROR); }
                case TOKEN_TYPE_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_TYPE_ERROR); }
                case TOKEN_UNSUPPORTED_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_UNSUPPORTED_ERROR); }
                case TOKEN_UNKNOWN_ERROR -> { sendErrorResponse(response, ErrorCode.TOKEN_UNKNOWN_ERROR); }
            }
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> map = new HashMap<>();
        map.put("success", Boolean.FALSE);
        map.put("data", null);
        map.put("error", errorCode.getMessage());

        response.getWriter().print(JSONValue.toJSONString(map));
    }
}