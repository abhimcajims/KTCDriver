package com.ktcdriver.activities.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ktcdriver.R;
import com.ktcdriver.adapter.NotificationAdapter;
import com.ktcdriver.fragments.DashboardFragment;
import com.ktcdriver.fragments.FeedbackFragment;
import com.ktcdriver.fragments.NotificationFragment;
import com.ktcdriver.fragments.OrderHistoryFragment;
import com.ktcdriver.fragments.ProfileFragment;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.model.NotificationData;
import com.ktcdriver.utils.Utility;
import com.ktcdriver.webservices.APIClient;
import com.ktcdriver.webservices.OnResponseInterface;
import com.ktcdriver.webservices.ResponseListner;
import com.mukesh.tinydb.TinyDB;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnResponseInterface,
        NotificationAdapter.NotificationInterface {
    private float lastTranslate = 0.0f;
    public static Toolbar toolbar;
    public static DrawerLayout drawer;
    private TinyDB tinyDB;
    private LoginResponse loginResponse;
    private int min=0, max=5;
    private String limit = min + "," + max, driverID;
    private List<NotificationData.NotificationDataBean>notificationDataBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tinyDB = new TinyDB(getApplicationContext());
        notificationDataBeans = new ArrayList<>();
        String login = tinyDB.getString("login_data");
        if (tinyDB.contains("notifi"))
            tinyDB.remove("notifi");
        loginResponse = new Gson().fromJson(login,LoginResponse.class);
        driverID = loginResponse.getProfileInfo().getDriverId();
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
        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView name = (TextView)header.findViewById(R.id.nav_header_name);
        TextView email = (TextView)header.findViewById(R.id.nav_header_email);
        if (loginResponse.getProfileInfo().getUserName()!=null)
        name.setText(loginResponse.getProfileInfo().getUserName());
        if (loginResponse.getProfileInfo().getEmail()!=null)
        email.setText(loginResponse.getProfileInfo().getEmail());
        ImageView imgUser = header.findViewById(R.id.nav_header_img);
        if (loginResponse.getProfileInfo().getPhotoURL()!=null)
        Glide.with(getApplicationContext())
                .load(loginResponse.getProfileInfo().getPhotoURL())
                .apply(RequestOptions.circleCropTransform())
                .into(imgUser);
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
        getNotification();

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
        final MenuItem menuItem = menu.findItem(R.id.action_settings);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

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
        RecyclerView recyclerView = view.findViewById(R.id.popup_notification_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        NotificationData notificationData = new Gson().fromJson(tinyDB.getString("notifi"),NotificationData.class);
        List<NotificationData.NotificationDataBean> notificationDataBeanList =
                new ArrayList<>(notificationData.getNotification_data());
        mNotificationCount = Integer.parseInt(notificationData.getCount_notification());

        setNotiAdapter(recyclerView,notificationDataBeanList);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(width - 150);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setContentView(view);

        return popupWindow;
    }

    private NotificationAdapter notificationAdapter;

    private void setNotiAdapter(RecyclerView recyclerView, List<NotificationData.NotificationDataBean> notificationDataBeanList){
        notificationAdapter = new NotificationAdapter(getApplicationContext(),notificationDataBeanList,this);
        recyclerView.setAdapter(notificationAdapter);
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
        } else if (id == R.id.nav_profile){
            new Utility().callFragment(new ProfileFragment(),getSupportFragmentManager(),
                    R.id.fragment_container,ProfileFragment.class.getName());
        } else if (id == R.id.nav_order_history){
            new Utility().callFragment(new OrderHistoryFragment(),getSupportFragmentManager(),
                    R.id.fragment_container,OrderHistoryFragment.class.getName());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    TextView textCartItemCount;
    int mNotificationCount = 0;

    private void setupBadge(int mNotificationCount) {

        if (textCartItemCount != null) {
            if (mNotificationCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(this.mNotificationCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
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

    private void getNotification(){
        if (min==0&&max==5){
            notificationDataBeans.clear();
        }
        new Utility().showProgressDialog(HomeActivity.this);
        Call<NotificationData> call = APIClient.getInstance().getApiInterface().getNotification(driverID,limit);
        call.request().url();
        Log.d("TAG", "rakhi: "+call.request().url());

        new ResponseListner(this,getApplicationContext()).getResponse( call);
    }


    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        if (response!=null){
            try{
                if (response instanceof NotificationData){
                    if (notificationDataBeans!=null){
                        notificationDataBeans.clear();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
                    NotificationData notificationData = (NotificationData) response;
                    if (notificationData.getStatus().equals("1")){
                        notificationDataBeans.addAll(notificationData.getNotification_data());
                        tinyDB.putString("notifi",new Gson().toJson(notificationData));
                        if (notificationData.getCount_notification()!=null )
                        mNotificationCount = Integer.parseInt(notificationData.getCount_notification());
                        setupBadge(mNotificationCount);
                    } else {
                        Utility.showToast(getApplicationContext(),notificationData.getMessage());
                    }
                }
            } catch (Exception e){
                Log.d("TAG", "error: "+e.getMessage());
            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        new Utility().hideDialog();
        Utility.showToast(getApplicationContext(),getResources().getString(R.string.error));
    }

    @Override
    public void onClick(int pos) {
        Toast.makeText(this, ""+pos, Toast.LENGTH_SHORT).show();
        if (notificationDataBeans.get(pos).getType().equals("DUTY")){
            if (popupwindow_obj!=null)
            popupwindow_obj.dismiss();
        } else {
            if (popupwindow_obj!=null)
                popupwindow_obj.dismiss();
            new Utility().callFragment(new NotificationFragment(),getSupportFragmentManager(),
                    R.id.fragment_container,NotificationFragment.class.getName());
        }

    }

    @Override
    public void onLast(int pos) {

    }
}
