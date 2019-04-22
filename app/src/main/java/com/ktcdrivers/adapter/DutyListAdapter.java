package com.ktcdrivers.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ktcdrivers.R;
import java.util.ArrayList;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class DutyListAdapter extends RecyclerView.Adapter<DutyListAdapter.MyViewHolder> {
    private Context context;
    private DutyListInterface dashboardInterface;
    private ArrayList<String>title2List;
    private ArrayList<String>title1List;
    private ArrayList<String> title2ListValue;
    private ArrayList<String>title1ListValue;
    private boolean isEndMeter, isEndTime;

    public DutyListAdapter(Context context, DutyListInterface dashboardInterface, ArrayList<String> title2List,
                           ArrayList<String> title1List, ArrayList<String> title2ListValue,
                           ArrayList<String> title1ListValue, boolean isEndMeter, boolean isEndTime) {
        this.context = context;
        this.dashboardInterface = dashboardInterface;
        this.title2List = title2List;
        this.title1List = title1List;
        this.title2ListValue = title2ListValue;
        this.title1ListValue = title1ListValue;
        this.isEndMeter = isEndMeter;
        this.isEndTime = isEndTime;
    }

    public interface  DutyListInterface{
        void openTimerPicker(int i, TextView textView, EditText edtValue);
        void onTextChanged(int position, String charSeq);
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
        if (title1ListValue.get(i)!=null)
        myViewHolder.edtValue.setText(title1ListValue.get(i));
        if (title2ListValue.get(i)!=null)
        myViewHolder.txtTime.setText(title2ListValue.get(i));

        if (i==4){
            myViewHolder. edtValue.setEnabled(false);
            myViewHolder.imgClock.setClickable(false);
        }else {
            myViewHolder.edtValue.setEnabled(true);
            myViewHolder.imgClock.setClickable(true);
        }
     /*   if (isEndMeter && isEndTime){
            if (i<=4){
                for (i=0;i<4;i++){
                    myViewHolder. edtValue.setEnabled(false);
                    myViewHolder.imgClock.setClickable(false);
                }
            }
            else {
                if (i==5){
                    myViewHolder. edtValue.setEnabled(false);
                    myViewHolder.imgClock.setClickable(false);
                }else {
                    myViewHolder.edtValue.setEnabled(true);
                    myViewHolder.imgClock.setClickable(true);
                }
            }
        } else {
                myViewHolder.edtValue.setEnabled(true);
                myViewHolder.imgClock.setClickable(true);
        }*/

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
        EditText edtValue;
        TextView txtTitle, txtTitle2,txtTime;
        ImageView imgClock;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_duty_title1);
            txtTitle2 = itemView.findViewById(R.id.item_duty_title2);
            edtValue = itemView.findViewById(R.id.item_duty_slip_edt);
            txtTime = itemView.findViewById(R.id.item_duty_cal_value);
            imgClock = itemView.findViewById(R.id.item_duty_slip_img_clock);

            edtValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String s = charSequence.toString();
                    Log.d("TAG", "onTextChanged: "+s);

                    dashboardInterface.onTextChanged(getAdapterPosition(), edtValue.getText().toString().trim());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            imgClock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboardInterface.openTimerPicker(getAdapterPosition(),txtTime, edtValue);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
