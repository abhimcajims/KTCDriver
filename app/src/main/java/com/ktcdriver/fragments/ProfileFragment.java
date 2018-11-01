package com.ktcdriver.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ktcdriver.R;
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

        txtName.setText(loginResponse.getProfileInfo().getUserName());
        Glide.with(getContext()).load(loginResponse.getProfileInfo().getPhotoURL()).into(imgProfile);
    }
}
