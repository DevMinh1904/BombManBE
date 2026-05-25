package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for updating user profile info")
public class UpdateProfileRequest {

    @Schema(description = "New username for the player", example = "bomber_pro_updated")
    private String username;

    @Schema(description = "New email address", example = "bomber_pro_new@game.com")
    private String email;
}
