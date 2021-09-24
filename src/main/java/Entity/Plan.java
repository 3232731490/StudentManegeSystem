package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/28-15:34
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Plan {
    private String plan_id; //计划id
    private String c_id;    //课程号 一个id对应多个课程号

    public Plan(String plan_id, String c_id) {
        this.plan_id = plan_id;
        this.c_id = c_id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
}
