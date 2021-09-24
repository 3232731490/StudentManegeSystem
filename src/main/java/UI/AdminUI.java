package UI;

import UI.AdminUtilUI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author: 倪路
 * Time: 2021/6/26-12:07
 * StuNo: 1910400731
 * Class: 19104221
 * Description: 管理员页面
 *              功能： 查看学生选课信息
 *                    查看课程有哪些学生选
 *                    删除学生信息
 *                    删除课程信息
 *                    增加课程信息
 *                    增加学生信息
 */
public class AdminUI extends JFrame {
    private JLabel top_img; //顶部图片
    private JLabel top_tip;     //顶部标题
    private JLabel left_tip;    //左部提示
    private JLabel find_stu_jd;    //查找学生
    private JLabel find_course_jd;    //查找课程
    private JLabel del_stu_jd;    //删除学生
    private JLabel del_course_jd;    //删除课程
    private JLabel add_stu_jd;    //添加学生
    private JLabel add_course_jd;    //添加课程
    private JLabel return_btn;    //返回登陆
    private JLabel Exit_btn;        //退出系统
    private JLabel modify_stu;  //修改学生
    private JLabel modify_course;   //修改课程
    private JLabel del_plan;        //增加计划
    private JLabel add_plan;        //删除计划
    //不同字体
    private Font font=StyleUI.font;  //标题字体
    private Font font1=StyleUI.font1; //输入提示
    private Font font2=StyleUI.font2; //单选框
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
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
            if(e.getSource()==find_course_jd){
                new Find_Course();
            }else if(e.getSource()==find_stu_jd){
                new Find_Stu();
            }else if(e.getSource()==del_course_jd){
                new Del_Course();
            }else if(e.getSource()==del_stu_jd){
                new Del_Stu();
            }else if(e.getSource()==add_course_jd){
                new Add_Course();
            }else if(e.getSource()==add_stu_jd){
                new Add_Stu();
            }else if(e.getSource()==modify_course){
                new Modify_Course();
            }else if(e.getSource()==modify_stu){
                new Modify_Stu();
            }else if(e.getSource()==add_plan){
                new Add_Plan();
            }else if(e.getSource()==del_plan){
                new Del_Plan();
            } else if(e.getSource()==return_btn){
                if(JOptionPane.showConfirmDialog(jf,"是否确认返回登陆页面？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                    new LoginUI();
                }
            }else if(e.getSource()==Exit_btn){
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出系统？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                    System.exit(0);
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
     * 构造函数 展示窗体
     */
    public AdminUI(){
        Container container=this.getContentPane();

        container.setLayout(null);
        init();
        JPanel jPanel=new JPanel(null);

        top_img.setBounds(0,5,800,100);
        top_tip.setBounds(0,110,800,40);
        left_tip.setBounds(0,200,200,40);
        jPanel.setBounds(0,155,800,540);

        find_course_jd.setBounds(105,113,100,50);
        find_stu_jd.setBounds(330,113,100,50);
        modify_course.setBounds(555,113,100,50);
        modify_stu.setBounds(105,211,100,50);
        add_course_jd.setBounds(330,211,100,50);
        add_stu_jd.setBounds(555,211,100,50);
        del_course_jd.setBounds(105,309,100,50);
        del_stu_jd.setBounds(330,309,100,50);
        add_plan.setBounds(555,309,100,50);
        del_plan.setBounds(105,417,100,50);
        return_btn.setBounds(330,417,100,50);
        Exit_btn.setBounds(555,417,100,50);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        find_stu_jd.addMouseListener(myBtnLisen);
        find_course_jd.addMouseListener(myBtnLisen);
        modify_course.addMouseListener(myBtnLisen);
        modify_stu.addMouseListener(myBtnLisen);
        add_course_jd.addMouseListener(myBtnLisen);
        add_stu_jd.addMouseListener(myBtnLisen);
        del_course_jd.addMouseListener(myBtnLisen);
        del_stu_jd.addMouseListener(myBtnLisen);
        return_btn.addMouseListener(myBtnLisen);
        Exit_btn.addMouseListener(myBtnLisen);
        add_plan.addMouseListener(myBtnLisen);
        del_plan.addMouseListener(myBtnLisen);

        jPanel.add(find_course_jd);
        jPanel.add(find_stu_jd);
        jPanel.add(modify_course);
        jPanel.add(modify_stu);
        jPanel.add(add_course_jd);
        jPanel.add(add_stu_jd);
        jPanel.add(del_course_jd);
        jPanel.add(del_stu_jd);
        jPanel.add(return_btn);
        jPanel.add(Exit_btn);
        jPanel.add(add_plan);
        jPanel.add(del_plan);

        container.add(left_tip);
        container.add(top_img);
        container.add(top_tip);
        container.add(jPanel);

        setTitle("管理员系统");
        setBackground(Color.LIGHT_GRAY);
        setSize(800,700);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * 初始化组件
     */
    private void init(){
        find_stu_jd=new JLabel("查找课程",SwingConstants.CENTER);
        find_course_jd=new JLabel("查找学生",SwingConstants.CENTER);
        del_stu_jd=new JLabel("删除学生",SwingConstants.CENTER);
        del_course_jd=new JLabel("删除课程",SwingConstants.CENTER);
        add_stu_jd=new JLabel("增加学生",SwingConstants.CENTER);
        add_course_jd=new JLabel("增加课程",SwingConstants.CENTER);
        Exit_btn=new JLabel("退出系统",SwingConstants.CENTER);
        return_btn=new JLabel("返回登陆",SwingConstants.CENTER);
        modify_course=new JLabel("修改课程",SwingConstants.CENTER);
        modify_stu=new JLabel("修改学生",SwingConstants.CENTER);
        del_plan=new JLabel("删除计划",SwingConstants.CENTER);
        add_plan=new JLabel("增加计划",SwingConstants.CENTER);

        left_tip=new JLabel("请选择您要使用的功能",SwingConstants.CENTER);

        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\sit.jpg");
        top_img.setIcon(icon);
        top_tip=new JLabel("您好，尊贵的管理员",SwingConstants.CENTER);
        modify_stu.setOpaque(true);

        left_tip.setFont(font4);
        del_plan.setFont(font3);
        add_plan.setFont(font3);
        modify_course.setFont(font3);
        modify_stu.setFont(font3);
        Exit_btn.setFont(font3);
        return_btn.setFont(font3);
        find_stu_jd.setFont(font3);
        find_course_jd.setFont(font3);
        del_stu_jd.setFont(font3);
        del_course_jd.setFont(font3);
        add_stu_jd.setFont(font3);
        add_course_jd.setFont(font3);
        top_tip.setFont(font);

        del_plan.setOpaque(true);
        add_plan.setOpaque(true);
        modify_course.setOpaque(true);
        Exit_btn.setOpaque(true);
        return_btn.setOpaque(true);
        find_stu_jd.setOpaque(true);
        find_course_jd.setOpaque(true);
        del_stu_jd.setOpaque(true);
        del_course_jd.setOpaque(true);
        add_stu_jd.setOpaque(true);
        add_course_jd.setOpaque(true);

        modify_course.setBackground(color1);
        modify_stu.setBackground(color1);
        Exit_btn.setBackground(color1);
        return_btn.setBackground(color1);
        find_stu_jd.setBackground(color1);
        find_course_jd.setBackground(color1);
        del_stu_jd.setBackground(color1);
        del_course_jd.setBackground(color1);
        add_stu_jd.setBackground(color1);
        add_course_jd.setBackground(color1);
        del_plan.setBackground(color1);
        add_plan.setBackground(color1);

        modify_stu.setForeground(Color.white);
        del_plan.setForeground(Color.WHITE);
        add_plan.setForeground(Color.white);
        modify_course.setForeground(Color.white);
        Exit_btn.setForeground(Color.WHITE);
        return_btn.setForeground(Color.white);
        find_stu_jd.setForeground(Color.white);
        find_course_jd.setForeground(Color.white);
        del_stu_jd.setForeground(Color.white);
        del_course_jd.setForeground(Color.white);
        add_stu_jd.setForeground(Color.white);
        add_course_jd.setForeground(Color.white);

    }
}
