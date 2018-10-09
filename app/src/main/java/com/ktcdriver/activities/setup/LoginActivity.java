package com.ktcdriver.activities.setup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.ktcdriver.utils.AppbaseActivity;

public class LoginActivity extends AppbaseActivity implements View.OnClickListener {
    private TextView txtLogin;

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
        txtLogin = findViewById(R.id.activity_login_txtLogin);
        setlistener();
    }

    private void setlistener(){
        txtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }
}
