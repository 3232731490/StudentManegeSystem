package UI;

import Dao.AdminDaoImpl;
import Dao.UserDaoImpl;
import Entity.Admin;
import Entity.Student;
import Entity.User;
import Service.AdminService;
import Service.StuService;
import Service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/26-7:28
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class RegisterUI extends JFrame {
    private JLabel username_lb; //用户名-学号
    private JLabel password_lb; //密码
    private JTextField username_jt; //输入用户名
    private JPasswordField password_jt; //输入密码
    private JPasswordField confirm_jt;  //确认密码
    private  JLabel confirm_lb; //确认密码
    private JLabel role_lb; //身份选择
    private JComboBox<String>   role_jc;    //身份
    private JLabel name_lb; //姓名
    private JLabel age_lb;  //年龄
    private JLabel sex_lb;  //性别
    private JTextField name_jt; //输入姓名
    private JTextField age_jt;  //输入年龄
    private JRadioButton sex_male;   //男
    private JRadioButton sex_female;   //女
    private ButtonGroup buttonGroup;
    private JLabel submmit_btn; //提交按钮
    private JLabel return_btn;  //返回登录按钮
    private JLabel tip;     //提示信息
    private JLabel top;     //顶部图片
    private boolean isSubmited;     //是否已提交
    final static int SUCCESS=0;     //输入正确
    final static int USER_ERROR=1;  //账号输入有误
    final static int PASS_ERROR=2;  //密码输入有误
    final static int CONFIRM_ERROR=3;   //确认密码输入有误
    final static int NAME_ERROR=4;  //姓名输入有误
    final static int AGE_ERROR=5;   //年龄输入有误
    final static int SEX_ERROR=6;   //性别未选择
    private boolean is_admin=false;    //是否为管理员注册
    final static String NAME_MATCH="^([\\u4e00-\\u9fa5]{2,})|([a-zA-Z]*)$";  //匹配姓名-中文或英文
    final static String USER_MATCH="^\\d{10}$";  //匹配账号
    final static String PASS_MATCH="^[a-zA-Z]\\w{5,17}$";  //匹配密码
    final static String AGE_MATCH="^\\d{1,2}$";   //匹配年龄

    List<User> student_List=new ArrayList<>();
    List<Admin> admin_List=new ArrayList<>();

    //不同字体
    private Font font=StyleUI.font;  //标题字体
    private Font font2=StyleUI.font2; //单选框
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入



    /**
     * 检查用户输入是否合法
     * @return  返回错误信息
     */
    private int check()
    {
        String username=username_jt.getText();
        String password=new String(password_jt.getPassword()).trim();
        String confirm=new String(confirm_jt.getPassword()).trim();
        String name=name_jt.getText();
        String age=age_jt.getText();
        boolean sex_seleced=sex_female.isSelected()||sex_male.isSelected();
        if(!Pattern.matches(USER_MATCH,username))
        {
            return USER_ERROR;
        }else if(!Pattern.matches(PASS_MATCH,password))
        {
            return PASS_ERROR;
        }else if(!Pattern.matches(PASS_MATCH,confirm)||!confirm.equals(password))
        {
            return CONFIRM_ERROR;
        }else if(!is_admin&&!Pattern.matches(NAME_MATCH,name)){
            return NAME_ERROR;
        }else if(!is_admin&&(!Pattern.matches(AGE_MATCH,age)||"".equals(age)||Integer.parseInt(age)<8||Integer.parseInt(age)>50))
        {
            return AGE_ERROR;
        }else if(!is_admin&&!sex_seleced)
        {
            return SEX_ERROR;
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
            if(e.getSource()==submmit_btn)
            {
                int result=check();
                if(result==SUCCESS)
                {
                    isSubmited=true;
                    String username=username_jt.getText();
                    String password=new String(password_jt.getPassword());
                    if(is_admin){
                        if(AdminService.is_existed_admin(username,admin_List))
                        {
                            AdminService.insert_admin(username,password);
                            JOptionPane.showConfirmDialog(jf,"信息提交成功,即将返回登陆","注册成功",JOptionPane.PLAIN_MESSAGE);
                            jf.dispose();
                            new LoginUI();
                        }
                        else {
                            JOptionPane.showConfirmDialog(jf,"已存在该管理员，请检查输入","提示信息",JOptionPane.PLAIN_MESSAGE);
                        }
                    }else{
                        String sname=name_jt.getText();
                        String sex=sex_male.isSelected()?"男":"女";
                        int age=Integer.parseInt(age_jt.getText());
                        if(UserService.is_existed_stu(username,student_List))
                        {
                            UserService.insert_user(username,password);
                            StuService.insert_part(username,sname,sex,age);
                            JOptionPane.showConfirmDialog(jf,"信息提交成功,即将返回登陆","注册成功",JOptionPane.PLAIN_MESSAGE);
                            jf.dispose();
                            new LoginUI();
                        }
                        else {
                            JOptionPane.showConfirmDialog(jf,"已存在该用户，请检查输入","提示信息",JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }else if(result==USER_ERROR){
                    JOptionPane.showConfirmDialog(jf,"账号输入不合法!\n(需输入10位数字)","账号不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==PASS_ERROR){
                    JOptionPane.showConfirmDialog(jf,"密码输入不合法!\n(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)","密码不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==CONFIRM_ERROR){
                    JOptionPane.showConfirmDialog(jf,"确认密码输入不合法!\n(密码本身不合法或两次密码不相同)","确认密码不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==NAME_ERROR){
                    JOptionPane.showConfirmDialog(jf,"姓名输入不合法!\n(只能为中文或者英文)","姓名不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==SEX_ERROR){
                    JOptionPane.showConfirmDialog(jf,"性别未选择!","性别不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==AGE_ERROR){
                    JOptionPane.showConfirmDialog(jf,"年龄输入不合法!\n(年龄范围为8-50岁)","年龄不合法",JOptionPane.OK_CANCEL_OPTION);
                }
            }
            else{
                if(!isSubmited)
                {
                    if(JOptionPane.showConfirmDialog(jf,"您还未提交信息，是否要返回登陆界面？","确认窗口",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                }
                jf.dispose();
                new LoginUI();
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

    private class myComboListen implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(role_jc.getSelectedItem().equals("管理员"))
            {
                name_jt.setEnabled(false);
                age_jt.setEnabled(false);
                sex_female.setEnabled(false);
                sex_male.setEnabled(false);
                is_admin=true;
            }
            else
            {
                name_jt.setEnabled(true);
                age_jt.setEnabled(true);
                sex_female.setEnabled(true);
                sex_male.setEnabled(true);
                is_admin=false;
            }
        }
    }

    /**
     * 构造方法 展示窗体
     */
    public RegisterUI(){
        Container container=getContentPane();
        container.setLayout(null);
        JPanel jp1=new JPanel(null);
        jp1.setBounds(0,150,800,450);
        jp1.setBorder(new RoundBorderUI());
        init();
        top.setBounds(0,0,800,140);
        tip.setBounds(0,5,800,40);
        role_lb.setBounds(200,50,100,30);
        role_jc.setBounds(400,50,200,30);
        username_lb.setBounds(200,110,100,30);
        username_jt.setBounds(400,110,200,30);
        password_lb.setBounds(200,170,100,30);
        password_jt.setBounds(400,170,200,30);
        confirm_lb.setBounds(200,230,100,30);
        confirm_jt.setBounds(400,230,200,30);
        name_lb.setBounds(200,290,100,30);
        name_jt.setBounds(400,290,200,30);
        sex_lb.setBounds(200,350,100,30);
        sex_female.setBounds(400,350,100,30);
        sex_male.setBounds(510,350,100,30);
        age_lb.setBounds(200,410,50,30);
        age_jt.setBounds(400,410,50,30);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        submmit_btn.addMouseListener(myBtnLisen);
        return_btn.addMouseListener(myBtnLisen);
        myComboListen myComboListen=new myComboListen();
        role_jc.addItemListener(myComboListen);
        jp1.add(tip);
        jp1.add(role_jc);
        jp1.add(role_lb);
        jp1.add(username_jt);
        jp1.add(username_lb);
        jp1.add(password_jt);
        jp1.add(password_lb);
        jp1.add(confirm_jt);
        jp1.add(confirm_lb);
        jp1.add(name_jt);
        jp1.add(name_lb);
        jp1.add(sex_female);
        jp1.add(sex_lb);
        jp1.add(sex_male);
        jp1.add(age_jt);
        jp1.add(age_lb);

        JPanel jp2=new JPanel(null);
        jp2.setBounds(0,600,800,100);
        submmit_btn.setBounds(250,20,100,40);
        return_btn.setBounds(450,20,100,40);
        jp2.add(submmit_btn);
        jp2.add(return_btn);
        container.add(top);
        container.add(jp1);
        container.add(jp2);

        setTitle("注册信息");
        setBackground(Color.LIGHT_GRAY);
        setSize(800,720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    //初始化组件
    private void init(){
        isSubmited=false;
        username_jt=new JTextField();
        username_lb=new JLabel("账号");
        password_jt=new JPasswordField();
        password_lb=new JLabel("密码");
        confirm_jt=new JPasswordField();
        confirm_lb=new JLabel("确认密码");
        sex_female=new JRadioButton("女");
        sex_male=new JRadioButton("男");
        sex_lb=new JLabel("性别");
        name_jt=new JTextField();
        name_lb=new JLabel("姓名");
        age_jt=new JTextField();
        age_lb=new JLabel("年龄");
        buttonGroup=new ButtonGroup();
        submmit_btn=new JLabel("提交信息",SwingConstants.CENTER);
        return_btn=new JLabel("返回登陆",SwingConstants.CENTER);
        String[] datalist={"学生","管理员"};
        role_jc=new JComboBox<>(datalist);
        role_lb=new JLabel("身份");
        top=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\register.jpg");
        top.setIcon(icon);
        tip=new JLabel("请输入您的信息",SwingConstants.CENTER);
        buttonGroup.add(sex_female);
        buttonGroup.add(sex_male);

        return_btn.setFont(font3);
        submmit_btn.setFont(font3);

        role_lb.setFont(font4);
        password_lb.setFont(font4);
        confirm_lb.setFont(font4);
        name_lb.setFont(font4);
        sex_lb.setFont(font4);
        username_lb.setFont(font4);
        age_lb.setFont(font4);
        sex_lb.setFont(font4);
        role_jc.setFont(font2);
        tip.setFont(font);

        sex_male.setFont(font2);
        sex_female.setFont(font2);

        submmit_btn.setOpaque(true);
        return_btn.setOpaque(true);
        submmit_btn.setBackground(color1);
        return_btn.setBackground(color1);
        tip.setForeground(color1);
        submmit_btn.setForeground(Color.white);
        return_btn.setForeground(Color.white);

        student_List= UserDaoImpl.query_user();
        admin_List= AdminDaoImpl.query_admin();
    }
}
