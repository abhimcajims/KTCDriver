package com.ktcdriver.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloseDsFragment extends Fragment implements OnClickListener{
    private TextView txtCloseDS;

    public CloseDsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_close_ds, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        txtCloseDS = getView().findViewById(R.id.fragment_close_ds);

        txtCloseDS.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Duty Slip");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        HomeActivity.toolbar.setNavigationOnClickListener(new OnClickListener() {
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
            case R.id.fragment_close_ds:
                new Utility().callFragment(new ThankFragment(),getFragmentManager(),R.id.fragment_container,ThankFragment.class.getName());
                break;
        }
    }
}
