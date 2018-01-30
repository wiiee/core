package com.wiiee.core.domain.service;

/**
 * Created by wiiee on 9/17/2017.
 */
public class ServiceResult {
    private boolean successful;
    private String message;

    public ServiceResult(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
