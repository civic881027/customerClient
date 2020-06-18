package com.strawberry.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

import com.strawberry.delivery.data.DBhelper;
import com.strawberry.delivery.data.foodCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Order extends AppCompatActivity {
    private SQLiteDatabase mDb;
    private Map<String, Integer> restID=new HashMap<>();
    private int restaurantID;
    private String restName;
    private int[] temp=new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        RecyclerView rv=(RecyclerView)findViewById(R.id.rvFood);
        createMap();
        Intent intent=getIntent();
        restName=intent.getStringExtra("restaurantID");
        Log.d("test",restName);
        restaurantID=restID.get(restName);
        DBhelper dBhelper=new DBhelper(this);

        mDb=dBhelper.getReadableDatabase();
        try{
            Cursor cursor=getAllfood();
            Log.d("test",String.valueOf(cursor.getCount()));
            foodAdapter adapter=new foodAdapter(this,cursor);
            adapter.setOnItemClickListener(new foodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView count=(TextView)view.findViewById(R.id.tvFoodCount);
                    count.setText(String.valueOf(Integer.parseInt(count.getText().toString())+1));
                    temp[position]=Integer.parseInt(count.getText().toString());
                }
            });
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    };


    private Cursor getAllfood(){

        return mDb.rawQuery("SELECT _ID,foodName,foodPrice,foodUrl FROM RestFood WHERE restaurant="+restaurantID,null);
    }


    public void toShoppingCart(View view){
        ArrayList<foodCounter> foodCount=new ArrayList<>();
        for(int i=0;i<temp.length;i++){
            if(temp[i]>0){
                Cursor cursor=mDb.rawQuery("SELECT _ID,foodName,foodPrice from RestFood where restaurant="+restaurantID,null);
                cursor.moveToPosition(i);
                String name=cursor.getString(cursor.getColumnIndex("foodName"));
                int price=cursor.getInt(cursor.getColumnIndex("foodPrice"));
                foodCount.add(new foodCounter(name,price,temp[i]));
                Log.d("test",foodCount.get(0).getName());
            }
        }



        Intent intent=new Intent(this,ShoppingCart.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("countFood",foodCount);
        bundle.putString("restaurant",restName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createMap(){

        restID.put("麥當勞-台北新生店",1);
        restID.put("可不可熟成紅茶-台北公館店",2);
        restID.put("再睡5分鐘",3);
    }

    @Override
    protected void onDestroy() {
        mDb.close();
        super.onDestroy();
    }
}
