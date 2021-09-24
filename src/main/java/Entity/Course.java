package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:01
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Course {
    private String cno; //课程号
    private String cname;   //课程名
    private double Ct;  //学份
    private int time;   //学时
    private String t_no;    //教职工编号
    private String location;    //地点

    public Course(String cno, String cname, double ct, int time, String t_no, String location) {
        this.cno = cno;
        this.cname = cname;
        Ct = ct;
        this.time = time;
        this.t_no = t_no;
        this.location = location;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public double getCt() {
        return Ct;
    }

    public void setCt(double ct) {
        Ct = ct;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getT_no() {
        return t_no;
    }

    public void setT_no(String t_no) {
        this.t_no = t_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
