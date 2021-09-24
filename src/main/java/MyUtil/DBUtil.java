package MyUtil;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * @author: 倪路
 * Time: 2021/6/27-18:31
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class DBUtil {
    public static String DRIVER;
    public static String URL;
    public static String USERNAME;
    public static String PASSWORD;
    public static final String filename = "src/main/resources/Base.properties";

    static{
        // 1.从配置文件获取四大金刚
        // Properties类专门用于读取扩展名为properties的配置文件
        Properties prop = new Properties();
        File file=new File(filename);
        InputStream is= null;
        try {
            is = new FileInputStream(file);
            prop.load(is);
            DRIVER = prop.getProperty("DRIVER");
            URL = prop.getProperty("URL");
            USERNAME = prop.getProperty("USERNAME");
            PASSWORD = prop.getProperty("PASSWORD");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                System.out.println("【" + filename + "】不存在!");
            }
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return  返回连接对象
     */
    public static  Connection get_Connection() {
        Connection conn=null;
        try {
            conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取预处理通道
     * @param conn  连接
     * @param sql   sql语句
     * @return  返回预处理通道
     */
    public static PreparedStatement get_PrepareedStatement(Connection conn,String sql){
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ps;
    }

    /**
     * 关闭所有数据库操作对象
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
            // 不为空，才能调用close()
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

    }

    public static void close(Connection conn, Statement stmt) {
        // 及时释放三大对象
        close(conn, stmt, null);
    }

    public static void close(Connection conn) {
        // 及时释放三大对象
        close(conn, null, null);
    }

    /**
     * 封装DML操作  增删改
     * @param sql   sql语句
     * @param objs  具体值
     * @return  返回影响数据库条数
     */
    public static int excuteDml(String sql,Object...objs){

        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn= get_Connection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for(int i=0;i<objs.length;i++){
                ps.setObject(i+1,objs[i]);
            }
            int i=ps.executeUpdate();
            conn.commit();
            return i;
        } catch (SQLException e) {  //发生异常回滚
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            close(conn,ps,null);
        }
        return 0;
    }
}
