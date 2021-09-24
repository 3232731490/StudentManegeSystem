package UI.AdminUtilUI;

import Dao.CourseDaoImpl;
import Service.CourseService;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/27-9:05
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Add_Course extends JFrame {
    private JLabel top_img;     //顶部图片
    private JLabel top_tip;     //顶部提示
    private JLabel right_img;   //右部图片

    private JLabel course_no_lb;    //课程号
    private JLabel course_name_lb;  //课程名
    private JLabel study_time_lb;      //学时
    private JLabel study_edit_lb;      //学分
    private JLabel teacher_lb;      //老师
    private JLabel location_lb;     //地点

    private JLabel submit_lb;   //提交
    private JLabel exit_lb;     //退出

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
    final static String LOACTION_MATCH="^[A-I]\\d{3}$";  //匹配地点    格式如 二教E301

    //不同字体
    private Font font= StyleUI.font;  //标题字体
    private Font font1=StyleUI.font1; //输入提示
    private Font font3=StyleUI.font3; //按钮

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

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
        //Double study_no=(Double) study_edit_jt.getSelectedItem();

        if(!Pattern.matches(COURSE_NAME_MATCH,course_name))
        {
            return COURSE_NAME_ERROR;
        }else if(!Pattern.matches(COURSE_NO_MATCH,course_no))
        {
            return COURSE_NO_ERROR;
        }else if(!Pattern.matches(STUDY_TIME_MATCH,study_time)||"".equals(study_time)||Integer.parseInt(study_time)<8||Integer.parseInt(study_time)>60)
        {
            return STUDY_TIME_ERROR;
        }else if(!Pattern.matches(TEACHER_MATCH,teacher))
        {
            return TEACHER_ERROR;
        }else if(!Pattern.matches(LOACTION_MATCH,location))
        {
            return LOACTION_ERROR;
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
            if(e.getSource()==exit_lb)
            {
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                }
            }else if(e.getSource()==submit_lb)
            {
                int result=check();
                String cno=course_no_jt.getText();
                String cname=course_name_jt.getText();
                double ct=(double)study_edit_jt.getSelectedItem();
                int time=Integer.parseInt(study_time_jt.getText());
                String Tno=teacher_jt.getText();
                String address=location_jt.getText();
                if(result==SUCCESS)
                {
                    boolean is_existed;
                    is_existed= CourseDaoImpl.query_cno_course(cno)==null;
                    if(!is_existed)
                    {
                        JOptionPane.showMessageDialog(jf,"当前数据库已存在该门课程，您可以选择修改该课或重新检查后添加","提示信息",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        CourseService.insert_course(cno,cname,ct,time,Tno,address);
                        JOptionPane.showMessageDialog(jf,"增加课程成功","增加成功",JOptionPane.PLAIN_MESSAGE);
                    }
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
    public Add_Course(){
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(0,5,800,105);
        top_tip.setBounds(0,115,400,40);

        JPanel jp1=new JPanel(null);
        jp1.setBounds(0,160,800,460);
        course_no_lb.setBounds(60,31,80,30);
        course_no_jt.setBounds(190,31,150,30);
        course_name_lb.setBounds(60,107,80,30);
        course_name_jt.setBounds(190,107,150,30);
        study_time_lb.setBounds(60,183,80,30);
        study_time_jt.setBounds(190,183,150,30);
        study_edit_lb.setBounds(60,259,80,30);
        study_edit_jt.setBounds(190,259,60,30);
        teacher_lb.setBounds(60,335,80,30);
        teacher_jt.setBounds(190,335,150,30);
        location_lb.setBounds(60,411,80,30);
        location_jt.setBounds(190,411,150,30);
        right_img.setBounds(420,5,360,440);
        submit_lb.setBounds(0,625,800,25);
        exit_lb.setBounds(0,655,800,25);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        submit_lb.addMouseListener(myBtnLisen);
        exit_lb.addMouseListener(myBtnLisen);


        jp1.add(course_name_lb);
        jp1.add(course_no_jt);
        jp1.add(course_name_jt);
        jp1.add(course_no_lb);
        jp1.add(study_time_lb);
        jp1.add(study_time_jt);
        jp1.add(study_edit_lb);
        jp1.add(study_edit_jt);
        jp1.add(teacher_lb);
        jp1.add(teacher_jt);
        jp1.add(location_lb);
        jp1.add(location_jt);
        jp1.add(right_img);
        container.add(top_img);
        container.add(top_tip);
        container.add(jp1);
        container.add(submit_lb);
        container.add(exit_lb);

        setTitle("增加课程");
        setBackground(Color.LIGHT_GRAY);
        setSize(815,720);
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
        Icon icon=new ImageIcon("src\\main\\resources\\addcourse.jpg");
        top_img.setIcon(icon);
        right_img=new JLabel();
        icon=new ImageIcon("src\\main\\resources\\lufei.jpg");
        right_img.setIcon(icon);
        top_tip=new JLabel("请输入课程信息",SwingConstants.CENTER);
        course_name_lb=new JLabel("课程名",SwingConstants.LEFT);
        course_no_lb=new JLabel("课程号",SwingConstants.LEFT);
        study_time_lb=new JLabel("学时",SwingConstants.LEFT);
        study_edit_lb=new JLabel("学分",SwingConstants.LEFT);
        teacher_lb=new JLabel("教师编号",SwingConstants.LEFT);
        location_lb=new JLabel("地点",SwingConstants.LEFT);
        submit_lb=new JLabel("提交",SwingConstants.CENTER);
        exit_lb=new JLabel("退出",SwingConstants.CENTER);

        course_no_jt=new JTextField();
        course_name_jt=new JTextField();
        study_time_jt=new JTextField();
        teacher_jt=new JTextField();
        location_jt=new JTextField();
        study_edit_jt=new JComboBox<>(edit_data);


        submit_lb.setFont(font1);
        exit_lb.setFont(font1);
        top_tip.setFont(font);
        course_no_lb.setFont(font1);
        course_name_lb.setFont(font1);
        study_edit_lb.setFont(font1);
        study_time_lb.setFont(font1);
        teacher_lb.setFont(font1);
        location_lb.setFont(font1);
        study_edit_jt.setFont(font3);

        submit_lb.setOpaque(true);
        exit_lb.setOpaque(true);

        submit_lb.setBackground(color1);
        exit_lb.setBackground(color1);

        top_tip.setForeground(color1);
        submit_lb.setForeground(Color.white);
        exit_lb.setForeground(Color.white);
    }

}
