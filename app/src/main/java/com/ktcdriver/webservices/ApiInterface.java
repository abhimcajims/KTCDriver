package com.ktcdriver.webservices;

import com.ktcdriver.model.ApiResponse;
import com.ktcdriver.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST()
    Call<ApiResponse> job_details(@Field("Reservationid")String Reservationid);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> getLoginDetails(@Field("driverId")String driverId,
                                        @Field("password")String password);

}
