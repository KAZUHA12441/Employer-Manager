package data;

import javax.print.DocFlavor;
import java.io.Serializable;

public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;
    public static Integer max_count = 0;

    private Integer ID;          //工号，自动分配

    private String name;         //名字

    private String sex;          //性别

    private String posts;        //职位

    private Integer phoneNumber; //电话号码

    private Integer salary;      //薪水

    private Float performance;    //绩效

    private String IDNum;         //实际编号
    /**
     * 创建一个学生基础信息
     * @param name  学生的名字
     * @param sex   性别
     * @param posts 职位
     */
    public Employee(String name, String sex, String posts,Integer phoneNumber)
    {
        this.name = name;
        this.sex = sex;
        this.posts = posts;
        this.phoneNumber = Integer.valueOf(phoneNumber);
        max_count++; //自动配工号
        this.ID = max_count;
    }

    /**
     * 服务端发送一个修改后的学生对象信息
     * @param name  员工的名字
     * @param sex   性别
     * @param posts 职位
     * @param phoneNumber 电话号码
     * @param salary 薪水
     * @param performance 绩效
     */
    public Employee(String name, String sex, String posts, String phoneNumber, String salary,String performance)
    {
        this.name = name;
        this.sex = sex;
        this.posts = posts;
        this.phoneNumber = Integer.valueOf(phoneNumber) ;
        this.salary = Integer.valueOf(salary);
        this.performance = Float.valueOf(performance);
    }

    /**
     * 对象比较方法
     * @param obj 对象
     * @return 布尔值 相同/不相同
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        return true;
    }

    /**
     * 设置一个名字
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getPosts() {
        return posts;
    }

    public String getIDNum() {
        return IDNum;
    }

    public void setIDNum(String IDNum) {
        this.IDNum = IDNum;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getPerformance() {
        return performance;
    }

    public void setPerformance(Float performance) {
        this.performance = performance;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
