package com.ktcdriver.model;

import java.util.ArrayList;

/**
 * Created by Rakhi on 11/3/2018.
 */
public class UploadDocRequest {
    private String dutyslipnum;
    private String driverId;
    private String action;
    private ArrayList<String>document_name;
    private ArrayList<String>document_file;

    public String getDutyslipnum() {
        return dutyslipnum;
    }

    public void setDutyslipnum(String dutyslipnum) {
        this.dutyslipnum = dutyslipnum;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<String> getDocument_name() {
        return document_name;
    }

    public void setDocument_name(ArrayList<String> document_name) {
        this.document_name = document_name;
    }

    public ArrayList<String> getDocument_file() {
        return document_file;
    }

    public void setDocument_file(ArrayList<String> document_file) {
        this.document_file = document_file;
    }
}
