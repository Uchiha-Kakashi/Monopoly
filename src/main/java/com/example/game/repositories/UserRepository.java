package com.example.game.repositories;

import com.example.game.entities.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {
    List<User> findByIsHost(Boolean isHost);
    User findByEmail(String email);
}
