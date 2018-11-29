package com.ktcdriver.webservices;

import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.model.NewUserResponse;
import com.ktcdriver.model.NotificationData;
import com.ktcdriver.model.NotificationRequest;
import com.ktcdriver.model.OrderHistroyData;
import com.ktcdriver.model.SaveResponse;
import com.ktcdriver.model.UploadDocRequest;
import com.ktcdriver.model.ViewDetailsData;

import retrofit2.Call;
import retrofit2.http.Body;
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
    @POST("new_user_register.php")
    Call<NewUserResponse> registerNewUser(@Field("driverid")String driverId,
                                          @Field("IMEI")String IMEI,
                                          @Field("TOKEN_ID")String TOKEN_ID);

    @FormUrlEncoded
    @POST("save_duty_slip.php")
    Call<SaveResponse> saveDutySlip(@Field("dutyslipnum")String dutyslipnum,
                                    @Field("starting_date")String starting_date,
                                    @Field("ending_date")String ending_date,
                                    @Field("starting_meter")String starting_meter,
                                    @Field("reporting_meter")String reporting_meter,
                                    @Field("starting_time")String starting_time,
                                    @Field("reporting_time")String reporting_time,
                                    @Field("ending_meter")String ending_meter,
                                    @Field("ending_time")String ending_time,
                                    @Field("meter_at_garage")String meter_at_garage,
                                    @Field("time_at_garage")String time_at_garage,
                                    @Field("total_meter")String total_meter,
                                    @Field("total_time")String total_time);

    @FormUrlEncoded
    @POST("misc_charge1.php")
    Call<SaveResponse> saveMis1(@Field("dutyslipnum")String dutyslipnum,
                                   @Field("night_halt")String night_halt,
                                   @Field("toll")String toll,
                                   @Field("parking")String parking,
                                   @Field("e_toll")String e_toll,
                                   @Field("interstate_tax")String interstate_tax,
                                   @Field("others")String others);

    @FormUrlEncoded
    @POST("misc_charge2.php")
    Call<SaveResponse> saveMis2(@Field("dutyslipnum")String dutyslipnum,
                                @Field("beverages_charges")String beverages_charges,
                                @Field("entrance_charge")String entrance_charge,
                                @Field("guide_charge")String guide_charge,
                                @Field("driver_ta")String driver_ta);

    @FormUrlEncoded
    @POST("end_job.php")
    Call<SaveResponse> sendFeedback(@Field("dutyslipnum")String dutyslipnum,
                                    @Field("signature")String signature,
                                    @Field("rating")String rating,
                                    @Field("duty_remarks")String duty_remarks,
                                    @Field("no_signature") String no_signature);

    @FormUrlEncoded
    @POST("job_history.php")
    Call<OrderHistroyData> getHistory(@Field("driverId")String driverId,
                                      @Field("limit")String limit);

    @FormUrlEncoded
    @POST("notification.php")
    Call<NotificationData> getNotification(@Field("driverId")String driverId,
                                           @Field("limit")String limit);
    @FormUrlEncoded
    @POST("notification.php")
    Call<NotificationData> readNotification(@Field("driverId")String driverId,
                                           @Field("limit")String limit,
                                            @Field("notification_id")String notification_id );

//    @FormUrlEncoded
    @POST("notification.php")
    Call<NotificationData> readNotification1(@Body NotificationRequest notificationRequest);

    @FormUrlEncoded
    @POST("upload_ds_docuement.php")
    Call<SaveResponse> deleteDoc(@Field("dutyslipnum") String dutyslipnum,
                                 @Field("driverId") String driverId,
                                 @Field("action") String action,
                                 @Field("remove_id") String remove_id);

    @FormUrlEncoded
    @POST("close_ds.php")
    Call<SaveResponse> closeDs(@Field("dutyslipnum")String dutyslipnum);

}

