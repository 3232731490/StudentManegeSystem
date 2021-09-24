package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/27-19:50
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Student {
    private String sno; //学号
    private String sname;   //姓名
    private String sex; //性别
    private int age;    //年龄
    private String Dept;    //学院
    private  String Major;  //专业

    /**
     * 构造函数
     * @param sno
     * @param sname
     * @param sex
     * @param age
     * @param dept
     * @param major
     */
    public Student(String sno, String sname, String sex, int age, String dept, String major) {
        this.sno = sno;
        this.sname = sname;
        this.sex = sex;
        this.age = age;
        Dept = dept;
        Major = major;
    }

    /**
     * 以下为获取以及设置值
     */
    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }
}
