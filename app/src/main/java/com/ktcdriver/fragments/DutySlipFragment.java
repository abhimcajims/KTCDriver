package com.ktcdriver.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.adapter.DutyListAdapter;
import com.ktcdriver.model.ViewDetailsData;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;

import java.util.ArrayList;
import java.util.Calendar;

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
    private TextView txtSave,txtCompanyName,txtComapAdd,txtCompMobile,txtCompEmail,txtCompFax,txtDriverDetail,txtCarDetail,
    txtrqNo,txtBookerNo, txtPaymentMode,txtCompName2,txtBookedName,txtReportPlace, txtAssignment,
            txtDetails,txtStartingDate,txtEdndingDate,txtcity,txtVehicleReq,txtUserName;
    private LinearLayout txtCharge1, txtCharge2;
    private String reservationId;

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
        recyclerView = getView().findViewById(R.id.fragment_duty_slip);
        txtSave = getView().findViewById(R.id.fragment_duty_slip_txtsave);
        txtCharge1 = getView().findViewById(R.id.fragment_duty_slip_txt_charge1);
        txtCharge2 = getView().findViewById(R.id.fragment_duty_slip_txt_charge2);
        txtCompanyName = getView().findViewById(R.id.fragment_duty_slip_txt_companyName);
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
        txtCompanyName = getView().findViewById(R.id.fragment_duty_slip_txt_companyName);
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
       // reservationId = "709180043";
        fetchDetails(reservationId);
    }

    private void setAdapter(ArrayList<String> title2ListValue,ArrayList<String> titleListValue){
        DutyListAdapter dutyListAdapter = new DutyListAdapter(getContext(),this,title2List,title1List,
                title2ListValue,titleListValue);
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

    @Override
    public void openTimerPicker(int i,TextView textView) {
        String selectedTime = openTimer();
        textView.setText(selectedTime);

        switch (i){
            case 0:
                viewDetailsData.getJob_detail().setStarting_time(selectedTime);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    String time;
    private String openTimer(){

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               time = selectedHour + ":" + selectedMinute;
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
        return time;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_duty_slip_txtsave:
                new Utility().callFragment(new FeedbackFragment(),getFragmentManager(),R.id.fragment_container,FeedbackFragment.class.getName());
                break;
            case R.id.fragment_duty_slip_txt_charge2:
                showChargeDialog(R.layout.dialog_duty_slip_charge);

                break;
            case R.id.fragment_duty_slip_txt_charge1:
                showCharge2Dialog(R.layout.dialog_duty_slip_charge2);

                break;
        }
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

        TextView txtSave = charge1Dialog.findViewById(R.id.duty_slip_charge1_save);
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charge1Dialog.dismiss();
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

        TextView txtSave = charge2Dialog.findViewById(R.id.duty_slip_charge2_save);
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charge2Dialog.dismiss();
            }
        });
    }

    private ProgressDialog progressDialog;
    private void fetchDetails(final String reservationId) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Call<ViewDetailsData> call = APIClient.getInstance().getApiInterface().job_details(reservationId);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    private ViewDetailsData viewDetailsData;

    @Override
    public void onApiResponse(Object response) {
        progressDialog.dismiss();
        main_Layout.setVisibility(View.VISIBLE);
        if (response!=null){
            viewDetailsData = new ViewDetailsData();
            viewDetailsData= (ViewDetailsData) response;
            if (viewDetailsData.getJob_detail()!=null){
                if (viewDetailsData.getJob_detail().getVehiclerequest()!=null)
                    txtVehicleReq.setText(viewDetailsData.getJob_detail().getVehiclerequest());
                if (viewDetailsData.getJob_detail().get_$CompanyName59()!=null){
                    txtCompanyName.setText(viewDetailsData.getJob_detail().get_$CompanyName59());
                    txtCompName2.setText(viewDetailsData.getJob_detail().get_$CompanyName59());
                }

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
                if (viewDetailsData.getJob_detail().getStarting_date()!=null)
                    txtStartingDate.setText(viewDetailsData.getJob_detail().getStarting_date());
                if (viewDetailsData.getJob_detail().getEnding_date()!=null)
                    txtEdndingDate.setText(viewDetailsData.getJob_detail().getEnding_date());

                title1ListValue.add(viewDetailsData.getJob_detail().getStarting_meter());
                title1ListValue.add(viewDetailsData.getJob_detail().getReporting_meter());
                title1ListValue.add(viewDetailsData.getJob_detail().getEnding_meter());
                title1ListValue.add(viewDetailsData.getJob_detail().getMeter_at_garage());
                title1ListValue.add(viewDetailsData.getJob_detail().getTotal_meter());
                title2ListValue.add(viewDetailsData.getJob_detail().getStarting_time());
                title2ListValue.add(viewDetailsData.getJob_detail().getReporingtime());
                title2ListValue.add(viewDetailsData.getJob_detail().getEnding_time());
                title2ListValue.add(viewDetailsData.getJob_detail().getTime_at_garage());
                title2ListValue.add(viewDetailsData.getJob_detail().getTotal_time());
                setAdapter(title2ListValue,title1ListValue);

            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        Utility.showToast(getContext(),getContext().getResources().getString(R.string.error));
        progressDialog.dismiss();
    }

}
