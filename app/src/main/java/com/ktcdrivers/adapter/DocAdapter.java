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
import com.ktcdrivers.model.ViewDetailsData;

import java.util.List;

/**
 * Created by Rakhi on 10/9/2018.
 */
public class DocAdapter extends RecyclerView.Adapter<DocAdapter.MyViewHolder> {
    private Context context;
    private List<ViewDetailsData.ImagelistBean>imagelistBeans;
    private DocInterface docInterface;

    public DocAdapter(Context context, List<ViewDetailsData.ImagelistBean> imagelistBeans, DocInterface docInterface) {
        this.context = context;
        this.imagelistBeans = imagelistBeans;
        this.docInterface = docInterface;
    }

    public interface DocInterface{
        void delete(int pos);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doc,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);



        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txtName.setText(imagelistBeans.get(i).getName());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imagelistBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        LinearLayout docCrossLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_doc_name);
            docCrossLayout = itemView.findViewById(R.id.item_doc_cross);

            docCrossLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    docInterface.delete(getAdapterPosition());
                }
            });
        }
    }


}
