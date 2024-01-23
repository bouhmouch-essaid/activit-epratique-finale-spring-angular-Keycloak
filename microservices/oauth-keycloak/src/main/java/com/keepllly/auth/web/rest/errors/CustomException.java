package com.keepllly.auth.web.rest.errors;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
