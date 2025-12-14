package com.game.dicegame.service;

import com.game.dicegame.engine.GameEngine;
import com.game.dicegame.model.dto.BetRequest;
import com.game.dicegame.model.dto.BetResponse;
import com.game.dicegame.model.entity.BetResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

    private GameEngine gameEngine;

    public BetResponse placeBet (BetRequest request) {
        return gameEngine.placeBet(request);
    }
}
