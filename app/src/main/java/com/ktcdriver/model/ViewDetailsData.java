package com.ktcdriver.model;

import java.util.List;

/**
 * Created by Rakhi on 10/16/2018.
 */
public class ViewDetailsData {


    /**
     * status : 1
     * message : Success
     * job_detail : {"Reservationid":"401180349","dutyslipnum":"171826674","Drivername":"BAHADUR SINGH","Carno":"DL1NA -1944","Carname":"TOYOTA CAMRY HYBRID ","Guestname":"GROUP TOUR AGRA","Guestcontacto":"NA","ReportingPlace":"HOTEL'S","Reportingfrom":"03-11-2018","Reporingto":"03-11-2018","Reporingtime":"06:30","Bookername":"MR. SSA","Bookercontactno":"","Assignment":"OUTSTATION","City_of_usage":"Delhi/NCR","Vehiclerequest":"TOYOTA INNOVA","Paymentmode":"Credit","Details":"SAME DAY AGRA","Company_Name":"TRAVELITE (INDIA)","starting_date":null,"ending_date":null,"starting_meter":null,"starting_time":null,"reporting_meter":null,"reporting_time":null,"ending_meter":null,"ending_time":null,"meter_at_garage":null,"time_at_garage":null,"total_meter":null,"total_time":null,"misc_charges1":{"night_halt":null,"toll":null,"parking":null,"e_toll":null,"interstate_tax":null,"others":null},"misc_charges2":{"beverage_charge":null,"entrance_charge":null,"guide_charge":null,"driver_ta":null}}
     * imagelist : [{"name":"Slip1","id":"5"},{"name":"Slip2","id":"6"}]
     */

    private String status;
    private String message;
    private JobDetailBean job_detail;
    private List<ImagelistBean> imagelist;

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

    public JobDetailBean getJob_detail() {
        return job_detail;
    }

    public void setJob_detail(JobDetailBean job_detail) {
        this.job_detail = job_detail;
    }

    public List<ImagelistBean> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<ImagelistBean> imagelist) {
        this.imagelist = imagelist;
    }

    public static class JobDetailBean {
        /**
         * Reservationid : 401180349
         * dutyslipnum : 171826674
         * Drivername : BAHADUR SINGH
         * Carno : DL1NA -1944
         * Carname : TOYOTA CAMRY HYBRID
         * Guestname : GROUP TOUR AGRA
         * Guestcontacto : NA
         * ReportingPlace : HOTEL'S
         * Reportingfrom : 03-11-2018
         * Reporingto : 03-11-2018
         * Reporingtime : 06:30
         * Bookername : MR. SSA
         * Bookercontactno :
         * Assignment : OUTSTATION
         * City_of_usage : Delhi/NCR
         * Vehiclerequest : TOYOTA INNOVA
         * Paymentmode : Credit
         * Details : SAME DAY AGRA
         * Company_Name : TRAVELITE (INDIA)
         * starting_date : null
         * ending_date : null
         * starting_meter : null
         * starting_time : null
         * reporting_meter : null
         * reporting_time : null
         * ending_meter : null
         * ending_time : null
         * meter_at_garage : null
         * time_at_garage : null
         * total_meter : null
         * total_time : null
         * misc_charges1 : {"night_halt":null,"toll":null,"parking":null,"e_toll":null,"interstate_tax":null,"others":null}
         * misc_charges2 : {"beverage_charge":null,"entrance_charge":null,"guide_charge":null,"driver_ta":null}
         */

        private String Reservationid;
        private String dutyslipnum;
        private String Drivername;
        private String Carno;
        private String Carname;
        private String Guestname;
        private String Guestcontacto;
        private String ReportingPlace;
        private String Reportingfrom;
        private String Reporingto;
        private String Reporingtime;
        private String Bookername;
        private String Bookercontactno;
        private String Assignment;
        private String City_of_usage;
        private String Vehiclerequest;
        private String Paymentmode;
        private String Details;
        private String Company_Name;
        private String starting_date;
        private String ending_date;
        private String starting_meter;
        private String starting_time;
        private String reporting_meter;
        private String reporting_time;
        private String ending_meter;
        private String ending_time;
        private String meter_at_garage;
        private String time_at_garage;
        private String total_meter;
        private String total_time;
        private MiscCharges1Bean misc_charges1;
        private MiscCharges2Bean misc_charges2;

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

        public String getDrivername() {
            return Drivername;
        }

        public void setDrivername(String Drivername) {
            this.Drivername = Drivername;
        }

        public String getCarno() {
            return Carno;
        }

        public void setCarno(String Carno) {
            this.Carno = Carno;
        }

        public String getCarname() {
            return Carname;
        }

        public void setCarname(String Carname) {
            this.Carname = Carname;
        }

        public String getGuestname() {
            return Guestname;
        }

        public void setGuestname(String Guestname) {
            this.Guestname = Guestname;
        }

        public String getGuestcontacto() {
            return Guestcontacto;
        }

        public void setGuestcontacto(String Guestcontacto) {
            this.Guestcontacto = Guestcontacto;
        }

        public String getReportingPlace() {
            return ReportingPlace;
        }

        public void setReportingPlace(String ReportingPlace) {
            this.ReportingPlace = ReportingPlace;
        }

        public String getReportingfrom() {
            return Reportingfrom;
        }

        public void setReportingfrom(String Reportingfrom) {
            this.Reportingfrom = Reportingfrom;
        }

        public String getReporingto() {
            return Reporingto;
        }

        public void setReporingto(String Reporingto) {
            this.Reporingto = Reporingto;
        }

        public String getReporingtime() {
            return Reporingtime;
        }

        public void setReporingtime(String Reporingtime) {
            this.Reporingtime = Reporingtime;
        }

        public String getBookername() {
            return Bookername;
        }

        public void setBookername(String Bookername) {
            this.Bookername = Bookername;
        }

        public String getBookercontactno() {
            return Bookercontactno;
        }

        public void setBookercontactno(String Bookercontactno) {
            this.Bookercontactno = Bookercontactno;
        }

        public String getAssignment() {
            return Assignment;
        }

        public void setAssignment(String Assignment) {
            this.Assignment = Assignment;
        }

        public String getCity_of_usage() {
            return City_of_usage;
        }

        public void setCity_of_usage(String City_of_usage) {
            this.City_of_usage = City_of_usage;
        }

        public String getVehiclerequest() {
            return Vehiclerequest;
        }

        public void setVehiclerequest(String Vehiclerequest) {
            this.Vehiclerequest = Vehiclerequest;
        }

        public String getPaymentmode() {
            return Paymentmode;
        }

        public void setPaymentmode(String Paymentmode) {
            this.Paymentmode = Paymentmode;
        }

        public String getDetails() {
            return Details;
        }

        public void setDetails(String Details) {
            this.Details = Details;
        }

        public String getCompany_Name() {
            return Company_Name;
        }

        public void setCompany_Name(String Company_Name) {
            this.Company_Name = Company_Name;
        }

        public String getStarting_date() {
            return starting_date;
        }

        public void setStarting_date(String starting_date) {
            this.starting_date = starting_date;
        }

        public String getEnding_date() {
            return ending_date;
        }

        public void setEnding_date(String ending_date) {
            this.ending_date = ending_date;
        }

        public String getStarting_meter() {
            return starting_meter;
        }

        public void setStarting_meter(String starting_meter) {
            this.starting_meter = starting_meter;
        }

        public String getStarting_time() {
            return starting_time;
        }

        public void setStarting_time(String starting_time) {
            this.starting_time = starting_time;
        }

        public String getReporting_meter() {
            return reporting_meter;
        }

        public void setReporting_meter(String reporting_meter) {
            this.reporting_meter = reporting_meter;
        }

        public String getReporting_time() {
            return reporting_time;
        }

        public void setReporting_time(String reporting_time) {
            this.reporting_time = reporting_time;
        }

        public String getEnding_meter() {
            return ending_meter;
        }

        public void setEnding_meter(String ending_meter) {
            this.ending_meter = ending_meter;
        }

        public String getEnding_time() {
            return ending_time;
        }

        public void setEnding_time(String ending_time) {
            this.ending_time = ending_time;
        }

        public String getMeter_at_garage() {
            return meter_at_garage;
        }

        public void setMeter_at_garage(String meter_at_garage) {
            this.meter_at_garage = meter_at_garage;
        }

        public String getTime_at_garage() {
            return time_at_garage;
        }

        public void setTime_at_garage(String time_at_garage) {
            this.time_at_garage = time_at_garage;
        }

        public String getTotal_meter() {
            return total_meter;
        }

        public void setTotal_meter(String total_meter) {
            this.total_meter = total_meter;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }

        public MiscCharges1Bean getMisc_charges1() {
            return misc_charges1;
        }

        public void setMisc_charges1(MiscCharges1Bean misc_charges1) {
            this.misc_charges1 = misc_charges1;
        }

        public MiscCharges2Bean getMisc_charges2() {
            return misc_charges2;
        }

        public void setMisc_charges2(MiscCharges2Bean misc_charges2) {
            this.misc_charges2 = misc_charges2;
        }

        public static class MiscCharges1Bean {
            /**
             * night_halt : null
             * toll : null
             * parking : null
             * e_toll : null
             * interstate_tax : null
             * others : null
             */

            private Object night_halt;
            private Object toll;
            private Object parking;
            private Object e_toll;
            private Object interstate_tax;
            private Object others;

            public Object getNight_halt() {
                return night_halt;
            }

            public void setNight_halt(Object night_halt) {
                this.night_halt = night_halt;
            }

            public Object getToll() {
                return toll;
            }

            public void setToll(Object toll) {
                this.toll = toll;
            }

            public Object getParking() {
                return parking;
            }

            public void setParking(Object parking) {
                this.parking = parking;
            }

            public Object getE_toll() {
                return e_toll;
            }

            public void setE_toll(Object e_toll) {
                this.e_toll = e_toll;
            }

            public Object getInterstate_tax() {
                return interstate_tax;
            }

            public void setInterstate_tax(Object interstate_tax) {
                this.interstate_tax = interstate_tax;
            }

            public Object getOthers() {
                return others;
            }

            public void setOthers(Object others) {
                this.others = others;
            }
        }

        public static class MiscCharges2Bean {
            /**
             * beverage_charge : null
             * entrance_charge : null
             * guide_charge : null
             * driver_ta : null
             */

            private Object beverage_charge;
            private Object entrance_charge;
            private Object guide_charge;
            private Object driver_ta;

            public Object getBeverage_charge() {
                return beverage_charge;
            }

            public void setBeverage_charge(Object beverage_charge) {
                this.beverage_charge = beverage_charge;
            }

            public Object getEntrance_charge() {
                return entrance_charge;
            }

            public void setEntrance_charge(Object entrance_charge) {
                this.entrance_charge = entrance_charge;
            }

            public Object getGuide_charge() {
                return guide_charge;
            }

            public void setGuide_charge(Object guide_charge) {
                this.guide_charge = guide_charge;
            }

            public Object getDriver_ta() {
                return driver_ta;
            }

            public void setDriver_ta(Object driver_ta) {
                this.driver_ta = driver_ta;
            }
        }
    }

    public static class ImagelistBean {
        /**
         * name : Slip1
         * id : 5
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
