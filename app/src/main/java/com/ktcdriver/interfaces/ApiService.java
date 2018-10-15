package com.ktcdriver.interfaces;

import com.ktcdriver.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Rakhi on 10/15/2018.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST()
    Call<ApiResponse> job_details(@Field("Reservationid")String Reservationid);

}
