package com.strawberry.delivery.ui.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strawberry.delivery.data.DBhelper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.strawberry.delivery.Order;
import com.strawberry.delivery.R;
import com.strawberry.delivery.restAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SQLiteDatabase mDb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView=(RecyclerView)root.findViewById(R.id.rvRestaurant);
        DBhelper dBhelper=new DBhelper(getActivity());
        mDb=dBhelper.getReadableDatabase();
        try{
            Cursor cursor=getAllrest();
            restAdapter adapter=new restAdapter(requireContext(),cursor);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter.setOnItemClickListener(new restAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView nameview=(TextView)view.findViewById(R.id.tvItemName);
                    Intent intent=new Intent(getActivity(),Order.class);
                    intent.putExtra("restaurantID",nameview.getText());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }


        return root;
    }
    private Cursor getAllrest(){
        return mDb.rawQuery("SELECT _ID,restName,restUrl FROM restaurant",null);
    }


    @Override
    public void onDestroy() {
        mDb.close();

        super.onDestroy();
    }
}
