package com.ktcdrivers.webservices;

public interface OnResponseInterface {
    void onApiResponse(Object response);
    void onApiFailure(String message);
}
