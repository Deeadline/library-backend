package com.yoko.libraryproject.exception;

public class UserBookNotFoundException extends RuntimeException {
    public UserBookNotFoundException(Long userId, Long bookId) {
        super("Could not find user_book with user_id = " + userId + ", book_id=" + bookId);
    }
}
