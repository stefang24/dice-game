package com.game.dicegame.service;

import com.game.dicegame.exception.NotAuthenticatedException;
import com.game.dicegame.model.dto.PlayerBalanceResponse;
import com.game.dicegame.model.entity.Currency;
import com.game.dicegame.model.entity.Player;
import com.game.dicegame.model.entity.PlayerCredentials;
import com.game.dicegame.repository.PlayerCredentialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerCredentialsRepository credentialsRepository;

    @InjectMocks
    private PlayerService playerService;

    private final String TEST_USERNAME = "testUser";
    private final double TEST_BALANCE = 100.00;
    private final String TEST_CURRENCY = "EUR";

    private Currency testCurrency;
    private Player testPlayer;
    private PlayerCredentials testCredentials;
    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        testCurrency = new Currency();
        testCurrency.setName(TEST_CURRENCY);

        testPlayer = new Player();
        testPlayer.setBalance(TEST_BALANCE);
        testPlayer.setCurrency(testCurrency);

        testCredentials = new PlayerCredentials();
        testCredentials.setUsername(TEST_USERNAME);
        testCredentials.setPlayer(testPlayer);

        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
    }

    @Test
    void GetLoggedPlayer_ShouldReturnPlayer_WhenAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(credentialsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testCredentials));

        try (MockedStatic<SecurityContextHolder> utilities = mockStatic(SecurityContextHolder.class)) {
            utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Player loggedPlayer = playerService.GetLoggedPlayer();

            assertNotNull(loggedPlayer);
            assertEquals(TEST_USERNAME, testCredentials.getUsername());
            assertEquals(TEST_BALANCE, loggedPlayer.getBalance());
            verify(credentialsRepository, times(1)).findByUsername(TEST_USERNAME);
        }
    }

    @Test
    void GetLoggedPlayer_ShouldThrowNotAuthenticatedException_WhenAnonymous() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("anonymousUser");

        try (MockedStatic<SecurityContextHolder> utilities = mockStatic(SecurityContextHolder.class)) {
            utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            assertThrows(NotAuthenticatedException.class, () -> playerService.GetLoggedPlayer());
            verify(credentialsRepository, never()).findByUsername(anyString());
        }
    }

    @Test
    void GetLoggedPlayer_ShouldThrowNoSuchElementException_WhenPlayerNotFound() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(credentialsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.empty());

        try (MockedStatic<SecurityContextHolder> utilities = mockStatic(SecurityContextHolder.class)) {
            utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            assertThrows(NoSuchElementException.class, () -> playerService.GetLoggedPlayer());
            verify(credentialsRepository, times(1)).findByUsername(TEST_USERNAME);
        }
    }

    @Test
    void getPlayerBalance_ShouldReturnBalanceResponse_WhenAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(credentialsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testCredentials));

        try (MockedStatic<SecurityContextHolder> utilities = mockStatic(SecurityContextHolder.class)) {
            utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            PlayerBalanceResponse response = playerService.getPlayerBalance();

            assertNotNull(response);
            assertEquals(TEST_BALANCE, response.getBalance());
            assertEquals(TEST_CURRENCY, response.getCurrency());
        }
    }

    @Test
    void getPlayerBalance_ShouldThrowNotAuthenticatedException_WhenAnonymous() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("anonymousUser");

        try (MockedStatic<SecurityContextHolder> utilities = mockStatic(SecurityContextHolder.class)) {
            utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            assertThrows(NotAuthenticatedException.class, () -> playerService.getPlayerBalance());
        }
    }
}