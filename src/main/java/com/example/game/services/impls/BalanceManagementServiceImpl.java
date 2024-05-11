package com.example.game.services.impls;

import com.example.game.entities.Place;
import com.example.game.entities.Player;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.PlaceRepository;
import com.example.game.repositories.PlayerRepository;
import com.example.game.repositories.UserRepository;
import com.example.game.services.BalanceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceManagementServiceImpl implements BalanceManagementService {

    @Autowired private UserRepository userRepository;
    @Autowired private GameRepository gameRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private PlaceRepository placeRepository;

    @Override
    public void manageBalanceForBoughtPlace(Player player, Place boughtPlace) {
        player.setBalance(player.getBalance() - boughtPlace.getBuyPrice());
        playerRepository.save(player);
    }

    @Override
    public void manageBalanceForRentedPlace(Player player, Place rentedPlace) {
        player.setBalance(player.getBalance() - rentedPlace.getRentPrice());
        playerRepository.save(player);

        Optional<Player> ownerList = playerRepository.findById(rentedPlace.getOwnerId());
        if(ownerList.isPresent()){
            Player owner = ownerList.get();
            owner.setBalance(owner.getBalance() + rentedPlace.getRentPrice());
            playerRepository.save(owner);
        }
    }

    @Override
    public void manageBalanceForStartPoint(Player player, Integer lastPos, Integer newPos) {
        if(lastPos != 1) return ;
        if(newPos == 1) return ;
        player.setBalance(player.getBalance() + 200);
    }
}
