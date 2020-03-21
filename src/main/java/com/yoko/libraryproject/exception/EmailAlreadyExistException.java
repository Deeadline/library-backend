package com.yoko.libraryproject.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String email) {
        super("Provided email already exist: " + email);
    }
}
