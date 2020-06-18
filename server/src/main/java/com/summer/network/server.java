package com.summer.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class server {
    private static int serverport = 5050;     //自訂的 Port
    private static ServerSocket serverSocket; //伺服端的Socket
    private static int count=0; //計算有幾個 Client 端連線

    // 用 ArrayList 來儲存每個 Client 端連線
    private static HashMap<String,Socket> socketList=new HashMap<>();
    private static ArrayList clients = new ArrayList();
    private static BufferedReader br;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is start.");
            // 顯示等待客戶端連接
            System.out.println("Waiting for client connect");
            // 當Server運作中時
            while (!serverSocket.isClosed()) {
                // 呼叫等待接受客戶端連接
                waitNewClient();

            }
        } catch (IOException e) {
            System.out.println("Server Socket ERROR");
        }
    }

    // 等待接受 Client 端連接
    public static void waitNewClient() {
        try {
            Socket socket = serverSocket.accept();
            ++count;
            System.out.println("現在使用者個數："+count);
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"BIG5"));
            String temp=br.readLine();
            if(!socketList.containsKey(temp)){
                socketList.put(temp,socket);
                System.out.println("Now is connecting:"+temp);
            }
            // 呼叫加入新的 Client 端
            addNewClient(socket);

        } catch (IOException e) {}
    }

    // 加入新的 Client 端
    public static void addNewClient(final Socket socket) throws IOException {
        // 以新的執行緒來執行
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 取得網路串流
                    String temp=new String();
                    // 當Socket已連接時連續執行
                    BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"BIG5"));
                    while (socket.isConnected()){
                        // 取得網路串流的資料
                        if((temp=br.readLine())!=null){
                            System.out.println("buffer:"+temp);
                            castMsg(temp);
                        }
                    }
                }
                catch (IOException e) {
                    e.getStackTrace();
                }
                finally{
                    --count;
                    System.out.println("現在使用者個數："+count);
                }
            }
        });

        // 啟動執行緒
        t.start();
    }

    // 廣播訊息給其它的客戶端
    public static void castMsg(String Msg){
        Socket socket;
        String[] temp=Msg.split(" ");
        String toClient=temp[temp.length-1];
        if(socketList.containsKey(toClient)){
            socket=socketList.get(toClient);
            System.out.println("TOclient:"+toClient);
            pushMsg(socket,Msg);
        }
    }

    public static void pushMsg(Socket socket,String msg){
        try{
            BufferedWriter bw;
            bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"BIG5"));
            bw.write(msg+"\n");
            bw.flush();
        }catch (IOException e){}finally {
        }
    }
}
