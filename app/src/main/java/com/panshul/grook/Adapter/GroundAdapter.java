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
        TextView rating,address,name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.gimage);
            rating = itemView.findViewById(R.id.grating);
            address = itemView.findViewById(R.id.gaddress);
            name = itemView.findViewById(R.id.gname);
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
        holder.name.setText(model.getGname());
        holder.rating.setText(model.getGrating());
        holder.address.setText(model.getGaddress());
        Glide.with(context).load(model.getGpic()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = context.getSharedPreferences("com.panshul.matchup.ground",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(model);
                editor.putString("groundInfo",json);
                editor.apply();
                GroundFragment fragment = new GroundFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
