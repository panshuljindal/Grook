package com.panshul.grook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.panshul.grook.Model.SlotModel;
import com.panshul.grook.Model.SportModel;
import com.panshul.grook.R;

import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.MyViewHolder> {

    List<SportModel> list;
    Context context;

    public SportAdapter(List<SportModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slot_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SportModel model = list.get(position);
        holder.slot.setText(model.getSname());
        if (model.getIsPresent().equals("true")){
            holder.slot.setTextColor(ContextCompat.getColor(context,R.color.groundHeading));
            holder.itemView.setEnabled(true);
        }
        else {
            holder.slot.setTextColor(ContextCompat.getColor(context,R.color.searchBack));
            holder.itemView.setEnabled(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slot;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            slot  = itemView.findViewById(R.id.textSlot);
        }
    }
}
