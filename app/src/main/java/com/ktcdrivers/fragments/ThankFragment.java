package com.ktcdrivers.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ktcdrivers.R;
import com.ktcdrivers.activities.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThankFragment extends Fragment implements View.OnClickListener{
    private TextView txtNextDuty,txtDesc;

    public ThankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thank, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        txtNextDuty = getView().findViewById(R.id.fragment_thank_txt_next_Duty);
        txtDesc = getView().findViewById(R.id.fragment_thank_txt);

        txtDesc.setText("Your Duty No "+DutySlipFragment.dutyslipnum+" has been completed.Please go for");

        txtNextDuty.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager()!=null){
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_thank_txt_next_Duty:

//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
                Log.d("TAG", "onClick: "+getFragmentManager().getBackStackEntryCount());

                getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }
    }


}
