package com.game.dicegame.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "currency")
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BetLimits betLimits;
}
