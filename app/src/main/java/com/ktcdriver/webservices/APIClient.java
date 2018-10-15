package com.ktcdriver.webservices;

import com.ktcdriver.utils.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient sApiClient = new APIClient();
    private ApiInterface mApiInterface;
    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private APIClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiInterface = retrofit.create(ApiInterface.class);
    }

    public static APIClient getInstance() {
        return sApiClient;
    }

    public ApiInterface getApiInterface() {
        return mApiInterface;
    }

}
