package com.ktcdriver.activities.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.fragments.DashboardFragment;
import com.ktcdriver.fragments.FeedbackFragment;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.utils.Utility;
import com.mukesh.tinydb.TinyDB;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private float lastTranslate = 0.0f;
    public static Toolbar toolbar;
    public static DrawerLayout drawer;
    private TinyDB tinyDB;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tinyDB = new TinyDB(getApplicationContext());
        String login = tinyDB.getString("login_data");
        loginResponse = new Gson().fromJson(login,LoginResponse.class);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon((R.drawable.ic_menu)); //set your own
        getSupportActionBar().setTitle("Dashboard");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container_frame);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (navigationView.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    frameLayout.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frameLayout.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }
        };

        drawer.setDrawerListener(toggle);

//        call dashboard fragment

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private PopupWindow popupwindow_obj;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            View view = findViewById(R.id.action_settings);
            popupwindow_obj = showMyPopup();
            popupwindow_obj.showAsDropDown(view, 10, 20); // where u want show on view click event popupwindow.showAsDropDown(view, x, y);

            return true;
        }

        return true;
    }

    public PopupWindow showMyPopup() {
        final PopupWindow popupWindow = new PopupWindow(getApplicationContext());

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.popup_notification, null);

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        popupWindow.setFocusable(true);
        popupWindow.setWidth(width - 150);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setContentView(view);
        return popupWindow;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_job_list) {

//        call dashboard fragment
            new Utility().callFragment(new DashboardFragment(),getSupportFragmentManager(),
                    R.id.fragment_container,DashboardFragment.class.getName());
            // Handle the camera action
        } else if (id == R.id.nav_feedback) {
            new Utility().callFragment(new FeedbackFragment(),getSupportFragmentManager(),
                    R.id.fragment_container,FeedbackFragment.class.getName());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(popupwindow_obj != null) {
            popupwindow_obj.dismiss();
            popupwindow_obj = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(popupwindow_obj != null) {
            popupwindow_obj.dismiss();
            popupwindow_obj = null;
        }

    }

}
