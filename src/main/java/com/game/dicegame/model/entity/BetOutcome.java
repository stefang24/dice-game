package com.game.dicegame.model.entity;

import com.game.dicegame.model.enums.BetType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bet_outcomes")
@Data
public class BetOutcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BetType type;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private Double odd;

}
