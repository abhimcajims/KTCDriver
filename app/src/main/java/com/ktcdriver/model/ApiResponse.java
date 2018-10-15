package com.ktcdriver.model;

/**
 * Created by Rakhi on 10/15/2018.
 */
public class ApiResponse {

    private String status;
    private String message;
    public LoginResponse loginResponse;


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
}
