package com.game.api.repository;

import com.game.api.entity.Leaderboard;
import com.game.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    
    Optional<Leaderboard> findByUser(User user);
    Optional<Leaderboard> findByUserId(Long userId);

    @Query("SELECT l FROM Leaderboard l ORDER BY l.totalWins DESC LIMIT 10")
    List<Leaderboard> findTop10ByTotalWinsDesc();
}
