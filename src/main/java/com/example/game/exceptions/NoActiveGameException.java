package com.example.game.exceptions;

public class NoActiveGameException extends RuntimeException {
    public NoActiveGameException(String message) {
        super(message);
    }
}
