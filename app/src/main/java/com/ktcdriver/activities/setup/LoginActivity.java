package com.ktcdriver.activities.setup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.model.NewUserResponse;
import com.ktcdriver.service.Config;
import com.ktcdriver.utils.AppbaseActivity;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;
import com.mukesh.tinydb.TinyDB;

import retrofit2.Call;

public class LoginActivity extends AppbaseActivity implements View.OnClickListener,OnResponseInterface {
    private TextView txtLogin;
    private EditText edtDriverID, edtpass;
    private TinyDB tinyDB;
    private String pass,driverId,token,imei;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

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

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                   // txtMessage.setText(message);
                }
            }
        };
     displayFirebaseRegId();
        checkSelfPermission();

        init();
    }

    private static String TAG = LoginActivity.class.getName();

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

    //    Toast.makeText(this, regId + "", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            token = regId;
        else{
           // Utility.showToast(getApplicationContext(),"Token not generated");
        }
    }

    private final int PERMISSION_READ_PHONE_STATE = 1;

    private void checkSelfPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            imei = getDeviceIMEI();
            Log.d(TAG, "rakhi: "+imei);
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    imei = getDeviceIMEI();
                    Log.d(TAG, "rakhi: "+imei);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void init(){
        tinyDB = new TinyDB(getApplicationContext());
        txtLogin = findViewById(R.id.activity_login_txtLogin);
        edtpass = findViewById(R.id.activity_login_edtPass);
        edtDriverID = findViewById(R.id.activity_login_edtDriverId);

        setlistener();
    }

    private void setlistener(){
        txtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        driverId = edtDriverID.getText().toString().trim();
        pass = edtpass.getText().toString().trim();

        if (driverId.isEmpty()){
            edtDriverID.setError("Please enter driver id");
            edtDriverID.requestFocus();
        } else if (pass.isEmpty()){
            edtpass.setError("Please enter your password ");
            edtpass.requestFocus();
        } else {
            fetchLoginData(driverId, pass);
        }
    }

    private void fetchLoginData(final String driverId, String pass) {
        new Utility().showProgressDialog(LoginActivity.this);
        Call<LoginResponse> call = APIClient.getInstance().getApiInterface().getLoginDetails(driverId,pass);
         call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());

         new ResponseListner(this,getApplicationContext()).getResponse(call);
    }

    private void registerNewUser(String imei, String token,  String driverId){
        new Utility().showProgressDialog(LoginActivity.this);
        Call<NewUserResponse> call = APIClient.getInstance().getApiInterface().registerNewUser(driverId,imei,token);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());
        new ResponseListner(this,getApplicationContext()).getResponse( call);
    }

    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        if (response!=null){
            if (response instanceof LoginResponse){
                LoginResponse loginResponse = (LoginResponse) response;
                if (loginResponse.getStatus().equals("1")){
                    tinyDB.putString("password",pass);
                    tinyDB.putString("driver_id",driverId);
                    tinyDB.putString("login_data",new Gson().toJson(loginResponse));
                    registerNewUser(imei,token,driverId);
                }
                else if (loginResponse.getStatus().equals("0")){
                    Utility.showToast(getApplicationContext(),loginResponse.getMessage());
                }
            } else if (response instanceof NewUserResponse){
                new Utility().hideDialog();
                NewUserResponse newUserResponse = (NewUserResponse) response;
                if (newUserResponse.getResponse().get(0).getResponse().equals("Success")){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Utility.showToast(getApplicationContext(),getResources().getString(R.string.error));
                }
            }
        } else {
            Utility.showToast(getApplicationContext(),getResources().getString(R.string.error));
        }
    }

    @Override
    public void onApiFailure(String message) {
        new Utility().hideDialog();
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        Log.d(TAG, "getDeviceIMEI: "+deviceUniqueIdentifier);
        return deviceUniqueIdentifier;
    }

}
