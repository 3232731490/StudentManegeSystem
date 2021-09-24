package Dao;

import Entity.Audience;
import MyUtil.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/28-15:35
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class AudienceDaoImpl {

    /**
     * 查询是否存在计划id的计划
     * @param id
     * @return
     */
    public static boolean is_existed(String id)
    {
        Connection conn= DBUtil.get_Connection();
        String sql="select * from Audience where plan_id=?";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            ps.setString(1,id);
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
    public static List<Audience> query_all()
    {
        List<Audience> all=new ArrayList<>();
        Connection conn= DBUtil.get_Connection();
        String sql="select * from Audience";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            rs=ps.executeQuery();
            String planid;
            String dept;
            String major;
            String syear;
            String sema;
            while(rs.next())
            {
                planid=rs.getString(1);
                dept=rs.getString(2);
                major=rs.getString(3);
                syear=rs.getString(4);
                sema=rs.getString(5);
                Audience audience=new Audience(planid,dept,major,syear,sema);
                all.add(audience);
            }
            return all;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }
    /**
     * 插入计划归属
     * @param audience
     * @return
     */
    public static int insert_audience(Audience audience)
    {
        String plan_id=audience.getPlan_id();
        String dept=audience.getDept();
        String major=audience.getMajor();
        String year=audience.getYear();
        String sme=audience.getSemester();
        String sql="insert into Audience(Plan_id,Dept,Major,Syear,Semester) values(?,?,?,?,?)";
        return DBUtil.excuteDml(sql,plan_id,dept,major,year,sme);
    }
    /**
     * 删除计划归属
     */
    public static int del_audience(String id)
    {
        String sql="delete from Audience where Plan_id=?";
        return DBUtil.excuteDml(sql,id);
    }

    /**
     * 获得计划归属的专业
     */
    public static String get_major(String id)
    {
        String sql="select * from Audience where plan_id=?";
        Connection conn= DBUtil.get_Connection();
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        ResultSet rs=null;
        try {
            ps.setString(1,id);
            rs=ps.executeQuery();
            rs.next();
            return rs.getString(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

}
