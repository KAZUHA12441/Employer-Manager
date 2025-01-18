package data;

import java.util.ArrayList;

/**
 * 发送接收数据包
 */
public class Result {
    private boolean success;

    private Employee employee;

    private ArrayList<Employee> employees_list;

    private String message;

    public void setErrorMessage(String message) {
        success=false;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Employee getStudent() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ArrayList<Employee> getEmployees_list() {
        return employees_list;
    }

    public void setEmployees_list(ArrayList<Employee> employees_list) {
        this.employees_list = employees_list;
    }
}
