package com.game.dicegame.model;

import lombok.Data;

@Data
public class RollResult {
    private int dice1;
    private int dice2;
    private int sum;

    public RollResult(int dice1, int dice2, int sum) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.sum = sum;
    }
}
