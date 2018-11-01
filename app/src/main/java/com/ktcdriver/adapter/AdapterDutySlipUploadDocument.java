package com.ktcdriver.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ktcdriver.R;

import java.util.ArrayList;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class AdapterDutySlipUploadDocument extends RecyclerView.Adapter<AdapterDutySlipUploadDocument.MyViewHolder> {

    private Context context;
    private ArrayList<String>array_img;
    private UploadDocInterface uploadDocInterface;

    public AdapterDutySlipUploadDocument(Context context, ArrayList<String> array_img, UploadDocInterface uploadDocInterface) {
        this.context = context;
        this.array_img = array_img;
        this.uploadDocInterface = uploadDocInterface;
    }

    public interface UploadDocInterface{
        void browse(View view, int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_duty_slip_upload_document,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return array_img.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView txtUpload = itemView.findViewById(R.id.fragment_duty_slip_upload_document);

            txtUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadDocInterface.browse(view,getAdapterPosition());
                }
            });
        }
    }


}
