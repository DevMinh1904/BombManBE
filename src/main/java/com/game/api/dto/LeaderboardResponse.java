package com.game.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Leaderboard row representing player statistics")
public class LeaderboardResponse {

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Username", example = "bomber_pro")
    private String username;

    @Schema(description = "Total number of wins", example = "42")
    private int totalWins;

    @Schema(description = "Total games played", example = "100")
    private int totalGames;

    @Schema(description = "Average survival time in seconds", example = "154.5")
    private double avgSurvivalTime;
}
