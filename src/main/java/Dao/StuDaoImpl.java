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
 * Time: 2021/6/27-19:10
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class StuDaoImpl {

    /**
     * 获取学生列表
     * @return
     */
    public static List<Student> query_stu(){
        String sql="select * from S";
        Connection conn=DBUtil.get_Connection();
        PreparedStatement ps= DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs= null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Student> stu_list=new ArrayList<>();
        try {
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
                stu_list.add(s);
            }
            return stu_list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 通过学号查询学生
     */
    public static Student query_a_stu(String id)
    {
        String sql="select * from S where Sno=?";
        Connection conn=DBUtil.get_Connection();
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,id);
            rs=ps.executeQuery();
            String sno;
            String sname;
            String sex;
            int age;
            String dept;
            String major;
            if(rs.next())
            {
                sno=rs.getString(1);
                sname=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getInt(4);
                dept=rs.getString(5);
                major=rs.getString(6);
                return new Student(sno,sname,sex,age,dept,major);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }
    /**
     * 通过姓名查询学生
     */
    public static List<Student> query_name_stu(String sname)
    {
        String sql="select * from S where Sname=?";
        List<Student> students=new ArrayList<>();
        Connection conn=DBUtil.get_Connection();
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,sname);
            rs=ps.executeQuery();
            String sno;
            String sn;
            String sex;
            int age;
            String dept;
            String major;
            while(rs.next())
            {
                sno=rs.getString(1);
                sn=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getInt(4);
                dept=rs.getString(5);
                major=rs.getString(6);
                students.add( new Student(sno,sn,sex,age,dept,major));
            }
            return students;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 增加学生
     * @param stu
     * @return 返回更新条数
     */
    public static int add_stu(Student stu)
    {
        String sql="insert into S(Sno,Sname,Sex,Birth,Dept,Major) values(?,?,?,?,?,?)";
        String sno=stu.getSno();
        String sname=stu.getSname();
        String sex=stu.getSex();
        int age=stu.getAge();
        String dept=stu.getDept();
        String major=stu.getMajor();
        return DBUtil.excuteDml(sql,sno,sname,sex,age,dept,major);
    }

    /**
     * 插入部分信息的添加学生
     * @param stu
     * @return
     */
    public static int insert_part(Student stu)
    {
        String sql="insert into S(Sno,Sname,Sex,Birth) values(?,?,?,?)";
        String sno=stu.getSno();
        String sname=stu.getSname();
        String sex=stu.getSex();
        int age=stu.getAge();
        return DBUtil.excuteDml(sql,sno,sname,sex,age);
    }


    /**
     * 删除学生
     * @param id
     * @return
     */
    public static int del_stu(String id)
    {
        String sql="delete from S where Sno=?";
        int i=DBUtil.excuteDml(sql,id);
        return i;
    }

    /**
     * 修改学生 先删除原来的学生再增加学生
     */
    public static int update_stu(Student stu,String id)
    {
        String sql="update S set Sno=?,Sname=?,Sex=?,Birth=?,Dept=?,Major=? where sno=?";
        return DBUtil.excuteDml(sql,stu.getSno(),stu.getSname(),stu.getSex(),stu.getAge(),stu.getDept(),stu.getMajor(),id);
    }

    /**
     * 获取当前学生的专业
     */
    public static String get_major(String id)
    {
        String sql="select Major from S where Sno=?";
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
     * 判断学生信息是否已完善
     */
    public static boolean is_complete(String id)
    {
        String sql="select * from S where Sno=?";
        Connection conn=DBUtil.get_Connection();
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,id);
            rs=ps.executeQuery();
            Object dept;
            if(rs.next())
            {
                dept=rs.getObject(5);
                return dept!=null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        return false;
    }

}
