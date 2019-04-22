package com.ktcdrivers.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ktcdrivers.R;

/**
 * Created by Rakhi on 10/8/2018.
 */
public class AppbaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        if (!Utility.isNetworkConnected(getApplicationContext())) {
            Utility.no_internet(AppbaseActivity.this,getApplicationContext());
        }*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.slide_out_bottom);
    }

}
