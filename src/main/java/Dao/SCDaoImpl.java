package Dao;

import Entity.Student;
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
public class SCDaoImpl {

    /**
     * 选课
     */
    public static int insert_course(String sno,String cno)
    {
        String sql="insert into SC(sno,cno) values(?,?)";
        return DBUtil.excuteDml(sql,sno,cno);
    }

    /**
     * 查询课程是否已选
     */
    public static boolean is_existed(String sno,String cno)
    {
        Connection conn=DBUtil.get_Connection();
        String sql="select * from SC where Sno=? and Cno=?";
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,sno);
            ps.setString(2,cno);
            rs=ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return false;
    }

    /**
     * 查询某课程号选课学生
     */
    public static List<Student> query_all_stu(String cno)
    {
        List<Student> all=new ArrayList<>();
        Connection conn=DBUtil.get_Connection();
        String sql="select * from S where Sno in (select Sno from SC where Cno=?)";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            ps.setString(1,cno);
            rs=ps.executeQuery();
            String sno;
            String sname;
            String sex;
            int age;
            String dept;
            String major;
            while(rs.next())
            {
                sno=rs.getString(1);
                sname=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getInt(4);
                dept=rs.getString(5);
                major=rs.getString(6);
                Student s=new Student(sno,sname,sex,age,dept,major);
                all.add(s);
            }
            return all;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }

        return null;
    }

    /**
     * 退课
     */
    public static int del_course(String sno,String cno)
    {
        String sql="delete from SC where sno=? and cno=?";
        return DBUtil.excuteDml(sql,sno,cno);
    }

    /**
     * 更新学号
     */
    public static int update_sno(String oldsno,String newsno)
    {
        String sql="update SC set sno=? where sno=?";
        return DBUtil.excuteDml(sql,newsno,oldsno);
    }

    /**
     * 更新课程
     */
    public static int update_cno(String oldcno,String newcno){
        String sql="update SC set cno=? where cno=?";
        return DBUtil.excuteDml(sql,newcno,oldcno);
    }

}
