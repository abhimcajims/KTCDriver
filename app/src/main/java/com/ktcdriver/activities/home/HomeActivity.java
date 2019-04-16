package com.ktcdriver.activities.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ktcdriver.R;
import com.ktcdriver.adapter.NotificationAdapter;
import com.ktcdriver.customtext.CustomTextView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnResponseInterface,
        NotificationAdapter.NotificationInterface, NotificationFragment.UpdateNotifi {
    public static Toolbar toolbar;
    public static DrawerLayout drawer;
    TextView textCartItemCount;
    int mNotificationCount = 0;
    private float lastTranslate = 0.0f;
    private TinyDB tinyDB;
    private int min = 0, max = 5;
    private String limit = min + "," + max,driverID;
    private List<NotificationData.NotificationDataBean> notificationDataBeans;
    private boolean notification_clicked = false;
    private PopupWindow popupwindow_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tinyDB = new TinyDB(getApplicationContext());
        notificationDataBeans = new ArrayList<>();
        String login = tinyDB.getString("login_data");
        String fromNoti = tinyDB.getString("FromNoti");
       /* if (tinyDB.contains("notifi"))b
            tinyDB.remove("notifi");*/
        LoginResponse loginResponse = new Gson().fromJson(login, LoginResponse.class);
        driverID = loginResponse.getProfileInfo().getDriverId();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon((R.drawable.ic_menu)); //set your own
        getSupportActionBar().setTitle("Dashboard");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        Menu nav_Menu = navigationView.getMenu();
        if (loginResponse.getProfileInfo().getJob_history().equals("0")) {
            nav_Menu.findItem(R.id.nav_order_history).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.nav_order_history).setVisible(true);
        }

        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView name = header.findViewById(R.id.nav_header_name);
        TextView email = header.findViewById(R.id.nav_header_email);
        if (loginResponse.getProfileInfo().getUserName() != null)
            name.setText(loginResponse.getProfileInfo().getUserName());
        if (loginResponse.getProfileInfo().getEmail() != null)
            email.setText(loginResponse.getProfileInfo().getEmail());
        ImageView imgUser = header.findViewById(R.id.nav_header_img);
        if (loginResponse.getProfileInfo().getPhotoURL() != null)
            Glide.with(getApplicationContext())
                    .load(loginResponse.getProfileInfo().getPhotoURL())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgUser);
        final FrameLayout frameLayout = findViewById(R.id.container_frame);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (navigationView.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    frameLayout.setTranslationX(moveFactor);
                } else {
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
        getNotification(driverID, limit);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
        notification_clicked = false;
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
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
        textCartItemCount = actionView.findViewById(R.id.cart_badge);


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

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
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomTextView txtSeeAll = view.findViewById(R.id.popup_notification_see_all);

        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment())
                        .addToBackStack(NotificationFragment.class.getName()).commit();
                popupwindow_obj.dismiss();
            }
        });

        if (tinyDB.contains("notification_list")) {
            String data = tinyDB.getString("notification_list");
            Log.e("MyNotifications", data);

            NotificationData notificationData = new Gson().fromJson(data, NotificationData.class);
            if (notificationData.getNotification_data() != null) {
                List<NotificationData.NotificationDataBean> notificationDataBeanList =
                        new ArrayList<>(notificationData.getNotification_data());
                if (tinyDB.contains("notification_count"))
                    mNotificationCount = Integer.parseInt(tinyDB.getString("notification_count"));
                setNotiAdapter(recyclerView, notificationDataBeanList);
                setupBadge(mNotificationCount);
            }
        }

        popupWindow.setFocusable(true);
        popupWindow.setWidth(width - 150);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setContentView(view);

        return popupWindow;
    }

    private void setNotiAdapter(RecyclerView recyclerView, List<NotificationData.NotificationDataBean> notificationDataBeanList) {
        NotificationAdapter notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationDataBeanList, this);
        recyclerView.setAdapter(notificationAdapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_job_list) {

//        call dashboard fragment
            new Utility().callFragment(new DashboardFragment(), getSupportFragmentManager(),
                    R.id.fragment_container, DashboardFragment.class.getName());
            // Handle the camera action
        } else if (id == R.id.nav_feedback) {
            new Utility().callFragment(new FeedbackFragment(), getSupportFragmentManager(),
                    R.id.fragment_container, FeedbackFragment.class.getName());
        } else if (id == R.id.nav_profile) {
            new Utility().callFragment(new ProfileFragment(), getSupportFragmentManager(),
                    R.id.fragment_container, ProfileFragment.class.getName());
        } else if (id == R.id.nav_order_history) {
            new Utility().callFragment(new OrderHistoryFragment(), getSupportFragmentManager(),
                    R.id.fragment_container, OrderHistoryFragment.class.getName());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void setupBadge(int mNotificationCount) {

        if (textCartItemCount != null) {
            if (mNotificationCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mNotificationCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (popupwindow_obj != null) {
            popupwindow_obj.dismiss();
            popupwindow_obj = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (popupwindow_obj != null) {
            popupwindow_obj.dismiss();
            popupwindow_obj = null;
        }

    }

    private void getNotification(String driverID, String limit) {
        if (min == 0 && max == 5) {
            notificationDataBeans.clear();
        }
        new Utility().showProgressDialog(HomeActivity.this);
        Call<NotificationData> call = APIClient.getInstance().getApiInterface().getNotification(driverID, limit);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());

        new ResponseListner(this, getApplicationContext()).getResponse(call);
    }


    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        if (response != null) {
            try {
                if (response instanceof NotificationData) {
                    if (tinyDB.equals("1")) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment())
                                .addToBackStack(NotificationFragment.class.getName()).commit();
                    } else {
                        if (notificationDataBeans != null) {
                            notificationDataBeans.clear();
                        }
                        NotificationData notificationData = (NotificationData) response;
                        if (notificationData.getStatus().equals("1")) {
                            notificationDataBeans.addAll(notificationData.getNotification_data());
                            tinyDB.putString("notification_list", new Gson().toJson(notificationData));
                            if (notificationData.getCount_notification() != null)
                                mNotificationCount = Integer.parseInt(notificationData.getCount_notification());
                            setupBadge(mNotificationCount);
                        } else {
                            Utility.showToast(getApplicationContext(), notificationData.getMessage());
                        }
                        /*      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
                         */
                        if (notification_clicked) {
                            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                            if (f instanceof NotificationFragment) {

                            } else {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment())
                                        .addToBackStack(NotificationFragment.class.getName()).commit();
                            }
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
                        }
                    }

                }
            } catch (Exception e) {
                Log.d("TAG", "error: " + e.getMessage());
            }
        }
    }

    @Override
    public void onApiFailure(String message) {
        new Utility().hideDialog();
        Utility.showToast(getApplicationContext(), getResources().getString(R.string.error));
    }

    @Override
    public void onClick(int pos) {

        notification_clicked = true;
        if (notificationDataBeans != null && notificationDataBeans.size() > 0) {
            if (notificationDataBeans.get(pos).getType().equals("DUTY")) {
                if (popupwindow_obj != null)
                    popupwindow_obj.dismiss();
                readNotification(driverID, limit, notificationDataBeans.get(pos).getNotification_id());
            } else {
                if (popupwindow_obj != null)
                    popupwindow_obj.dismiss();
                readNotification(driverID, limit, notificationDataBeans.get(pos).getNotification_id());
            }
        }


       /* Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(f instanceof NotificationFragment){

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NotificationFragment())
                    .addToBackStack(NotificationFragment.class.getName()).commit();
        }*/
    }

    @Override
    public void onLast(int pos) {
      /*  min = min+5;
        max = max+5;
        limit = min+","+max;
        getNotification(driverID,limit);*/
    }

    private void readNotification(String driverID, String limit, String notification_id) {
        if (min == 0 && max == 5) {
            notificationDataBeans.clear();
        }
        new Utility().showProgressDialog(HomeActivity.this);
        Call<NotificationData> call = APIClient.getInstance().getApiInterface().readNotification(driverID,
                limit, notification_id);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());

        new ResponseListner(this, getApplicationContext()).getResponse(call);
    }

    @Override
    public void updateCount(int count) {
        setupBadge(count);
    }
}
