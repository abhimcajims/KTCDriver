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
import android.widget.TextView;

import com.ktcdrivers.R;

import java.util.ArrayList;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class AdapterDutySlipUploadDocument extends RecyclerView.Adapter<AdapterDutySlipUploadDocument.MyViewHolder> {

    private Context context;
    private ArrayList<String>array_img, arr_name;
    private UploadDocInterface uploadDocInterface;

    public AdapterDutySlipUploadDocument(Context context, ArrayList<String> array_img, ArrayList<String> arr_name,
                                         UploadDocInterface uploadDocInterface) {
        this.context = context;
        this.array_img = array_img;
        this.arr_name = arr_name;
        this.uploadDocInterface = uploadDocInterface;
    }

    public interface UploadDocInterface{
        void browse(View view, int pos);
        void getText(int pos, String value);
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

//        if(array_img.get(i).equals("Show Error Message")){
//            myViewHolder.edtName.setError("This field required.");
//        }

        if(arr_name.get(i).equals("Photo1")){
            myViewHolder.txtUpload.setText("Selected");
        } else {
            myViewHolder.txtUpload.setText("Choose Document");
        }

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

        public EditText edtName;
        public TextView txtUpload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edtName = itemView.findViewById(R.id.fragment_duty_slip_edt_upload_document);
            edtName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String s = charSequence.toString();
                    Log.d("TAG", "onTextChanged: "+s);
                    if (edtName.getText().toString()!=null){
                        uploadDocInterface.getText(getAdapterPosition(), edtName.getText().toString().trim());
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            txtUpload = itemView.findViewById(R.id.fragment_duty_slip_upload_document);

            txtUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadDocInterface.browse(view, getAdapterPosition());
                }
            });
        }
    }


}
