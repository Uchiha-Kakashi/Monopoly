package com.example.game.services.impls;


import com.example.game.services.DiceService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DiceServiceImpl implements DiceService {

    @Override
    public Integer rollDice(){
        return new Random().nextInt(6) + 1;
    }
}
