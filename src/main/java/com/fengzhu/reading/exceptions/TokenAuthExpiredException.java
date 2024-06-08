package com.fengzhu.reading.exceptions;

public class TokenAuthExpiredException extends RuntimeException {

    public TokenAuthExpiredException(String message) {
        super(message);
    }
}
