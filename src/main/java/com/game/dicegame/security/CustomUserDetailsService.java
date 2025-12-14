package com.game.dicegame.security;

import com.game.dicegame.model.entity.PlayerCredentials;
import com.game.dicegame.repository.PlayerCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PlayerCredentialsRepository playerCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlayerCredentials playerCredentials = playerCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(playerCredentials.getUsername())
                .password(playerCredentials.getPassword())
                .build();
    }
}