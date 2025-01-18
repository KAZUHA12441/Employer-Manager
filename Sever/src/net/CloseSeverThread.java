
package net;

import tool.FileTool;

import java.util.Scanner;

public class CloseSeverThread extends Thread {
    public void run()
    {
        System.out.println("输入exit关闭服务器");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        if("exit".equals(message))
        {
            int flag = FileTool.saveDate();
            if(flag == 1)
            {
                System.out.println("数据保存成功,关闭服务器");
                System.exit(0);
            }
            else if(flag == -1)
            {
                System.out.println("丢失文件!!!");
            }
            else if(flag == -2)
            {
                System.out.println("写入文件失败");
            }
            else if(flag == -3)
            {
                System.out.println("关闭文件失败");
            }
        }
    }

}
