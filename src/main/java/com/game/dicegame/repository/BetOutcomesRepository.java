package com.game.dicegame.repository;

import com.game.dicegame.model.entity.BetOutcome;
import com.game.dicegame.model.enums.BetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BetOutcomesRepository extends JpaRepository<BetOutcome, Long> {
    Optional<BetOutcome> findByTypeAndValue(BetType type, String value);
}
