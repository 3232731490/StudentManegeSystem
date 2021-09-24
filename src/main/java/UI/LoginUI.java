package UI;


import Dao.AdminDaoImpl;
import Dao.UserDaoImpl;
import Entity.Admin;
import Entity.User;
import Service.AdminService;
import Service.UserService;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/25-16:08
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class LoginUI extends JFrame {

    private JLabel jLabel1; //顶部图片显示
    private JLabel name_lb;    //账号;
    private JLabel password_lb;    //密码
    private JLabel welcom;  //欢迎
    private JLabel submi_lb;  //登录
    private JLabel register_lb;    //注册
    private JTextField name_jt; //输入密码
    private JPasswordField password_jt; //输入密码
    private  JPanel jp1; //账号密码面板
    private JRadioButton student;   //学生
    private JRadioButton admin; //管理员
    private ButtonGroup buttonGroup;    //单选框分组
    private int curselected=-1;    //当前角色
    //不同字体
    private Font font=StyleUI.font;  //标题字体
    private Font font1=StyleUI.font1; //输入提示
    private Font font2=StyleUI.font2; //单选框
    private Font font3=StyleUI.font3; //按钮

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    final static int SUCCESS=0;     //输入正确
    final static int USER_ERROR=1;  //账号输入有误
    final static int PASS_ERROR=2;  //密码输入有误
    final static int ROLE_ERROR=3;  //身份信息未选择

    final static String USER_MATCH="^\\d{10}$";  //匹配账号
    final static String PASS_MATCH="^[a-zA-Z]\\w{5,17}$";  //匹配密码

    List<User> student_List=new ArrayList<>();
    List<Admin> admin_List=new ArrayList<>();
    /**
     * 检查用户输入是否合法
     * @return  返回错误信息
     */
    private int check()
    {
        String username=name_jt.getText();
        String password=new String(password_jt.getPassword());
        boolean selected=curselected!=-1;
        if(!Pattern.matches(USER_MATCH,username))
        {
            return USER_ERROR;
        }else if(!Pattern.matches(PASS_MATCH,password))
        {
            return PASS_ERROR;
        }else if(!selected)
        {
            return ROLE_ERROR;
        }
        return SUCCESS;
    }

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
            if(e.getSource()==submi_lb)
            {
                int result=check();
                String id=name_jt.getText();
                String pass=new String(password_jt.getPassword());
                if(result==SUCCESS)
                {
                    if(curselected==1)
                    {
                        if(UserService.is_legal_user(id,pass,student_List))
                        {
                            JOptionPane.showConfirmDialog(jf,"登录成功!!","成功登录",JOptionPane.PLAIN_MESSAGE);
                            jf.dispose();
                            new StudentUI(id);
                        }else{
                            JOptionPane.showConfirmDialog(jf,"用户信息不正确!!","登陆失败",JOptionPane.PLAIN_MESSAGE);
                        }
                    }else if(curselected==2)
                    {
                        if(AdminService.is_legal_admin(id,pass,admin_List))
                        {
                            JOptionPane.showConfirmDialog(jf,"登录成功!!","成功登录",JOptionPane.PLAIN_MESSAGE);
                            jf.dispose();
                            new AdminUI();
                        }else{
                            JOptionPane.showConfirmDialog(jf,"管理员信息不正确!!","登陆失败",JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }else if(result==USER_ERROR){
                    JOptionPane.showConfirmDialog(jf,"账号输入不合法!\n(需输入10位数字)","账号不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==PASS_ERROR){
                    JOptionPane.showConfirmDialog(jf,"密码输入不合法!\n(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)","密码不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==ROLE_ERROR){
                    JOptionPane.showConfirmDialog(jf,"未选择身份信息!","身份不合法",JOptionPane.OK_CANCEL_OPTION);
                }
            }else{
                jf.dispose();
                new RegisterUI();
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
     * 监听单选框事件
     */
    private class MyRatioListen implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getItem()==student)
            {
                if(student.isSelected())
                    curselected=1;
            }else if(e.getItem()==admin)
            {
                if(admin.isSelected())
                    curselected=2;
            }
        }
    }
    /**
     * 构造方法 展示窗体
     */
    public LoginUI(){
        Container container=this.getContentPane();

        container.setLayout(null);
        init();

        jLabel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.YELLOW,Color.LIGHT_GRAY));

        jLabel1.setBounds(0,5,800,200);
        Icon icon=new ImageIcon("src\\main\\resources\\top.jpg");
        jLabel1.setIcon(icon);
        welcom.setFont(font);
        name_lb.setFont(font1);
        password_lb.setFont(font1);
        welcom.setBounds(0,40,800,40);
        jp1.setBounds(0,210,800,450);

        name_lb.setBounds(220,150,100,30);
        name_jt.setBounds(350,150,150,30);
        password_lb.setBounds(220,250,100,30);
        password_jt.setBounds(350,250,150,30);
        buttonGroup.add(student);
        buttonGroup.add(admin);
        student.setBounds(280,300,100,40);
        admin.setBounds(440,300,100,40);
        student.setFont(font2);
        admin.setFont(font2);
        MyRatioListen myRatioListen=new MyRatioListen();
        student.addItemListener(myRatioListen);
        admin.addItemListener(myRatioListen);
        submi_lb.setFont(font3);
        register_lb.setFont(font3);
        submi_lb.setBounds(200,350,100,50);
        register_lb.setBounds(480,350,100,50);
        submi_lb.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        register_lb.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        MyBtnLisen myLisen=new MyBtnLisen(this);
        submi_lb.addMouseListener(myLisen);
        register_lb.addMouseListener(myLisen);
        jp1.add(name_lb);
        jp1.add(name_jt);
        jp1.add(password_lb);
        jp1.add(password_jt);
        jp1.add(welcom);
        jp1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.YELLOW,Color.LIGHT_GRAY));
        jp1.add(student);
        jp1.add(admin);
        jp1.add(submi_lb);
        jp1.add(register_lb);

        container.add(jLabel1);
        container.add(jp1);

        setTitle("学生选课系统");
        setBackground(Color.LIGHT_GRAY);
        setSize(800,700);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    private void init()
    {
        jLabel1=new JLabel();
        name_lb=new JLabel("账号",SwingConstants.CENTER);
        password_lb=new JLabel("密码",SwingConstants.CENTER);
        password_jt=new JPasswordField(20);
        name_jt=new JTextField(10);
        welcom=new JLabel("欢迎使用学生选课系统",SwingConstants.CENTER);
        jp1=new JPanel(null);
        student=new JRadioButton("学生");
        student.setForeground(Color.GRAY);
        admin=new JRadioButton("管理员");
        admin.setForeground(Color.GRAY);
        buttonGroup=new ButtonGroup();
        submi_lb=new JLabel("登录",SwingConstants.CENTER);
        register_lb=new JLabel("注册",SwingConstants.CENTER);
        submi_lb.setOpaque(true);
        register_lb.setOpaque(true);
        welcom.setForeground(color1);
        name_lb.setForeground(color1);
        password_lb.setForeground(color1);
        submi_lb.setBackground(color1);
        register_lb.setBackground(color1);
        submi_lb.setForeground(Color.white);
        register_lb.setForeground(Color.white);

        student_List= UserDaoImpl.query_user();
        admin_List= AdminDaoImpl.query_admin();
    }
}
