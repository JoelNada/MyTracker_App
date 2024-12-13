package com.enterprise.tracker.app.exceptions.customExceptions;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String message){
        super(message);
    }

}
