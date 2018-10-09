package com.ktcdriver.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter();

        txtSave.setOnClickListener(this);
    }

    private void setAdapter(){
        DutyListAdapter dutyListAdapter = new DutyListAdapter(getContext(),this,title2List,title1List);
        recyclerView.setAdapter(dutyListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Duty Slip");
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
        }
    }
}
