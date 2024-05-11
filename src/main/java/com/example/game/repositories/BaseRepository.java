package com.example.game.repositories;

import com.example.game.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity, PK> extends JpaRepository<T, PK> {
}
