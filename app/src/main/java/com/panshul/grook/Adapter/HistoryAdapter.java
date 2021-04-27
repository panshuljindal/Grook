package com.panshul.grook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.panshul.grook.Model.HistoryGroundModel;
import com.panshul.grook.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    List<HistoryGroundModel> list1;

    public HistoryAdapter(Context context, List<HistoryGroundModel> list1) {
        this.context = context;
        this.list1 = list1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.historycard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HistoryGroundModel model = list1.get(position);
        holder.name.setText(model.getHname());
        holder.address.setText(model.getHaddress());
        holder.sport.setText(model.getHsport());
        holder.timeslot.setText(model.getHtimeslot());
        holder.rating.setText(model.getHrating());
        Glide.with(context).load(model.getHimage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,sport,timeslot,rating;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hname);
            address = itemView.findViewById(R.id.haddress);
            sport = itemView.findViewById(R.id.hsport);
            timeslot = itemView.findViewById(R.id.htimeSlot);
            rating = itemView.findViewById(R.id.hrating);
            image = itemView.findViewById(R.id.himage);
        }
    }
}
