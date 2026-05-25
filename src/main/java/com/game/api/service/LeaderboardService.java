package com.game.api.service;

import com.game.api.dto.LeaderboardResponse;
import com.game.api.entity.Leaderboard;
import com.game.api.entity.User;
import com.game.api.repository.LeaderboardRepository;
import com.game.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final UserRepository userRepository;

    public List<LeaderboardResponse> getTop10Leaderboard() {
        List<Leaderboard> top10 = leaderboardRepository.findTop10ByTotalWinsDesc();
        return top10.stream()
                .map(l -> LeaderboardResponse.builder()
                        .userId(l.getUser().getId())
                        .username(l.getUser().getUsername())
                        .totalWins(l.getTotalWins())
                        .totalGames(l.getTotalGames())
                        .avgSurvivalTime(l.getAvgSurvivalTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePlayerStats(Long userId, boolean isWinner, double survivalTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Leaderboard stats = leaderboardRepository.findByUser(user)
                .orElseGet(() -> Leaderboard.builder()
                        .user(user)
                        .totalWins(0)
                        .totalGames(0)
                        .avgSurvivalTime(0.0)
                        .build());

        int oldGames = stats.getTotalGames();
        double oldAvg = stats.getAvgSurvivalTime();

        stats.setTotalGames(oldGames + 1);
        if (isWinner) {
            stats.setTotalWins(stats.getTotalWins() + 1);
        }

        // Calculate new average survival time
        double newAvg = ((oldAvg * oldGames) + survivalTime) / stats.getTotalGames();
        stats.setAvgSurvivalTime(newAvg);

        leaderboardRepository.save(stats);
    }

    public LeaderboardResponse getUserLeaderboardRank(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        
        Leaderboard stats = leaderboardRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Leaderboard entry not found for user: " + userId));

        return LeaderboardResponse.builder()
                .userId(stats.getUser().getId())
                .username(stats.getUser().getUsername())
                .totalWins(stats.getTotalWins())
                .totalGames(stats.getTotalGames())
                .avgSurvivalTime(stats.getAvgSurvivalTime())
                .build();
    }
}
