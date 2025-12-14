package com.game.dicegame.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "player_credentials")
@Data
public class PlayerCredentials{

    @Id
    @Column(name = "player_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

}