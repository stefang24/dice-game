package com.game.dicegame.repository;

import com.game.dicegame.model.entity.BetResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetResultRepository extends JpaRepository<BetResult, Long> {
}
