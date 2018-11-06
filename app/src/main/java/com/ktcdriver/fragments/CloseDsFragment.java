package com.ktcdriver.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.model.SaveResponse;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;

import retrofit2.Call;

import static com.ktcdriver.fragments.DutySlipFragment.dutyslipnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloseDsFragment extends Fragment implements OnClickListener, OnResponseInterface{
    private TextView txtCloseDS, txtDs;

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
        txtDs = getView().findViewById(R.id.fragment_close_ds_txtNo);
        txtDs.setText(DutySlipFragment.dutyslipnum);
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
                closeDS();
                break;
        }
    }

    private void closeDS(){
        new Utility().showProgressDialog(getContext());
        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().closeDs(dutyslipnum);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this, getContext()).getResponse(call);
    }

    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        if (response!=null){
            if (response instanceof SaveResponse){
                SaveResponse saveResponse = (SaveResponse) response;
                if (saveResponse.getStatus().equals("1")){
                    Utility.showToast(getContext(),saveResponse.getMessage());
                    new Utility().callFragment(new ThankFragment(),getFragmentManager(),R.id.fragment_container,ThankFragment.class.getName());
                } else Utility.showToast(getContext(),saveResponse.getMessage());
            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        try {
            Utility.showToast(getContext(), getContext().getResources().getString(R.string.error));
        } catch (Exception e) {
            Log.d("TAG", "onApiFailure: " + e.getMessage());
        }
        new Utility().hideDialog();
    }
}
