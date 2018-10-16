package com.ktcdriver.activities.setup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.model.LoginResponse;
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
    private String pass,driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        init();
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

    private ProgressDialog progressDialog;
    private void fetchLoginData(final String driverId, String pass) {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Call<LoginResponse> call = APIClient.getInstance().getApiInterface().getLoginDetails(driverId,pass);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());
        new ResponseListner(this,getApplicationContext()).getResponse( call);
    }

    @Override
    public void onApiResponse(Object response) {
        progressDialog.dismiss();
        if (response!=null){
            LoginResponse loginResponse = (LoginResponse) response;
            if (loginResponse.getStatus().equals("1")){
                tinyDB.putString("password",pass);
                tinyDB.putString("driver_id",driverId);
                tinyDB.putString("login_data",new Gson().toJson(loginResponse));
                Utility.showToast(getApplicationContext(),"Login successfully");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
            else if (loginResponse.getStatus().equals("0")){
                Utility.showToast(getApplicationContext(),loginResponse.getMessage());
            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        progressDialog.dismiss();
    }
}
