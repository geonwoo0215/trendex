package com.trendex.trendex.global.client.webclient.exception;

import com.trendex.trendex.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UpbitTooManyRequestsException extends CustomException {

    private static final HttpStatus STATUS = HttpStatus.TOO_MANY_REQUESTS;

    private static final String MESSAGE = "업비트에 너무 많은 요청을 하였습니다.";

    public UpbitTooManyRequestsException(Object... params) {
        super(STATUS, params, MESSAGE);
    }
}
