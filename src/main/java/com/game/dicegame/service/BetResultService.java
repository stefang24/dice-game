package com.game.dicegame.service;

import com.game.dicegame.model.entity.BetResult;
import com.game.dicegame.repository.BetResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BetResultService {
    private final BetResultRepository betResultRepository;

    public void addBetResult(BetResult betResult)
    {
        betResultRepository.save(betResult);
    }
}
