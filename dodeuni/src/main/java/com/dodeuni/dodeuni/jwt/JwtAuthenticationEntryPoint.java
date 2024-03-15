package com.dodeuni.dodeuni.jwt;

import com.dodeuni.dodeuni.handler.ErrorCode;
import com.dodeuni.dodeuni.handler.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_UNAUTHORIZED);
        response.getWriter().write(errorResponse.convertToJson());
        response.setStatus(errorResponse.getStatus());
    }
}