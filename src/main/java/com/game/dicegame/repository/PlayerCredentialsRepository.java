package com.game.dicegame.repository;

import com.game.dicegame.model.entity.PlayerCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerCredentialsRepository  extends JpaRepository<PlayerCredentials, Long> {
    Optional<PlayerCredentials> findByUsername(String username);
}