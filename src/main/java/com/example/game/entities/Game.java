package com.example.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game extends BaseEntity {

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive;

    @Column(name = "winner_id")
    private Boolean winnerId;

    @Column(name = "total_turns", nullable = false)
    private Integer totalTurns;

    @Column(name = "total_players", nullable = false)
    private Integer totalPlayers;

}
