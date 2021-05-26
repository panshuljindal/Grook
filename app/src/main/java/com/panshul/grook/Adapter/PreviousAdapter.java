package com.panshul.grook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panshul.grook.Model.AllHistoryModel;
import com.panshul.grook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyViewHolder> {

    ArrayList<AllHistoryModel> list;
    Context context;

    public PreviousAdapter(ArrayList<AllHistoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.previous_booked,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        AllHistoryModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.date.setText(model.getPhone());
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM yyyy").format(df);
        return vv;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,date;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.previousName);
            date = itemView.findViewById(R.id.previousDate);
        }
    }
}
