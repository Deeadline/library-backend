package com.yoko.libraryproject.exception;

public class UserBookNotLoanedException extends RuntimeException {
    public UserBookNotLoanedException(Long id) {
        super("Book with id= " + id + " is not loaned by you!");
    }
}
