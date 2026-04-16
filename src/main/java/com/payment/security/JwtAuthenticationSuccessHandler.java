package com.payment.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String token = jwtTokenProvider.generateToken(authentication.getName());

        // Return a page that will store the jwt token then redirect to the main page
        response.setContentType("text/html");
        response.getWriter().write("""
            <html>
            <script>
                localStorage.setItem('jwt', '%s');
                window.location.href = '/';
            </script>
            </html>
        """.formatted(token));
    }
}