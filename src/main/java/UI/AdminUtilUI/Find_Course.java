package UI.AdminUtilUI;

import Dao.CourseDaoImpl;
import Dao.SCDaoImpl;
import Dao.StuDaoImpl;
import Entity.Student;
import Service.CourseService;
import Service.StuService;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/27-6:52
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Find_Course extends JFrame {
    private JLabel top_img; //顶部图
    private JLabel query;   //查询按钮
    private JLabel clear;   //清除按钮
    private JLabel tip; //提示
    private JLabel show_all;    //显示全部课程
    private JTextField condition;   //输入框
    private JPanel jp1; //表格面板
    private JTable jTable;  //表格
    private JLabel exit_btn;    //退出页面
    private JTable empty_tb;    //空表

    private String[] columnNames={"学号", "姓名", "性别", "年龄", "学院", "专业"};
    private Object[][] rowData = {};
    private List<Student> studentList=new ArrayList<>();
    //不同字体
    private Font font2= StyleUI.font2; //单选框
    List<Student> all=StuDaoImpl.query_stu();;
    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    private static final String COURSE_MATCH="^[A-Z]\\d{6}$";    //匹配课程号   大写字母+6位数字

    private void init_data(String cno)
    {
        if(CourseDaoImpl.query_cno_course(cno)==null)
        {
            rowData=new Object[][]{};
            JOptionPane.showMessageDialog(this,"您输入的课程号不存在，请检查输入","错误提示",JOptionPane.PLAIN_MESSAGE);
        }else{
            studentList= SCDaoImpl.query_all_stu(cno);
            Iterator<Student> iterator=studentList.iterator();
            rowData=new Object[studentList.size()][6];
            int i=0;
            while(iterator.hasNext())
            {
                Student stu=iterator.next();
                rowData[i][0]=stu.getSno();
                rowData[i][1]=stu.getSname();
                rowData[i][2]=stu.getSex();
                rowData[i][3]=stu.getAge();
                rowData[i][4]=stu.getDept();
                rowData[i][5]=stu.getMajor();
                i++;
            }
        }
        update_table();
    }

    private void init_all(){
        Iterator<Student> iterator=all.iterator();
        rowData=new Object[all.size()][6];
        int i=0;
        while(iterator.hasNext())
        {
            Student stu=iterator.next();
            rowData[i][0]=stu.getSno();
            rowData[i][1]=stu.getSname();
            rowData[i][2]=stu.getSex();
            rowData[i][3]=stu.getAge();
            rowData[i][4]=stu.getDept();
            rowData[i][5]=stu.getMajor();
            i++;
        }
        update_table();
    }
    private void update_table(){
        jTable=new JTable(rowData,columnNames);
    }
    /**
     * 监听按钮事件
     */
    private class MyBtnLisen extends MouseAdapter {
        JFrame jf;
        JPanel jp1;
        public MyBtnLisen(JFrame jf, JPanel jp1){
            this.jf=jf;
            this.jp1=jp1;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource()==exit_btn)
            {
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                }
            }else if(e.getSource()==query){
                String cond=condition.getText();
                if(cond.equals(""))
                {
                    JOptionPane.showConfirmDialog(jf,"您还未输入课程号","提示窗口",JOptionPane.YES_NO_OPTION);
                    return;
                }else
                {
                    if(!Pattern.matches(COURSE_MATCH,cond))
                    {
                        JOptionPane.showConfirmDialog(jf,"您的课程号输入格式不正确，需大写字母加6位数字    如E123456？","错误提示",JOptionPane.YES_NO_OPTION);
                        return;
                    }
                    init_data(cond);
                    jp1.removeAll();
                    jp1.add(jTable.getTableHeader(),BorderLayout.NORTH);
                    jp1.add(new JScrollPane(jTable),BorderLayout.CENTER);
                    jp1.updateUI();
                }
            }else if(e.getSource()==clear){
                jp1.removeAll();
                jp1.add(empty_tb.getTableHeader(),BorderLayout.NORTH);
                jp1.add(new JScrollPane(empty_tb),BorderLayout.CENTER);
                jp1.updateUI();
                condition.setText("");
            }else if(e.getSource()==show_all){
                init_all();
                jp1.removeAll();
                jp1.add(jTable.getTableHeader(),BorderLayout.NORTH);
                jp1.add(new JScrollPane(jTable),BorderLayout.CENTER);
                jp1.updateUI();
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
     * 构造函数 展示窗体
     */
    public Find_Course(){
        Container container=this.getContentPane();
        container.setLayout(null);
        init();
        top_img.setBounds(0,0,800,160);
        tip.setBounds(80,165,200,20);
        condition.setBounds(300,165,150,20);
        query.setBounds(500,165,40,20);
        clear.setBounds(600,165,40,20);
        show_all.setBounds(690,165,90,20);

        jp1.setBounds(0,200,800,460);
        exit_btn.setBounds(0,660,800,20);
        MyBtnLisen myBtnLisen=new MyBtnLisen(this,jp1);
        query.addMouseListener(myBtnLisen);
        clear.addMouseListener(myBtnLisen);
        exit_btn.addMouseListener(myBtnLisen);
        show_all.addMouseListener(myBtnLisen);

        container.add(top_img);
        container.add(tip);
        container.add(condition);
        container.add(query);
        container.add(clear);
        container.add(show_all);
        container.add(exit_btn);
        JScrollPane jsp=new JScrollPane(jTable);

        jp1.add(jTable.getTableHeader(),BorderLayout.NORTH);
        jp1.add(jsp,BorderLayout.CENTER);

        container.add(jp1);

        setTitle("查询课程选课情况");
        setBackground(Color.LIGHT_GRAY);
        setSize(820,720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * 初始化组件
     */

    public void init(){
        Icon icon=new ImageIcon("src\\main\\resources\\find_course.jpg");
        top_img=new JLabel();
        top_img.setIcon(icon);
        query=new JLabel("查询",SwingConstants.CENTER);
        clear=new JLabel("清空",SwingConstants.CENTER);
        tip=new JLabel("请输入课程号查询已选学生",SwingConstants.CENTER);
        condition=new JTextField(20);
        jp1=new JPanel(new BorderLayout());
        jTable=new JTable(rowData,columnNames);
        empty_tb=new JTable(rowData,columnNames);
        show_all=new JLabel("显示全部学生");
        show_all.setFont(font2);
        query.setFont(font2);
        clear.setFont(font2);
        tip.setFont(StyleUI.font2);
        exit_btn=new JLabel("退出",SwingConstants.CENTER);
        exit_btn.setOpaque(true);
        query.setOpaque(true);
        clear.setOpaque(true);
        show_all.setOpaque(true);
        show_all.setBackground(color1);
        exit_btn.setBackground(color1);
        query.setBackground(color1);
        clear.setBackground(color1);

        show_all.setForeground(Color.white);
        exit_btn.setForeground(Color.white);
        query.setForeground(Color.white);
        clear.setForeground(Color.white);
    }
}
