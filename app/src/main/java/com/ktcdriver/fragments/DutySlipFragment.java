package com.ktcdriver.fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.adapter.DutyListAdapter;
import com.ktcdriver.utils.Utility;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DutySlipFragment extends Fragment implements DutyListAdapter.DutyListInterface, View.OnClickListener{
    private RecyclerView recyclerView;
    private ArrayList<String> title2List;
    private ArrayList<String>title1List;
    private TextView txtSave;
    LinearLayout txtCharge1, txtCharge2;

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

        recyclerView = getView().findViewById(R.id.fragment_duty_slip);
        txtSave = getView().findViewById(R.id.fragment_duty_slip_txtsave);
        txtCharge1 = getView().findViewById(R.id.fragment_duty_slip_txt_charge1);
        txtCharge2 = getView().findViewById(R.id.fragment_duty_slip_txt_charge2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter();

        txtSave.setOnClickListener(this);
        txtCharge2.setOnClickListener(this);
        txtCharge1.setOnClickListener(this);
    }

    private void setAdapter(){
        DutyListAdapter dutyListAdapter = new DutyListAdapter(getContext(),this,title2List,title1List);
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
    public void startJob(int i) {

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


}
