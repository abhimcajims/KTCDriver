package com.ktcdrivers.activities.setup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ktcdrivers.R;
import com.ktcdrivers.activities.home.HomeActivity;
import com.ktcdrivers.service.Config;
import com.ktcdrivers.utils.AppbaseActivity;
import com.mukesh.tinydb.TinyDB;

public class SplashActivity extends AppbaseActivity {
    private TinyDB tinyDB;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e){
            Log.d(TAG, "onCreate: "+e.getMessage());
        }
        tinyDB = new TinyDB(getApplicationContext());
//        tinyDB.putString("FromNoti","0");
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


       /* int TIME_INTERVAL = 3000;
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
        }, TIME_INTERVAL);*/
    }

    public static String token;
    private static String TAG = LoginActivity.class.getName();

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
        if (!TextUtils.isEmpty(regId)){
         //   Toast.makeText(this, regId + "ABC", Toast.LENGTH_SHORT).show();
            token = regId;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int TIME_INTERVAL = 4000;
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
