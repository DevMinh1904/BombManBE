package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for user registration")
public class RegisterRequest {

    @Schema(description = "Unique username for the player", example = "bomber_pro")
    private String username;

    @Schema(description = "Unique email address", example = "bomber_pro@game.com")
    private String email;

    @Schema(description = "Password for registration", example = "securePassword123")
    private String password;
}
