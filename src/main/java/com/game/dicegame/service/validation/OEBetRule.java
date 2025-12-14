package com.game.dicegame.service.validation;

import com.game.dicegame.model.dto.BetRequest;
import com.game.dicegame.model.enums.OEType;
import org.springframework.stereotype.Component;

@Component
public class OEBetRule implements BetRule {

    @Override
    public boolean isBetWon(BetRequest betRequest, int sum) {
        if((betRequest.getBetValue().equals(OEType.EVEN.toString()) && sum%2==0) || (betRequest.getBetValue().equals(OEType.ODD.toString()) && sum%2==1))
            return true;
        return false;
    }
}