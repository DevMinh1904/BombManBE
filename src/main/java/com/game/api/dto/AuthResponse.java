package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authentication response containing login token")
public class AuthResponse {

    @Schema(description = "Stub JWT or authentication token string", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.stub-token")
    private String token;

    @Schema(description = "Player's username", example = "bomber_pro")
    private String username;
}
