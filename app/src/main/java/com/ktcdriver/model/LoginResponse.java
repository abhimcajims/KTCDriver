package com.ktcdriver.model;

import java.util.List;

/**
 * Created by Rakhi on 10/15/2018.
 */
public class LoginResponse {

    /**
     * status : 1
     * message : Login Successful
     * profileInfo : {"driverId":"20905","userName":"BAHADUR SINGH","email":"","photoURL":"http://gst.fleet.ktcindia.com/uploads/profile.png","happay_card":"2021-2515-5122-5151","issue_date":"11-05-2018","deactivate_date":"12-12-2018","job_history":"1"}
     * job_list : [{"color":"#ff9900","Reservationid":"401180347","dutyslipnum":"171826672","ReportingDate":"01-11-2018","ReportingtoDate":"01-11-2018","ReportingTime":"06:30","ReportingPlace":"HOTEL'S","carno":"DL1NA -1944","ClientName":"TRAVELITE (INDIA)","Paymentmode":"Credit"}]
     */

    private String status;
    private String message;
    private ProfileInfoBean profileInfo;
    private List<JobListBean> job_list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProfileInfoBean getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(ProfileInfoBean profileInfo) {
        this.profileInfo = profileInfo;
    }

    public List<JobListBean> getJob_list() {
        return job_list;
    }

    public void setJob_list(List<JobListBean> job_list) {
        this.job_list = job_list;
    }

    public static class ProfileInfoBean {
        /**
         * driverId : 20905
         * userName : BAHADUR SINGH
         * email :
         * photoURL : http://gst.fleet.ktcindia.com/uploads/profile.png
         * happay_card : 2021-2515-5122-5151
         * issue_date : 11-05-2018
         * deactivate_date : 12-12-2018
         * job_history : 1
         */

        private String driverId;
        private String userName;
        private String email;
        private String photoURL;
        private String happay_card;
        private String issue_date;
        private String deactivate_date;
        private String job_history;

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhotoURL() {
            return photoURL;
        }

        public void setPhotoURL(String photoURL) {
            this.photoURL = photoURL;
        }

        public String getHappay_card() {
            return happay_card;
        }

        public void setHappay_card(String happay_card) {
            this.happay_card = happay_card;
        }

        public String getIssue_date() {
            return issue_date;
        }

        public void setIssue_date(String issue_date) {
            this.issue_date = issue_date;
        }

        public String getDeactivate_date() {
            return deactivate_date;
        }

        public void setDeactivate_date(String deactivate_date) {
            this.deactivate_date = deactivate_date;
        }

        public String getJob_history() {
            return job_history;
        }

        public void setJob_history(String job_history) {
            this.job_history = job_history;
        }
    }

    public static class JobListBean {
        /**
         * color : #ff9900
         * Reservationid : 401180347
         * dutyslipnum : 171826672
         * ReportingDate : 01-11-2018
         * ReportingtoDate : 01-11-2018
         * ReportingTime : 06:30
         * ReportingPlace : HOTEL'S
         * carno : DL1NA -1944
         * ClientName : TRAVELITE (INDIA)
         * Paymentmode : Credit
         */

        private String color;
        private String Reservationid;
        private String dutyslipnum;
        private String ReportingDate;
        private String ReportingtoDate;
        private String ReportingTime;
        private String ReportingPlace;
        private String carno;
        private String ClientName;
        private String Paymentmode;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getReservationid() {
            return Reservationid;
        }

        public void setReservationid(String Reservationid) {
            this.Reservationid = Reservationid;
        }

        public String getDutyslipnum() {
            return dutyslipnum;
        }

        public void setDutyslipnum(String dutyslipnum) {
            this.dutyslipnum = dutyslipnum;
        }

        public String getReportingDate() {
            return ReportingDate;
        }

        public void setReportingDate(String ReportingDate) {
            this.ReportingDate = ReportingDate;
        }

        public String getReportingtoDate() {
            return ReportingtoDate;
        }

        public void setReportingtoDate(String ReportingtoDate) {
            this.ReportingtoDate = ReportingtoDate;
        }

        public String getReportingTime() {
            return ReportingTime;
        }

        public void setReportingTime(String ReportingTime) {
            this.ReportingTime = ReportingTime;
        }

        public String getReportingPlace() {
            return ReportingPlace;
        }

        public void setReportingPlace(String ReportingPlace) {
            this.ReportingPlace = ReportingPlace;
        }

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }

        public String getClientName() {
            return ClientName;
        }

        public void setClientName(String ClientName) {
            this.ClientName = ClientName;
        }

        public String getPaymentmode() {
            return Paymentmode;
        }

        public void setPaymentmode(String Paymentmode) {
            this.Paymentmode = Paymentmode;
        }
    }
}
