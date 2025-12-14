package com.game.dicegame.controller;

import com.game.dicegame.model.dto.AuthRequest;
import com.game.dicegame.model.dto.AuthResponse;
import com.game.dicegame.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}