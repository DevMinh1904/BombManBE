package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User profile response without sensitive data")
public class UserProfileResponse {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "Username", example = "bomber_pro")
    private String username;

    @Schema(description = "Email", example = "bomber_pro@game.com")
    private String email;
}
