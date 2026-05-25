package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for user login")
public class LoginRequest {

    @Schema(description = "Player's username", example = "bomber_pro")
    private String username;

    @Schema(description = "Player's password", example = "securePassword123")
    private String password;
}
