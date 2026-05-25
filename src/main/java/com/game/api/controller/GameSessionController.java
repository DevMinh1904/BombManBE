package com.game.api.controller;

import com.game.api.dto.EndSessionRequest;
import com.game.api.entity.GameSession;
import com.game.api.service.GameSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game-sessions")
@RequiredArgsConstructor
@Tag(name = "Game Sessions", description = "Endpoints for managing active matches and recording session finishes")
public class GameSessionController {

    private final GameSessionService gameSessionService;

    @PostMapping
    @Operation(summary = "Create a new match session", description = "Initializes game session placeholders inside the backend prior to starting the multiplayer match")
    public ResponseEntity<String> createSession() {
        return ResponseEntity.ok("Session successfully initialized");
    }

    @PostMapping("/end")
    @Operation(summary = "End a game match", description = "Persists game metadata and recalculates statistics for participating users")
    public ResponseEntity<GameSession> endSession(@RequestBody EndSessionRequest request) {
        try {
            GameSession gameSession = gameSessionService.recordFinishedMatch(request);
            return ResponseEntity.ok(gameSession);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/history/{userId}")
    @Operation(summary = "Get player match history", description = "Retrieves complete lists of past game sessions where the player took part")
    public ResponseEntity<java.util.List<GameSession>> getPlayerHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(gameSessionService.getPlayerHistory(userId));
    }
}
