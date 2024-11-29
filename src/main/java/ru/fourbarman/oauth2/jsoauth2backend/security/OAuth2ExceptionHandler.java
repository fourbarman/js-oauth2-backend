package ru.fourbarman.oauth2.jsoauth2backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OAuth2ExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // формируем json body
        final Map<String, Object> jsonBody = new HashMap<>();

        //параметры для нового json
        jsonBody.put("type", authException.getClass().getSimpleName());
        jsonBody.put("class", authException.getClass());
        jsonBody.put("message", authException.getMessage());
        jsonBody.put("exception", authException.getCause());
        jsonBody.put("path", request.getServletPath());
        jsonBody.put("timestamp", new Date().getTime());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // формируем сам json с параметрами
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), jsonBody);
    }
}
