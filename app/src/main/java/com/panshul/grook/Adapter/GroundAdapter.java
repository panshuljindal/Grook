package com.panshul.grook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.grook.Activity.GroundActivity;
import com.panshul.grook.Activity.Login;
import com.panshul.grook.Model.GroundModel;
import com.panshul.grook.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        Glide.with(context).load(model.getGpic()).priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.image);
       // new ImageLoadTask(model.getGpic(),holder.image).execute();
        holder.image.setClipToOutline(true);
        holder.name.setText(model.getGname());
        holder.address.setText(model.getGaddress());
        holder.sport.setText(model.getGsport());
        holder.timing.setText(model.getGtiming());
        holder.closed.setText(model.getGclosed());

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(list.get(position));
                Intent i = new Intent(v.getContext(), GroundActivity.class);
                i.putExtra("ground",json);
                context.startActivity(i);
            }
        });
//        int i = holder.getAdapterPosition();
//        Log.i("string",String.valueOf(list.size()));
//        if (i==list.size()-1){
//            Log.i("hello","hello");
//            DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Ground");
//            myref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot ds: snapshot.getChildren()){
//                        GroundModel model = ds.getValue(GroundModel.class);
//                        list.add(model);
//                    }
//                    notifyDataSetChanged();
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
