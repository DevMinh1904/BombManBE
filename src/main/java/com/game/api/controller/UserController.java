package com.game.api.controller;

import com.game.api.dto.UpdateProfileRequest;
import com.game.api.dto.UserProfileResponse;
import com.game.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing user accounts and retrieving player profile information")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get player profile information", description = "Retrieves user statistics, profile information, and account details by user ID")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long id) {
        try {
            UserProfileResponse profile = userService.getUserProfile(id);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update player profile settings", description = "Updates user username and email with validation checks")
    public ResponseEntity<UserProfileResponse> updateUserProfile(@PathVariable Long id, @RequestBody UpdateProfileRequest request) {
        try {
            UserProfileResponse profile = userService.updateUserProfile(id, request);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
