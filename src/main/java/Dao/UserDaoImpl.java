package Dao;

import Entity.User;
import MyUtil.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:05
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class UserDaoImpl {
    /**
     * 插入用户
     * @param user
     * @return
     */
    public static int intsert_admin(User user){
        String username=user.getUser_id() ;
        String password=user.getUser_pass();
        String sql="insert into U(id,Upassword) values(?,?)";
        int result=DBUtil.excuteDml(sql,username,password);
        return result;
    }

    /**
     * 获取用户列表
     * @return
     */
    public static List<User> query_user()
    {
        String sql="select * from U";
        Connection conn=DBUtil.get_Connection();
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet resultSet= null;
        try {
            resultSet = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            List<User> user_list=new ArrayList<>();
            while(resultSet.next()){
                String user=resultSet.getString(1);
                String pass=resultSet.getString(2);
                User user1=new User(user,pass);
                user_list.add(user1);
            }
            return user_list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,resultSet);
        }
        return null;
    }

    /**
     * 删除用户
     * @return
     */
    public static int del_user(String id)
    {
        String sql="delete from U where id=?";
        return DBUtil.excuteDml(sql,id);
    }

    /**
     * 更新用户
     * @return
     */
    public static int update_user(String id,String password)
    {
        String sql="update U set Upassword=? where id=?";
        return DBUtil.excuteDml(sql,password,id);
    }

    /**
     * 查询用户密码
     */
    public static String get_pass(String id)
    {
        String sql="select Upassword from U where id=?";
        Connection conn=DBUtil.get_Connection();
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,id);
            rs=ps.executeQuery();
            if(rs.next())
            {
                return rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 查询用户是否存在
     * @param id
     * @return
     */
    public static boolean is_existed(String id)
    {
        String sql="select * from U where id=?";
        Connection conn=DBUtil.get_Connection();
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,id);
            rs=ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        return false;
    }
}
