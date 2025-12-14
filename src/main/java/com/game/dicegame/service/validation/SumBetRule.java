package com.game.dicegame.service.validation;

import com.game.dicegame.model.dto.BetRequest;
import org.springframework.stereotype.Component;

@Component
public class SumBetRule implements BetRule{

    @Override
    public boolean isBetWon(BetRequest betRequest, int sum) {
        return sum == Integer.parseInt(betRequest.getBetValue());
    }
}
