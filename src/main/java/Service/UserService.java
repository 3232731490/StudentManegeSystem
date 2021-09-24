package Service;

import Dao.UserDaoImpl;
import Entity.Admin;
import Entity.User;

import java.util.Iterator;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:07
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class UserService {

    /**
     * 查询用户是否存在
     * @param id
     * @return  存在返回false
     */
    public static boolean is_existed_stu(String id,List<User> student_List){
        Iterator<User> iterator=student_List.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getUser_id().equals(id))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 查询用户是否合法
     * @param id
     * @return  合法返回true
     */
    public static boolean is_legal_user(String id,String pass,List<User> user_List){
        Iterator<User> iterator=user_List.iterator();
        while(iterator.hasNext()){
            User user=iterator.next();
            if(user.getUser_id().trim().equals(id.trim())&&user.getUser_pass().trim().equals(pass.trim()))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * 插入用户
     * @param username
     * @param password
     * @return
     */
    public static int insert_user(String username,String password){
        User user=new User(username,password);
        return UserDaoImpl.intsert_admin(user);
    }

    /**
     * 删除用户
     */
    public static int del_user(String username)
    {
        return UserDaoImpl.del_user(username);
    }

    /**
     * 修改用户
     */
    public static int update_user(String id,String pass){
        return UserDaoImpl.update_user(id,pass);
    }



}
