package com.game.dicegame.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.game.dicegame.exception.InvalidBetException;

import java.util.Arrays;
import java.util.List;

public enum BetType {
    SUM,
    OE;
    private static final List<String> VALID_TYPES = Arrays.asList("SUM", "OE");

    @JsonCreator
    public static BetType fromString(String type) {
        if (type == null) {
            throw new InvalidBetException("Invalid Bet Type");
        }
        try {
            return BetType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidBetException("Invalid Bet Type");
        }
    }
}
