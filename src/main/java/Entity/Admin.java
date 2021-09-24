package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/27-19:59
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Admin {
    private String admin_id;    //账号
    private String admin_pass;  //密码

    public Admin(String admin_id, String admin_pass) {
        this.admin_id = admin_id;
        this.admin_pass = admin_pass;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_pass() {
        return admin_pass;
    }

    public void setAdmin_pass(String admin_pass) {
        this.admin_pass = admin_pass;
    }
}
