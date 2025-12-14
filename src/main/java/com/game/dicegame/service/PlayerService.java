package com.game.dicegame.service;

import com.game.dicegame.exception.NotAuthenticatedException;
import com.game.dicegame.model.dto.PlayerBalanceResponse;
import com.game.dicegame.model.entity.Player;
import com.game.dicegame.model.entity.PlayerCredentials;
import com.game.dicegame.repository.PlayerCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerCredentialsRepository credentialsRepository;

    public PlayerBalanceResponse getPlayerBalance() {
        Player player = GetLoggedPlayer();

        if (player == null) {
            throw new NoSuchElementException("Player not connected");
        }

        PlayerBalanceResponse pbr = PlayerBalanceResponse.builder()
                .balance(player.getBalance())
                .currency(player.getCurrency().getName())
                .build();

        return pbr;
    }

    public Player GetLoggedPlayer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(username)) {
            throw new NotAuthenticatedException("You must log in first.");
        }
        PlayerCredentials credentials = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Player not found"));
        return credentials.getPlayer();
    }
}