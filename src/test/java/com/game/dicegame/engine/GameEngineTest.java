package com.game.dicegame.engine;

import com.game.dicegame.exception.InvalidBetException;
import com.game.dicegame.model.RollResult;
import com.game.dicegame.model.dto.BetRequest;
import com.game.dicegame.model.dto.BetResponse;
import com.game.dicegame.model.entity.*;
import com.game.dicegame.model.enums.BetStatus;
import com.game.dicegame.model.enums.BetType;
import com.game.dicegame.service.BetOutcomeService;
import com.game.dicegame.service.BetResultService;
import com.game.dicegame.service.PlayerService;
import com.game.dicegame.service.validation.BetRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameEngineTest {

    @Mock
    private PlayerService playerService;
    @Mock
    private BetOutcomeService betOutcomeService;

    @InjectMocks
    private GameEngine gameEngine;

    private Player player;
    private BetRequest validBetRequest;
    private BetOutcome winningOutcome;
    private BetOutcome losingOutcome;

    private final double INITIAL_BALANCE = 100.0;
    private final double BET_AMOUNT = 10.0;
    private final double WINNING_ODD = 5.0;

    @BeforeEach
    void setUp() {
        Currency currency = new Currency();
        currency.setName("EUR");

        BetLimits betLimits = new BetLimits();
        betLimits.setMinLimit(5.0);
        betLimits.setMaxLimit(50.0);
        currency.setBetLimits(betLimits);

        player = new Player();
        player.setBalance(INITIAL_BALANCE);
        player.setCurrency(currency);

        validBetRequest = new BetRequest(BET_AMOUNT, BetType.SUM, "7");

        winningOutcome = new BetOutcome();
        winningOutcome.setOdd(WINNING_ODD);
        winningOutcome.setType(BetType.SUM);
        winningOutcome.setValue("7");
        losingOutcome = new BetOutcome();
        losingOutcome.setOdd(1.0);
        losingOutcome.setType(BetType.SUM);
        losingOutcome.setValue("2");

        when(playerService.GetLoggedPlayer()).thenReturn(player);
    }

    @Test
    void placeBet_ShouldThrowInvalidBetException_WhenOutcomeIsInvalid() {
        when(betOutcomeService.getByTypeAndValue(BetType.SUM, "7")).thenReturn(null);

        assertThrows(InvalidBetException.class, () -> gameEngine.placeBet(validBetRequest));
    }

    @Test
    void placeBet_ShouldThrowInvalidBetException_WhenBetAmountIsTooLow() {
        validBetRequest.setBetAmount(4.0);
        when(betOutcomeService.getByTypeAndValue(BetType.SUM, "7")).thenReturn(winningOutcome);

        assertThrows(InvalidBetException.class, () -> gameEngine.placeBet(validBetRequest));
    }

    @Test
    void placeBet_ShouldThrowInvalidBetException_WhenInsufficientBalance() {
        validBetRequest.setBetAmount(110.0);
        when(betOutcomeService.getByTypeAndValue(BetType.SUM, "7")).thenReturn(winningOutcome);

        assertThrows(InvalidBetException.class, () -> gameEngine.placeBet(validBetRequest));
    }
}