package com.ktcdriver.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.adapter.DutyListAdapter;
import com.ktcdriver.model.SaveResponse;
import com.ktcdriver.model.ViewDetailsData;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class DutySlipFragment extends Fragment implements DutyListAdapter.DutyListInterface, View.OnClickListener,
        OnResponseInterface{
    private LinearLayout main_Layout;
    private RecyclerView recyclerView;
    private ArrayList<String> title2List;
    private ArrayList<String>title1List;
    private ArrayList<String> title2ListValue;
    private ArrayList<String>title1ListValue;
    private boolean isEndMeter,isEndTime;
    private TextView txtCompanyName;
    private TextView txtComapAdd;
    private TextView txtCompMobile;
    private TextView txtCompEmail;
    private TextView txtCompFax;
    private TextView txtDriverDetail;
    private TextView txtCarDetail;
    private TextView txtrqNo;
    private TextView txtBookerNo;
    private TextView txtPaymentMode;
    private TextView txtCompName2;
    private TextView txtBookedName;
    private TextView txtReportPlace;
    private TextView txtAssignment;
    private TextView txtDetails;
    private TextView txtStartingDate;
    private TextView txtEdndingDate;
    private TextView txtcity;
    private TextView txtVehicleReq;
    private TextView txtUserName,txtMisc1Value, txtMisc2Value;
    private LinearLayout txtCharge1, txtCharge2;
    public static String dutyslipnum;
    private String reservationId,  starting_date, ending_date, starting_meter,
            starting_time, reporting_meter, reporting_time, ending_meter, ending_time, meter_at_garage,
            time_at_garage, total_meter, total_time,night_halt,toll,parking,e_toll,interstate_tax,others,
            beverages_charges,entrance_charge,guide_charge,driver_ta;
    private int end_status;

    private ImageView startClock, endClock;

    public DutySlipFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_duty_slip, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
        title1List = new ArrayList<>();
        title2List = new ArrayList<>();
        title1ListValue = new ArrayList<>();
        title2ListValue = new ArrayList<>();
        if (getArguments()!=null){
            reservationId = getArguments().getString("reservationid");
        }
        title1List.add("Starting Meter");
        title1List.add("Reporting Meter");
        title1List.add("Ending Meter");
        title1List.add("Meter at Garrage");
        title1List.add("Total Meter");
        title2List.add("Starting Time");
        title2List.add("Reporting Time");
        title2List.add("Ending Time");
        title2List.add("Time at Garrage");
        title2List.add("Total Time");

        main_Layout = getView().findViewById(R.id.fragment_duty_slip_layout);
        main_Layout.setVisibility(View.GONE);
        txtMisc1Value = getView().findViewById(R.id.fragment_duty_slip_misc1_value);
        txtMisc2Value = getView().findViewById(R.id.fragment_duty_slip_misc2_value);
        startClock = getView().findViewById(R.id.fragment_duty_slip_txt_starting_clock);
        endClock = getView().findViewById(R.id.fragment_duty_slip_txt_end_clock);
        recyclerView = getView().findViewById(R.id.fragment_duty_slip);
        TextView txtSave = getView().findViewById(R.id.fragment_duty_slip_txtsave);
        txtCharge1 = getView().findViewById(R.id.fragment_duty_slip_txt_charge1);
        txtCharge2 = getView().findViewById(R.id.fragment_duty_slip_txt_charge2);
        txtComapAdd = getView().findViewById(R.id.fragment_duty_slip_txt_companyAdd);
        txtCompMobile = getView().findViewById(R.id.fragment_duty_slip_txt_companyMob);
        txtCompEmail = getView().findViewById(R.id.fragment_duty_slip_txt_companyEmail);
        txtCompFax = getView().findViewById(R.id.fragment_duty_slip_txt_companyFax);
        txtDriverDetail = getView().findViewById(R.id.fragment_duty_slip_txt_driver_detail);
        txtCarDetail = getView().findViewById(R.id.fragment_duty_slip_txt_car_detail);
        txtrqNo = getView().findViewById(R.id.fragment_duty_slip_txt_rq_no);
        txtBookerNo = getView().findViewById(R.id.fragment_duty_slip_txt_booker_no);
        txtPaymentMode = getView().findViewById(R.id.fragment_duty_slip_txt_payment);
        txtcity = getView().findViewById(R.id.fragment_duty_slip_txt_city);
        txtVehicleReq = getView().findViewById(R.id.fragment_duty_slip_txt_vehicle_req);
        txtUserName = getView().findViewById(R.id.fragment_duty_slip_txt_user_name);
        txtCompName2 = getView().findViewById(R.id.fragment_duty_slip_txt_companyName2);
        txtCompanyName = getView().findViewById(R.id.fragment_duty_slip_txt_companyName);
        txtBookedName = getView().findViewById(R.id.fragment_duty_slip_txt_bookedName);
        txtCompName2 = getView().findViewById(R.id.fragment_duty_slip_txt_companyName2);
        txtReportPlace = getView().findViewById(R.id.fragment_duty_slip_txt_report_place);
        txtAssignment = getView().findViewById(R.id.fragment_duty_slip_txt_assign);
        txtDetails = getView().findViewById(R.id.fragment_duty_slip_txt_details);
        txtStartingDate = getView().findViewById(R.id.fragment_duty_slip_txt_starting_date);
        txtEdndingDate = getView().findViewById(R.id.fragment_duty_slip_txt_end_date);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        txtSave.setOnClickListener(this);
        txtCharge2.setOnClickListener(this);
        txtCharge1.setOnClickListener(this);
        startClock.setOnClickListener(this);
        endClock.setOnClickListener(this);
       // reservationId = "709180043";
        fetchDetails(reservationId);
    }
    private DutyListAdapter dutyListAdapter;
    private void setAdapter(ArrayList<String> title2ListValue, ArrayList<String> titleListValue,
                            boolean isEndMeter, boolean isEndTime){
        dutyListAdapter = new DutyListAdapter(getContext(),this,title2List,
                title1List, title2ListValue,titleListValue,isEndMeter,isEndTime);
        recyclerView.setAdapter(dutyListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Duty Slip");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private TextView timeText;

    @Override
    public void onTextChanged(int position, String charSeq) {
        Log.d("TAG", "onTextChanged: "+charSeq);
        switch (position){
            case 0:
                starting_meter = charSeq;
                break;
            case 1:
                reporting_meter = charSeq;
                break;
            case 2:
                ending_meter = charSeq;
                end_status = 1;
                break;
            case 3:
                meter_at_garage = charSeq;
                if (meter_at_garage!=null && meter_at_garage.length()>0){
                    calculateMeter();
                }
                break;
        }
    }

    private void calculateMeter(){
        double total = Double.parseDouble(starting_meter)+ Double.parseDouble(reporting_meter)
                +Double.parseDouble(ending_meter)+Double.parseDouble(meter_at_garage);
        if (total_meter!=null && total_meter.length()>0)
        total_meter=Double.parseDouble(total_meter)+"";
        if(!String.valueOf(total).trim().equals(total_meter))
        {
            total_meter= total+"";
            title1ListValue.set(4,total_meter);
            dutyListAdapter.notifyItemChanged(4);
        }
    }

    public void calculateTime(String starting_date, String starting_time, String ending_date, String ending_time) {
        String dateStart = starting_date +" "+starting_time;
        String dateStop = ending_date+" " +ending_time;

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) ;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            String diffMin = null,diffH = null;
            if (diffMinutes<10){
                diffMin = "0"+diffMinutes;
            } else {
                diffMin = diffMinutes+"";
            }
            if (diffHours<10){
                diffH = "0"+diffHours;
            } else {
                diffH = diffHours+"";
            }
            total_time = diffH+":"+diffMin;
            title2ListValue.set(4,total_time);
            dutyListAdapter.notifyItemChanged(4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openTimerPicker(int i, TextView textView, EditText edtValue) {
        timeText = textView;
        openTimer(i);
    }

    private String time;

    private void openTimer(final int i){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time = String.format("%02d:%02d", selectedHour, selectedMinute);
                switch (i){
                    case 0:
                        starting_time = time;
                        timeText.setText(time );
                        break;
                    case 1:
                        reporting_time = time;
                        timeText.setText(time );
                        break;
                    case 2:
                        ending_time = time;
                        timeText.setText(time );
                        break;
                    case 3:
                        time_at_garage = time;
                        timeText.setText(time );
                        if (time_at_garage!=null && time_at_garage.length()>0)
                        calculateTime(starting_date,starting_time,ending_date,time_at_garage);
                        break;
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_duty_slip_txtsave:
                if (meter_at_garage!=null &&meter_at_garage.length()>0){
                    showGarrageMeterDialog();
                }  else if (ending_meter!=null &&ending_meter.length()>0){
                    if (isEndMeter && isEndTime){ }
                    else {
                        showEndMeterDialog();
                    }
                }
                else {
                    saveDutySlip();
                }

         //      new Utility().callFragment(new FeedbackFragment(),getFragmentManager(),R.id.fragment_container,FeedbackFragment.class.getName());
                break;
            case R.id.fragment_duty_slip_txt_charge2:
                showChargeDialog(R.layout.dialog_duty_slip_charge);
                break;
            case R.id.fragment_duty_slip_txt_charge1:
                showCharge2Dialog(R.layout.dialog_duty_slip_charge2);
                break;
            case R.id.fragment_duty_slip_txt_starting_clock:
                if (starting_date==null){
                    openDateTicker(1);
                }
                break;
            case R.id.fragment_duty_slip_txt_end_clock:
                openDateTicker(2);
                break;
        }
    }

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    private void openDateTicker(final int i){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String a = year + "-" + (month + 1) + "-" + day;
                        if (i==1){
                            starting_date = a;
                            txtStartingDate.setText(starting_date);
                        }
                        else if (i==2){
                            ending_date = a;
                            txtEdndingDate.setText(ending_date);
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private Dialog charge1Dialog,charge2Dialog;

    private void showChargeDialog(int id){
        charge1Dialog = new Dialog(getContext());
        charge1Dialog.setContentView(id);
        charge1Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        charge1Dialog.show();
        LinearLayout linearLayout = charge1Dialog.findViewById(R.id.duty_slip_charge1_cross);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charge1Dialog.dismiss();
            }
        });
        final EditText edtBeverage = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_beverage);
        final EditText edtEntrance = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_entrance);
        final EditText edtDTA = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_driver_ta);
        final EditText edtGuide = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_guide);

        if (beverages_charges!=null)
        edtBeverage.setText(beverages_charges);
        if (entrance_charge!=null)
        edtEntrance.setText(entrance_charge);
        if (driver_ta!=null)
        edtDTA.setText(driver_ta);
        if (guide_charge!=null)
        edtGuide.setText(guide_charge);

        TextView txtSave = charge1Dialog.findViewById(R.id.duty_slip_charge1_save);
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beverages_charges = edtBeverage.getText().toString().trim();
                entrance_charge = edtEntrance.getText().toString().trim();
                driver_ta = edtDTA.getText().toString().trim();
                guide_charge = edtGuide.getText().toString().trim();
                saveMis2();
               // charge1Dialog.dismiss();
            }
        });

    }

    private void showCharge2Dialog(int id){
        charge2Dialog = new Dialog(getContext());
        charge2Dialog.setContentView(id);
        charge2Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        charge2Dialog.show();
        LinearLayout linearLayout = charge2Dialog.findViewById(R.id.duty_slip_charge2_cross);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charge2Dialog.dismiss();
            }
        });

        final EditText edtNight = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_night);
        final EditText edtEToll = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_e_toll);
        final EditText edtParking = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_parking);
        final EditText edttoll = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_toll);
        final EditText edtinterstate = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_entrance);
        final EditText edtOther = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_other);

        if (e_toll!=null)
            edtEToll.setText(e_toll);
        if (night_halt!=null)
            edtNight.setText(night_halt);
        if (parking!=null)
            edtParking.setText(parking);
        if (toll!=null)
            edttoll.setText(toll);
        if (interstate_tax!=null)
            edtinterstate.setText(interstate_tax);
        if (others!=null)
            edtOther.setText(others);

        TextView txtSave = charge2Dialog.findViewById(R.id.duty_slip_charge2_save);
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e_toll = edtEToll.getText().toString().trim();
                night_halt = edtNight.getText().toString().trim();
                parking = edtParking.getText().toString().trim();
                toll = edttoll.getText().toString().trim();
                interstate_tax = edtinterstate.getText().toString().trim();
                others = edtOther.getText().toString().trim();
                saveMis1();
                //charge2Dialog.dismiss();
            }
        });
    }

    private void fetchDetails(final String reservationId) {
        new Utility().showProgressDialog(getContext());

        Call<ViewDetailsData> call = APIClient.getInstance().getApiInterface().job_details(reservationId);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    @Override
    public void onApiResponse(Object response) {
       new Utility().hideDialog();
        main_Layout.setVisibility(View.VISIBLE);
        if (response!=null){
            if (response instanceof ViewDetailsData){
                ViewDetailsData viewDetailsData = new ViewDetailsData();
                viewDetailsData = (ViewDetailsData) response;
                if (viewDetailsData.getJob_detail()!=null){
                    if (viewDetailsData.getJob_detail().getVehiclerequest()!=null)
                        txtVehicleReq.setText(viewDetailsData.getJob_detail().getVehiclerequest());
                    if (viewDetailsData.getJob_detail().get_$CompanyName59()!=null){
                        txtCompanyName.setText(viewDetailsData.getJob_detail().get_$CompanyName59());
                        txtCompName2.setText(viewDetailsData.getJob_detail().get_$CompanyName59());
                    }
                    if (viewDetailsData.getJob_detail().getDutyslipnum()!=null)
                        dutyslipnum = viewDetailsData.getJob_detail().getDutyslipnum();
//            txtCompFax.setText(viewDetailsData.getJob_detail().getVehiclerequest());
//            txtCompMobile.setText(viewDetailsData.getJob_detail().getVehiclerequest());
//            txtCompEmail.setText(viewDetailsData.getJob_detail().getVehiclerequest());
//            txtComapAdd.setText(viewDetailsData.getJob_detail().getVehiclerequest());
                    if (viewDetailsData.getJob_detail().getDetails()!=null)
                        txtDetails.setText(viewDetailsData.getJob_detail().getDetails());
                    if (viewDetailsData.getJob_detail().getBookername()!=null)
                        txtBookedName.setText(viewDetailsData.getJob_detail().getBookername());
                    if (viewDetailsData.getJob_detail().getAssignment()!=null)
                        txtAssignment.setText(viewDetailsData.getJob_detail().getAssignment());
                    if (viewDetailsData.getJob_detail().get_$CityOfUsage184()!=null)
                        txtcity.setText(viewDetailsData.getJob_detail().get_$CityOfUsage184());
                    if (viewDetailsData.getJob_detail().getPaymentmode()!=null)
                        txtPaymentMode.setText(viewDetailsData.getJob_detail().getPaymentmode());
                    if (viewDetailsData.getJob_detail().getReportingPlace()!=null)
                        txtReportPlace.setText(viewDetailsData.getJob_detail().getReportingPlace());
                    if (viewDetailsData.getJob_detail().getBookercontactno()!=null)
                        txtBookerNo.setText(viewDetailsData.getJob_detail().getBookercontactno());
                    if (viewDetailsData.getJob_detail().getBookername()!=null)
                        txtUserName.setText(viewDetailsData.getJob_detail().getBookername());
                    if (viewDetailsData.getJob_detail().getDrivername()!=null)
                        txtDriverDetail.setText(viewDetailsData.getJob_detail().getDrivername());
                    if (viewDetailsData.getJob_detail().getStarting_date()!=null &&
                            !viewDetailsData.getJob_detail().getStarting_date().equals("0000-00-00")){
                        starting_date = viewDetailsData.getJob_detail().getStarting_date();
                        txtStartingDate.setText(viewDetailsData.getJob_detail().getStarting_date());
                    } else {
                        txtStartingDate.setText(viewDetailsData.getJob_detail().getReportingfrom());
                        starting_date = viewDetailsData.getJob_detail().getReportingfrom();
                    }
                    if (viewDetailsData.getJob_detail().getEnding_date()!=null){
                        ending_date = viewDetailsData.getJob_detail().getEnding_date();
                        txtEdndingDate.setText(viewDetailsData.getJob_detail().getEnding_date());
                    } else {
                        txtEdndingDate.setText(viewDetailsData.getJob_detail().getReporingto());
                        ending_date=viewDetailsData.getJob_detail().getReporingto();
                    }
                    if (viewDetailsData.getJob_detail().getStarting_meter()!=null)
                        starting_meter = viewDetailsData.getJob_detail().getStarting_meter();
                    title1ListValue.add(starting_meter);
                    if (viewDetailsData.getJob_detail().getReporting_meter()!=null)
                        reporting_meter = viewDetailsData.getJob_detail().getReporting_meter();
                    title1ListValue.add(reporting_meter);
                    if (viewDetailsData.getJob_detail().getEnding_meter()!=null
                            &&viewDetailsData.getJob_detail().getEnding_meter().length()>0){
                        isEndMeter =true;
                        ending_meter = viewDetailsData.getJob_detail().getEnding_meter();
                    }
                    title1ListValue.add(ending_meter);
                    if (viewDetailsData.getJob_detail().getMeter_at_garage()!=null)
                        meter_at_garage = viewDetailsData.getJob_detail().getMeter_at_garage();
                    title1ListValue.add(meter_at_garage);
                    if (viewDetailsData.getJob_detail().getTotal_meter()!=null)
                        total_meter = viewDetailsData.getJob_detail().getTotal_meter();
                    title1ListValue.add(total_meter);
                    if (viewDetailsData.getJob_detail().getStarting_time()!=null)
                        starting_time = viewDetailsData.getJob_detail().getStarting_time();
                    title2ListValue.add(starting_time);
                    if (viewDetailsData.getJob_detail().getReporingtime()!=null)
                        reporting_time = viewDetailsData.getJob_detail().getReporting_time();
                    title2ListValue.add(reporting_time);
                    if (viewDetailsData.getJob_detail().getEnding_time()!=null&&viewDetailsData.getJob_detail().getEnding_time().length()>0){
                        isEndTime = true;
                        ending_time = viewDetailsData.getJob_detail().getEnding_time();
                    }

                    title2ListValue.add(ending_time);
                    if (viewDetailsData.getJob_detail().getTime_at_garage()!=null)
                        time_at_garage = viewDetailsData.getJob_detail().getTime_at_garage();
                    title2ListValue.add(time_at_garage);
                    if (viewDetailsData.getJob_detail().getTotal_time()!=null)
                        total_time = viewDetailsData.getJob_detail().getTotal_time();
                    title2ListValue.add(total_time);
                    /**
                     * night_halt : null
                     * toll : null
                     * parking : null
                     * e_toll : null
                     * interstate_tax : null
                     * others : null
                     */

                    if (viewDetailsData.getJob_detail().getMisc_charges1().getNight_halt()!=null){
                        night_halt = viewDetailsData.getJob_detail().getMisc_charges1().getNight_halt()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getE_toll()!=null){
                        e_toll = viewDetailsData.getJob_detail().getMisc_charges1().getE_toll()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getInterstate_tax()!=null){
                        interstate_tax = viewDetailsData.getJob_detail().getMisc_charges1().getInterstate_tax()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getToll()!=null){
                        toll = viewDetailsData.getJob_detail().getMisc_charges1().getToll()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getOthers()!=null){
                        others = viewDetailsData.getJob_detail().getMisc_charges1().getOthers()+"";
                    }
                    calculateMischrage1();
                     /**
                     * beverage_charge : null
                     * entrance_charge : null
                     * parking : null
                     * driver_ta : null
                     */

                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge()!=null){
                        beverages_charges = viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge()!=null){
                        entrance_charge = viewDetailsData.getJob_detail().getMisc_charges2().getEntrance_charge()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge()!=null){
                        guide_charge = viewDetailsData.getJob_detail().getMisc_charges2().getParking()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getParking()!=null){
                        parking = viewDetailsData.getJob_detail().getMisc_charges1().getParking()+"";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge()!=null){
                        driver_ta = viewDetailsData.getJob_detail().getMisc_charges2().getDriver_ta()+"";
                    }
                    calculateMischrage2();
                    setAdapter(title2ListValue,title1ListValue,isEndMeter, isEndTime);
                }
            } else if (response instanceof SaveResponse){
                SaveResponse saveResponse = (SaveResponse) response;
                if (saveResponse.getStatus().equals("1")){
                    Utility.showToast(getContext(), "Saved successfully...");
                    if (charge1Dialog!=null)
                        charge1Dialog.dismiss();
                    if (charge2Dialog!=null)
                        charge2Dialog.dismiss();

                    if (garageMeterDialog!=null){
                        garageMeterDialog.dismiss();
                        new Utility().callFragment(new ThankFragment(),getFragmentManager(),R.id.fragment_container,
                                FeedbackFragment.class.getName());
                    }
                    if (txtMisc1Value.getText().toString().equals("None")||
                            !txtMisc1Value.getText().toString().equals("None")||
                            txtMisc2Value.getText().toString().equals("None")||
                            !txtMisc2Value.getText().toString().equals("None")){
                        end_status=0;
                    }
                    if (endMeterDialog!=null){
                        endMeterDialog.dismiss();
                        FeedbackFragment feedbackFragment = new FeedbackFragment();
                        new Utility().callFragment(feedbackFragment,getFragmentManager(),R.id.fragment_container,
                                FeedbackFragment.class.getName());
                    }
                } else {
                    Utility.showToast(getContext(),getContext().getResources().getString(R.string.error));
                }
            }
        } else {
            Utility.showToast(getContext(),getResources().getString(R.string.error));
        }
    }

    private void calculateMischrage2(){
        if (beverages_charges==null || beverages_charges.length()==0){
            beverages_charges = "0";
        } else {
            beverages_charges = beverages_charges;
        }

        if (entrance_charge==null || entrance_charge.length()==0){
            entrance_charge = "0";
        } else {
            entrance_charge = toll;
        }

        if (guide_charge==null || guide_charge.length()==0){
            guide_charge = "0";
        } else {
            guide_charge = guide_charge;
        }

        if (driver_ta==null || driver_ta.length()==0){
            driver_ta = "0";
        } else {
            driver_ta = driver_ta;
        }

        double price = Double.parseDouble(beverages_charges)+ Double.parseDouble(entrance_charge)+Double.parseDouble(guide_charge)
                +Double.parseDouble(driver_ta);
        if (price>0)
            txtMisc2Value.setText(price+"");
        else txtMisc2Value.setText("None");
    }


    private void calculateMischrage1(){
        if (night_halt==null || night_halt.length()==0){
            night_halt = "0";
        } else {
            night_halt = night_halt;
        }

        if (toll==null || toll.length()==0){
            toll = "0";
        } else {
            toll = toll;
        }

        if (parking==null || parking.length()==0){
            parking = "0";
        } else {
            parking = parking;
        }

        if (e_toll==null || e_toll.length()==0){
            e_toll = "0";
        } else {
            e_toll = e_toll;
        }

        if (interstate_tax==null || interstate_tax.length()==0){
            interstate_tax = "0";
        } else {
            interstate_tax = interstate_tax;
        }
        if (others==null || others.length()==0){
            others = "0";
        } else {
            others = others;
        }

        double price = Double.parseDouble(night_halt)+ Double.parseDouble(toll)+Double.parseDouble(parking)
                +Double.parseDouble(e_toll)+Double.parseDouble(interstate_tax)+Double.parseDouble(others);
        if (price>0)
        txtMisc1Value.setText(price+"");
        else txtMisc1Value.setText("None");
    }


    @Override
    public void onApiFailure(String message) {
       try {
           Utility.showToast(getContext(),getContext().getResources().getString(R.string.error));
       }catch (Exception e){
           Log.d("TAG", "onApiFailure: "+e.getMessage());
       }
        new Utility().hideDialog();
    }

    private void saveDutySlip(){
        new Utility().showProgressDialog(getContext());
        if (ending_date==null)
            ending_date="";
        if (starting_meter==null)
            starting_meter="";
        if (reporting_meter==null)
            reporting_meter="";
        if (starting_time==null)
            starting_time="";
        if (reporting_time==null)
            reporting_time="";
        if (ending_meter==null)
            ending_meter="";
        if (ending_time==null)
            ending_time="";
        if (meter_at_garage==null)
            meter_at_garage="";
        if (time_at_garage==null)
            time_at_garage="";
        if (total_meter==null)
            total_meter="";
        if (total_time==null)
            total_time="";

        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().saveDutySlip(dutyslipnum,starting_date,ending_date,
                starting_meter,reporting_meter,starting_time,reporting_time,ending_meter,ending_time,
                meter_at_garage,time_at_garage,total_meter,total_time);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    private void saveMis1(){
        new Utility().showProgressDialog(getContext());
        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().saveMis1(dutyslipnum,night_halt,
                toll,parking,e_toll,interstate_tax,others);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    private void saveMis2(){
        new Utility().showProgressDialog(getContext());
        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().saveMis2(dutyslipnum,beverages_charges,
                entrance_charge,guide_charge,driver_ta);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    private Dialog endMeterDialog,garageMeterDialog;

    private void showEndMeterDialog(){
        endMeterDialog = new Dialog(getContext());
        endMeterDialog.setContentView(R.layout.dialog_end_meter_confirmation);
        endMeterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        endMeterDialog.show();

        TextView txtOk = endMeterDialog.findViewById(R.id.dialog_end_meter_confim_ok);
        TextView txtCancel = endMeterDialog.findViewById(R.id.dialog_end_meter_confim_cancel);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endMeterDialog.dismiss();
            }
        });

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endMeterDialog.dismiss();
                saveDutySlip();
            }
        });

    }

    private void showGarrageMeterDialog(){
        garageMeterDialog = new Dialog(getContext());
        garageMeterDialog.setContentView(R.layout.dialog_garrage_meter_confirmation);
        garageMeterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        garageMeterDialog.show();

        TextView txtOk = garageMeterDialog.findViewById(R.id.dialog_end_meter_confim_ok);
        TextView txtCancel = garageMeterDialog.findViewById(R.id.dialog_end_meter_confim_cancel);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                garageMeterDialog.dismiss();
            }
        });

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                garageMeterDialog.dismiss();
                saveDutySlip();
            }
        });
    }

}
