package com.ktcdriver.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.adapter.DashboardAdapter;
import com.ktcdriver.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardAdapter.DashboardInterface{
    private RecyclerView recyclerView;
    private DashboardAdapter dashboardAdapter;

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
        recyclerView = getView().findViewById(R.id.fragment_dashboard_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setDashboardAdapter();
    }

    private void setDashboardAdapter(){
        dashboardAdapter= new DashboardAdapter(getActivity(),this);
        recyclerView.setAdapter(dashboardAdapter);
    }

    @Override
    public void startJob(int i) {
        new Utility().callFragment(new DutySlipFragment(),getFragmentManager(),
                R.id.fragment_container,DutySlipFragment.class.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Dashboard");
    }
}
