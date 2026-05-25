package com.game.api.controller;

import com.game.api.dto.LeaderboardResponse;
import com.game.api.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@Tag(name = "Leaderboard", description = "Endpoints for checking global player high scores and statistics")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    @Operation(
        summary = "Get top 10 players", 
        description = "Retrieves a list of the top 10 players ordered by total wins descending. Requires a valid login Authorization bearer token."
    )
    public ResponseEntity<List<LeaderboardResponse>> getLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getTop10Leaderboard());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get a single player's ranking", description = "Retrieves specific leaderboard and statistics rank profiles for a single user ID")
    public ResponseEntity<LeaderboardResponse> getUserRank(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(leaderboardService.getUserLeaderboardRank(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
