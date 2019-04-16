package com.ktcdriver.webservices;

import android.app.Activity;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseListner {

    private OnResponseInterface onResponseInterface;
    private String message;
    private Context context;

    public ResponseListner(OnResponseInterface onResponseInterface, Context context) {
        this.onResponseInterface = onResponseInterface;
        this.context = context;
    }

    public void getResponse(Call call) {

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                message = response.message();
                if (response.code()==200)
                    onResponseInterface.onApiResponse(response.body());
                else onResponseInterface.onApiFailure(message);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                message = t.getMessage();
                onResponseInterface.onApiFailure(message);
            }
        });
    }


}
