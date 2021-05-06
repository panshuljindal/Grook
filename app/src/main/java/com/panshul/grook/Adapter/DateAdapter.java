package com.panshul.grook.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.panshul.grook.Activity.GroundActivity;
import com.panshul.grook.Model.DateModel;
import com.panshul.grook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {

    ArrayList<DateModel> list;
    Context context;
    int row_number;

    public DateAdapter(ArrayList<DateModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.date_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DateModel model = list.get(position);
        holder.date.setText(unixconvertDate(model.getDate()));
        holder.day.setText(unixconvertDay(model.getDate()));

        if (!GroundActivity.booking.getDate().equals("null")){
            if (row_number==position){
                holder.cl.setBackground(ContextCompat.getDrawable(context,R.drawable.slot_selected));
                holder.day.setTextColor(ContextCompat.getColor(context,R.color.white));
                holder.date.setTextColor(ContextCompat.getColor(context,R.color.white));
                holder.itemView.setEnabled(true);
            }
            else {
                if (model.getIsPresent().equals("false")){
                    holder.cl.setBackground(ContextCompat.getDrawable(context,R.drawable.date_notpresent));
                    holder.day.setTextColor(ContextCompat.getColor(context,R.color.searchBack));
                    holder.date.setTextColor(ContextCompat.getColor(context,R.color.searchBack));
                    holder.itemView.setEnabled(false);

                }
                else {
                    holder.cl.setBackground(ContextCompat.getDrawable(context,R.drawable.date_present));
                    holder.day.setTextColor(ContextCompat.getColor(context,R.color.groundHeading));
                    holder.date.setTextColor(ContextCompat.getColor(context,R.color.groundHeading));
                    holder.itemView.setEnabled(true);

                }
            }
        }
        else {
            if (model.getIsPresent().equals("false")){
                holder.cl.setBackground(ContextCompat.getDrawable(context,R.drawable.date_notpresent));
                holder.day.setTextColor(ContextCompat.getColor(context,R.color.searchBack));
                holder.date.setTextColor(ContextCompat.getColor(context,R.color.searchBack));
                holder.itemView.setEnabled(false);

            }
            else {
                holder.cl.setBackground(ContextCompat.getDrawable(context,R.drawable.date_present));
                holder.day.setTextColor(ContextCompat.getColor(context,R.color.groundHeading));
                holder.date.setTextColor(ContextCompat.getColor(context,R.color.groundHeading));
                holder.itemView.setEnabled(true);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_number=position;
                GroundActivity.booking.setDate(unixconvertFinal(list.get(position).getDate()));
                notifyDataSetChanged();
            }
        });

    }
    public String unixconvertFinal(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd/MMM/yyyy").format(df);
        return vv;
    }
    public String unixconvertDate(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd").format(df);
        return vv;
    }
    public String unixconvertDay(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        int i=df.getDay();
        if (i==1){
            return "MON";
        }
        else if (i==2){
            return "TUE";
        }
        else if (i==3){
            return "WED";
        }
        else if (i==4){
            return "THU";
        }
        else if (i==5){
            return "FRI";
        }
        else if (i==6){
            return "SAT";
        }
        else {
            return "SUN";
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date,day;
        ConstraintLayout cl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateText);
            day = itemView.findViewById(R.id.dayText);
            cl = itemView.findViewById(R.id.dateCl);
        }
    }
}
