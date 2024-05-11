package com.example.game.controllers;

import com.example.game.dto.GameCreationDto;
import com.example.game.services.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializationController {

    @Autowired private InitializationService initializationService;

    @PostMapping("/create-game")
    public String createNewGame(@RequestBody GameCreationDto request){
        return initializationService.createGame(request);
    }

    @GetMapping("/somepath")
    public String sendString() {
        return "App is UP";
    }
}
