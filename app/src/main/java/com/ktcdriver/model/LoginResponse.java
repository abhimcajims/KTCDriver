package com.ktcdriver.model;

import java.util.List;

/**
 * Created by Rakhi on 10/15/2018.
 */
public class LoginResponse {

    /**
     * status : 1
     * message : Login Successful
     * profileInfo : {"driverId":"20905","userName":"BAHADUR SINGH","email":"","photoURL":"http://gst.fleet.ktcindia.com/uploads/profile.png"}
     * job_list : [{"color":"","Reservationid":"1110180039","dutyslipnum":"181926742","ReportingDate":"15-10-2018","ReportingtoDate":"15-10-2018","ReportingTime":"05:00","ReportingPlace":"Patel Nagar Metro Station","carno":"DL 1NA 2483","ClientName":"DESTINOS INDIA GURUS PRIVATE LIMITED","Paymentmode":"Credit"},{"color":"","Reservationid":"1110180032","dutyslipnum":"181926735","ReportingDate":"16-10-2018","ReportingtoDate":"16-10-2018","ReportingTime":"09:30","ReportingPlace":"TBC","carno":"DL 1NA 2483","ClientName":"DESTINOS INDIA GURUS PRIVATE LIMITED","Paymentmode":"Credit"}]
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
         */

        private String driverId;
        private String userName;
        private String email;
        private String photoURL;

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
    }

    public static class JobListBean {
        /**
         * color :
         * Reservationid : 1110180039
         * dutyslipnum : 181926742
         * ReportingDate : 15-10-2018
         * ReportingtoDate : 15-10-2018
         * ReportingTime : 05:00
         * ReportingPlace : Patel Nagar Metro Station
         * carno : DL 1NA 2483
         * ClientName : DESTINOS INDIA GURUS PRIVATE LIMITED
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
