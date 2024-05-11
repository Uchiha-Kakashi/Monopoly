package com.example.game.repositories;

import com.example.game.entities.Player;

import java.util.List;

public interface PlayerRepository extends BaseRepository<Player, Long> {

    List<Player> findByGameIdOrderById(Long gameId);
}
