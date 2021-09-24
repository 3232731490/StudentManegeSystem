package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/28-15:32
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Audience {
    private String plan_id; //计划编号
    private String Dept;    //学院
    private String Major;   //专业
    private String year;    //学年
    private String semester;    //学期

    public Audience(String plan_id, String dept, String major, String year, String semester) {
        this.plan_id = plan_id;
        Dept = dept;
        Major = major;
        this.year = year;
        this.semester = semester;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
