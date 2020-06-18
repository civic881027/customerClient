package com.strawberry.delivery;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class foodAdapter extends RecyclerView.Adapter<foodAdapter.foodViewHolder> {
    private Cursor mCursor;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public foodAdapter(Context context,Cursor cursor){
        this.mCursor=cursor;
        this.mContext=context;
    }

    @NonNull
    @Override
    public foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.food_list,parent,false);
        return new foodViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void onBindViewHolder(@NonNull final foodViewHolder holder, final int position) {
        if(!mCursor.moveToPosition(position))return;
        String name=mCursor.getString(mCursor.getColumnIndex("foodName"));
        int price=mCursor.getInt(mCursor.getColumnIndex("foodPrice"));
        String url=mCursor.getString(mCursor.getColumnIndex("foodUrl"));
        long id=mCursor.getLong(mCursor.getColumnIndex("_ID"));

        holder.nameTextView.setText(name);
        holder.priceTextView.setText(String.valueOf(price));
        Glide.with(mContext).load(url).into(holder.imageView);
        holder.countTextView.setText("0");
        holder.itemView.setTag(id);

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    class foodViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView priceTextView;
        TextView countTextView;
        ImageView imageView;
        public foodViewHolder(View view){
            super(view);
            nameTextView=(TextView)view.findViewById(R.id.tvFoodName);
            priceTextView=(TextView)view.findViewById(R.id.tvPrice);
            countTextView=(TextView)view.findViewById(R.id.tvFoodCount);
            imageView=(ImageView)view.findViewById(R.id.ivFood);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

}
