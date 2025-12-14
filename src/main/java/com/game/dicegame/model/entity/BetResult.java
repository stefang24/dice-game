package com.game.dicegame.model.entity;

import com.game.dicegame.model.enums.BetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "bet_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcome_id", nullable = false)
    private BetOutcome outcome;

    @Column(name = "bet_date", nullable = false)
    private Instant date;

    @Column(name = "bet_amount", nullable = false)
    private Double betAmount;

    @Column(name = "win_amount", nullable = false)
    private Double winAmount;

    @Enumerated(EnumType.STRING)
    private BetStatus status;

    @Column(name = "dice1_result")
    private Integer dice1Result;

    @Column(name = "dice2_result")
    private Integer dice2Result;

    @Column(name = "total_sum")
    private Integer totalSum;
}