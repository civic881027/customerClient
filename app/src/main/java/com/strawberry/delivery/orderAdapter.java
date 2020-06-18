package com.strawberry.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.strawberry.delivery.data.foodCounter;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.orderViewHolder>{
    private ArrayList<foodCounter> foodCounters;
    private Context mContext;

    public orderAdapter(Context context,ArrayList<foodCounter> data){
        this.foodCounters=data;
        this.mContext=context;
    }


    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.sc_food_list,parent,false);
        return new orderViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return foodCounters.size();
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        holder.nameTextView.setText(foodCounters.get(position).getName());
        holder.countTextView.setText(String.valueOf(foodCounters.get(position).getCount()));
        holder.priceTextView.setText(String.valueOf(foodCounters.get(position).getPrice()));
        holder.totalTextView.setText(String.valueOf(foodCounters.get(position).getTotal()));

    }

    class orderViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView priceTextView;
        TextView countTextView;
        TextView totalTextView;

        public orderViewHolder(View view){
            super(view);
            nameTextView=(TextView)view.findViewById(R.id.tvSCFoodName);
            priceTextView=(TextView)view.findViewById(R.id.tvSCPrice);
            countTextView=(TextView)view.findViewById(R.id.tvSCFoodCount);
            totalTextView=(TextView)view.findViewById(R.id.tvSCTotal);
        }
    }

}
