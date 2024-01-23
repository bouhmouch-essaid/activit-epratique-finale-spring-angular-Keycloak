package com.keepllly.auth.web.rest.errors;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
