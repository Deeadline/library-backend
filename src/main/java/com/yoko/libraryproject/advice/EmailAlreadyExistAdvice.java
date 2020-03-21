package com.yoko.libraryproject.advice;

import com.yoko.libraryproject.exception.EmailAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(EmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String emailAlreadyExistHandler(EmailAlreadyExistException ex) {
        return ex.getMessage();
    }
}
