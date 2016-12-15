package com.pq.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/12/14.
 */
public class SocketClient {

    public static void main(String[] args) {
        SocketClient client=new SocketClient();
        client.start();
    }
    public void start(){
        BufferedReader inputReader=null;
        BufferedWriter writer=null;
        BufferedReader reader=null;
        Socket socket=null;
        try {
            socket=new Socket("127.0.0.1",9898);
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputReader=new BufferedReader(new InputStreamReader(System.in));
            //启动一下就可以在哪里都行
            startServerReplyListener(reader);
            String inputContent;
            while (!(inputContent=inputReader.readLine()).equals("bye")){
               writer.write(inputContent+"\n");//这里一定要有\n服务端才认为读取完要不然服务器会认为没有结束一直等
                writer.flush();
                //这里我们向服务端发送数据后就会立即给我们返回数据我们直接去读就可以了
                //当我们开启线程监听之后这个就没有必要了
              /*  String response=reader.readLine();
                System.out.println(response);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
                inputReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void startServerReplyListener(final BufferedReader reader){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response;
                    //开启线程从服务器获取消息如果没有就会阻塞住
                    while ((response=reader.readLine())!=null){
                        System.out.println(response);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
