package com.social.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UaAuthException extends RuntimeException {

    public UaAuthException(String message) {
        super(message);
    }
}
