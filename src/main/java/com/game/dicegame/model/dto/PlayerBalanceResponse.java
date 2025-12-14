package com.game.dicegame.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlayerBalanceResponse {
    private Double balance;
    private String currency;
}
