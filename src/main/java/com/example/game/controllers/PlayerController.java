package com.example.game.controllers;

import com.example.game.services.PlayersMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired private PlayersMoveService playersMoveService;

    @PutMapping("/roll-die/p{playerNum}")
    public String playDice(@PathVariable Integer playerNum){
        return playersMoveService.rollDiceAndMakeMove(playerNum);
    }
}
