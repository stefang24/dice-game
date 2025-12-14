package com.game.dicegame.service;

import com.game.dicegame.model.dto.AuthRequest;
import com.game.dicegame.model.dto.AuthResponse;
import com.game.dicegame.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private AuthRequest validAuthRequest;
    private AuthRequest invalidAuthRequest;
    private final String VALID_USERNAME = "testUser";
    private final String VALID_PASSWORD = "password123";
    private final String MOCK_JWT_TOKEN = "mocked.jwt.token";

    @BeforeEach
    void setUp() {
        validAuthRequest = new AuthRequest();
        validAuthRequest.setUsername(VALID_USERNAME);
        validAuthRequest.setPassword(VALID_PASSWORD);

        invalidAuthRequest = new AuthRequest();
        invalidAuthRequest.setUsername("wrongUser");
        invalidAuthRequest.setPassword("wrongPass");
    }

    @Test
    void login_ShouldReturnAuthResponse_WhenAuthenticationSuccessful() {
        Authentication mockAuthentication = mock(Authentication.class);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(VALID_USERNAME, VALID_PASSWORD)))
                .thenReturn(mockAuthentication);

        when(mockAuthentication.getName()).thenReturn(VALID_USERNAME);

        when(jwtTokenProvider.generateToken(VALID_USERNAME)).thenReturn(MOCK_JWT_TOKEN);

        AuthResponse response = authService.login(validAuthRequest);

        assertNotNull(response);
        assertEquals(MOCK_JWT_TOKEN, response.getToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).generateToken(VALID_USERNAME);
    }

    @Test
    void login_ShouldThrowAuthenticationException_WhenAuthenticationFails() {
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(invalidAuthRequest.getUsername(), invalidAuthRequest.getPassword())))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        assertThrows(AuthenticationException.class, () -> authService.login(invalidAuthRequest));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, never()).generateToken(anyString());
    }
}