package com.keepllly.auth.web.rest.errors;

import org.springframework.http.HttpStatus;

public class CustomResponse {

    private String status = HttpStatus.BAD_REQUEST.getReasonPhrase();

    private String message;

    private String details;

    public CustomResponse() {}

    public CustomResponse(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
