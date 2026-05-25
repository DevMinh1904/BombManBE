package com.game.api.service;

import com.game.api.dto.AuthResponse;
import com.game.api.dto.LoginRequest;
import com.game.api.dto.RegisterRequest;
import com.game.api.entity.User;
import com.game.api.entity.Leaderboard;
import com.game.api.repository.UserRepository;
import com.game.api.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LeaderboardRepository leaderboardRepository;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Password criteria: Must be over 8 characters, at least 1 digit, at least 1 lowercase letter, at least 1 uppercase letter, and at least 1 special symbol (e.g. @, !, *, etc.)
        String password = request.getPassword();
        if (password == null || password.length() <= 8) {
            throw new IllegalArgumentException("Password must be over 8 characters");
        }
        
        // Regex checking for required rules
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*");

        if (!hasDigit || !hasLower || !hasUpper || !hasSpecial) {
            throw new IllegalArgumentException("Password must contain at least one number, one lowercase letter, one uppercase letter, and one special character (e.g., @, !, *, ...)");
        }

        // Secure password hashing with SHA-256
        String passwordHash = hashPassword(password);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordHash)
                .build();

        User savedUser = userRepository.save(user);

        // Auto-initialize leaderboard entry for the player
        Leaderboard leaderboard = Leaderboard.builder()
                .user(savedUser)
                .totalWins(0)
                .totalGames(0)
                .avgSurvivalTime(0.0)
                .build();
        leaderboardRepository.save(leaderboard);

        return savedUser;
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // Verify hash
        String expectedHash = hashPassword(request.getPassword());
        if (!expectedHash.equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Stub token generation
        String stubToken = "eyJhbGciOiJIUzI1NiJ9.STUB_TOKEN_FOR_" + user.getUsername() + "." + System.currentTimeMillis();

        return AuthResponse.builder()
                .token(stubToken)
                .username(user.getUsername())
                .build();
    }

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error hashing password", ex);
        }
    }
}
