package com.game.dicegame.middleware;

import com.game.dicegame.exception.InvalidBetException;
import com.game.dicegame.exception.NotAuthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAuthenticatedException.class)
    public ResponseEntity<String> handleNotAuthenticatedException(NotAuthenticatedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidBetException.class)
    public ResponseEntity<String> handleInvalidBetException(InvalidBetException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Not found.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        String message = "Authentication failed: Invalid username or password.";
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(message);
    }
}