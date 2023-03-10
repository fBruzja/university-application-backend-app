package com.social.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UaBadRequestException extends RuntimeException {

    public UaBadRequestException(String message) {
        super(message);
    }
}