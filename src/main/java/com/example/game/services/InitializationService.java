package com.example.game.services;

import com.example.game.dto.GameCreationDto;

public interface InitializationService {
    String createGame(GameCreationDto request);
}
