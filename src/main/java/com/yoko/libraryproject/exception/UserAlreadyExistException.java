package com.yoko.libraryproject.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username) {
        super("Provided username already exist: " + username);
    }
}
