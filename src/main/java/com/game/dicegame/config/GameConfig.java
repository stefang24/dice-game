package com.game.dicegame.config;

import com.game.dicegame.model.enums.BetType;
import com.game.dicegame.service.validation.BetRule;
import com.game.dicegame.service.validation.OEBetRule;
import com.game.dicegame.service.validation.SumBetRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class GameConfig {

    @Bean
    public Map<BetType, BetRule> rulesMap() {
        return Map.of(
                BetType.SUM, new SumBetRule(),
                BetType.OE, new OEBetRule()
        );
    }
}