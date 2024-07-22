package com.trendex.trendex.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;

    private final Object[] params;

    private final String message;

    public CustomException(HttpStatus status, Object[] params, String message) {
        this.status = status;
        this.params = params;
        this.message = message;
    }
}
