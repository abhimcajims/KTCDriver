package com.ktcdrivers.adapter;

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

import com.ktcdrivers.R;
import com.ktcdrivers.model.NotificationData;

import java.util.List;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context context;
    private List<NotificationData.NotificationDataBean>notificationDataBeans;
    private NotificationInterface notificationInterface;

    public NotificationAdapter(Context context, List<NotificationData.NotificationDataBean> notificationDataBeans,
                               NotificationInterface notificationInterface) {
        this.context = context;
        this.notificationDataBeans = notificationDataBeans;
        this.notificationInterface = notificationInterface;
    }

    public interface NotificationInterface{
        void onClick(int pos);
        void onLast(int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txtMsg.setText(notificationDataBeans.get(i).getMessage_data());

        if (notificationDataBeans.get(i).getStatus().equals("0")){
            myViewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGrey));
        } else {
            myViewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        if (i==notificationDataBeans.size()-1){
            notificationInterface.onLast(i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notificationDataBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMsg;
        LinearLayout layout;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
             txtMsg = itemView.findViewById(R.id.item_noti_msg);
             layout = itemView.findViewById(R.id.item_notify_bg);
             layout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     notificationInterface.onClick(getAdapterPosition());
                 }
             });
        }
    }

}
