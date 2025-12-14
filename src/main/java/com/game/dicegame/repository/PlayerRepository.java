package com.game.dicegame.repository;

import com.game.dicegame.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, Long> {
}
