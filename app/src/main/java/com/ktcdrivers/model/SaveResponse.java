package com.ktcdrivers.model;

/**
 * Created by Rakhi on 10/24/2018.
 */
public class SaveResponse {

    /**
     * status : 1
     * message : Successful.
     */

    private String status;
    private String message;

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
