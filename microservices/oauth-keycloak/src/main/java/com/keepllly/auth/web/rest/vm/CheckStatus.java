package com.keepllly.auth.web.rest.vm;

public class CheckStatus {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CheckStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CheckStatus{" + "status='" + status + '\'' + '}';
    }
}
