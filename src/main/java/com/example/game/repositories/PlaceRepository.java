package com.example.game.repositories;

import com.example.game.entities.Place;

public interface PlaceRepository extends BaseRepository<Place, Long> {
    Place findByPosition(Integer position);
}
