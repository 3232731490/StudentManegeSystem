package Dao;

import Entity.Course;
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
public class CourseDaoImpl {

    /**
     * 获取所有课程列表
     * @return
     */
    public static List<Course> query_all_course()
    {
        Connection conn= DBUtil.get_Connection();
        String sql="select * from C";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        List<Course> courses=new ArrayList<>();
        try {
            rs=ps.executeQuery();
            String cno;
            String cname;
            double ct;
            String year;
            int time;
            String location;
            while(rs.next())
            {
                cno=rs.getString(1);
                cname=rs.getString(2);
                ct=rs.getDouble(3);
                time=rs.getInt(4);
                year=rs.getString(5);
                location=rs.getString(6);
                Course course=new Course(cno,cname,ct,time,year,location);
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 获取某个学生选课列表
     */
    public static List<Course> query_special_course(String id)
    {
        Connection conn= DBUtil.get_Connection();
        String sql="select * from C where Cno in (select Cno from SC where Sno=?)";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            ps.setString(1,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Course> courses=new ArrayList<>();
        try {
            rs=ps.executeQuery();
            String cno;
            String cname;
            double ct;
            String Tno;
            int time;
            String location;
            while(rs.next())
            {
                cno=rs.getString(1);
                cname=rs.getString(2);
                ct=rs.getDouble(3);
                time=rs.getInt(4);
                Tno=rs.getString(5);
                location=rs.getString(6);
                Course course=new Course(cno,cname,ct,time,Tno,location);
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 查询特定课程号的课程信息
     * @param Cno
     * @return
     */
    public static Course query_cno_course(String Cno)
    {
        Connection conn= DBUtil.get_Connection();
        String sql="select * from C where Cno=?";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            ps.setString(1,Cno);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs=ps.executeQuery();
            String cno;
            String cname;
            double ct;
            String Tno;
            int time;
            String location;
            Course course=null;
            while(rs.next())
            {
                cno=rs.getString(1);
                cname=rs.getString(2);
                ct=rs.getDouble(3);
                time=rs.getInt(4);
                Tno=rs.getString(5);
                location=rs.getString(6);
                course=new Course(cno,cname,ct,time,Tno,location);
            }
            return course;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 查询特定课程名的课程信息
     * @param Cname
     * @return
     */
    public static List<Course> query_cname_course(String Cname)
    {
        Connection conn= DBUtil.get_Connection();
        List<Course> courses=new ArrayList<>();
        String sql="select * from C where Cname=?";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            ps.setString(1,Cname);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs=ps.executeQuery();
            String cno;
            String cname;
            double ct;
            String Tno;
            int time;
            String location;
            Course course=null;
            while(rs.next())
            {
                cno=rs.getString(1);
                cname=rs.getString(2);
                ct=rs.getDouble(3);
                time=rs.getInt(4);
                Tno=rs.getString(5);
                location=rs.getString(6);
                course=new Course(cno,cname,ct,time,Tno,location);
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 删除指定课程
     * @param cno
     * @return
     */
    public static int del_course(String cno)
    {
        String sql="delete from C where Cno=?";
        return DBUtil.excuteDml(sql,cno);
    }

    /**
     * 增加课程
     * @param course
     * @return
     */
    public static int add_course(Course course)
    {
        String cno=course.getCno();
        String cname=course.getCname();
        double ct=course.getCt();
        String Tno=course.getT_no();
        int time=course.getTime();
        String location=course.getLocation();

        String sql="insert into C(Cno,Cname,CT,TNO,TIME,Addresss) values(?,?,?,?,?,?)";
        return DBUtil.excuteDml(sql,cno,cname,ct,Tno,time,location);
    }

    /**
     * 更新课程
     * @param course    更新后的课程
     * @param cno   课程id
     * @return
     */
    public static int update_course(Course course,String cno)
    {
        String sql="update C set cno=?,Cname=?,CT=?,Tno=?,TIME=?,Address=? where cno=?";
        return DBUtil.excuteDml(sql,course.getCno(),course.getCname(),course.getCt(),course.getT_no(),course.getTime(),course.getLocation(),cno);
    }

}
