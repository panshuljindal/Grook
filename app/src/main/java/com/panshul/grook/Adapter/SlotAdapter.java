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
import com.panshul.grook.Model.SlotModel;
import com.panshul.grook.R;

import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.MyViewHolder> {

    List<SlotModel> list;
    Context context;
    int row_number=-1;
    public SlotAdapter(List<SlotModel> list, Context context) {
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
        SlotModel model = list.get(position);
        holder.slot.setText(model.getSlot());
        //Log.i("slot",model.getSlot());
        if (!GroundActivity.booking.getSlot().equals("null")){
            if (row_number==position){
                holder.slot.setTextColor(ContextCompat.getColor(context,R.color.white));
                holder.cl.setBackground(ContextCompat.getDrawable(context,R.drawable.slot_selected));
                holder.itemView.setEnabled(true);
            }
            else {
                if (model.getIsPresent().equals("true")) {
                    holder.slot.setTextColor(ContextCompat.getColor(context, R.color.groundHeading));
                    holder.cl.setBackground(ContextCompat.getDrawable(context, R.drawable.slot_available));
                    holder.itemView.setEnabled(true);
                } else {
                    holder.slot.setTextColor(ContextCompat.getColor(context, R.color.searchBack));
                    holder.cl.setBackground(ContextCompat.getDrawable(context, R.drawable.slot_available));
                    holder.itemView.setEnabled(false);
                }
            }
        }
        else {
            if (model.getIsPresent().equals("true")) {
                holder.slot.setTextColor(ContextCompat.getColor(context, R.color.groundHeading));
                holder.cl.setBackground(ContextCompat.getDrawable(context, R.drawable.slot_available));
                holder.itemView.setEnabled(true);
            } else {
                holder.slot.setTextColor(ContextCompat.getColor(context, R.color.searchBack));
                holder.cl.setBackground(ContextCompat.getDrawable(context, R.drawable.slot_available));
                holder.itemView.setEnabled(false);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_number = position;
                GroundActivity.booking.setSlot(list.get(position).getSlot());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slot;
        ConstraintLayout cl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            slot  = itemView.findViewById(R.id.textSlot);
            cl = itemView.findViewById(R.id.slotCl);
        }
    }
}
