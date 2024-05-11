package com.example.game.services.impls;

import com.example.game.entities.Game;
import com.example.game.entities.Place;
import com.example.game.entities.Player;
import com.example.game.entities.User;
import com.example.game.enums.PlaceStatus;
import com.example.game.exceptions.InvalidMoveException;
import com.example.game.exceptions.NoActiveGameException;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.PlaceRepository;
import com.example.game.repositories.PlayerRepository;
import com.example.game.repositories.UserRepository;
import com.example.game.services.BalanceManagementService;
import com.example.game.services.DiceService;
import com.example.game.services.PlaceOperationService;
import com.example.game.services.PlayersMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayersMoveServiceImpl implements PlayersMoveService {

    @Autowired private DiceService diceService;
    @Autowired private BalanceManagementService balanceManagementService;
    @Autowired private PlaceOperationService placeOperationService;
    @Autowired private UserRepository userRepository;
    @Autowired private GameRepository gameRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private PlaceRepository placeRepository;

    @Override
    public String rollDiceAndMakeMove(Integer playerId) {

        String state = new String("Current Move Made -->");

        Game activeGame = gameRepository.findByIsActive(true);

        if(activeGame == null) throw new NoActiveGameException("Invalid Move. No Game is currently Active. Please create a new Game!!");

        if(activeGame.getTotalTurns() == 50) {
            // Maximum moves done. Declaring winner

            activeGame.setIsActive(false);
            gameRepository.save(activeGame);

            return declareWinner(activeGame);
        }

        Player currPlayer = getCurrentPlayer(playerId, activeGame);
        if(currPlayer == null) throw new InvalidMoveException("This is an invalid move. Player #" + playerId + " cannot make move at the moment");

        Integer dice1Roll = diceService.rollDice();
        Integer dice2Roll = diceService.rollDice();

        // Since total number of places is 10
        Integer newPosition = (currPlayer.getPosition() + dice1Roll + dice2Roll) % 10;

        balanceManagementService.manageBalanceForStartPoint(currPlayer, currPlayer.getPosition(), newPosition);
        currPlayer.setPosition(newPosition);

        state += placeOperationService.manageActivityAtNewPlace(currPlayer, newPosition);

        if(currPlayer.getBalance() < 0) activeGame.setIsActive(false);

        activeGame.setTotalTurns(activeGame.getTotalTurns() + 1);
        gameRepository.save(activeGame);

        return state;
    }

    private String declareWinner(Game activeGame) {
        String winnerStatement = "Winner Declared after 50 Turns. ";

        List<Player> gamePlayers = playerRepository.findByGameIdOrderById(activeGame.getId());
        Player winner = null;
        Integer maxBalance = 0;
        for(Player player : gamePlayers) {
            if(maxBalance < player.getBalance()) winner = player;
        }

        Optional<User> winnerUser = userRepository.findById(winner.getUserId());
        if(winnerUser.isPresent()){
            User winUser = winnerUser.get();
            winnerStatement += winUser.getName() + " with remaining balance - " + winner.getBalance();
        }

        return winnerStatement;
    }

    private Player getCurrentPlayer(Integer playerId, Game activeGame) {
        List<Player> gamePlayers = playerRepository.findByGameIdOrderById(activeGame.getId());
        Integer currentMoveNum = activeGame.getTotalTurns() + 1;
        Integer currentMovePlayerIndex = currentMoveNum % activeGame.getTotalPlayers();
        if(!currentMovePlayerIndex.equals(playerId % activeGame.getTotalPlayers())) return null;
        return gamePlayers.get(playerId - 1);
    }
}
