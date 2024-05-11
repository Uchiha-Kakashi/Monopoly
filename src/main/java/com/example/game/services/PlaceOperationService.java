package com.example.game.services;

import com.example.game.entities.Place;
import com.example.game.entities.Player;

public interface PlaceOperationService {
    String manageActivityAtNewPlace(Player currPlayer, Integer newPositionId);

}
