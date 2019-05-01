package com.ktcdrivers.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ktcdrivers.R;
import com.ktcdrivers.activities.home.HomeActivity;
import com.ktcdrivers.adapter.AdapterDutySlipUploadDocument;
import com.ktcdrivers.adapter.DocAdapter;
import com.ktcdrivers.adapter.DutyListAdapter;
import com.ktcdrivers.model.SaveResponse;
import com.ktcdrivers.model.ViewDetailsData;
import com.ktcdrivers.utils.Constant;
import com.ktcdrivers.utils.ImageInputHelper;
import com.ktcdrivers.utils.Utility;
import com.ktcdrivers.webservices.APIClient;
import com.ktcdrivers.webservices.OnResponseInterface;
import com.ktcdrivers.webservices.ResponseListner;
import com.mukesh.permissions.AppPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class DutySlipFragment extends Fragment implements DutyListAdapter.DutyListInterface, View.OnClickListener,
        OnResponseInterface, AdapterDutySlipUploadDocument.UploadDocInterface, DocAdapter.DocInterface {
    public static String dutyslipnum;
    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    int pos;
    private LinearLayout main_Layout;
    private RecyclerView recyclerView, recyclerViewAddMore;
    private ArrayList<String> title2List;
    private ArrayList<String> img_base_64;
    private ArrayList<String> slip_name;
    private ArrayList<String> title1List;
    private ArrayList<String> title2ListValue;
    private ArrayList<String> title1ListValue;
    private boolean isEndMeter, isEndTime;
    private List<ViewDetailsData.ImagelistBean> imagelistBeans;
    private TextView txtCompanyName;
    private TextView txtComapAdd;
    private TextView txtCompMobile;
    private TextView txtCompEmail;
    private TextView txtCompFax;
    private TextView txtDriverDetail;
    private TextView txtCarDetail;
    private TextView txtrqNo;
    private TextView txtGuestNo;
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
    private TextView txtUserName, txtBookerNo, txtMisc1Value, txtMisc2Value, txtAddmore, txtSaveDoc;
    private LinearLayout txtCharge1, txtCharge2;
    private String reservationId, starting_date, ending_date, starting_meter,initial_meter,
            starting_time, reporting_meter, reporting_time, ending_meter, ending_time, meter_at_garage,
            time_at_garage, total_meter, total_time, night_halt, toll, parking, e_toll, interstate_tax, others,
            beverages_charges, entrance_charge, guide_charge, driver_ta, base_64, remove_id, signature;
    private int end_status;
    private ArrayList<String> arr_img, arr_document_name;
    private ImageView startClock, endClock;
    private ImageInputHelper imageInputHelper;
    private AppPermissions appPermissions;
    private String userChoosenTask;
    private EditText edt_upload_document;
    private TextView tv_upload_document;
    private RecyclerView rv_upload_document;
    private AdapterDutySlipUploadDocument adapterDutySlipUploadDocument;
    private DocAdapter docAdapter;
    private DutyListAdapter dutyListAdapter;
    private TextView timeText;
    private String time;
    private Dialog charge1Dialog, charge2Dialog;
    private EditText edtNight, edtEToll, edtParking, edttoll, edtinterstate, edtOther,
            edtBeverage, edtEntrance, edtDTA, edtGuide;
    private Dialog endMeterDialog, garageMeterDialog;

    public DutySlipFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        }
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

    private void init() {
        imagelistBeans = new ArrayList<>();
        appPermissions = new AppPermissions(getActivity());

        //Document Stuff
        arr_img = new ArrayList<>();
        arr_document_name = new ArrayList<>();
        title1List = new ArrayList<>();
        title2List = new ArrayList<>();
        title1ListValue = new ArrayList<>();
        title2ListValue = new ArrayList<>();

        img_base_64 = new ArrayList<>();
        slip_name = new ArrayList<>();

        imageInputHelper = new ImageInputHelper(this);

        if (getArguments() != null) {
            reservationId = getArguments().getString("reservationid");
        }

        title1List.add("Starting Meter(At Garrage)");
        title1List.add("Reporting Meter");
        title1List.add("Ending Meter");
        title1List.add("Meter at Garrage");
        title1List.add("Total Meter");
        title2List.add("Starting Time(At Garrage)");
        title2List.add("Reporting Time");
        title2List.add("Ending Time");
        title2List.add("Time at Garrage");
        title2List.add("Total Time");

        txtBookerNo = getView().findViewById(R.id.fragment_duty_slip_txt_bookerNo);
        txtSaveDoc = getView().findViewById(R.id.duty_slip_save);
        txtAddmore = getView().findViewById(R.id.duty_slip_add_more);
        recyclerViewAddMore = getView().findViewById(R.id.fragment_duty_slip_photos_recycler);
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
        txtGuestNo = getView().findViewById(R.id.fragment_duty_slip_txt_booker_no);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        recyclerViewAddMore.setLayoutManager(linearLayoutManager1);

        txtSave.setOnClickListener(this);
        txtCharge2.setOnClickListener(this);
        txtCharge1.setOnClickListener(this);
        startClock.setOnClickListener(this);
        endClock.setOnClickListener(this);
        // reservationId = "709180043";
        fetchDetails(reservationId);

        //Upload Document Stuffs
//        tv_upload_document = getView().findViewById(R.id.fragment_duty_slip_upload_document);
        edt_upload_document = getView().findViewById(R.id.fragment_duty_slip_edt_upload_document);

        // Inititally setting one Document
        rv_upload_document = getView().findViewById(R.id.fragment_duty_slip_upload_document_rv);
        rv_upload_document.setLayoutManager(new LinearLayoutManager(getContext()));
        setDocAdapter();
        txtAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arr_img.get(arr_img.size() - 1).equals("Photo") ||
                        arr_document_name.get(arr_document_name.size() - 1).equals("Enter Slip Name")) {
//                    arr_img.add("Show Error Message");
//                    adapterDutySlipUploadDocument.notifyDataSetChanged();
//                    arr_img.remove(arr_img.size()-1);

                    Toast.makeText(getContext(), "Please Enter Slip Name and Select Document first",
                            Toast.LENGTH_LONG).show();

                } else {
                    arr_img.add("Photo");
                    arr_document_name.add("Enter Slip Name");
                    adapterDutySlipUploadDocument.notifyDataSetChanged();
                }
            }
        });

        txtSaveDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_base_64 != null && img_base_64.size() > 0 &&
                        !arr_document_name.get(arr_document_name.size() - 1).equals("Enter Slip Name")) {
                    saveDoc();
                } else {
                    Toast.makeText(getContext(), "Please Enter Slip Name and Select Document first",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setDocAdapter() {
        arr_img.add("Photo");
        arr_document_name.add("Enter Slip Name");
        adapterDutySlipUploadDocument = new
                AdapterDutySlipUploadDocument(getContext(), arr_img, arr_document_name, this);
        rv_upload_document.setAdapter(adapterDutySlipUploadDocument);

    }

    private void setSlipAdapter(List<ViewDetailsData.ImagelistBean> imagelistBeans) {
        if (docAdapter == null) {
            docAdapter = new DocAdapter(getContext(), this.imagelistBeans, this);
            recyclerViewAddMore.setAdapter(docAdapter);
        } else {
            docAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getText(int pos, String value) {
        if (value != null && pos == slip_name.size() && value.length() > 0 && value.length() == 1) {
            slip_name.add(pos, value);
        } else {
            slip_name.set(pos, value);
        }

        if (value != null && value.length() > 0) {
            if (pos < arr_document_name.size()) {
                arr_document_name.set(pos, value);
            }
        }
        arr_document_name.add(value);

    }

    private void setAdapter(ArrayList<String> title2ListValue, ArrayList<String> titleListValue,
                            boolean isEndMeter, boolean isEndTime) {
        dutyListAdapter = new DutyListAdapter(getContext(), this, title2List,
                title1List, title2ListValue, titleListValue, isEndMeter, isEndTime);
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
    public void onTextChanged(int position, String charSeq) {
        Log.d("TAG", "onTextChanged: " + charSeq);
        switch (position) {
            case 0:
                starting_meter = charSeq;
                break;
            case 1:
                reporting_meter = charSeq;
//                dutyListAdapter.notifyItemChanged(1);
                break;
            case 2:
                ending_meter = charSeq;
                end_status = 1;
                break;
            case 3:
                meter_at_garage = charSeq;
                if (meter_at_garage != null && meter_at_garage.length() > 0) {
                    calculateMeter();
                }
                break;
        }
    }

    private void calculateMeter() {
        if (meter_at_garage != null && meter_at_garage.length() > 0) {
            double total = (/*Double.parseDouble(reporting_meter)
                    + Double.parseDouble(ending_meter) +*/ Double.parseDouble(meter_at_garage)) -
                    (Double.parseDouble(starting_meter));
            if (total_meter != null && total_meter.length() > 0)
                total_meter = Double.parseDouble(total_meter) + "";
            if (!String.valueOf(total).trim().equals(total_meter)) {
                total_meter = ((int) total) + "";
                title1ListValue.set(4, total_meter);
                if (dutyListAdapter != null)
                    dutyListAdapter.notifyItemChanged(4);
            }
        }
    }

    public void calculateTime(String starting_date, String starting_time, String ending_date, String time_at_garage) {
        String dateStart = starting_date + " " + starting_time;
        String dateStop = ending_date + " " + time_at_garage;
        if (time_at_garage != null && time_at_garage.length() > 0) {
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
                long diffHours = diff / (60 * 60 * 1000);
                long diffDays = diff / (24 * 60 * 60 * 1000);
                String diffMin = null, diffH = null;
                if (diffMinutes < 10) {
                    diffMin = "0" + diffMinutes;
                } else {
                    diffMin = diffMinutes + "";
                }
                if (diffHours < 10) {
                    diffH = "0" + diffHours;
                } else {
                    diffH = diffHours + "";
                }
                total_time = diffH + ":" + diffMin;
                title2ListValue.set(4, total_time);
                dutyListAdapter.notifyItemChanged(4);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void openTimerPicker(int i, TextView textView, EditText edtValue) {
        timeText = textView;
        openTimer(i);
    }

    private void openTimer(final int i) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar c = Calendar.getInstance();
                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);
                switch (i) {
                    case 0:
                        int hour1 = selectedHour % 12;
                        timeText.setText(String.format("%02d:%02d %s", hour1 == 0 ? 12 : hour1,
                                selectedMinute, selectedHour < 12 ? "am" : "pm"));
                        time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        starting_time = time;
                        if (time != null && time.length() > 0)
                            timeText.setText(time);
                        time=null;
                        break;
                    case 1:
                        validateTime(datetime,c,selectedHour,selectedMinute);
                        reporting_time = time;
                        if (time != null && time.length() > 0)
                            timeText.setText(time);
                        break;
                    case 2:
                        validateTime(datetime,c,selectedHour,selectedMinute);

                        ending_time = time;
                        if (time != null && time.length() > 0)
                            timeText.setText(time);
                        break;
                    case 3:
                        validateTime(datetime,c,selectedHour,selectedMinute);

                        time_at_garage = time;
                        if (time != null && time.length() > 0)
                            timeText.setText(time);
                        if (time_at_garage != null && time_at_garage.length() > 0)
                            calculateTime(starting_date, starting_time, ending_date, time_at_garage);
                        break;
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void validateTime(Calendar datetime, Calendar c, int selectedHour, int selectedMinute){
        if (!ending_date.isEmpty()) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate;
            try {
                myDate = timeFormat.parse(ending_date);
                String cDate = timeFormat.format(datetime.getTime());
                if (ending_date.equals(cDate)){
                    Log.d("TAG", "onTimeSet: equal");
                    if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                        //it's after current
                        int hour1 = selectedHour % 12;
                        timeText.setText(String.format("%02d:%02d %s", hour1 == 0 ? 12 : hour1,
                                selectedMinute, selectedHour < 12 ? "am" : "pm"));
                        time = String.format("%02d:%02d", selectedHour, selectedMinute);

                    } else Toast.makeText(getContext(), "You can't select previous time , Please change your ending date.", Toast.LENGTH_LONG).show();

                }
                else if (myDate.after(datetime.getTime())) {
                           /* if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                                //it's after current
                                int hour1 = selectedHour % 12;
                                timeText.setText(String.format("%02d:%02d %s", hour1 == 0 ? 12 : hour1,
                                        selectedMinute, selectedHour < 12 ? "am" : "pm"));
                                time = String.format("%02d:%02d", selectedHour, selectedMinute);

                            } else {
                                //it's before current'
                                Toast.makeText(getContext(), "You can't select previous time , Please change your ending date.", Toast.LENGTH_LONG).show();
                            }*/
                    int hour1 = selectedHour % 12;
                    timeText.setText(String.format("%02d:%02d %s", hour1 == 0 ? 12 : hour1,
                            selectedMinute, selectedHour < 12 ? "am" : "pm"));
                    time = String.format("%02d:%02d", selectedHour, selectedMinute);
                } else {
                    int hour1 = selectedHour % 12;
                    Toast.makeText(getContext(), "You can't select previous time , Please change your ending date.", Toast.LENGTH_LONG).show();
                          /*  timeText.setText(String.format("%02d:%02d %s", hour1 == 0 ? 12 : hour1,
                                    selectedMinute, selectedHour < 12 ? "am" : "pm"));
                            time = String.format("%02d:%02d", selectedHour, selectedMinute);*/
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_duty_slip_txtsave:
               /* try {
                    if (meter_at_garage != null && meter_at_garage.length() > 0
                            || ending_meter != null && ending_meter.length() > 0
                            || reporting_meter != null && reporting_meter.length() > 0
                            || starting_meter != null && starting_meter.length() > 0) {*/

               /*if(ending_meter != null && ending_meter.length()>0){
                   if(starting_meter == null || starting_meter.length() <=0 ||
                           reporting_meter == null || reporting_meter.length() <= 0){
                       Utility.showToast(getContext(), "Please fill Sarting Time and Reporting Time first");
                       break;
                   } else {
                   }
               }*/

                if (Double.parseDouble(starting_meter)<Double.parseDouble(initial_meter)){
                    Utility.showToast(getContext(),"Stating meter should not less than"+initial_meter);
                }
                else if (meter_at_garage != null && meter_at_garage.length() > 0) {

                    if (ending_meter != null && ending_meter.length() > 0) {

                        if (Double.parseDouble(meter_at_garage) < Double.parseDouble(ending_meter)) {
                            Utility.showToast(getContext(), "Meter at garrage should be > than ending meter");
                        } else {
                            showGarrageMeterDialog();
                        }
                    }
                } else if (ending_meter != null && ending_meter.length() > 0) {
                    if (!isEndMeter || !isEndTime) {
                        if (reporting_meter != null && reporting_meter.length() > 0) {
                            if (Double.parseDouble(ending_meter) < Double.parseDouble(reporting_meter)) {
                                Utility.showToast(getContext(), "Ending meter should be > than reporting meter");
                            } else {

                                if (signature == null || signature.length() == 0) {
                                    showEndMeterDialog();
                                } else {
                                    saveDutySlip();
                                }
//                                    showEndMeterDialog();
                            }
                        } else {
                            if (signature == null || signature.length() == 0) {
                                showEndMeterDialog();
                            } else {
                                saveDutySlip();
                            }
//                            showEndMeterDialog();
                        }
                    }
                } else {
                    if (starting_meter != null && starting_meter.length() > 0) {
                        if (reporting_meter != null && reporting_meter.length() > 0 && Double.parseDouble(reporting_meter)
                                < Double.parseDouble(starting_meter)) {
                            Utility.showToast(getContext(), "Reporting meter should be > than starting meter");
                        } else {
                            saveDutySlip();
                        }
                    } else {
                        saveDutySlip();
                    }
                }
                break;
            case R.id.fragment_duty_slip_txt_charge2:
/*
                if (ending_meter == null || ending_meter.length() == 0) {
                }
*/
                showChargeDialog();
                break;
            case R.id.fragment_duty_slip_txt_charge1:/*
                if (ending_meter == null || ending_meter.length() == 0) {

                }*/
                showCharge2Dialog();
                break;
            case R.id.fragment_duty_slip_txt_starting_clock:
                if (starting_date == null) {
                    openDateTicker(1);
                }
                break;
            case R.id.fragment_duty_slip_txt_end_clock:
                openDateTicker(2);
                break;

            case R.id.fragment_duty_slip_upload_document:

                if (edt_upload_document.getText().toString().trim().isEmpty()) {
                    Utility.showToast(getContext(), "Please enter text");
                }
                break;

            default:
                break;
        }
    }

    private void openDateTicker(final int i) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        String mMonth = null, dDay;
                        if (month < 10) {
                            mMonth = "0" + (month + 1);
                        } else mMonth = month + 1 + "";
                        if (day < 10) {
                            dDay = "0" + day;
                        } else dDay = day + "";
                        String a = year + "-" + mMonth + "-" + dDay;
                        if (i == 1) {
                            starting_date = a;
                            txtStartingDate.setText(starting_date);
                        } else if (i == 2) {
                            ending_date = a;
                            txtEdndingDate.setText(ending_date);
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void showChargeDialog() {
        charge1Dialog = new Dialog(getContext());
        charge1Dialog.setContentView(R.layout.dialog_duty_slip_charge);
        charge1Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        charge1Dialog.show();
        LinearLayout linearLayout = charge1Dialog.findViewById(R.id.duty_slip_charge1_cross);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charge1Dialog.dismiss();
            }
        });
        edtBeverage = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_beverage);
        edtEntrance = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_entrance);
        edtDTA = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_driver_ta);
        edtGuide = charge1Dialog.findViewById(R.id.duty_slip_charge1_edt_guide);

        if (beverages_charges != null && !beverages_charges.equals("0"))
            edtBeverage.setText(beverages_charges);
        if (entrance_charge != null && !entrance_charge.equals("0"))
            edtEntrance.setText(entrance_charge);
        if (driver_ta != null && !driver_ta.equals("0"))
            edtDTA.setText(driver_ta);
        if (guide_charge != null && !guide_charge.equals("0"))
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

    private void showCharge2Dialog() {
        charge2Dialog = new Dialog(getContext());
        charge2Dialog.setContentView(R.layout.dialog_duty_slip_charge2);
        charge2Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        charge2Dialog.show();
        LinearLayout linearLayout = charge2Dialog.findViewById(R.id.duty_slip_charge2_cross);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charge2Dialog.dismiss();
            }
        });

        edtNight = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_night);
        edtEToll = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_e_toll);
        edtParking = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_parking);
        edttoll = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_toll);
        edtinterstate = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_entrance);
        edtOther = charge2Dialog.findViewById(R.id.duty_slip_charge2_edt_other);

        if (e_toll != null && !e_toll.equals("0"))
            edtEToll.setText(e_toll);
        if (night_halt != null && !night_halt.equals("0"))
            edtNight.setText(night_halt);
        if (parking != null && !parking.equals("0"))
            edtParking.setText(parking);
        if (toll != null && !toll.equals("0"))
            edttoll.setText(toll);
        if (interstate_tax != null && !interstate_tax.equals("0"))
            edtinterstate.setText(interstate_tax);
        if (others != null && !others.equals("0"))
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
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this, getContext()).getResponse(call);
    }

    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("SetTextI18n")
    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        Log.e("MyResponse", new Gson().toJson(response));
        main_Layout.setVisibility(View.VISIBLE);
        if (response != null) {
            if (response instanceof ViewDetailsData) {
                ViewDetailsData viewDetailsData = (ViewDetailsData) response;
                if (imagelistBeans != null)
                    imagelistBeans.clear();
                if (viewDetailsData.getJob_detail() != null) {
                    if (viewDetailsData.getJob_detail().getVehiclerequest() != null)
                        txtVehicleReq.setText(viewDetailsData.getJob_detail().getVehiclerequest());
                    if (viewDetailsData.getJob_detail().getCompany_Name() != null) {
                        //  txtCompanyName.setText(viewDetailsData.getJob_detail().getCompany_Name());
                        txtCompName2.setText(viewDetailsData.getJob_detail().getCompany_Name());
                    }
                    if (viewDetailsData.getJob_detail().getDutyslipnum() != null)
                        dutyslipnum = viewDetailsData.getJob_detail().getDutyslipnum();
                    if (viewDetailsData.getJob_detail().getDetails() != null)
                        txtDetails.setText(viewDetailsData.getJob_detail().getDetails());
                    if (viewDetailsData.getJob_detail().getBookername() != null)
                        txtBookedName.setText(viewDetailsData.getJob_detail().getBookername());
                    if (viewDetailsData.getJob_detail().getAssignment() != null)
                        txtAssignment.setText(viewDetailsData.getJob_detail().getAssignment());
                    if (viewDetailsData.getJob_detail().getCity_of_usage() != null)
                        txtcity.setText(viewDetailsData.getJob_detail().getCity_of_usage());
                    if (viewDetailsData.getJob_detail().getPaymentmode() != null)
                        txtPaymentMode.setText(viewDetailsData.getJob_detail().getPaymentmode());
                    if (viewDetailsData.getJob_detail().getReportingPlace() != null)
                        txtReportPlace.setText(viewDetailsData.getJob_detail().getReportingPlace());
                    if (viewDetailsData.getJob_detail().getGuestcontacto() != null)
                        txtGuestNo.setText(viewDetailsData.getJob_detail().getGuestcontacto());
                    if (viewDetailsData.getJob_detail().getGuestname() != null)
                        txtUserName.setText(viewDetailsData.getJob_detail().getGuestname());
                    if (viewDetailsData.getJob_detail().getDrivername() != null)
                        txtDriverDetail.setText(viewDetailsData.getJob_detail().getDrivername());
                    if (viewDetailsData.getJob_detail().getCarname() != null)
                        txtCarDetail.setText(viewDetailsData.getJob_detail().getCarname() + " (" + viewDetailsData.getJob_detail().getCarno() + ")");
                    if (viewDetailsData.getJob_detail().getReservationid() != null)
                        txtrqNo.setText(viewDetailsData.getJob_detail().getReservationid());
                    if (viewDetailsData.getJob_detail().getStarting_date() != null &&
                            !viewDetailsData.getJob_detail().getStarting_date().equals("0000-00-00")) {
                        starting_date = viewDetailsData.getJob_detail().getStarting_date();
                        boolean valid = isValidFormat("yyyy-MM-dd",starting_date);
                        if (!valid){
                            try {
                                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(starting_date);
                                starting_date = timeFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }

                        txtStartingDate.setText(starting_date);

                    } else {
                        starting_date = viewDetailsData.getJob_detail().getReportingfrom();
                        boolean valid = isValidFormat("yyyy-MM-dd",starting_date);
                        if (!valid){
                            try {
                                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(starting_date);
                                starting_date = timeFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        txtStartingDate.setText(starting_date);


                    }
                    if (viewDetailsData.getJob_detail().getSignature() != null && viewDetailsData.getJob_detail().getSignature().length() > 0) {
                        signature = viewDetailsData.getJob_detail().getSignature();
                    }
                    if (viewDetailsData.getJob_detail().getBookercontactno() != null)
                        txtBookerNo.setText(viewDetailsData.getJob_detail().getBookercontactno());

                    if (viewDetailsData.getJob_detail().getEnding_date() != null) {
                        ending_date = viewDetailsData.getJob_detail().getEnding_date();
                        boolean valid = isValidFormat("yyyy-MM-dd",ending_date);
                        if (!valid){
                            try {
                                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(ending_date);
                                ending_date = timeFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        txtEdndingDate.setText(ending_date);

                    } else {
                        ending_date = viewDetailsData.getJob_detail().getReporingto();
                        boolean valid = isValidFormat("yyyy-MM-dd",ending_date);
                        if (!valid){
                            try {
                                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(ending_date);
                                ending_date = timeFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        txtEdndingDate.setText(ending_date);

                    }
                    if (viewDetailsData.getJob_detail().getStarting_meter() != null)
                        starting_meter = viewDetailsData.getJob_detail().getStarting_meter();
                    initial_meter = starting_meter;
                    title1ListValue.add(starting_meter);

                    if (viewDetailsData.getJob_detail().getReporting_meter() != null)
                        reporting_meter = viewDetailsData.getJob_detail().getReporting_meter();
                    title1ListValue.add(reporting_meter);
                    if (viewDetailsData.getJob_detail().getEnding_meter() != null
                            && viewDetailsData.getJob_detail().getEnding_meter().length() > 0) {
//                        isEndMeter =true;
                        ending_meter = viewDetailsData.getJob_detail().getEnding_meter();
                    }
                    title1ListValue.add(ending_meter);
                    if (viewDetailsData.getJob_detail().getMeter_at_garage() != null)
                        meter_at_garage = viewDetailsData.getJob_detail().getMeter_at_garage();
                    title1ListValue.add(meter_at_garage);
                    if (viewDetailsData.getJob_detail().getTotal_meter() != null)
                        total_meter = viewDetailsData.getJob_detail().getTotal_meter();
                    title1ListValue.add(total_meter);
                    if (viewDetailsData.getJob_detail().getStarting_time() != null)
                        starting_time = viewDetailsData.getJob_detail().getStarting_time();
                    title2ListValue.add(starting_time);
                    if (viewDetailsData.getJob_detail().getReporingtime() != null)
                        reporting_time = viewDetailsData.getJob_detail().getReporting_time();
                    title2ListValue.add(reporting_time);
                    if (viewDetailsData.getJob_detail().getEnding_time() != null && viewDetailsData.getJob_detail().getEnding_time().length() > 0) {
//                        isEndTime = true;
                        ending_time = viewDetailsData.getJob_detail().getEnding_time();
                    }

                    title2ListValue.add(ending_time);
                    if (viewDetailsData.getJob_detail().getTime_at_garage() != null)
                        time_at_garage = viewDetailsData.getJob_detail().getTime_at_garage();
                    title2ListValue.add(time_at_garage);
                    if (viewDetailsData.getJob_detail().getTotal_time() != null)
                        total_time = viewDetailsData.getJob_detail().getTotal_time();
                    title2ListValue.add(total_time);

                    if (viewDetailsData.getJob_detail().getMisc_charges1().getNight_halt() != null) {
                        night_halt = viewDetailsData.getJob_detail().getMisc_charges1().getNight_halt() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getE_toll() != null) {
                        e_toll = viewDetailsData.getJob_detail().getMisc_charges1().getE_toll() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getInterstate_tax() != null) {
                        interstate_tax = viewDetailsData.getJob_detail().getMisc_charges1().getInterstate_tax() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getToll() != null) {
                        toll = viewDetailsData.getJob_detail().getMisc_charges1().getToll() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getOthers() != null) {
                        others = viewDetailsData.getJob_detail().getMisc_charges1().getOthers() + "";
                    }
                    calculateMischrage1();

                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge() != null) {
                        beverages_charges = viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge() != null) {
                        entrance_charge = viewDetailsData.getJob_detail().getMisc_charges2().getEntrance_charge() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge() != null) {
                        guide_charge = viewDetailsData.getJob_detail().getMisc_charges2().getGuide_charge() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges1().getParking() != null) {
                        parking = viewDetailsData.getJob_detail().getMisc_charges1().getParking() + "";
                    }
                    if (viewDetailsData.getJob_detail().getMisc_charges2().getBeverage_charge() != null) {
                        driver_ta = viewDetailsData.getJob_detail().getMisc_charges2().getDriver_ta() + "";
                    }
                    calculateMischrage2();
                    if (starting_date != null && starting_date.length() > 0 && ending_date != null &&
                            ending_date.length() > 0 && starting_time != null && starting_time.length() > 0
                            && ending_time != null && ending_date.length() > 0) {
                        calculateTime(starting_date, starting_time, ending_date, time_at_garage);
                    }
                    if (starting_meter != null && starting_meter.length() > 0 && meter_at_garage != null && meter_at_garage.length() > 0) {
                        calculateMeter();
                    }
                    if (viewDetailsData.getImagelist() != null && viewDetailsData.getImagelist().size() > 0) {
                        imagelistBeans.addAll(viewDetailsData.getImagelist());
                    }
                    setSlipAdapter(imagelistBeans);
                    setAdapter(title2ListValue, title1ListValue, isEndMeter, isEndTime);
                }
            } else if (response instanceof SaveResponse) {
                SaveResponse saveResponse = (SaveResponse) response;
                if (saveResponse.getStatus().equals("1")) {
                    if (charge1Dialog == null && charge2Dialog == null && img_base_64 != null && img_base_64.size() == 0) {
                        Utility.showToast(getContext(), saveResponse.getMessage());
                    }
                    if (charge1Dialog != null)
                        charge1Dialog.dismiss();
                    if (charge2Dialog != null)
                        charge2Dialog.dismiss();
                    if (garageMeterDialog != null) {
                        garageMeterDialog.dismiss();
                        endMeterDialog = null;
                        new Utility().callFragment(new CloseDsFragment(), getFragmentManager(), R.id.fragment_container,
                                CloseDsFragment.class.getName());
                    }

                    if (txtMisc1Value.getText().toString().equals("None") ||
                            !txtMisc1Value.getText().toString().equals("None") ||
                            txtMisc2Value.getText().toString().equals("None") ||
                            !txtMisc2Value.getText().toString().equals("None")) {
                        end_status = 0;
                    }

                    if (endMeterDialog != null) {
                        endMeterDialog.dismiss();
                        //   endMeterDialog = null;

                        if (signature == null || signature.length() == 0) {
                            FeedbackFragment feedbackFragment = new FeedbackFragment();
                            new Utility().callFragment(feedbackFragment, getFragmentManager(), R.id.fragment_container,
                                    FeedbackFragment.class.getName());
                        }
                    }

                    setTotal2();        // To set total of Misc. Charges 1
                    setTotal1();        // To set total of Misc. Charges 2

                } else {
                    Utility.showToast(getContext(), getContext().getResources().getString(R.string.error));
                }
            } else {
                Utility.showToast(getContext(), getResources().getString(R.string.error));
            }
        } else {
            Utility.showToast(getContext(), getResources().getString(R.string.error));
        }
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    private void calculateMischrage2() {
        if (beverages_charges == null || beverages_charges.length() == 0) {
            beverages_charges = "0";
        }
        if (entrance_charge == null || entrance_charge.length() == 0) {
            entrance_charge = "0";
        } else {
            entrance_charge = toll;
        }

        if (guide_charge == null || guide_charge.length() == 0) {
            guide_charge = "0";
        } else {
            guide_charge = guide_charge;
        }

        if (driver_ta == null || driver_ta.length() == 0) {
            driver_ta = "0";
        } else {
            driver_ta = driver_ta;
        }
        double price = Double.parseDouble(beverages_charges) + Double.parseDouble(entrance_charge) + Double.parseDouble(guide_charge)
                + Double.parseDouble(driver_ta);
        if (price > 0)
            txtMisc2Value.setText(price + "");
        else txtMisc2Value.setText("None");
    }

    private void calculateMischrage1() {
        if (night_halt == null || night_halt.length() == 0) {
            night_halt = "0";
        } else {
            night_halt = night_halt;
        }

        if (toll == null || toll.length() == 0) {
            toll = "0";
        } else {
            toll = toll;
        }

        if (parking == null || parking.length() == 0) {
            parking = "0";
        } else {
            parking = parking;
        }

        if (e_toll == null || e_toll.length() == 0) {
            e_toll = "0";
        } else {
            e_toll = e_toll;
        }

        if (interstate_tax == null || interstate_tax.length() == 0) {
            interstate_tax = "0";
        } else {
            interstate_tax = interstate_tax;
        }
        if (others == null || others.length() == 0) {
            others = "0";
        } else {
            others = others;
        }

        double price = Double.parseDouble(night_halt) + Double.parseDouble(toll) + Double.parseDouble(parking)
                + Double.parseDouble(e_toll) + Double.parseDouble(interstate_tax) + Double.parseDouble(others);
        if (price > 0)
            txtMisc1Value.setText(price + "");
        else txtMisc1Value.setText("None");
    }

    private void setTotal2() {

        if (charge2Dialog != null) {
            e_toll = edtEToll.getText().toString().trim();
            parking = edtParking.getText().toString().trim();
            toll = edttoll.getText().toString().trim();
            night_halt = edtNight.getText().toString().trim();
            interstate_tax = edtinterstate.getText().toString().trim();
            others = edtOther.getText().toString().trim();

            if (e_toll == null || e_toll.length() == 0) {
                e_toll = "0";
            }
            if (parking == null || parking.length() == 0) {
                parking = "0";
            }
            if (toll == null || toll.length() == 0) {
                toll = "0";
            }
            if (night_halt == null || night_halt.length() == 0) {
                night_halt = "0";
            }
            if (interstate_tax == null || interstate_tax.length() == 0) {
                interstate_tax = "0";
            }
            if (others == null || others.length() == 0) {
                others = "0";
            }

            double d1 = Double.parseDouble(night_halt) +
                    Double.parseDouble(e_toll) +
                    Double.parseDouble(parking) +
                    Double.parseDouble(toll) +
                    Double.parseDouble(interstate_tax) +
                    Double.parseDouble(others);
            if (d1 > 0)
                txtMisc1Value.setText(d1 + "");
            else txtMisc1Value.setText("None");
        }
    }

    private void setTotal1() {
        /**
         * beverage_charge : null
         * entrance_charge : null
         * parking : null
         * driver_ta : null
         */

        if (charge1Dialog != null) {
            driver_ta = edtDTA.getText().toString().trim();
            guide_charge = edtGuide.getText().toString().trim();
            entrance_charge = edtEntrance.getText().toString().trim();
            beverages_charges = edtBeverage.getText().toString().trim();

            if (beverages_charges.length() == 0) {
                beverages_charges = "0";
            }
            if (entrance_charge == null || entrance_charge.length() == 0) {
                entrance_charge = "0";
            } else {
                entrance_charge = toll;
            }

            if (guide_charge == null || guide_charge.length() == 0) {
                guide_charge = "0";
            } else {
                guide_charge = guide_charge;
            }

            if (driver_ta == null || driver_ta.length() == 0) {
                driver_ta = "0";
            } else {
                driver_ta = driver_ta;
            }

            double d2 = Double.parseDouble(beverages_charges) +
                    Double.parseDouble(entrance_charge) +
                    Double.parseDouble(guide_charge) +
                    Double.parseDouble(driver_ta);
            if (d2 > 0)
                txtMisc2Value.setText(d2 + "");
            else txtMisc2Value.setText("None");
        }
    }

    @Override
    public void onApiFailure(String message) {
        try {
            new Utility().hideDialog();
            Utility.showToast(getContext(), getContext().getResources().getString(R.string.error));
        } catch (Exception e) {
            Log.d("TAG", "onApiFailure: " + e.getMessage());
        }
    }

    private void saveDutySlip() {
        new Utility().showProgressDialog(getContext());
        if (ending_date == null)
            ending_date = "";
        if (starting_meter == null)
            starting_meter = "";
        if (reporting_meter == null)
            reporting_meter = "";
        if (starting_time == null)
            starting_time = "";
        if (reporting_time == null)
            reporting_time = "";
        if (ending_meter == null)
            ending_meter = "";
        if (ending_time == null)
            ending_time = "";
        if (meter_at_garage == null)
            meter_at_garage = "";
        if (time_at_garage == null)
            time_at_garage = "";
        if (total_meter == null)
            total_meter = "";
        if (total_time == null)
            total_time = "";

        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().saveDutySlip(dutyslipnum, starting_date,
                ending_date,
                starting_meter, reporting_meter, starting_time, reporting_time, ending_meter, ending_time,
                meter_at_garage, time_at_garage, total_meter, total_time);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this, getContext()).getResponse(call);
    }

    private void saveMis1() {
        new Utility().showProgressDialog(getContext());
        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().saveMis1(dutyslipnum, night_halt,
                toll, parking, e_toll, interstate_tax, others);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this, getContext()).getResponse(call);
    }

    private void saveMis2() {
        new Utility().showProgressDialog(getContext());
        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().saveMis2(dutyslipnum, beverages_charges,
                entrance_charge, guide_charge, driver_ta);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this, getContext()).getResponse(call);
    }

    private void showEndMeterDialog() {
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
                endMeterDialog = null;
            }
        });

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endMeterDialog.dismiss();
                //  endMeterDialog = null;
                saveDutySlip();
            }
        });

    }

    private void showGarrageMeterDialog() {
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

    @Override
    public void browse(View view, int pos) {
        this.pos = pos;
        selectImage();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getActivity(), "Camera Permissions not granted", Toast.LENGTH_SHORT).show();
                } else {
                    cameraIntent();
                }
                break;
            case SELECT_FILE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getActivity(), "file Permissions not granted", Toast.LENGTH_SHORT).show();
                } else {
                    galleryIntent();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (appPermissions.hasPermission(Manifest.permission.CAMERA)) {
                        cameraIntent();
                    } else {
                        appPermissions.requestPermission(getActivity(), Manifest.permission.CAMERA, REQUEST_CAMERA);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (appPermissions.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        galleryIntent();
                    } else {
                        appPermissions.requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, SELECT_FILE);
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        base_64 = Utility.BitMapToString(thumbnail);

        /*if (pos == img_base_64.size()) {
            img_base_64.add(base_64);
            arr_img.set(pos, "Photo1");
        } else {
            img_base_64.set(pos, base_64);
        }*/

        if (pos > img_base_64.size())
            img_base_64.set(pos, base_64);
        else {
            img_base_64.add(pos, base_64);
            arr_img.set(pos, "Photo1");
        }
        adapterDutySlipUploadDocument.notifyDataSetChanged();


//        Toast.makeText(getActivity(), "" + img_base_64.size(), Toast.LENGTH_SHORT).show();
//        endClock.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                base_64 = Utility.BitMapToString(bm);

                /*if (pos == img_base_64.size()) {
                    img_base_64.add(base_64);
                    arr_img.set(pos, "Photo1");
                } else {
                    img_base_64.set(pos, base_64);
                }*/

                if (pos > img_base_64.size())
                    img_base_64.set(pos, base_64);
                else {
                    img_base_64.add(pos, base_64);
                    arr_img.set(pos, "Photo1");
                }
                adapterDutySlipUploadDocument.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //   endClock.setImageBitmap(bm);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    public void delete(int pos) {
        remove_id = imagelistBeans.get(pos).getId();

        deleteDoc(remove_id);
        imagelistBeans.remove(pos);
        docAdapter.notifyDataSetChanged();
    }


    private void deleteDoc(String remove_id) {
        new Utility().showProgressDialog(getContext());
        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().deleteDoc(dutyslipnum,
                DashboardFragment.driverId, "remove", remove_id);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this, getContext()).getResponse(call);
    }

    private void saveDoc() {
        new Utility().showProgressDialog(getContext());
        String url = Constant.BASE_URL + "/upload_ds_docuement.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Utility().hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {
                              //  saveDutySlip();
                                List<ViewDetailsData.ImagelistBean> ilb = new ArrayList<>();
                                for (int i = 0; i < slip_name.size(); i++) {
                                    ViewDetailsData.ImagelistBean obj = new ViewDetailsData.ImagelistBean();
                                    obj.setId("0");
                                    obj.setName(slip_name.get(i));
                                    ilb.add(obj);
                                }

                                if (slip_name != null) {
                                    slip_name.clear();
                                    arr_document_name.clear();
                                    arr_img.clear();
                                    img_base_64.clear();
                                }
                                setDocAdapter();
                                fetchDetails(reservationId);
                            } else {
                                Utility.showToast(getContext(), jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("TAG", "onResponse: " + response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Utility().hideDialog();
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("dutyslipnum", dutyslipnum);
                hashMap.put("driverId", DashboardFragment.driverId);
                hashMap.put("action", "ADD");

                for (int i = 0; i < slip_name.size(); i++) {
                    hashMap.put("document_name[" + i + "]", slip_name.get(i));
                }
                for (int i = 0; i < img_base_64.size(); i++) {
                    hashMap.put("document_file[" + i + "]", img_base_64.get(i));
                }

                Log.d("TAG", "getParams: " + hashMap);
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}