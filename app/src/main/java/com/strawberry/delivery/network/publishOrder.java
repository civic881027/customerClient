package com.strawberry.delivery.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.strawberry.delivery.R;
import com.strawberry.delivery.data.orderform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.strawberry.delivery.data.foodCounter;

public class publishOrder extends Activity {

    private static String orderMessage;
    private static Socket clientSocket;
    private Thread thread;
    private Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_order);

        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            orderform orderForm = (orderform) bundle.getSerializable("orderForm");
            List<foodCounter> foods = orderForm.getFoods();
            String sFoods = "";
            for (foodCounter food : foods) {
                sFoods =  sFoods+"Name:" + food.getName() + " Count:" + String.valueOf(food.getCount()) + "\n";
            }
            orderMessage = "ID:" + orderForm.getID() + "\nrestAddress:"+orderForm.getRestAddress()+"\nfoods:\n" + sFoods + "\nTotal:" + String.valueOf(orderForm.getTotal()) + "\ntargetAddress:" + orderForm.getTargetAddress()+"\n\n";
            tvTest.setText(orderMessage);
            orderMessage=orderMessage.replaceAll("\n"," ");
            Log.d("testMessage",orderMessage);

            thread=new Thread(socket_server);
            thread.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private Runnable socket_server=new Runnable() {
        @Override
        public void run() {
            try{
                InetAddress serverIP=InetAddress.getByName("10.0.2.2");
                clientSocket=new Socket(serverIP,5050);
                BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "Big5"));
                BufferedReader br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"Big5"));
                bw.write("orderClient\n");
                bw.flush();
                bw.write(orderMessage+" restClient\n");
                bw.flush();
                while(clientSocket.isConnected()){
                    String temp=br.readLine();
                    if(temp!=null&&temp.contains("正在備餐")){
                        System.out.println("orderRead");
                        System.out.println(temp);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(publishOrder.this,"~正在備餐~",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }

            }catch (IOException e){

            }
        }
    };

    @Override
    protected void onDestroy() {
        try{

            clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        super.onDestroy();
    }
}

