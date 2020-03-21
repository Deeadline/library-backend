package com.yoko.libraryproject.advice;

import com.yoko.libraryproject.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userAlreadyExistHandler(UserAlreadyExistException ex) {
        return ex.getMessage();
    }
}
