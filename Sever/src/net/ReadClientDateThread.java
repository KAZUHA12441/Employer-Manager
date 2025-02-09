package net;

import com.google.gson.Gson;
import data.Result;

import java.io.*;
import java.net.Socket;

public class ReadClientDateThread extends Thread{

    private Socket socket;
    private Result result;
    private Gson gson;
    public ReadClientDateThread(Socket socket)
    {
       this.socket = socket;
       //创建数据包
       result = new Result();
       gson = new Gson();
    }

    public void run()
    {
        String type,date;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            type = reader.readLine();
            date = reader.readLine();

            switch (type)
            {
                case "login":
                    break;
                case "add":
                    server.add(date);
                    break;
                case "delete":
                    server.delete(date);
                    break;
                case "sreach":
                    server.search(date);
                    break;
                case "modify":
                    server.modify(date);
                    break;
                case "statistic":
                    server.statistic(date);
                    break;
                default:
                    break;
            }

            try
            {
                String resultString =  gson.toJson(result);
                PrintStream print = new PrintStream(socket.getOutputStream());
                print.println(resultString);
                print.flush();
            } catch (IOException e) {
                System.out.println("输出错误");
            }
        } catch (IOException e) {
            System.out.println("找不到用户"+"id为"+socket.getRemoteSocketAddress());
        }
    }

}
