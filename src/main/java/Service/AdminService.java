package Service;

import Dao.AdminDaoImpl;
import Entity.Admin;

import java.util.Iterator;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:07
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class AdminService {

    /**
     * 查询管理员是否存在
     * @param id
     * @return  存在返回false
     */
    public static boolean is_existed_admin(String id,List<Admin> admin_List){
        Iterator<Admin> iterator=admin_List.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getAdmin_id().equals(id))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 查询管理员是否合法
     * @param id
     * @return  合法返回true
     */
    public static boolean is_legal_admin(String id,String pass,List<Admin> admin_List){
        Iterator<Admin> iterator=admin_List.iterator();
        while(iterator.hasNext()){
            Admin admin=iterator.next();
            if(admin.getAdmin_id().trim().equals(id.trim())&&admin.getAdmin_pass().trim().equals(pass.trim()))
            {
                return true;
            }
        }
        return false;
    }
    public static int insert_admin(String username,String password){
        Admin admin=new Admin(username,password);
        return AdminDaoImpl.intsert_admin(admin);
    }
}
