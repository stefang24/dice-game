package com.game.dicegame.config;

import com.game.dicegame.model.entity.*;
import com.game.dicegame.model.enums.BetType;
import com.game.dicegame.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CurrencyRepository currencyRepository;
    private final BetLimitsRepository betLimitsRepository;
    private final BetOutcomesRepository betOutcomeRepository;
    private final PlayerRepository playerRepository;
    private final PlayerCredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    private static final double MARGIN = 0.95;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Currency eur = seedCurrencyAndLimits("EUR", 1.0, 500.0);
        List<BetOutcome> outcomes = seedBetOutcomes();

        Player testPlayer = seedPlayerAndCredentials(eur);

    }
    private Currency seedCurrencyAndLimits(String name, Double min, Double max) {
        Currency currency = new Currency();
        currency.setName(name);
        currency = currencyRepository.save(currency);

        BetLimits limits = new BetLimits();
        limits.setCurrency(currency);
        limits.setMinLimit(min);
        limits.setMaxLimit(max);

        betLimitsRepository.save(limits);

        return currency;
    }

    private List<BetOutcome> seedBetOutcomes() {
        List<BetOutcome> outcomes = new ArrayList<>();

        Map<Integer, Double> sumProbabilities = new HashMap<>() {{
            put(2, 1.0/36); put(3, 2.0/36); put(4, 3.0/36); put(5, 4.0/36);
            put(6, 5.0/36); put(7, 6.0/36); put(8, 5.0/36); put(9, 4.0/36);
            put(10, 3.0/36); put(11, 2.0/36); put(12, 1.0/36);
        }};

        sumProbabilities.forEach((sum, prob) -> {
            BetOutcome outcome = new BetOutcome();
            outcome.setType(BetType.SUM);
            outcome.setValue(String.valueOf(sum));
            outcome.setOdd(calculateOdd(prob));
            outcomes.add(outcome);
        });

        double oeProbability = 0.5;
        double oeOdd = calculateOdd(oeProbability);

        BetOutcome oddOutcome = new BetOutcome();
        oddOutcome.setType(BetType.OE);
        oddOutcome.setValue("ODD");
        oddOutcome.setOdd(oeOdd);
        outcomes.add(oddOutcome);

        BetOutcome evenOutcome = new BetOutcome();
        evenOutcome.setType(BetType.OE);
        evenOutcome.setValue("EVEN");
        evenOutcome.setOdd(oeOdd);
        outcomes.add(evenOutcome);

        betOutcomeRepository.saveAll(outcomes);
        return outcomes;
    }

    private Player seedPlayerAndCredentials(Currency currency) {
        Player player = new Player();
        player.setFirstName("Test");
        player.setLastName("User");
        player.setBalance(100.00);
        player.setCurrency(currency);
        player = playerRepository.save(player);

        PlayerCredentials credentials = new PlayerCredentials();
        credentials.setPlayer(player);
        credentials.setUsername("testuser");
        credentials.setPassword(passwordEncoder.encode("password"));

        credentialsRepository.save(credentials);

        return player;
    }

    private double calculateOdd(double probability) {
        if (probability == 0) return 0.0;
        return Math.round((MARGIN / probability) * 100.0) / 100.0;
    }
}