package com.enterprise.tracker.app.security;

import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Get custom error message set by JWTAuthFilter

        String errorMessage = (String)request.getAttribute("error");
        if (errorMessage == null) {
            errorMessage = "Unauthorized - Please provide valid credentials";
        }
        System.out.println("Error message from authException: " + errorMessage);

        // Build structured JSON response
        String jsonResponse = String.format(
                "{\"timestamp\":\"%s\", \"status\":401, \"error\":\"Unauthorized\", \"message\":\"%s\"}",
                java.time.Instant.now(), errorMessage);

        response.getWriter().write(jsonResponse);
    }
}

