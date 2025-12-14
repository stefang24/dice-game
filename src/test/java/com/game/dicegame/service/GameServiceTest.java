package com.game.dicegame.service;

import com.game.dicegame.engine.GameEngine;
import com.game.dicegame.model.dto.BetRequest;
import com.game.dicegame.model.dto.BetResponse;
import com.game.dicegame.model.enums.BetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameEngine gameEngine;

    @InjectMocks
    private GameService gameService;

    private BetRequest testRequest;
    private BetResponse expectedResponse;

    @BeforeEach
    void setUp() {

        testRequest = new BetRequest(5.0, BetType.SUM, "7");

        expectedResponse = new BetResponse();
        expectedResponse.setBetWon(true);
        expectedResponse.setUpdatedBalance(150.0);
    }

    @Test
    void placeBet_ShouldDelegateCallToGameEngineAndReturnResponse() {
        when(gameEngine.placeBet(testRequest)).thenReturn(expectedResponse);

        BetResponse actualResponse = gameService.placeBet(testRequest);

        verify(gameEngine, times(1)).placeBet(testRequest);

        assertNotNull(actualResponse, "The answer should not be null");
        assertEquals(expectedResponse.isBetWon(), actualResponse.isBetWon(), "Win status needs to match.");
        assertEquals(expectedResponse.getUpdatedBalance(), actualResponse.getUpdatedBalance(), "Balance needs to match.");
    }
}