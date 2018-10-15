package com.ktcdriver.activities.setup;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.utils.AppbaseActivity;
import com.mukesh.tinydb.TinyDB;

public class SplashActivity extends AppbaseActivity {
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        tinyDB = new TinyDB(getApplicationContext());
        int TIME_INTERVAL = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tinyDB.contains("login_data")){
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }

            }
        }, TIME_INTERVAL);
    }
}
