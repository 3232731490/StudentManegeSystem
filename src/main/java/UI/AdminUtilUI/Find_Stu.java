package UI.AdminUtilUI;

import Dao.CourseDaoImpl;
import Dao.SCDaoImpl;
import Entity.Course;
import Entity.Student;
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
 * Time: 2021/6/26-17:59
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Find_Stu extends JFrame {
    JLabel top_img; //顶部图
    JLabel query;   //查询按钮
    JLabel clear;   //清除按钮
    JLabel tip; //提示
    JLabel show_all;    //显示全部课程
    JTextField condition;   //输入框
    JPanel jp1; //表格面板
    JTable jTable;  //表格
    JLabel exit_btn;    //退出页面
    JTable empty_tb;    //空表

    String[] columnNames={"课程序号", "课程名", "学分", "学时", "教师编号", "上课地点"};
    Object[][] rowData = {};
    List<Course> all=new ArrayList<>();
    //不同字体
    private Font font2=StyleUI.font2; //单选框

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    private static final String STUNO_MATCH="^(\\d{10})||(\\d{9}(Y|y))$";    //匹配学号 10位数或9位加Y  如1910400731|191040073Y

    private void init_data(String Sno)
    {
        if(!StuService.is_existed(Sno))
        {
            rowData=new Object[][]{};
            JOptionPane.showMessageDialog(this,"您输入的学号不存在，请检查输入","错误提示",JOptionPane.PLAIN_MESSAGE);
        }else{
            all= CourseDaoImpl.query_special_course(Sno);
            Iterator<Course> iterator=all.iterator();
            rowData=new Object[all.size()][6];
            int i=0;
            while(iterator.hasNext())
            {
                Course course=iterator.next();
                rowData[i][0]=course.getCno();
                rowData[i][1]=course.getCname();
                rowData[i][2]=course.getCt();
                rowData[i][3]=course.getTime();
                rowData[i][4]=course.getT_no();
                rowData[i][5]=course.getLocation();
                i++;
            }
        }
        update_table();
    }

    private void init_all(){
        all= CourseDaoImpl.query_all_course();
        Iterator<Course> iterator=all.iterator();
        rowData=new Object[all.size()][6];
        int i=0;
        while(iterator.hasNext())
        {
            Course course=iterator.next();
            rowData[i][0]=course.getCno();
            rowData[i][1]=course.getCname();
            rowData[i][2]=course.getCt();
            rowData[i][3]=course.getTime();
            rowData[i][4]=course.getT_no();
            rowData[i][5]=course.getLocation();
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
                    JOptionPane.showConfirmDialog(jf,"您还未输入学号","提示窗口",JOptionPane.YES_NO_OPTION);
                    return;
                }else
                {
                    if(!Pattern.matches(STUNO_MATCH,cond))
                    {
                        JOptionPane.showConfirmDialog(jf,"您的学号输入格式不正确，需10位数或9位加Y 如1910400731|191040073Y","错误提示",JOptionPane.YES_NO_OPTION);
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
                jp1.removeAll();
                init_all();
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

    public Find_Stu(){
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

        setTitle("查询学生选课");
        setBackground(Color.LIGHT_GRAY);
        setSize(820,720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void init(){
        Icon icon=new ImageIcon("src\\main\\resources\\Find_stu.jpg");
        top_img=new JLabel();
        top_img.setIcon(icon);
        query=new JLabel("查询",SwingConstants.CENTER);
        clear=new JLabel("清空",SwingConstants.CENTER);
        tip=new JLabel("请输入学生学号查询已选课程",SwingConstants.CENTER);
        condition=new JTextField(20);
        jp1=new JPanel(new BorderLayout());
        jTable=new JTable(rowData,columnNames);
        empty_tb=new JTable(rowData,columnNames);
        show_all=new JLabel("显示全部课程");
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
