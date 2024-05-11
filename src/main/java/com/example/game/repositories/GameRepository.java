package com.example.game.repositories;

import com.example.game.entities.Game;

public interface GameRepository extends BaseRepository<Game, Long> {
    Game findByIsActive(Boolean isActive);
}
