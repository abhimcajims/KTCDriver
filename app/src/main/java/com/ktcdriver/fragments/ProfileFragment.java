package com.ktcdriver.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.model.LoginResponse;
import com.mukesh.tinydb.TinyDB;


public class ProfileFragment extends Fragment {
    private LoginResponse loginResponse;
    private TinyDB tinyDB;
    private ImageView imgProfile;
    private TextView txtName, txtCardNo, txtIssueDate,txtDeactivate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tinyDB = new TinyDB(getContext());
        loginResponse = new Gson().fromJson(tinyDB.getString("login_data"),LoginResponse.class);
        init();
    }

    private void init(){
        txtName = getView().findViewById(R.id.fragment_profile_driver_detail);
        imgProfile = getView().findViewById(R.id.fragment_profile_img);
        txtCardNo = getView().findViewById(R.id.fragment_profile_card_no);
        txtIssueDate = getView().findViewById(R.id.fragment_profile_issue_date);
        txtDeactivate = getView().findViewById(R.id.fragment_profile_deactivate);
        if (loginResponse.getProfileInfo().getUserName()!=null)
        txtName.setText(loginResponse.getProfileInfo().getUserName());
        if (loginResponse.getProfileInfo().getHappay_card()!=null)
        txtCardNo.setText(loginResponse.getProfileInfo().getHappay_card());
        if (loginResponse.getProfileInfo().getDeactivate_date()!=null)
        txtDeactivate.setText(loginResponse.getProfileInfo().getDeactivate_date());
        if (loginResponse.getProfileInfo().getIssue_date()!=null)
        txtIssueDate.setText(loginResponse.getProfileInfo().getIssue_date());
        if (loginResponse.getProfileInfo().getPhotoURL()!=null)
        Glide.with(getContext()).load(loginResponse.getProfileInfo().getPhotoURL()).into(imgProfile);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Profile");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });
    }
}
