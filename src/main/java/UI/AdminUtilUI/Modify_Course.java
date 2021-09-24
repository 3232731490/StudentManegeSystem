package UI.AdminUtilUI;

import Dao.CourseDaoImpl;
import Dao.PlanDaoImpl;
import Dao.SCDaoImpl;
import Entity.Course;

import Service.CourseService;
import UI.RoundBorderUI;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/27-15:39
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Modify_Course extends JFrame {
    private JLabel top_img; //顶部图
    private JLabel query;   //查询按钮
    private JLabel clear;   //清除按钮
    private JLabel tip; //提示
    private JTextField condition;   //输入框
    private JPanel jp1; //输入信息面板
    private JPanel jp2; //顶部输面板
    private JLabel enable_tip;  //查询提示

    private JLabel top_tip;     //输入提示
    private JLabel course_no_lb;    //课程号
    private JLabel course_name_lb;  //课程名
    private JLabel study_time_lb;      //学时
    private JLabel study_edit_lb;      //学分
    private JLabel teacher_lb;      //老师
    private JLabel location_lb;     //地点

    private JTextField course_no_jt;    //课程号
    private JTextField course_name_jt;  //课程名
    private JTextField study_time_jt;      //学时
    private JComboBox<Double> study_edit_jt;      //学分
    private JTextField teacher_jt;      //老师
    private JTextField location_jt;     //地点
    private final Double[] edit_data={0.5,1.0,1.5,2.0,2.5,3.0};

    final static int SUCCESS=0;     //输入正确
    final static int COURSE_NAME_ERROR=1;  //课程名输入有误
    final static int COURSE_NO_ERROR=2;     //课程号输入有误
    final static int STUDY_TIME_ERROR=3;  //学时输入有误
    final static int TEACHER_ERROR=4;  //老师输入错误
    final static int LOACTION_ERROR=5;  //位置输入错误

    final static String COURSE_NAME_MATCH="^([\\u4e00-\\u9fa5]{2,})|([A-Za-z]{2,})$";  //匹配课程名 （中文或英文）
    final static String COURSE_NO_MATCH="^[A-Z]\\d{6}$";  //匹配课程号   大写字母+6位数字
    final static String STUDY_TIME_MATCH="^\\d{1,2}$";  //匹配学时  一到两位
    final static String TEACHER_MATCH="^\\d{4}$";  //匹配老师   四位数字
    final static String LOACTION_MATCH="^[A-I]\\d{3}$";  //匹配地点    格式如 E301

    private JLabel Exit_btn;        //退出系统
    private JLabel modi_btn;        //修改按钮

    //不同字体
    private Font font= StyleUI.font;  //标题字体
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    private boolean is_existed=false;       //是否存在课程

    //监听器
    MyBtnLisen myBtnLisen=new MyBtnLisen(this);

    /**
     * 检查输入是否合法
     * @return  返回合法性
     */
    private int check(){
        String course_name=course_name_jt.getText();
        String course_no=course_no_jt.getText();
        String study_time=study_time_jt.getText();
        String teacher=teacher_jt.getText();
        String location=location_jt.getText();

        if(!course_name.equals("")&&!Pattern.matches(COURSE_NAME_MATCH,course_name))
        {
            return COURSE_NAME_ERROR;
        }else if(!course_no.equals("")&&!Pattern.matches(COURSE_NO_MATCH,course_no))
        {
            return COURSE_NO_ERROR;
        }else if(!study_time.equals("")&&(!Pattern.matches(STUDY_TIME_MATCH,study_time)||"".equals(study_time)||Integer.parseInt(study_time)<8||Integer.parseInt(study_time)>60))
        {
            return STUDY_TIME_ERROR;
        }else if(!teacher.equals("")&&!Pattern.matches(TEACHER_MATCH,teacher))
        {
            return TEACHER_ERROR;
        }else if(!location.equals("")&&!Pattern.matches(LOACTION_MATCH,location))
        {
            return LOACTION_ERROR;
        }
        return SUCCESS;
    }

    /**
     * 设置组件可用与否
     */
    private void set_enable()
    {
        top_tip.setEnabled(is_existed);
        course_name_lb.setEnabled(is_existed);
        course_no_lb.setEnabled(is_existed);
        study_edit_lb.setEnabled(is_existed);
        study_time_lb.setEnabled(is_existed);
        teacher_lb.setEnabled(is_existed);
        location_lb.setEnabled(is_existed);
        course_name_jt.setEnabled(is_existed);
        course_no_jt.setEnabled(is_existed);
        study_edit_jt.setEnabled(is_existed);
        study_time_jt.setEnabled(is_existed);
        teacher_jt.setEnabled(is_existed);
        location_jt.setEnabled(is_existed);
        jp1.setEnabled(is_existed);
        modi_btn.setEnabled(is_existed);
        if(!is_existed)
            modi_btn.removeMouseListener(myBtnLisen);
        else
            modi_btn.addMouseListener(myBtnLisen);
    }

    private void clear_com(){
        course_name_jt.setText("");
        course_no_jt.setText("");
        study_time_jt.setText("");
        teacher_jt.setText("");
        location_jt.setText("");
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
            if(e.getSource()==Exit_btn)
            {
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                }
            }else if(e.getSource()==modi_btn)
            {
                int result=check();
                if(result==SUCCESS)
                {
                    String cno=condition.getText();
                    Course course=CourseDaoImpl.query_cno_course(cno);
                    String newcno=course_no_jt.getText().trim();
                    newcno=newcno.equals("")?course.getCno():newcno;
                    String newcname=course_name_jt.getText().trim();
                    newcname=newcname.equals("")?course.getCname():newcname;
                    double newct=(double)study_edit_jt.getSelectedItem();
                    int newtime;
                    if(study_time_jt.getText().trim().equals(""))
                        newtime=course.getTime();
                    else newtime=Integer.parseInt(study_time_jt.getText());
                    String newte=teacher_jt.getText().trim();
                    newte=newte.equals("")?course.getT_no():newte;
                    String location=location_jt.getText().trim();
                    location=location.equals("")?course.getLocation():location;
                    if(!cno.equals(newcno)){
                        if(CourseDaoImpl.query_cno_course(newcno)!=null)
                        {
                            JOptionPane.showMessageDialog(jf,"当前数据库已存在课程号为 "+newcno+"的课程，请检查修改","错误提示",JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        CourseDaoImpl.add_course(new Course(newcno,newcname,newct,newtime,newte,location));
                        SCDaoImpl.update_cno(cno,newcno);
                        PlanDaoImpl.update_cno(cno,newcno);
                        CourseDaoImpl.del_course(cno);
                    }
                    else{
                        CourseDaoImpl.update_course(new Course(newcno,newcname,newct,newtime,newte,location),cno);
                    }
                    JOptionPane.showMessageDialog(jf,"课程信息更新成功","更新成功",JOptionPane.PLAIN_MESSAGE);
                    is_existed=false;
                    enable_tip.setText("要先查询某一门课程才可进行修改");
                    set_enable();   //更新输入框不可用
                }else if(result==COURSE_NO_ERROR)
                {
                    JOptionPane.showMessageDialog(jf,"您输入的课程号有误，必须为大写英文字母加6位数字 如E123456","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==COURSE_NAME_ERROR)
                {
                    JOptionPane.showMessageDialog(jf,"您输入的课程名有误，必须为中文或者汉字 如高等数学","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==STUDY_TIME_ERROR)
                {
                    JOptionPane.showMessageDialog(jf,"您输入的学时有误，必须为1-2位数字且位于8-60之间","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==TEACHER_ERROR)
                {
                    JOptionPane.showMessageDialog(jf,"您输入的教师编号有误，必须为四位数字","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==LOACTION_ERROR)
                {
                    JOptionPane.showMessageDialog(jf,"您输入的地点信息有误，[A-I]+三位数 如A103","错误提示",JOptionPane.PLAIN_MESSAGE);
                }
            }else if(e.getSource()==query)
            {
                String no=condition.getText();
                if(no.equals(""))
                {
                    JOptionPane.showMessageDialog(jf,"请先输入课程号再进行查询","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }else if(!Pattern.matches(COURSE_NO_MATCH,no))
                {
                    JOptionPane.showMessageDialog(jf,"您输入的课程号有误，必须为大写英文字母加6位数字 如E123456","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                String cno=condition.getText();
                Course course=CourseDaoImpl.query_cno_course(cno);
                if(course==null)
                {
                    JOptionPane.showMessageDialog(jf,"未查询到您输入的课程","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                is_existed=true;
                enable_tip.setText("现在您可以输入该课程的新值");
                set_enable();   //更新输入框可用
            }else if(e.getSource()==clear)
            {
                clear_com();
                set_enable();   //更新输入框不可用
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
    public Modify_Course(){
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
        course_no_lb.setBounds(120,73,50,30);
        course_no_jt.setBounds(180,73,100,30);
        course_name_lb.setBounds(520,73,50,30);
        course_name_jt.setBounds(580,73,100,30);
        study_time_lb.setBounds(120,149,50,30);
        study_time_jt.setBounds(180,149,100,30);
        study_edit_lb.setBounds(520,149,50,30);
        study_edit_jt.setBounds(580,149,100,30);
        teacher_lb.setBounds(120,225,50,30);
        teacher_jt.setBounds(180,225,100,30);
        location_lb.setBounds(520,225,50,30);
        location_jt.setBounds(580,225,100,30);
        modi_btn.setBounds(5,550,795,40);
        Exit_btn.setBounds(5,600,795,40);

        query.addMouseListener(myBtnLisen);
        clear.addMouseListener(myBtnLisen);
        Exit_btn.addMouseListener(myBtnLisen);

        jp1.add(top_tip);
        jp1.add(course_no_lb);
        jp1.add(course_no_jt);
        jp1.add(course_name_lb);
        jp1.add(course_name_jt);
        jp1.add(study_time_lb);
        jp1.add(study_time_jt);
        jp1.add(study_edit_lb);
        jp1.add(study_edit_jt);
        jp1.add(teacher_lb);
        jp1.add(teacher_jt);
        jp1.add(location_lb);
        jp1.add(location_jt);
        set_enable();

        jp2.add(tip);
        jp2.add(condition);
        jp2.add(query);
        jp2.add(clear);

        container.add(top_img);
        container.add(enable_tip);
        container.add(jp2);
        container.add(jp1);
        container.add(modi_btn);
        container.add(Exit_btn);

        setTitle("修改课程");
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
        Icon icon=new ImageIcon("src\\main\\resources\\modicourse.jpg");
        top_img.setIcon(icon);

        enable_tip=new JLabel("要先查询某一门课程才可进行修改",SwingConstants.CENTER);
        tip=new JLabel("请输入您要修改的课程号",SwingConstants.LEFT);
        condition=new JTextField();
        query=new JLabel("查询",SwingConstants.CENTER);
        clear=new JLabel("清空",SwingConstants.CENTER);
        Exit_btn=new JLabel("退出",SwingConstants.CENTER);
        modi_btn=new JLabel("修改",SwingConstants.CENTER);

        top_tip=new JLabel("请输入课程信息",SwingConstants.CENTER);
        course_name_lb=new JLabel("课程名",SwingConstants.LEFT);
        course_no_lb=new JLabel("课程号",SwingConstants.LEFT);
        study_time_lb=new JLabel("学时",SwingConstants.LEFT);
        study_edit_lb=new JLabel("学分",SwingConstants.LEFT);
        teacher_lb=new JLabel("教师编号",SwingConstants.LEFT);
        location_lb=new JLabel("地点",SwingConstants.LEFT);

        course_no_jt=new JTextField();
        course_name_jt=new JTextField();
        study_time_jt=new JTextField();
        teacher_jt=new JTextField();
        location_jt=new JTextField();
        study_edit_jt=new JComboBox<>(edit_data);

        jp1=new JPanel(null);
        jp2=new JPanel(null);

        enable_tip.setFont(font4);
        top_tip.setFont(font);
        course_no_lb.setFont(font4);
        course_name_lb.setFont(font4);
        study_edit_lb.setFont(font4);
        study_time_lb.setFont(font4);
        teacher_lb.setFont(new Font("华文细黑", 1, 12));
        location_lb.setFont(font4);
        study_edit_jt.setFont(font3);

        tip.setFont(font4);
        query.setFont(font3);
        clear.setFont(font3);
        Exit_btn.setFont(font3);
        modi_btn.setFont(font3);

        query.setOpaque(true);
        clear.setOpaque(true);
        Exit_btn.setOpaque(true);
        modi_btn.setOpaque(true);

        enable_tip.setForeground(color1);
        top_tip.setForeground(color1);
        query.setBackground(color1);
        clear.setBackground(color1);
        Exit_btn.setBackground(color1);
        modi_btn.setBackground(color1);

        query.setForeground(Color.white);
        clear.setForeground(Color.white);
        Exit_btn.setForeground(Color.white);
        modi_btn.setForeground(Color.white);
    }

    public static void main(String[] args) {
        new Modify_Course();
    }
}
