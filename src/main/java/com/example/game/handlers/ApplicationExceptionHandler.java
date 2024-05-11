package com.example.game.handlers;

import com.example.game.exceptions.InvalidMoveException;
import com.example.game.exceptions.NoActiveGameException;
import com.example.game.exceptions.NoHostException;
import com.example.game.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoHostException.class)
    public String handleNoHostExistException(NoHostException ex, HttpServletResponse response){
        System.out.println("NoHostException occurred - " + ex.getMessage());

        return ex.getMessage();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, HttpServletResponse response){
        System.out.println("UserNotFoundException occurred - " + ex.getMessage());

        return ex.getMessage();
    }

    @ExceptionHandler(value = InvalidMoveException.class)
    public String handleInvalidMoveException(InvalidMoveException ex, HttpServletResponse response){
        System.out.println("InvalidMoveException occurred - " + ex.getMessage());

        return ex.getMessage();
    }

    @ExceptionHandler(value = NoActiveGameException.class)
    public String handleNoActiveGameException(NoActiveGameException ex, HttpServletResponse response){
        System.out.println("NoActiveGameException occurred - " + ex.getMessage());

        return ex.getMessage();
    }

}