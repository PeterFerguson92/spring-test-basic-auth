package com.example.demo.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PathVariableNotFoundException extends RuntimeException {

    public PathVariableNotFoundException(String exception) {
        super(exception);
    }
}