package com.strawberry.delivery;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class restAdapter extends RecyclerView.Adapter<restAdapter.ViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mOnItemClickListener;
    public restAdapter(Context context,Cursor cursor){
        this.mContext=context;
        this.mCursor=cursor;
    }
    //初始化Holder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    public String getRestName(int position){
        if(!mCursor.moveToPosition(position))return "";
        String name=mCursor.getString(mCursor.getColumnIndex("restName"));
        return name;
    }
    //將資料綁入View中的元件
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(!mCursor.moveToPosition(position))return;
        String name=mCursor.getString(mCursor.getColumnIndex("restName"));
        String url=mCursor.getString(mCursor.getColumnIndex("restUrl"));
        holder.tvPosterName.setText(name);
        holder.tvTime.setText("15-20min");
        //使用第三方套件Glide直接抓取圖片至ImageView
        Glide.with(mContext).load(url).into(holder.ivPosterThumbnail);

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivPosterThumbnail;
        public TextView tvPosterName;
        public TextView tvTime;


        public ViewHolder(View itemView){
            super(itemView);
            tvPosterName=(TextView)itemView.findViewById(R.id.tvItemName);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            ivPosterThumbnail=(ImageView)itemView.findViewById(R.id.ivResraurant);
        }

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
