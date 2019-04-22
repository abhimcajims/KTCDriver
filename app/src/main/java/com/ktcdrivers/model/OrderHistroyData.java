package com.ktcdrivers.model;

import java.util.List;

/**
 * Created by Rakhi on 11/2/2018.
 */
public class OrderHistroyData {

    /**
     * status : 1
     * message : Login Successful
     * job_list : [{"color":"#ff9900","Reservationid":"401180348","dutyslipnum":"171826673","ReportingDate":"02-11-2018","ReportingtoDate":"02-11-2018","ReportingTime":"06:30","ReportingPlace":"HOTEL'S","ClientName":"TRAVELITE (INDIA)","Paymentmode":"Credit"},{"color":"#ff66cc","Reservationid":"3107180116","dutyslipnum":"181914714","ReportingDate":"29-10-2018","ReportingtoDate":"29-10-2018","ReportingTime":"TBC","ReportingPlace":"MUNIRKA","ClientName":"ISRAEL AEROSPACE INDUSTRIES LTD.(LIAISON OFFICE.NEW DELHI)INDIA.","Paymentmode":"Credit"},{"color":"#8181F7","Reservationid":"3107180283","dutyslipnum":"181914881","ReportingDate":"29-10-2018","ReportingtoDate":"29-10-2018","ReportingTime":"06:30","ReportingPlace":"Janpath Office","ClientName":"KTC INDIA INTERNAL USE","Paymentmode":"Cash"},{"color":"","Reservationid":"3107180436","dutyslipnum":"181915034","ReportingDate":"29-10-2018","ReportingtoDate":"29-10-2018","ReportingTime":"06:30","ReportingPlace":"Janpath Office","ClientName":"KTC INDIA INTERNAL USE","Paymentmode":"Credit"},{"color":"","Reservationid":"2410180220","dutyslipnum":"181928837","ReportingDate":"25-10-2018","ReportingtoDate":"25-10-2018","ReportingTime":"16:30","ReportingPlace":"AI 442 & 9W 658","ClientName":"Apollo Tyres Ltd","Paymentmode":"Credit"}]
     */

    private String status;
    private String message;
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

    public List<JobListBean> getJob_list() {
        return job_list;
    }

    public void setJob_list(List<JobListBean> job_list) {
        this.job_list = job_list;
    }

    public static class JobListBean {
        /**
         * color : #ff9900
         * Reservationid : 401180348
         * dutyslipnum : 171826673
         * ReportingDate : 02-11-2018
         * ReportingtoDate : 02-11-2018
         * ReportingTime : 06:30
         * ReportingPlace : HOTEL'S
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
        private String ClientName;
        private String Paymentmode;
        private String carno;

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }

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
