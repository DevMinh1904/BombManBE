package com.game.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Standard list encapsulated as a comma-separated string or custom converter.
    // For ease of use and maximum compatibility without extra converter class, we map players as a simple comma-separated String or text field.
    @Column(name = "player_ids", nullable = false, columnDefinition = "TEXT")
    private String playerIds;

    @Column(name = "winner_id")
    private Long winnerId;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
