package com.game.dicegame.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetResponse {
    private int dice1Result;
    private int dice2Result;
    private int sum;

    private boolean betWon;
    private double winAmount;
    private double updatedBalance;

    private Long betId;
    private Instant betDate;
}
