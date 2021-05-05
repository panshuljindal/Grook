package com.panshul.grook.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.panshul.grook.Fragments.GroundFragment;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.R;

import java.util.List;

public class GroundAdapter extends RecyclerView.Adapter<GroundAdapter.MyViewHolder> {
    Context context;
    List<GroundModel> list;

    public GroundAdapter(Context context, List<GroundModel> list) {
        this.context = context;
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,address,sport,timing,closed;
        Button book;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.groundImage);
            name = itemView.findViewById(R.id.groundName);
            address = itemView.findViewById(R.id.groundAddress);
            sport = itemView.findViewById(R.id.groundSport);
            closed = itemView.findViewById(R.id.groundClosed);
            timing = itemView.findViewById(R.id.groundTiming);
            book = itemView.findViewById(R.id.homeBook);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.groundcard,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GroundModel model = list.get(position);
        holder.image.setClipToOutline(true);
        holder.name.setText(model.getGname());
        holder.address.setText(model.getGaddress());
        holder.sport.setText(model.getGsport());
        holder.timing.setText(model.getGtiming());
        holder.closed.setText(model.getGclosed());
        Glide.with(context).load(model.getGpic()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
