package com.game.api.service;

import com.game.api.dto.EndSessionRequest;
import com.game.api.entity.GameSession;
import com.game.api.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final LeaderboardService leaderboardService;

    @Transactional
    public GameSession recordFinishedMatch(EndSessionRequest request) {
        // Build playerIds list as string comma-separated
        String playersJoined = request.getPlayerIds().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        GameSession session = GameSession.builder()
                .playerIds(playersJoined)
                .winnerId(request.getWinnerId())
                .durationSeconds(request.getDurationSeconds())
                .build();

        GameSession savedSession = gameSessionRepository.save(session);

        // Update stats for all players
        for (Long playerId : request.getPlayerIds()) {
            boolean isWinner = playerId.equals(request.getWinnerId());
            // If they are the winner, they survived the whole duration.
            // If not, we can simulate survival time or just set it to duration for simplification,
            // let's assume they survived up to a certain random factor or the full length.
            double survivalTime = isWinner ? request.getDurationSeconds() : (request.getDurationSeconds() * 0.7);
            leaderboardService.updatePlayerStats(playerId, isWinner, survivalTime);
        }

        return savedSession;
    }

    public java.util.List<GameSession> getPlayerHistory(Long playerId) {
        return gameSessionRepository.findSessionsByPlayerId(String.valueOf(playerId));
    }
}
