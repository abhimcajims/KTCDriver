package com.ktcdriver.webservices;

import com.ktcdriver.model.ApiResponse;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.model.ViewDetailsData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("viewdetail.php")
    Call<ViewDetailsData> job_details(@Field("Reservationid")String Reservationid);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> getLoginDetails(@Field("driverId")String driverId,
                                        @Field("password")String password);
    @FormUrlEncoded
    @POST("new_user_register.php ")
    Call<LoginResponse> registerNewUser(@Field("driverId")String driverId,
                                        @Field("IMEI")String IMEI,
                                        @Field("TOKEN_ID")String TOKEN_ID);

}
