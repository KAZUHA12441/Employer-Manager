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

            //客户端发送俩行，一行操作类型，一行数据将数据序列化
            type = reader.readLine();
            date = reader.readLine();

            switch (type)
            {
                case "login": //登录
                    break;
                case "add":   //添加
                    server.add(date);
                    break;
                case "delete": //删除
                    server.delete(date);
                    break;
                case "sreach": //查找
                    server.search(date);
                    break;
                case "modify": //修改
                    server.modify(date);
                    break;
                case "statistic": //筛选
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
