package Dao;

import Entity.Course;
import Entity.Plan;
import MyUtil.DBUtil;

import javax.swing.text.StyledEditorKit;
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
public class PlanDaoImpl {

    /**
     * 获取计划id的所有课程号
     */
    public static List<String> query_all_course(String id)
    {
        List<String> courses=new ArrayList<>();
        Connection conn= DBUtil.get_Connection();
        ResultSet rs=null;
        String sql="select Cno from SC_plan where plan_id=?";
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,id);
            rs=ps.executeQuery();

            while(rs.next())
            {
                courses.add(rs.getString(1));
            }
            return courses;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        return null;
    }

    /**
     * 插入计划
     * @param plan
     * @return
     */
    public static int insert_plan(Plan plan)
    {
        String id=plan.getPlan_id();
        String cno=plan.getC_id();
        String sql="insert into SC_Plan(Plan_id,Cno) values(?,?)";
        return DBUtil.excuteDml(sql,id,cno);
    }

    /**
     * 查询某个计划是否存在
     */
    public static boolean is_existed(String id,String cno)
    {
        String sql="select * from SC_Plan where plan_id=? and Cno=?";
        Connection conn=DBUtil.get_Connection();
        ResultSet rs=null;
        PreparedStatement ps=DBUtil.get_PrepareedStatement(conn,sql);
        try {
            ps.setString(1,id);
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
     * 删除计划
     */
    public static int del_plan(String id)
    {
        String sql="delete from SC_Plan where plan_id=?";
        return DBUtil.excuteDml(sql,id);
    }

    /**
     * 更新学号
     */
    public static int update_cno(String oldcno,String newcno){
         String sql="update SC_Plan set cno=? where cno=?";
         return  DBUtil.excuteDml(sql,newcno,oldcno);
    }
}
