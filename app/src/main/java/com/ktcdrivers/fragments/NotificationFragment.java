package com.ktcdrivers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.ktcdrivers.R;
import com.ktcdrivers.activities.home.HomeActivity;
import com.ktcdrivers.adapter.NotificationAdapter;
import com.ktcdrivers.model.LoginResponse;
import com.ktcdrivers.model.NotificationData;
import com.ktcdrivers.utils.Utility;
import com.ktcdrivers.webservices.APIClient;
import com.ktcdrivers.webservices.OnResponseInterface;
import com.ktcdrivers.webservices.ResponseListner;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class NotificationFragment extends Fragment implements NotificationAdapter.NotificationInterface,
        OnResponseInterface {
    private RecyclerView recyclerView;
    private TinyDB tinyDB;
    private LoginResponse loginResponse;
    private int min = 0, max = 20;
    private String limit = min + "," + max, driverID;
    private List<NotificationData.NotificationDataBean> notificationDataBeans;
    private UpdateNotifi updateNotifi;

    public interface UpdateNotifi {
        void updateCount(int count);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    WebView webView;

    private void init() {
        tinyDB = new TinyDB(getContext());
        notificationDataBeans = new ArrayList<>();
        String login = tinyDB.getString("login_data");
        loginResponse = new Gson().fromJson(login, LoginResponse.class);
        driverID = loginResponse.getProfileInfo().getDriverId();
        recyclerView = getView().findViewById(R.id.fragment_notification_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getNotification(driverID, limit);

    }

    private void setNotiAdapter(RecyclerView recyclerView, List<NotificationData.NotificationDataBean> notificationDataBeanList) {
        NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), notificationDataBeanList,
                this);
        recyclerView.setAdapter(notificationAdapter);
    }

    private void getNotification(String driverID, String limit) {
        if (min == 0 && max == 20) {
            notificationDataBeans.clear();
        }
        new Utility().showProgressDialog(getContext());
        Call<NotificationData> call = APIClient.getInstance().getApiInterface().getNotification(driverID, limit);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());

        new ResponseListner(this, getContext()).getResponse(call);

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("Notification");
        HomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        HomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    boolean reachmax;

    @Override
    public void onApiResponse(Object response) {
        new Utility().hideDialog();
        Log.e("MyResponse", new Gson().toJson(response));
        if (response != null) {
            try {
                if (response instanceof NotificationData) {
                    NotificationData notificationData = (NotificationData) response;
                    if (notificationData.getStatus().equals("1")) {
                        if (notificationData.getNotification_data() != null && notificationData.getNotification_data().size() > 0) {
                            notificationDataBeans.addAll(notificationData.getNotification_data());
                        } else {
                            reachmax = true;
                        }
                        updateNotifi.updateCount(Integer.parseInt(notificationData.getCount_notification()));
                        tinyDB.putString("notification_list_", new Gson().toJson(notificationDataBeans));
                        tinyDB.putString("notification_count", notificationData.getCount_notification());
                        setNotiAdapter(recyclerView, notificationDataBeans);
                    } else {
                        Utility.showToast(getContext(), notificationData.getMessage());
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
        Utility.showToast(getContext(), getResources().getString(R.string.error));
    }

    @Override
    public void onClick(int pos) {
        if (notificationDataBeans.get(pos).getStatus().equals("0")) {
            notificationDataBeans.get(pos).setStatus("1");
            readNotification(driverID, limit, notificationDataBeans.get(pos).getNotification_id());
        } else if (notificationDataBeans.get(pos).getType().equals("DUTY")) {
            getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onLast(int pos) {
        min = min + 20;
        max = max + 20;
        limit = min + "," + max;
        if (!reachmax)
            getNotification(driverID, limit);
        else {
//            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void readNotification(String driverID, String limit, String notification_id) {
        if (min == 0 && max == 20) {
            notificationDataBeans.clear();
        }
        new Utility().showProgressDialog(getContext());
        Call<NotificationData> call = APIClient.getInstance().getApiInterface().readNotification(driverID,
                limit, notification_id);
        call.request().url();
        Log.d("TAG", "rakhi: " + call.request().url());

        new ResponseListner(this, getContext()).getResponse(call);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateNotifi = (UpdateNotifi) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}