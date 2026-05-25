package com.game.api.repository;

import com.game.api.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    
    @Query(value = "SELECT * FROM game_sessions WHERE ',' || player_ids || ',' LIKE '%,' || :playerId || ',%'", nativeQuery = true)
    List<GameSession> findSessionsByPlayerId(@Param("playerId") String playerId);
}
