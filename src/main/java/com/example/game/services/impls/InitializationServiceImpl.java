package com.example.game.services.impls;

import com.example.game.dto.GameCreationDto;
import com.example.game.entities.Game;
import com.example.game.entities.Place;
import com.example.game.entities.Player;
import com.example.game.entities.User;
import com.example.game.enums.PlaceStatus;
import com.example.game.exceptions.NoHostException;
import com.example.game.exceptions.UserNotFoundException;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.PlaceRepository;
import com.example.game.repositories.PlayerRepository;
import com.example.game.repositories.UserRepository;
import com.example.game.services.InitializationService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitializationServiceImpl implements InitializationService {

    @Autowired private UserRepository userRepository;
    @Autowired private GameRepository gameRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private PlaceRepository placeRepository;

    @Override
    @Transactional
    public String createGame(GameCreationDto request) {

        deactivateExistingGame();

        Game newGame = new Game();
        newGame.setIsActive(true);
        newGame.setTotalTurns(0);

        if(request != null) newGame.setTotalPlayers(request.getUsersEmailList().size() + 1);
        else newGame.setTotalPlayers(2);

        newGame = gameRepository.save(newGame);

        registerUsersInGame(request, newGame.getId());

        return "New Monopoly Game Has been Successfully created. HAVE FUN!!! ";
    }

    private void registerUsersInGame(GameCreationDto request, Long gameId) {
        registerHostUser(gameId);

        if(request == null){
            // Register any non-host user for the Game
            List<User> nonHostUsers = userRepository.findByIsHost(false);
            if(nonHostUsers == null || nonHostUsers.isEmpty()) throw new UserNotFoundException("GAME CREATION FAILED. No other user is registered other than Host");
            registerPlayersByEmail(nonHostUsers.get(0).getEmail(), gameId);
        }

        for(String userMail : request.getUsersEmailList()) registerPlayersByEmail(userMail, gameId);
    }

    private void deactivateExistingGame() {
        Game existingGame = gameRepository.findByIsActive(true);
        if(existingGame == null) return ;
        existingGame.setIsActive(false);
        gameRepository.save(existingGame);
    }

    private void registerHostUser(Long newGameId){
        List<User> hostList = userRepository.findByIsHost(true);
        if(hostList == null || hostList.isEmpty()) throw new NoHostException("GAME CREATION FAILED. Host is not defined for the Application. Player 1 could not be assigned");
        userToPlayerMapper(hostList.get(0), newGameId, true);
    }

    private void registerPlayersByEmail(String email, Long newGameId){
        User otherUser = userRepository.findByEmail(email);
        if(otherUser == null) throw new UserNotFoundException("GAME CREATION FAILED. No user is registered with the provided mail - " + email);
        userToPlayerMapper(otherUser, newGameId, false);
    }

    private Player userToPlayerMapper(User userInfo, Long newGameId, Boolean isFirstTurn){
        Player newPlayer = new Player();
        newPlayer.setUserId(userInfo.getId());

        newPlayer.setBalance(1000);
        newPlayer.setPosition(1);
        newPlayer.setGameId(newGameId);

        return playerRepository.save(newPlayer);
    }

    @PostConstruct
    public void registerPlacesAndPositions() {

        placeRepository.deleteAll();

        List<Place> placesPosition = new ArrayList<>();
        placesPosition.add(new Place("Old Kent Road", 60, 30, PlaceStatus.UNCLAIMED, null, 1));
        placesPosition.add(new Place("Whitechapel Road", 60, 30, PlaceStatus.UNCLAIMED, null, 2));
        placesPosition.add(new Place("King's Cross station", 200, 100, PlaceStatus.UNCLAIMED, null, 3));
        placesPosition.add(new Place("The Angel Islington", 100, 50, PlaceStatus.UNCLAIMED, null, 4));
        placesPosition.add(new Place("Euston Road ", 100, 50, PlaceStatus.UNCLAIMED, null, 5));
        placesPosition.add(new Place("Pentonville Road", 120, 60, PlaceStatus.UNCLAIMED, null, 6));
        placesPosition.add(new Place("Pall Mall ", 140, 70, PlaceStatus.UNCLAIMED, null, 7));
        placesPosition.add(new Place("Whitehall", 140, 70, PlaceStatus.UNCLAIMED, null, 8));
        placesPosition.add(new Place("Northumberland Avenue", 160, 80, PlaceStatus.UNCLAIMED, null, 9));
        placesPosition.add(new Place("Marylebone station", 200, 100, PlaceStatus.UNCLAIMED, null, 10));
        placeRepository.saveAll(placesPosition);
    }
}