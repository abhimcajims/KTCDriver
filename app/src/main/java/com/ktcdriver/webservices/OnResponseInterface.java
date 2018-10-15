package com.ktcdriver.webservices;

import retrofit2.Response;

public interface OnResponseInterface {
    void onApiResponse(Object response);
    void onApiFailure(String message);
}
