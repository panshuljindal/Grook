package com.panshul.grook.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.panshul.grook.Fragments.HistoryHomeFragment;
import com.panshul.grook.Model.UserHistoryModel;
import com.panshul.grook.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    List<UserHistoryModel> list1;

    public HistoryAdapter(Context context, List<UserHistoryModel> list1) {
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

        UserHistoryModel model = list1.get(position);
        holder.name.setText(model.getGname());
        holder.address.setText(model.getAddress());
        holder.sport.setText(model.getSport());
        holder.date.setText(unixconvertDay(model.getDate())+", "+unixconvert(model.getDate()));
        Glide.with(context).load(model.getGpic()).into(holder.image);
        holder.price.setText("Rs. "+model.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref  = context.getSharedPreferences("com.panshul.grook.history",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(list1.get(position));
                editor.putString("history",json);
                editor.apply();
                HistoryHomeFragment aboutus1 = new HistoryHomeFragment();
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,aboutus1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM").format(df);
        return vv;
    }
    public String unixconvertDay(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        int i=df.getDay();
        if (i==1){
            return "Monday";
        }
        else if (i==2){
            return "Tuesday";
        }
        else if (i==3){
            return "Wednesday";
        }
        else if (i==4){
            return "Thursday";
        }
        else if (i==5){
            return "Friday";
        }
        else if (i==6){
            return "Saturday";
        }
        else {
            return "Sunday";
        }
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,sport,date,price;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.historyName);
            address = itemView.findViewById(R.id.historyAddress);
            sport = itemView.findViewById(R.id.historySport);
            date = itemView.findViewById(R.id.historyDate);
            image = itemView.findViewById(R.id.historyImage);
            price = itemView.findViewById(R.id.historyPrice);
        }
    }
}
