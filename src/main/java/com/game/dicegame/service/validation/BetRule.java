package com.game.dicegame.service.validation;

import com.game.dicegame.model.dto.BetRequest;

public interface BetRule {
    boolean isBetWon(BetRequest betRequest, int sum);
}
