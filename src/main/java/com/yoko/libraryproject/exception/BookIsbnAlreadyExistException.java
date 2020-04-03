package com.yoko.libraryproject.exception;

public class BookIsbnAlreadyExistException extends RuntimeException {
    public BookIsbnAlreadyExistException() {
        super("ISBN already exist!");
    }
}
