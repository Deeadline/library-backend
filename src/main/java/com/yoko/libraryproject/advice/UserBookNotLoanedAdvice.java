package com.yoko.libraryproject.advice;

import com.yoko.libraryproject.exception.UserBookNotLoanedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserBookNotLoanedAdvice {

    @ResponseBody
    @ExceptionHandler(UserBookNotLoanedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userBookNotLoanedHandler(UserBookNotLoanedException ex) {
        return ex.getMessage();
    }
}
