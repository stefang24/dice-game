package com.game.dicegame.engine;

import com.game.dicegame.exception.InvalidBetException;
import com.game.dicegame.model.RollResult;
import com.game.dicegame.model.dto.BetRequest;
import com.game.dicegame.model.dto.BetResponse;
import com.game.dicegame.model.entity.BetResult;
import com.game.dicegame.model.entity.Player;
import com.game.dicegame.model.enums.BetStatus;
import com.game.dicegame.model.enums.BetType;
import com.game.dicegame.service.BetOutcomeService;
import com.game.dicegame.service.BetResultService;
import com.game.dicegame.service.PlayerService;
import com.game.dicegame.service.validation.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class GameEngine {
    private PlayerService playerService;
    private Map<BetType, BetRule> rules;

    private BetOutcomeService betOutcomeService;
    private BetResultService betResultService;

    public BetResponse placeBet(BetRequest betRequest)
    {
        Player player = playerService.GetLoggedPlayer();

        isValidBet(betRequest, player);

        RollResult rollResult = rollDices();

        BetRule rule = getRule(betRequest.getBetType());

        boolean isWin = rule.isBetWon(betRequest, rollResult.getSum());

        player.setBalance(player.getBalance() - betRequest.getBetAmount());
        player.setBalance(player.getBalance() + calculateWin(isWin,betRequest));

        BetResult betResult = BetResult.builder()
                        .betAmount(betRequest.getBetAmount())
                        .dice1Result(rollResult.getDice1())
                        .dice2Result(rollResult.getDice2())
                        .date(new Date().toInstant())
                        .player(player)
                        .outcome(betOutcomeService.getByTypeAndValue(betRequest.getBetType(), betRequest.getBetValue()))
                        .winAmount(calculateWin(isWin,betRequest))
                        .status(isWin ? BetStatus.WIN : BetStatus.LOSE)
                        .totalSum(rollResult.getSum()).build();

        betResultService.addBetResult(betResult);

        BetResponse betResponse = BetResponse.builder()
                        .dice1Result(rollResult.getDice1())
                        .dice2Result(rollResult.getDice2())
                        .sum(rollResult.getSum())
                        .winAmount(calculateWin(isWin,betRequest))
                        .betWon(isWin)
                        .updatedBalance(player.getBalance())
                        .betId(betResult.getId())
                        .betDate(betResult.getDate()).build();

        return betResponse;
    }

    private RollResult rollDices()
    {
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;

        return new RollResult(dice1, dice2, dice1 + dice2);
    }

    private BetRule getRule(BetType betType)
    {
        return rules.get(betType);
    }

    private double calculateWin(boolean betWon, BetRequest betRequest)
    {
        if(betWon)
        {
            return betOutcomeService.getByTypeAndValue(betRequest.getBetType(), betRequest.getBetValue()).getOdd() * betRequest.getBetAmount();
        }
        return 0.0;
    }

    private void isValidBet(BetRequest betRequest, Player player)
    {
        if(betOutcomeService.getByTypeAndValue(betRequest.getBetType(), betRequest.getBetValue()) == null)
            throw new InvalidBetException("Invalid Bet Value");

        if(player.getCurrency().getBetLimits().getMinLimit() > betRequest.getBetAmount() || player.getCurrency().getBetLimits().getMaxLimit() < betRequest.getBetAmount())
            throw new InvalidBetException("Bet amount must be in range: " + player.getCurrency().getBetLimits().getMinLimit() + " - " +  player.getCurrency().getBetLimits().getMaxLimit() + " " + player.getCurrency().getName());

        if(player.getBalance() < betRequest.getBetAmount())
            throw new InvalidBetException("INSUFFICIENT BALANCE");
    }
}