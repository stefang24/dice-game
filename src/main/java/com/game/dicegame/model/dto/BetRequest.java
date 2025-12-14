package com.game.dicegame.model.dto;

import com.game.dicegame.model.enums.BetType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BetRequest {

    private Double betAmount;

    private BetType betType;

    private String betValue;

}
