package com.social.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UaResourceNotFoundException extends RuntimeException {

    public UaResourceNotFoundException(String message) {
        super(message);
    }
}
