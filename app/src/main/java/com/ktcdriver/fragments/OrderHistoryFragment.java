package com.ktcdriver.fragments;

import android.content.Context;
import android.net.Uri;
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
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.activities.setup.LoginActivity;
import com.ktcdriver.adapter.DashboardAdapter;
import com.ktcdriver.adapter.OrderHistoryAdapter;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.model.OrderHistroyData;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class OrderHistoryFragment extends Fragment implements OrderHistoryAdapter.DashboardInterface,OnResponseInterface{
    private RecyclerView recyclerView;
    private TinyDB tinyDB;
    private int min=0, max=5;
    private String limit = min + "," + max, driverID;
    private ProgressBar pb_load_more,pb_main;


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

    private List<OrderHistroyData.JobListBean> jobListBeans;

    private void init(){
        tinyDB = new TinyDB(getContext());
        String login = tinyDB.getString("login_data");

        LoginResponse loginResponse = new Gson().fromJson(login,LoginResponse.class);
        driverID = loginResponse.getProfileInfo().getDriverId();
        jobListBeans = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.fragment_order_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
      /*  pb_main = getView().findViewById(R.id.fragment_order_progressbar);
        pb_load_more = getView().findViewById(R.id.fragment_order_load_more);*/
    }

    private OrderHistoryAdapter dashboardAdapter;

    private void setDashboardAdapter(List<OrderHistroyData.JobListBean> jobListBeans){
        if (dashboardAdapter == null){
            dashboardAdapter= new OrderHistoryAdapter(getContext(),this, this.jobListBeans);
            recyclerView.setAdapter(dashboardAdapter);
        } else{
            dashboardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void startJob(int i) {

    }

    @Override
    public void onScrollPage(int i) {
        min = min+5;
        max = max+5;
        limit = min+","+max;
        fetchHistoryData(driverID,limit);
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

        fetchHistoryData(driverID,limit);
    }

    private void fetchHistoryData(final String driverId, String limit) {
        if (min==0&&max==5){
            jobListBeans.clear();
        }
        new Utility().showProgressDialog(getContext());
        Call<OrderHistroyData> call = APIClient.getInstance().getApiInterface().getHistory(driverId,limit);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());

        new ResponseListner(this,getContext()).getResponse( call);
    }

    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        if (response!=null){
            try {
                if (response instanceof OrderHistroyData){
                    OrderHistroyData orderHistroyData = (OrderHistroyData) response;
                    Log.d("TAG", "rakhi: "+new Gson().toJson(orderHistroyData));
                    if (orderHistroyData.getStatus().equals("1")){
                        jobListBeans.addAll(orderHistroyData.getJob_list());
                        setDashboardAdapter(jobListBeans);
                    } else {
                        Utility.showToast(getContext(),orderHistroyData.getMessage());
                    }
                }
            } catch (Exception e){
                Log.d("TAG", "onApiResponse: "+e.getMessage());
            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        new Utility().hideDialog();
        Utility.showToast(getContext(),getString(R.string.error));
    }

}
