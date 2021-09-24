package Service;

import Dao.CourseDaoImpl;
import Entity.Course;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:07
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class CourseService {
    /**
     * 增加课程
     * @param cno
     * @param cname
     * @param ct
     * @param time
     * @param tno
     * @param location
     * @return
     */
    public static int insert_course(String cno,String cname,double ct,int time,String tno,String location)
    {
        Course course=new Course(cno,cname,ct,time,tno,location);
        return CourseDaoImpl.add_course(course);
    }
}
