package Dao;

import Entity.Admin;
import MyUtil.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:06
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class AdminDaoImpl {
    public static int intsert_admin(Admin admin){
        String username=admin.getAdmin_id() ;
        String password=admin.getAdmin_pass();
        String sql="insert into Admin(Admin_id,Apassword) values(?,?)";
        int result=DBUtil.excuteDml(sql,username,password);
        return result;
    }

    public static List<Admin> query_admin()
    {
        String sql="select * from Admin";
        Connection conn=DBUtil.get_Connection();
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet resultSet= null;
        try {
            resultSet = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {

            List<Admin> admin_list=new ArrayList<>();
            while(resultSet.next()){
                String user=resultSet.getString(1);
                String pass=resultSet.getString(2);
                Admin admin=new Admin(user,pass);
                admin_list.add(admin);
            }
            return admin_list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,resultSet);
        }
        return null;
    }
}
