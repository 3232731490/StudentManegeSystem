package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:00
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class User {
    private String User_id; //账号
    private String User_pass;   //密码

    public User(String user_id, String user_pass) {
        User_id = user_id;
        User_pass = user_pass;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_pass() {
        return User_pass;
    }

    public void setUser_pass(String user_pass) {
        User_pass = user_pass;
    }
}
