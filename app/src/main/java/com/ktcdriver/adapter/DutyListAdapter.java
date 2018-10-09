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

import java.util.ArrayList;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class DutyListAdapter extends RecyclerView.Adapter<DutyListAdapter.MyViewHolder> {
    private Context context;
    private DutyListInterface dashboardInterface;
    private ArrayList<String>title2List;
    private ArrayList<String>title1List;

    public DutyListAdapter(Context context, DutyListInterface dashboardInterface, ArrayList<String> title2List,
                           ArrayList<String> title1List) {
        this.context = context;
        this.dashboardInterface = dashboardInterface;
        this.title2List = title2List;
        this.title1List = title1List;
    }

    public interface  DutyListInterface{
        void startJob(int i);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_duty_slip,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txtTitle.setText(title1List.get(i));
        myViewHolder.txtTitle2.setText(title2List.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return title1List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtTitle2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_duty_title1);
            txtTitle2 = itemView.findViewById(R.id.item_duty_title2);
        }
    }
}
