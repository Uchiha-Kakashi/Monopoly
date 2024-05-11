package com.example.game.services;

import com.example.game.entities.Game;
import com.example.game.entities.User;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @PostConstruct
    private void registerDefaultUsers() {

        Game existingGame = gameRepository.findByIsActive(true);

        if(existingGame == null) {
            userRepository.deleteAll();

            List<User> usersList = new ArrayList<>();
            usersList.add(new User("Mrityunjay", "mrityunjay@gmail.com", false, true));
            usersList.add(new User("Amit", "amit@gmail.com", false, false));
            usersList.add(new User("Ujjal", "ujjal@gmail.com", false, false));
            usersList.add(new User("Ankur", "ankur@gmail.com", false, false));
            userRepository.saveAll(usersList);
        }
    }
}
