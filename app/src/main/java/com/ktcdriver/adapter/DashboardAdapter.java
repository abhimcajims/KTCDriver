package com.ktcdriver.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ktcdriver.R;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    private Context context;
    private DashboardInterface dashboardInterface;

    public DashboardAdapter(Context context, DashboardInterface dashboardInterface) {
        this.context = context;
        this.dashboardInterface = dashboardInterface;
    }

    public interface DashboardInterface{
        void startJob(int i);
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
        if (i>0){
            myViewHolder.mainLayout.setAlpha((float) 0.5);
            myViewHolder.txtStartJob.setBackground(context.getResources().getDrawable(R.drawable.button_grey_bg));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView txtStartJob;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.item_dash_board_layout);
            txtStartJob = itemView.findViewById(R.id.item_dashboard_txtStartJob);
            txtStartJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboardInterface.startJob(getAdapterPosition());
                }
            });

        }
    }
}
