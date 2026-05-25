package com.game.api.controller;

import com.game.api.dto.AuthResponse;
import com.game.api.dto.LoginRequest;
import com.game.api.dto.RegisterRequest;
import com.game.api.entity.User;
import com.game.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for Player Registration and Authentication Login stubs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new User (Player)", description = "Registers a player credentials and auto-initializes their empty leaderboard record")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.register(request);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login an existing User", description = "Verifies player credentials and returns a simulated token")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout player", description = "Simulates logging out the current active session")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Successfully logged out");
    }
}
