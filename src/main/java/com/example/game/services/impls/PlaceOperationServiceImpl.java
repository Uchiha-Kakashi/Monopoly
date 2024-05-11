package com.example.game.services.impls;

import com.example.game.entities.Place;
import com.example.game.entities.Player;
import com.example.game.enums.PlaceStatus;
import com.example.game.repositories.PlaceRepository;
import com.example.game.services.BalanceManagementService;
import com.example.game.services.PlaceOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlaceOperationServiceImpl implements PlaceOperationService {

    @Autowired
    private BalanceManagementService balanceManagementService;
    @Autowired private PlaceRepository placeRepository;

    public String manageActivityAtNewPlace(Player currPlayer, Integer newPositionId) {

        String retVal = "";
        Place newPlace = placeRepository.findByPosition(newPositionId);

        if(newPlace.getStatus() == PlaceStatus.UNCLAIMED) {
            balanceManagementService.manageBalanceForBoughtPlace(currPlayer, newPlace);

            retVal += " Remaining Balance - (" + currPlayer.getBalance() + ")";

            newPlace.setStatus(PlaceStatus.CLAIMED);
            newPlace.setOwnerId(currPlayer.getId());
            placeRepository.save(newPlace);

            retVal += " Newly bought place - " + newPlace.getName();
        }
        else if (newPlace.getStatus() == PlaceStatus.CLAIMED && !Objects.equals(newPlace.getOwnerId(), currPlayer.getId())){
            balanceManagementService.manageBalanceForRentedPlace(currPlayer, newPlace);
            retVal += " Remaining Balance - (" + currPlayer.getBalance() + ")";
            retVal += " Rented Place - " + newPlace.getName();
        }

        if(currPlayer.getBalance() < 0) retVal += " YOU GOT BANKRUPTED!! That's a LOSS!! Better Luck Next time ";

        return retVal;
    }
}
