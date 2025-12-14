package com.game.dicegame.repository;

import com.game.dicegame.model.entity.BetLimits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetLimitsRepository extends JpaRepository<BetLimits, Long> {
}
