package com.game.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leaderboards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;

    @Column(name = "total_wins", nullable = false)
    private int totalWins;

    @Column(name = "total_games", nullable = false)
    private int totalGames;

    @Column(name = "avg_survival_time", nullable = false)
    private double avgSurvivalTime;
}
