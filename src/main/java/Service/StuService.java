package Service;

import Dao.StuDaoImpl;
import Entity.Student;

import java.util.Iterator;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:07
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class StuService {

    /**
     * 查询是否存在学生 可以考虑删除 直接查询一个学生看是否返回null
     * @param sno
     * @return
     */
    public static boolean is_existed(String sno)
    {
        return StuDaoImpl.query_a_stu(sno)!=null;
    }

    /**
     * 插入学生
     */
    public static int insert_stu(String sno,String sname,String sex,int age,String dept,String major)
    {
        Student stu=new Student(sno,sname,sex,age,dept,major);
        return StuDaoImpl.add_stu(stu);
    }

    /**
     * 注册时插入学生信息
     */
    public static int insert_part(String sno,String sname,String sex,int age)
    {
        Student stu=new Student(sno,sname,sex,age,"","");
        return StuDaoImpl.insert_part(stu);
    }

}
