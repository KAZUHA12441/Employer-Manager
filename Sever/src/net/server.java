package net;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import data.Employee;
import data.Result;
import data.Search_Key;
import tool.FileTool;
import tool.Filter;
import tool.SortEmployee;
import tool.login;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class server {

    private static final String AdministratorName = "wdq";
    private static final String Administrator_Password = "123456890";


    public static ArrayList<Employee> employees = new ArrayList<Employee>();
    private Integer port;


    /**
     * 服务器构造
     * @param port 端口号
     */
    public server(Integer port)
    {
         this.port = port;
    }

    /**
     * 创建服务器启动线程
     */
    public void startServer() throws IOException {
        CloseSeverThread closeSever = new CloseSeverThread();
        Thread thread = new Thread(closeSever);
        thread.start();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务端已启动，等待客户端请求中 ....");

        try{
            while(true) {
                //等待用户连接
                Socket socket = serverSocket.accept();

                //启动服务器线程
                System.out.println("有人上线了"+socket.getRemoteSocketAddress());
                new ReadClientDateThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }

    }


    public static void main(String[] args) {
        //读取系统文件
        if(FileTool.getDate()<1)
        {
            System.out.println("文件读取失败");
            System.exit(0);
        }
        try
        {
            new server( 8983).startServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加员工
     * @param date 被序列化的数据(客户端)
     */
    public static Result add(String date)
    {
        Result result = new Result();
        try {
            Employee addEmployeeDate = (new Gson()).fromJson(date, Employee.class);
            Employee newEmployee = new Employee(addEmployeeDate.getName(), addEmployeeDate.getSex(), addEmployeeDate.getPosts(), addEmployeeDate.getPhoneNumber());
            employees.add(newEmployee);
            SortEmployee.ascSortSalary(employees);
            result.setMessage("添加成功");
        } catch (JsonSyntaxException e) {
              e.printStackTrace();
              result.setMessage("添加失败");
        }
        return result;

    }

    /**
     * 删除员工
     * @param date 客户端的数据
     * @return 处理结果(回传)
     */
    public static Result delete(String date)
    {
        Result result = new Result();
        boolean ifDeleteSuccess = false;
        try{
            //根据学号删除
            Integer deleteEmployeeId = (new Gson()).fromJson(date, Integer.class);
            Iterator<Employee> iterator = employees.iterator();
            while(iterator.hasNext())
            {
                 Employee it = iterator.next();
                 if (it.getID().equals(deleteEmployeeId))
                 {
                     employees.remove(it);
                     result.setMessage("删除成功");
                     ifDeleteSuccess = true;
                     break;
                 }
            }
            if(!ifDeleteSuccess)
            {
                result.setMessage("查无此人");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            result.setMessage("删除失败");
            //返回处理结果
        }
        return result;
    }

    /**
     * 修改员工信息
     * @param  date 序列化数据
     * @return  返回客户端信息
     */
    public static Result modify(String date)
    {
        Result result = new Result();
        Employee it = null;
       try
       {
           Employee modifyEmployeeDate = (new Gson()).fromJson(date, Employee.class);
           Iterator<Employee> iterator = employees.iterator();
           while (iterator.hasNext())
           {
               it = iterator.next();
               if(it.getID().equals(modifyEmployeeDate.getID()));
               {
                   //数据copy
                   it.setID(modifyEmployeeDate.getID());
                   it.setName(modifyEmployeeDate.getName());
                   it.setSex(modifyEmployeeDate.getSex());
                   it.setPosts(modifyEmployeeDate.getPosts());
                   it.setPhoneNumber(modifyEmployeeDate.getPhoneNumber());
                   it.setSalary(modifyEmployeeDate.getSalary());
                   it.setPerformance(modifyEmployeeDate.getPerformance());
                   result.setMessage("修改成功");
                   break;
               }
           }
           result.setMessage("查无此人");
       } catch (JsonSyntaxException e) {
           e.printStackTrace();
           result.setMessage("修改失败");
       }
        return  result; //返回结果
    }

    /**
     * 查找
     * @param date 序列化
     * @return 返回客户端的数据
     */
    public static Result search(String date)
    {
        Result result = new Result();
        Employee it = null;
        //客户端的查找键值
        try {
            Search_Key searchPackage = (new Gson()).fromJson(date, Search_Key.class);
            //获取迭代器
            Iterator<Employee> iterator = employees.iterator();
            if ("工号".equals(searchPackage.getFiled())) { //查找功能的选项
                while(iterator.hasNext()) {
                    it = iterator.next();
                    if (it.getID().toString().equals(searchPackage.getKey())) {
                        result.getEmployees_list().add(it);
                        break;
                    }
                }
            } else if ("姓名".equals(searchPackage.filed)){ //选项
                while(iterator.hasNext())
                {
                    it = iterator.next();
                    if(it.getName().equals(searchPackage.key)) {
                        result.getEmployees_list().add(it);
                        break;
                    }
                }
            }
            result.setMessage("查找成功");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            result.setMessage("查找出错");
        }
        return result; //返回查找结果
    }

    /**
     * 过滤器
     * @param date
     * @return 结果
     */
    public static Result statistic(String date)
    {
        //模糊搜索，过滤器
        Result result = new Result();
        Employee it;
        int employeeCount = 0;
        Iterator<Employee> iterator = employees.iterator();
        Search_Key key =  (new Gson()).fromJson(date, Search_Key.class);
        try {
            //获取客户端发下来的信息
            if("姓名".equals(key.getFiled()))
            {
                while (iterator.hasNext()) {
                    it = iterator.next();
                    if (it.getName().contains(key.getKey())) {
                        result.getEmployees_list().add(it);

                        employeeCount++;
                    }
                }
            }
            else if("薪水".equals(key.getFiled()))
            {
               while(iterator.hasNext())
               {
                   it = iterator.next();
                   if(it.getSalary()>Integer.parseInt(key.getKey()));
                   {
                       result.getEmployees_list().add(it);
                       employeeCount++;
                   }
               }
            }
            SortEmployee.ascSortSalary(result.getEmployees_list());
            result.setMessage("找到了"+ Integer.toString(employeeCount)+"个目标" );
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 管理员确认
     */
    public static Result loginCheck(String date) {
        boolean flag = false;
        Gson gson = new Gson();
        Result result = new Result();
        if (gson.fromJson(date, login.class).getName().equals(AdministratorName)) {
            if (gson.fromJson(date, login.class).getPassword().equals(Administrator_Password)) {
                result.setMessage("密码正确");
                flag = true;
            }
        }
        if (!flag) {

            result.setMessage("用户名或者密码错误");
        }
        return result;
    }
}

