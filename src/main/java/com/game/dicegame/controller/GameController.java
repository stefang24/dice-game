package com.game.dicegame.controller;

import com.game.dicegame.model.dto.BetRequest;
import com.game.dicegame.model.dto.BetResponse;
import com.game.dicegame.model.entity.BetResult;
import com.game.dicegame.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@AllArgsConstructor
public class GameController {

    private GameService gameService;

    @PostMapping("/bet")
    public BetResponse placeBet(@RequestBody BetRequest request) {
        return gameService.placeBet(request);
    }
}
