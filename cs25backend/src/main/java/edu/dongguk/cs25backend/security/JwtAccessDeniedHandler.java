package edu.dongguk.cs25backend.security;

import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        sendErrorResponse(response, ErrorCode.ACCESS_DENIED_ERROR);
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