package com.ktcdriver.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.activities.setup.LoginActivity;
import com.ktcdriver.adapter.DashboardAdapter;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardAdapter.DashboardInterface,OnResponseInterface{
    private RecyclerView recyclerView;
    private DashboardAdapter dashboardAdapter;
    private TinyDB tinyDB;
    private String driverId, pass;
    private LinearLayout  no_recordLayout;
    private List<LoginResponse.JobListBean>jobListBeans;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
        tinyDB = new TinyDB(getContext());
        recyclerView = getView().findViewById(R.id.fragment_dashboard_recycler);
        no_recordLayout = getView().findViewById(R.id.fragment_dashboard_no_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        driverId = tinyDB.getString("driver_id");
        pass = tinyDB.getString("password");

        fetchLoginData(driverId,pass);
    }

    private void setDashboardAdapter(){
        dashboardAdapter= new DashboardAdapter(getContext(),this,jobListBeans);
        recyclerView.setAdapter(dashboardAdapter);
    }

    private void fetchLoginData(final String driverId, String pass) {
        new Utility().showProgressDialog(getContext());
        Call<LoginResponse> call = APIClient.getInstance().getApiInterface().getLoginDetails(driverId,pass);
        call.request().url();
        Log.d("TAG", "fetchLoginData: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    @Override
    public void startJob(int i) {
        DutySlipFragment dutySlipFragment = new DutySlipFragment();
        Bundle bundle = new Bundle();
        bundle.putString("reservationid",jobListBeans.get(i).getReservationid());
        dutySlipFragment.setArguments(bundle);
        new Utility().callFragment(dutySlipFragment,getFragmentManager(),
                R.id.fragment_container,DutySlipFragment.class.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Dashboard");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public void onApiResponse(Object response) {
        jobListBeans = new ArrayList<>();
        new Utility().hideDialog();
        if (response!=null){
            LoginResponse loginResponse = (LoginResponse) response;
            if (loginResponse.getStatus().equals("1")){
                jobListBeans = loginResponse.getJob_list();
                if (jobListBeans.size()>0){
                    no_recordLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    setDashboardAdapter();
                } else {
                    no_recordLayout.setVisibility(View.VISIBLE);
                }
            }
            else if (loginResponse.getStatus().equals("0")){
                Utility.showToast(getActivity(),loginResponse.getMessage());
            }
        } else {
            Utility.showToast(getContext(),getResources().getString(R.string.error));
        }
    }

    @Override
    public void onApiFailure(String message) {
        new Utility().hideDialog();
        Utility.showToast(getContext(),getContext().getResources().getString(R.string.error));
    }
}
