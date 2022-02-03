package com.example.firebasestoragewithuploadimage.AdaptarClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasestoragewithuploadimage.Modelclass.ImageRetrive;
import com.example.firebasestoragewithuploadimage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyviewHolder> {

    Context context;
    private ArrayList<String> retriveList;

    public RecycleAdapter(Context context, ArrayList<String> uploadlist) {
        this.context = context;
        this.retriveList = uploadlist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =  layoutInflater.inflate(R.layout.sample_layout,viewGroup,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        Picasso.with(context).load(retriveList.get(position)).placeholder(R.drawable.ic_edit).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return retriveList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagelist);
        }
    }
}
