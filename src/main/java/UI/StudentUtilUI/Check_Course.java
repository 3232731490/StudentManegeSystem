package UI.StudentUtilUI;

import Dao.CourseDaoImpl;
import Entity.Course;
import Service.CourseService;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-12:33
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Check_Course extends JFrame {
    private JLabel top_img; //顶部图
    private JLabel tip; //提示
    private JPanel jp1; //表格面板
    private JTable jTable;  //表格
    private JLabel exit_btn;    //退出页面
    private String id; //当前学生

    private String[] columnNames={"课程序号", "课程名", "学分", "学时", "教师编号", "上课地点"};
    private Object[][] rowData = null;

    //不同字体
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    /**
     * 监听按钮事件
     */
    private class MyBtnLisen extends MouseAdapter {
        JFrame jf;
        public MyBtnLisen(JFrame jf){
            this.jf=jf;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource()==exit_btn)
            {
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                }
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel jl= (JLabel)e.getSource();
            jl.setBackground(color2);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel jl= (JLabel)e.getSource();
            jl.setBackground(color1);
        }
    }

    /**
     * 构造方法 展示窗体
     */
    public Check_Course(String id){
        this.id=id;
        Container container=this.getContentPane();
        container.setLayout(null);
        init();
        top_img.setBounds(5,5,795,150);
        tip.setBounds(5,160,795,40);
        jp1.setBounds(5,200,795,430);
        exit_btn.setBounds(5,640,795,30);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        exit_btn.addMouseListener(myBtnLisen);

        jp1.add(jTable.getTableHeader(),BorderLayout.NORTH);
        jp1.add(new JScrollPane(jTable),BorderLayout.CENTER);

        container.add(top_img);
        container.add(tip);
        container.add(jp1);
        container.add(exit_btn);

        setTitle("选课信息");
        setBackground(Color.LIGHT_GRAY);
        setSize(820,710);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void init_data()
    {
        List<Course> courses= CourseDaoImpl.query_special_course(id);
        rowData=new Object[courses.size()][6];
        Iterator<Course> iterator=courses.iterator();
        int i=0;
        while(iterator.hasNext())
        {
            Course cur=iterator.next();
            rowData[i][0]=cur.getCno();
            rowData[i][1]=cur.getCname();
            rowData[i][2]=cur.getCt();
            rowData[i][3]=cur.getTime();
            rowData[i][4]=cur.getT_no();
            rowData[i][5]=cur.getLocation();
            i++;
        }
    }

    /**
     * 初始化组件
     */
    private void init(){
        Icon icon=new ImageIcon("src\\main\\resources\\checkcourse.jpg");
        top_img=new JLabel();
        top_img.setIcon(icon);
        init_data();
        jTable=new JTable(rowData,columnNames);
        jp1=new JPanel(new BorderLayout());
        exit_btn=new JLabel("退出",SwingConstants.CENTER);
        tip=new JLabel("您的选课情况如下",SwingConstants.LEFT);
        tip.setFont(font4);
        exit_btn.setOpaque(true);
        exit_btn.setFont(font3);
        exit_btn.setBackground(color1);
        exit_btn.setForeground(Color.white);
        tip.setForeground(color1);
    }
}
