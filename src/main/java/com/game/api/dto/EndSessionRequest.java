package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to record finished match and update stats")
public class EndSessionRequest {

    @Schema(description = "List of player User IDs participating in the game session", example = "[1, 2, 3]")
    private List<Long> playerIds;

    @Schema(description = "User ID of the session winner", example = "1")
    private Long winnerId;

    @Schema(description = "Duration of the match in seconds", example = "180")
    private Integer durationSeconds;
}
