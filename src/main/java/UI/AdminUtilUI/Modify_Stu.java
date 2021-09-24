package UI.AdminUtilUI;

import Dao.CourseDaoImpl;
import Dao.SCDaoImpl;
import Dao.StuDaoImpl;
import Dao.UserDaoImpl;
import Entity.Course;
import Entity.Student;
import Entity.User;
import Service.UserService;
import UI.RoundBorderUI;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/27-15:40
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Modify_Stu extends JFrame {
    private JLabel top_img; //顶部图
    private JLabel query;   //查询按钮
    private JLabel clear;   //清除按钮
    private JLabel tip; //提示
    private JTextField condition;   //输入框
    private JPanel jp1; //输入信息面板
    private JPanel jp2; //顶部输面板
    private JLabel enable_tip;  //查询提示
    private JLabel top_tip;     //输入提示

    private JLabel modi_lb;   //提交
    private JLabel exit_lb;     //退出

    private JLabel stu_no_lb;    //学号
    private JLabel stu_name_lb;  //姓名
    private JLabel stu_major_lb;      //专业
    private JLabel academy_lb;      //学院
    private JLabel age_lb ;    //年龄
    private JLabel sex_lb;  //性别

    private JComboBox<String> sex_jt;     //性别
    private JTextField age_jt;  //年龄
    private JTextField stu_no_jt;    //学号
    private JTextField stu_name_jt;  //姓名
    private JComboBox<String> acadamy_jc; //学院
    private JTextField stu_major_jt;    //专业
    String[] major_data={"计算机","轨道交通","香精香料","公共管理学院","环境学院","经济管理学院","建筑学院","新雅书院","临床医学院","药学院","电机工程与应用电子技术系","工程物理系","生命科学学院","美术学院","化学工程系","体育部"};

    final static int SUCCESS=0;     //输入正确
    final static int STU_NAME_ERROR=1;  //姓名输入有误
    final static int STU_NO_ERROR=2;     //学号输入有误
    final static int MAJOR_ERROR=3;  //专业输入有误
    final static int AGE_ERROR=4;  //年龄输入错误

    final static String STU_NAME_MATCH="^([\\u4e00-\\u9fa5]{2,})|([A-Za-z]{2,})$";  //匹配姓名（中文或英文）
    final static String STU_NO_MATCH="^(\\d{10})|(\\d{9}(Y|y))$";  //匹配学号   10位数字或9位加y
    final static String MAJOR_MATCH="^[\\u4e00-\\u9fa5]{2,}$";  //匹配专业  中文
    final static String AGE_MATCH="^\\d{1,2}$";  //匹配年龄 8-40

    private boolean is_existed=false;

    //不同字体
    private Font font=StyleUI.font;  //标题字体
    private Font font1=StyleUI.font1; //输入提示
    private Font font2=StyleUI.font2; //单选框
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    private MyBtnLisen myBtnLisen=new MyBtnLisen(this);

    /**
     * 检查输入是否合法
     * @return  返回合法性
     */
    private int check(){
        String stu_name=stu_name_jt.getText();
        String stu_no=stu_no_jt.getText();
        String major=stu_major_jt.getText();
        String age=age_jt.getText();

        if(!stu_name.equals("")&&!Pattern.matches(STU_NAME_MATCH,stu_name))
        {
            return STU_NAME_ERROR;
        }else if(!stu_no.equals("")&&!Pattern.matches(STU_NO_MATCH,stu_no))
        {
            return STU_NO_ERROR;
        }else if(!major.equals("")&&!Pattern.matches(MAJOR_MATCH,major))
        {
            return MAJOR_ERROR;
        }else if(!age.equals("")&&(!Pattern.matches(AGE_MATCH,age)||"".equals(age)||Integer.parseInt(age)<8||Integer.parseInt(age)>40))
        {
            return AGE_ERROR;
        }
        return SUCCESS;
    }

    /**
     * 设置组件可用与否
     */
    private void set_enable()
    {
        top_tip.setEnabled(is_existed);
        stu_no_lb.setEnabled(is_existed);
        stu_no_jt.setEnabled(is_existed);
        stu_name_lb.setEnabled(is_existed);
        stu_name_jt.setEnabled(is_existed);
        stu_major_lb.setEnabled(is_existed);
        stu_major_jt.setEnabled(is_existed);
        academy_lb.setEnabled(is_existed);
        acadamy_jc.setEnabled(is_existed);
        age_jt.setEnabled(is_existed);
        age_lb.setEnabled(is_existed);
        sex_jt.setEnabled(is_existed);
        sex_lb.setEnabled(is_existed);
        jp1.setEnabled(is_existed);
        modi_lb.setEnabled(is_existed);
        if(!is_existed)
            modi_lb.removeMouseListener(myBtnLisen);
        else
            modi_lb.addMouseListener(myBtnLisen);
    }

    private void clear_com(){
        stu_no_jt.setText("");
        stu_name_jt.setText("");
        stu_major_jt.setText("");
        age_jt.setText("");
        enable_tip.setText("要先查询某一门课程才可进行修改");
        is_existed=false;
        condition.setText("");
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
            if(e.getSource()==exit_lb)
            {
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                }
            }else if(e.getSource()==modi_lb)
            {
                int result=check();
                if(result==SUCCESS){
                    String sno=condition.getText();
                    Student stu= StuDaoImpl.query_a_stu(sno);
                    String newcno=stu_no_jt.getText().trim();
                    newcno=newcno.equals("")?stu.getSno():newcno;
                    String newcname=stu_name_jt.getText().trim();
                    newcname=newcname.equals("")?stu.getSname():newcname;
                    String sex=(String)sex_jt.getSelectedItem();
                    int age;
                    if(age_jt.getText().trim().equals(""))
                        age=stu.getAge();
                    else
                        age=Integer.parseInt(age_jt.getText());
                    String dept=(String)acadamy_jc.getSelectedItem();
                    String major=(String)stu_major_jt.getText().trim();
                    major=major.equals("")?stu.getMajor():major;
                    if(!sno.equals(newcno))
                    {
                        if(StuDaoImpl.query_a_stu(newcno)!=null)
                        {
                            JOptionPane.showMessageDialog(jf,"当前数据库已存在学号为 "+newcno+"的学生，请检查修改","错误提示",JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        StuDaoImpl.add_stu(new Student(newcno,newcname,sex,age,dept,major));
                        String pass=UserDaoImpl.get_pass(sno);
                        if(UserDaoImpl.is_existed(sno)){
                            UserDaoImpl.del_user(sno);
                            UserService.insert_user(newcno,pass);
                        }
                        SCDaoImpl.update_sno(sno,newcno);
                        StuDaoImpl.del_stu(sno);
                    }else
                    {
                        StuDaoImpl.update_stu(new Student(newcno,newcname,sex,age,dept,major),sno);
                    }
                    JOptionPane.showMessageDialog(jf,"学生信息更新成功","更新成功",JOptionPane.PLAIN_MESSAGE);
                    is_existed=false;
                    enable_tip.setText("要先查询某一门课程才可进行修改");
                    set_enable();   //更新输入框不可用
                }else if(result==STU_NO_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的学号有误，必须为如下格式 \n1910400731或191040073Y","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==STU_NAME_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的姓名有误，必须为中文或英文","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==MAJOR_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的专业有误，必须为中文","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==AGE_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的年龄有误，必须在8-40之间","错误提示",JOptionPane.PLAIN_MESSAGE);
                }
            } else if(e.getSource()==clear){
                clear_com();
                set_enable();   //更新输入框不可用
            }else if(e.getSource()==query){
                String no=condition.getText();
                if(no.equals(""))
                {
                    JOptionPane.showMessageDialog(jf,"请先输入学号再进行查询","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }else if(!Pattern.matches(STU_NO_MATCH,no))
                {
                    JOptionPane.showMessageDialog(jf,"您输入的学号有误，必须为如下格式 \n1910400731或191040073Y","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                Student stu= StuDaoImpl.query_a_stu(no);
                if(stu==null)
                {
                    JOptionPane.showMessageDialog(jf,"未查询到您输入的学生","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                is_existed=true;
                enable_tip.setText("现在您可以输入该课程的新值");
                set_enable();   //更新输入框可用
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
    public Modify_Stu(){
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(0,5,800,100);
        jp2.setBounds(0,110,800,50);
        tip.setBounds(70,10,200,30);
        condition.setBounds(280,10,150,30);
        query.setBounds(500,10,60,30);
        clear.setBounds(620,10,60,30);

        enable_tip.setBounds(0,170,800,50);

        jp1.setBounds(5,230,795,300);
        jp1.setBorder(new RoundBorderUI());
        top_tip.setBounds(20,5,780,30);
        stu_no_lb.setBounds(120,73,50,30);
        stu_no_jt.setBounds(180,73,100,30);
        stu_name_lb.setBounds(520,73,50,30);
        stu_name_jt.setBounds(580,73,100,30);
        stu_major_lb.setBounds(120,149,50,30);
        stu_major_jt.setBounds(180,149,100,30);
        sex_lb.setBounds(520,149,50,30);
        sex_jt.setBounds(580,149,100,30);
        age_lb.setBounds(120,225,50,30);
        age_jt.setBounds(180,225,100,30);
        academy_lb.setBounds(520,225,50,30);
        acadamy_jc.setBounds(580,225,100,30);
        modi_lb.setBounds(5,550,795,40);
        exit_lb.setBounds(5,600,795,40);

        query.addMouseListener(myBtnLisen);
        clear.addMouseListener(myBtnLisen);
        exit_lb.addMouseListener(myBtnLisen);

        jp1.add(top_tip);
        jp1.add(stu_no_lb);
        jp1.add(stu_no_jt);
        jp1.add(acadamy_jc);
        jp1.add(stu_name_lb);
        jp1.add(stu_name_jt);
        jp1.add(stu_major_lb);
        jp1.add(stu_major_jt);
        jp1.add(sex_lb);
        jp1.add(sex_jt);
        jp1.add(age_lb);
        jp1.add(age_jt);
        jp1.add(academy_lb);
        set_enable();


        jp2.add(tip);
        jp2.add(condition);
        jp2.add(query);
        jp2.add(clear);

        container.add(top_img);
        container.add(enable_tip);
        container.add(jp2);
        container.add(jp1);
        container.add(modi_lb);
        container.add(exit_lb);

        setTitle("修改学生");
        setBackground(Color.LIGHT_GRAY);
        setSize(820,680);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * 初始化组件
     */
    private void init(){
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\modistu.jpg");
        top_img.setIcon(icon);

        enable_tip=new JLabel("要先查询某一个学生才可进行修改",SwingConstants.CENTER);
        tip=new JLabel("请输入您要修改的学生学号",SwingConstants.LEFT);

        top_tip=new JLabel("请输入学生信息",SwingConstants.CENTER);
        stu_name_lb=new JLabel("姓名",SwingConstants.LEFT);
        stu_no_lb=new JLabel("学号",SwingConstants.LEFT);
        stu_major_lb=new JLabel("专业",SwingConstants.LEFT);
        academy_lb=new JLabel("学院",SwingConstants.LEFT);
        age_lb=new JLabel("年龄",SwingConstants.LEFT);
        sex_lb=new JLabel("性别",SwingConstants.LEFT);
        query=new JLabel("查询",SwingConstants.CENTER);
        clear=new JLabel("清除",SwingConstants.CENTER);
        String[] sex_data={"男","女"};
        sex_jt=new JComboBox<>(sex_data);
        age_jt=new JTextField();
        modi_lb=new JLabel("修改",SwingConstants.CENTER);
        exit_lb=new JLabel("退出",SwingConstants.CENTER);
        condition=new JTextField();

        jp1=new JPanel(null);
        jp2=new JPanel(null);

        enable_tip.setFont(font4);

        acadamy_jc=new JComboBox<>(major_data);
        stu_major_jt=new JTextField();

        stu_no_jt=new JTextField();
        stu_name_jt=new JTextField();

        query.setFont(font3);
        clear.setFont(font3);
        acadamy_jc.setFont(font3);
        modi_lb.setFont(font1);
        exit_lb.setFont(font1);
        top_tip.setFont(font);
        stu_no_lb.setFont(font1);
        stu_name_lb.setFont(font1);
        stu_major_lb.setFont(font1);
        age_lb.setFont(font1);
        academy_lb.setFont(font1);
        sex_lb.setFont(font1);
        sex_jt.setFont(font3);
        tip.setFont(font4);

        query.setOpaque(true);
        clear.setOpaque(true);
        modi_lb.setOpaque(true);
        exit_lb.setOpaque(true);

        query.setBackground(color1);
        clear.setBackground(color1);
        modi_lb.setBackground(color1);
        exit_lb.setBackground(color1);
        enable_tip.setForeground(color1);

        query.setForeground(Color.white);
        clear.setForeground(Color.white);
        top_tip.setForeground(color1);
        modi_lb.setForeground(Color.white);
        exit_lb.setForeground(Color.white);
    }
}
