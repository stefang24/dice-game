package com.game.dicegame.service;

import com.game.dicegame.model.entity.BetOutcome;
import com.game.dicegame.model.enums.BetType;
import com.game.dicegame.repository.BetOutcomesRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BetOutcomeService {

    private final BetOutcomesRepository betOutcomesRepository;

    public BetOutcome getByTypeAndValue(BetType type, String value)
    {
        return betOutcomesRepository.findByTypeAndValue(type, value).orElse(null);
    }
}
