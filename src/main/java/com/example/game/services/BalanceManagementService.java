package com.example.game.services;

import com.example.game.entities.Place;
import com.example.game.entities.Player;

public interface BalanceManagementService {
    void manageBalanceForBoughtPlace(Player player, Place boughtPlace);
    void manageBalanceForRentedPlace(Player player, Place rentedPlace);
    void manageBalanceForStartPoint(Player player, Integer lastPos, Integer newPos);
}
