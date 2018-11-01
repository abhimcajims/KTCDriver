package com.ktcdriver.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.List;

import retrofit2.Call;


public class OrderHistoryFragment extends Fragment implements DashboardAdapter.DashboardInterface,OnResponseInterface{
    private RecyclerView recyclerView;
    private TinyDB tinyDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    private List<LoginResponse.JobListBean> jobListBeans;
    private void init(){
        tinyDB = new TinyDB(getContext());
        recyclerView = getView().findViewById(R.id.fragment_order_history_recycler);
        String login = tinyDB.getString("login_data");

        LoginResponse loginResponse = new Gson().fromJson(login,LoginResponse.class);
        if (loginResponse.getStatus().equals("1")){
            tinyDB.putString("login_data",new Gson().toJson(loginResponse));
            jobListBeans = loginResponse.getJob_list();
            if (jobListBeans.size()>0){
              //  no_recordLayout.setVisibility(View.GONE);
                setDashboardAdapter();
            } else {
                //no_recordLayout.setVisibility(View.VISIBLE);
            }
        }
        else if (loginResponse.getStatus().equals("0")){
            Utility.showToast(getActivity(),loginResponse.getMessage());
        }

    }

    private DashboardAdapter dashboardAdapter;
    private void setDashboardAdapter(){
        dashboardAdapter= new DashboardAdapter(getContext(),this,jobListBeans);
        recyclerView.setAdapter(dashboardAdapter);
    }

    @Override
    public void startJob(int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Order History");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void fetchHistoryData(final String driverId, String pass) {
        new Utility().showProgressDialog(getContext());
        Call<LoginResponse> call = APIClient.getInstance().getApiInterface().getHistory(driverId,pass);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());

        new ResponseListner(this,getContext()).getResponse( call);
    }

    @Override
    public void onApiResponse(Object response) {

    }

    @Override
    public void onApiFailure(String message) {

    }
}
