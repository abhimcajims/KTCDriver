package com.ktcdriver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakhi on 11/13/2018.
 */
public class NotificationRequest {

    @SerializedName("driverId")
    @Expose
    public String driverId;

    @SerializedName("limit")
    @Expose
    public String limit;

    @SerializedName("notification_id")
    @Expose
    public String notification_id;

    public NotificationRequest(String driverId, String limit, String notification_id) {
        this.driverId = driverId;
        this.limit = limit;
        this.notification_id = notification_id;
    }
}
