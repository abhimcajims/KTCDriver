package com.ktcdriver.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ktcdriver.R;
import com.ktcdriver.model.LoginResponse;
import com.ktcdriver.model.OrderHistroyData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    private Context context;
    private DashboardInterface dashboardInterface;
    private List<OrderHistroyData.JobListBean> jobListBeans;

    public OrderHistoryAdapter(Context context, DashboardInterface dashboardInterface, List<OrderHistroyData.JobListBean> jobListBeans) {
        this.context = context;
        this.dashboardInterface = dashboardInterface;
        this.jobListBeans = jobListBeans;
    }

    public interface DashboardInterface{
        void startJob(int i);
        void onScrollPage(int i);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dashboard,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
       /* if (i>0){
            myViewHolder.mainLayout.setAlpha((float) 0.5);
            myViewHolder.txtStartJob.setBackground(context.getResources().getDrawable(R.drawable.button_grey_bg));
        } else if (i==0){
            if (jobListBeans.get(i).getColor()!=null&&jobListBeans.get(i).getColor().length()>0){
                myViewHolder.mainLayout.setBackgroundColor(Color.parseColor(jobListBeans.get(i).getColor()));
            }
        }*/

        if (jobListBeans.get(i).getColor()!=null&&jobListBeans.get(i).getColor().length()>0){
            myViewHolder.mainLayout.setBackgroundColor(Color.parseColor(jobListBeans.get(i).getColor()));
        }
        myViewHolder.txtEndDate.setText(jobListBeans.get(i).getReportingtoDate());
        myViewHolder.txtStartDate.setText(jobListBeans.get(i).getReportingDate());
        myViewHolder.txtCompanyName.setText(jobListBeans.get(i).getClientName());
        myViewHolder.txtDutySlip.setText("#"+jobListBeans.get(i).getDutyslipnum());
        myViewHolder.txtTime.setText(jobListBeans.get(i).getReportingTime());
        myViewHolder.txtReprotingPlace.setText(jobListBeans.get(i).getReportingPlace());
        myViewHolder.txtPayment.setText(jobListBeans.get(i).getPaymentmode());
        myViewHolder.txtCarNo.setText(jobListBeans.get(i).getCarno());

        if (i==jobListBeans.size()-1){
            dashboardInterface.onScrollPage(i);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return jobListBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView txtStartJob, txtDutySlip,txtStartDate,txtEndDate,txtCompanyName,txtTime,txtPayment,txtReprotingPlace, txtCarNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.item_dash_board_layout);
            txtStartJob = itemView.findViewById(R.id.item_dashboard_txtStartJob);
            txtStartJob.setVisibility(View.GONE);

            txtDutySlip = itemView.findViewById(R.id.item_dashboard_txtSlipValue);
            txtStartDate = itemView.findViewById(R.id.item_dashboard_txtStartValue);
            txtCompanyName = itemView.findViewById(R.id.item_dashboard_txtComNameValue);
            txtEndDate = itemView.findViewById(R.id.item_dashboard_txtEndValue);
            txtTime = itemView.findViewById(R.id.item_dashboard_txtTime);
            txtPayment = itemView.findViewById(R.id.item_dashboard_txtPayment);
            txtReprotingPlace = itemView.findViewById(R.id.item_dashboard_txtReportingPlace);
            txtCarNo = itemView.findViewById(R.id.item_dashboard_txtCarNo);

            txtStartJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition()==0){
                       /* if (hours>=3){
                            dashboardInterface.startJob(getAdapterPosition());
                        }*//* else if (hours<0){
                            Utility.showToast(context,"Your journey has been expired");
                        }*//* else {
                            Utility.showToast(context,"You can't access before 3 hours");
                        }*/
                        dashboardInterface.startJob(getAdapterPosition());
                    }
                }
            });
        }
    }

}
