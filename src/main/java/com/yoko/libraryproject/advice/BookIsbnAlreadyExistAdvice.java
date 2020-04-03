package com.yoko.libraryproject.advice;

import com.yoko.libraryproject.exception.BookIsbnAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookIsbnAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(BookIsbnAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookNotFoundHandler(BookIsbnAlreadyExistException ex) {
        return ex.getMessage();
    }
}
