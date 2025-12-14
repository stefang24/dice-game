package com.game.dicegame.controller;

import com.game.dicegame.model.dto.PlayerBalanceResponse;
import com.game.dicegame.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/balance")
    public PlayerBalanceResponse getBalance() {
        PlayerBalanceResponse response = playerService.getPlayerBalance();
        return response;
    }
}