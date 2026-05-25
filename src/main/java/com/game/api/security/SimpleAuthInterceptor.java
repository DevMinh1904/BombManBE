package com.game.api.security;

import com.game.api.entity.User;
import com.game.api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SimpleAuthInterceptor extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();

        // Only enforce authentication on the leaderboard endpoints
        if (path.startsWith("/api/leaderboard")) {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Missing or invalid Authorization header");
                return;
            }

            String token = authHeader.substring(7);

            // Our login returns a stub token: eyJhbGciOiJIUzI1NiJ9.STUB_TOKEN_FOR_[username].[timestamp]
            // We validate the token format and make sure the user exists in our database.
            if (!token.contains("STUB_TOKEN_FOR_")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid token format");
                return;
            }

            try {
                // Extract username from token
                String userPart = token.substring(token.indexOf("STUB_TOKEN_FOR_") + 15);
                String username = userPart.split("\\.")[0];

                Optional<User> userOpt = userRepository.findByUsername(username);
                if (userOpt.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized: User not found");
                    return;
                }

                // Attach user entity to request context for downstream services
                request.setAttribute("currentUser", userOpt.get());

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Failed to parse authentication token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
