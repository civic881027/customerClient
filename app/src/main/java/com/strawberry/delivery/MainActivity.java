package com.strawberry.delivery;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.strawberry.delivery.data.DBhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        DBhelper dBhelper=new DBhelper(this);
        mDb=dBhelper.getWritableDatabase();
        dBhelper.onUpgrade(mDb,1,1);
        insertData();
        insertRestaurant();
    }

    private long addNewRestaurant(String name,String address,String phone,float percent,String url){
        ContentValues cv=new ContentValues();
        cv.put("restName",name);
        cv.put("restAddress",address);
        cv.put("restPhone",phone);
        cv.put("percent",percent);
        cv.put("restUrl",url);
        return mDb.insert("restaurant",null,cv);
    }
    private void insertRestaurant(){
        addNewRestaurant("麥當勞-台北新生店","106台北市大安區新生南路三段88-2號","0223620668",(float)0.25,"https://i.imgur.com/FtGifdh.jpg");
        addNewRestaurant("可不可熟成紅茶-台北公館店"," 100台北市中正區汀州路三段174號","0223680260", (float) 0.2,"https://i.imgur.com/kALZWlr.png");
        addNewRestaurant("再睡5分鐘","100台北市中正區南陽街24號","0223705200",(float)0.33,"https://i.imgur.com/CyRXw7A.png");
    }
    private void insertData() {
        addNewFood("大麥克餐",130,"https://imgur.com/Itg7XRG.jpg",1);
        addNewFood("雙層牛肉吉事堡餐",145,"https://imgur.com/qaCXcZ4.jpg",1);
        addNewFood("板烤雞腿堡餐",120,"https://imgur.com/0VAmzrE.jpg",1);
        addNewFood("愛司檸果",60,"https://imgur.com/cd00jNJ.jpg",2);
        addNewFood("愛司紅茶",65,"https://imgur.com/OeYRuM1.jpg",2);
        addNewFood("熟成歐蕾",55,"https://imgur.com/e8iWaFm.jpg",2);
        addNewFood("棉被午茉綠",65,"https://imgur.com/vy5jYkG.jpg",3);
        addNewFood("日安紅珍珠歐蕾",65,"https://imgur.com/bWKSvhu.jpg",3);
    }


    private long addNewFood(String name,int price,String url,int rest){
        ContentValues cv = new ContentValues();
        cv.put("foodName",name);
        cv.put("foodPrice",price);
        cv.put("foodUrl",url);
        cv.put("restaurant",rest);
        return mDb.insert("RestFood",null,cv);
    }
}
