package com.ktcdriver.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.model.SaveResponse;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment implements View.OnClickListener,OnResponseInterface{
    private TextView txtSubmit;
    private String dutyslipno,duty_remarks,signature="uytiutyiu";
    private RatingBar ratingBar;
    private EditText edtComment;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
       dutyslipno = DutySlipFragment.dutyslipnum;

        txtSubmit = getView().findViewById(R.id.fragment_feedback_submit);
        ratingBar = getView().findViewById(R.id.fragment_feedback_rating);
        edtComment = getView().findViewById(R.id.fragment_feedback_edtMsg);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(),Float.toString(rating), Toast.LENGTH_LONG).show();
            }
        });

        txtSubmit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Feedback");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_feedback_submit:
                duty_remarks = edtComment.getText().toString().trim();
                if (duty_remarks==null){
                    duty_remarks = "";
                }
                sendFeedback();
                break;
        }
    }

    private void sendFeedback(){
        new Utility().showProgressDialog(getContext());

        Call<SaveResponse> call = APIClient.getInstance().getApiInterface().sendFeedback(dutyslipno,
                signature,ratingBar.getRating()+"",duty_remarks);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getContext()).getResponse( call);
    }

    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();

        if (response!=null){
            if (response instanceof SaveResponse){
                SaveResponse saveResponse = (SaveResponse) response;
                if (saveResponse.getStatus().equals("1")){
                    Utility.showToast(getContext(), "Thank you for your valuable Feedback.");
                    new Utility().callFragment(new PaymentFragment(),getFragmentManager(),R.id.fragment_container,
                            PaymentFragment.class.getName());
                }
            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        Utility.showToast(getContext(), getResources().getString(R.string.error));
    }

}
