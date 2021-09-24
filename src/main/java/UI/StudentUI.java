package UI;

import Dao.StuDaoImpl;
import UI.AdminUtilUI.*;
import UI.StudentUtilUI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author: 倪路
 * Time: 2021/6/26-12:06
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class StudentUI extends JFrame {
    private String id;  //当前学生id
    private JLabel top_img; //顶部图片
    private JLabel top_tip;     //顶部标题
    private JLabel left_tip;    //左部提示
    private JLabel bottom_img;  //底部图片  视情况可以删除

    private JLabel check_course;    //查看选课
    private JLabel del_Course_jd;    //退课
    private JLabel add_Course;   //选课
    private JLabel modyfy_user;  //修改用户
    private JLabel return_btn;    //返回登陆
    private JLabel Exit_btn;        //退出系统
    private JLabel all_course;    //一键选课
    private JLabel check_plan;    //查看计划归属
    private JLabel complete_info;   //完善学生信息
    private JLabel complete_tip;    //提示完善信息
    MyBtnLisen myBtnLisen=new MyBtnLisen(this);

    //不同字体
    private Font font=StyleUI.font;  //标题字体
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
            if(e.getSource()==del_Course_jd){
                new Stu_Del_Course(id);
            }else if(e.getSource()==check_course){
                new Check_Course(id);
            }else if(e.getSource()==add_Course){
                new Stu_Add_Course(id);
            }else if(e.getSource()==modyfy_user){
                new Modify_User(id);
            }else if(e.getSource()==all_course){
                new All_Course(id);
            }else if(e.getSource()==check_plan){
                new CheckPlan();
            }else if(e.getSource()==complete_info){
                new Complete_Info(id);
            }else if(e.getSource()==return_btn){
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
     * 构造方法 展示窗体
     */
    public StudentUI(String id){
        this.id=id;
        Container container=this.getContentPane();

        container.setLayout(null);
        init();
        JPanel jPanel=new JPanel(null);

        top_img.setBounds(0,5,800,100);
        top_tip.setBounds(0,110,800,40);
        left_tip.setBounds(0,200,200,40);
        complete_tip.setBounds(0,160,800,30);
        jPanel.setBounds(0,155,800,540);

        check_course.setBounds(105,113,100,50);
        del_Course_jd.setBounds(330,113,100,50);
        add_Course.setBounds(555,113,100,50);
        modyfy_user.setBounds(105,211,100,50);


        all_course.setBounds(330,211,100,50);
        Exit_btn.setBounds(555,309,100,50);
        complete_info.setBounds(105,309,100,50);
        check_plan.setBounds(555,211,100,50);
        return_btn.setBounds(330,309,100,50);
        bottom_img.setBounds(0,380,800,130);

        return_btn.addMouseListener(myBtnLisen);
        Exit_btn.addMouseListener(myBtnLisen);

        jPanel.add(all_course);
        jPanel.add(del_Course_jd);
        jPanel.add(check_course);
        jPanel.add(add_Course);
        jPanel.add(modyfy_user);
        jPanel.add(check_plan);
        jPanel.add(return_btn);
        jPanel.add(Exit_btn);
        jPanel.add(bottom_img);
        jPanel.add(complete_info);

        container.add(complete_tip);
        container.add(left_tip);
        container.add(top_img);
        container.add(top_tip);
        container.add(jPanel);

        setTitle("学生选课系统");
        setBackground(Color.LIGHT_GRAY);
        setSize(800,700);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * 判断是否已完善信息，初始化组件是否可用
     */
    private void init_com()
    {
        if(StuDaoImpl.is_complete(id))
        {
            complete_info.setEnabled(false);
            check_course.setEnabled(true);
            add_Course.setEnabled(true);
            modyfy_user.setEnabled(true);
            all_course.setEnabled(true);
            check_plan.setEnabled(true);
            del_Course_jd.setEnabled(true);
            complete_tip.setText("");
            complete_info.removeMouseListener(myBtnLisen);
            check_course.addMouseListener(myBtnLisen);
            add_Course.addMouseListener(myBtnLisen);
            modyfy_user.addMouseListener(myBtnLisen);
            all_course.addMouseListener(myBtnLisen);
            check_plan.addMouseListener(myBtnLisen);
            del_Course_jd.addMouseListener(myBtnLisen);
            complete_info.removeMouseListener(myBtnLisen);
        }
        else
        {
            check_course.setEnabled(false);
            add_Course.setEnabled(false);
            modyfy_user.setEnabled(false);
            all_course.setEnabled(false);
            check_plan.setEnabled(false);
            del_Course_jd.setEnabled(false);

            complete_info.setEnabled(true);

            complete_info.addMouseListener(myBtnLisen);
            check_course.removeMouseListener(myBtnLisen);
            add_Course.removeMouseListener(myBtnLisen);
            modyfy_user.removeMouseListener(myBtnLisen);
            all_course.removeMouseListener(myBtnLisen);
            check_plan.removeMouseListener(myBtnLisen);
            del_Course_jd.removeMouseListener(myBtnLisen);
        }
    }
    /**
     * 初始化组件
     */
    private void init(){
        check_course=new JLabel("选课情况",SwingConstants.CENTER);
        del_Course_jd=new JLabel("退选课程",SwingConstants.CENTER);
        check_plan=new JLabel("查看计划",SwingConstants.CENTER);
        Exit_btn=new JLabel("退出系统",SwingConstants.CENTER);
        return_btn=new JLabel("返回登陆",SwingConstants.CENTER);
        all_course=new JLabel("一键选课",SwingConstants.CENTER);
        complete_info=new JLabel("完善信息",SwingConstants.CENTER);
        add_Course=new JLabel("选修课程",SwingConstants.CENTER);
        modyfy_user=new JLabel("修改密码",SwingConstants.CENTER);
        left_tip=new JLabel("请选择您要使用的功能",SwingConstants.CENTER);
        complete_tip=new JLabel("要使用全部功能，请完善您的信息",SwingConstants.CENTER);

        bottom_img=new JLabel();
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\bottom.jpg");
        top_img.setIcon(icon);
        icon=new ImageIcon("src\\main\\resources\\Student.jpg");
        bottom_img.setIcon(icon);

        top_tip=new JLabel("您好，欢迎使用选课系统",SwingConstants.CENTER);
        modyfy_user.setOpaque(true);

        complete_tip.setFont(font4);
        left_tip.setFont(font4);
        complete_info.setFont(font3);
        add_Course.setFont(font3);
        all_course.setFont(font3);
        modyfy_user.setFont(font3);
        Exit_btn.setFont(font3);
        return_btn.setFont(font3);
        check_course.setFont(font3);
        del_Course_jd.setFont(font3);
        check_plan.setFont(font3);
        top_tip.setFont(font);

        complete_info.setOpaque(true);
        all_course.setOpaque(true);
        add_Course.setOpaque(true);
        Exit_btn.setOpaque(true);
        return_btn.setOpaque(true);
        check_course.setOpaque(true);
        del_Course_jd.setOpaque(true);
        check_plan.setOpaque(true);

        complete_tip.setForeground(Color.RED);
        complete_info.setBackground(color1);
        all_course.setBackground(color1);
        add_Course.setBackground(color1);
        modyfy_user.setBackground(color1);
        Exit_btn.setBackground(color1);
        return_btn.setBackground(color1);
        check_course.setBackground(color1);
        del_Course_jd.setBackground(color1);
        check_plan.setBackground(color1);

        complete_info.setForeground(Color.white);
        all_course.setForeground(Color.white);
        modyfy_user.setForeground(Color.white);
        add_Course.setForeground(Color.white);
        Exit_btn.setForeground(Color.WHITE);
        return_btn.setForeground(Color.white);
        check_course.setForeground(Color.white);
        del_Course_jd.setForeground(Color.white);
        check_plan.setForeground(Color.white);
        init_com();
    }

}
