package com.game.dicegame.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bet_limits")
@Data
public class BetLimits {

    @Id
    @Column(name = "currency_id")
    private Long id;

    @Column(name = "min_limit", nullable = false)
    private Double minLimit;

    @Column(name = "max_limit", nullable = false)
    private Double maxLimit;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
}