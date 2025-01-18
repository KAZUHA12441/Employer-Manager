package tool;

import data.Employee;
import net.server;

import java.io.*;
import java.util.ArrayList;

public class FileTool {

    /**
     * 写入文件信息
     */
    public static int saveDate()
    {
        ObjectOutputStream output = null;

        try
        {
            output = new ObjectOutputStream(new FileOutputStream("employee.dat"));
            output.writeObject(server.employees);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return -3;
                }
            }
        }
        return 1;
    }


    /**
     *  读取文件信息
     */
    public static int getDate()
    {
        ObjectInputStream input = null;
        try
        {
            input = new ObjectInputStream(new FileInputStream("employee.dat"));
            if (input.available()>0)
            server.employees = (ArrayList<Employee>)input.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (EOFException e)
        {
            e.printStackTrace();
        } catch (IOException e ) {
            e.printStackTrace();
            return -2;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(input != null)
            {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        return 1;
    }
}
