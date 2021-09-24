package UI.AdminUtilUI;

import Service.StuService;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/27-10:52
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Add_Stu extends JFrame {
    private JLabel top_img;     //顶部图片
    private JLabel top_tip;     //顶部提示
    private JLabel right_img;   //右部图片

    private JLabel stu_no_lb;    //学号
    private JLabel stu_name_lb;  //姓名
    private JLabel stu_major_lb;      //专业
    private JLabel academy_lb;      //学院
    private JLabel age_lb ;    //年龄
    private JLabel sex_lb;  //性别

    private JLabel submit_lb;   //提交
    private JLabel exit_lb;     //退出

    private JComboBox<String> sex_jt;     //性别
    private JTextField age_jt;  //年龄
    private JTextField stu_no_jt;    //学号
    private JTextField stu_name_jt;  //姓名
    private JComboBox<String> acadamy_jc; //学院
    String[] major_data={"计算机","轨道交通","香精香料","公共管理学院","环境学院","经济管理学院","建筑学院","新雅书院","临床医学院","药学院","电机工程与应用电子技术系","工程物理系","生命科学学院","美术学院","化学工程系","体育部"};
    private JTextField stu_major_jt;    //专业

    final static int SUCCESS=0;     //输入正确
    final static int STU_NAME_ERROR=1;  //姓名输入有误
    final static int STU_NO_ERROR=2;     //学号输入有误
    final static int MAJOR_ERROR=3;  //专业输入有误
    final static int AGE_ERROR=4;  //年龄输入错误

    final static String STU_NAME_MATCH="^([\\u4e00-\\u9fa5]{2,})|([A-Za-z]{2,})$";  //匹配姓名（中文或英文）
    final static String STU_NO_MATCH="^(\\d{10})|(\\d{9}Y|y)$";  //匹配学号   10位数字或9位加y
    final static String MAJOR_MATCH="^[\\u4e00-\\u9fa5]{2,}$";  //匹配专业  中文
    final static String AGE_MATCH="^\\d{1,2}$";  //匹配年龄 8-40


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
        String stu_name=stu_name_jt.getText();
        String stu_no=stu_no_jt.getText();
        String major=stu_major_jt.getText();
        String age=age_jt.getText();

        if(!Pattern.matches(STU_NAME_MATCH,stu_name))
        {
            return STU_NAME_ERROR;
        }else if(!Pattern.matches(STU_NO_MATCH,stu_no))
        {
            return STU_NO_ERROR;
        }else if(!Pattern.matches(MAJOR_MATCH,major))
        {
            return MAJOR_ERROR;
        }else if(!Pattern.matches(AGE_MATCH,age)||"".equals(age)||Integer.parseInt(age)<8||Integer.parseInt(age)>40)
        {
            return AGE_ERROR;
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
                if(result==SUCCESS)
                {
                    boolean is_existed=false;
                    String sno=stu_no_jt.getText();
                    String sname=stu_name_jt.getText();
                    String sex=(String)sex_jt.getSelectedItem();
                    int age=Integer.parseInt(age_jt.getText());
                    String dept=(String)acadamy_jc.getSelectedItem();
                    String major=stu_major_jt.getText();
                    is_existed= StuService.is_existed(sno);
                    if(is_existed)
                    {
                        JOptionPane.showMessageDialog(jf,"当前数据库已存在该学生，您可以选择修改学生信息或重新检查后添加","提示信息",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        StuService.insert_stu(sno,sname,sex,age,dept,major);
                        JOptionPane.showMessageDialog(jf,"增加学生成功","增加成功",JOptionPane.PLAIN_MESSAGE);
                    }
                }else if(result==STU_NO_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的学号有误，必须为如下格式 \n1910400731或191040073Y","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==STU_NAME_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的姓名有误，必须为中文或英文","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==MAJOR_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的专业有误，必须为中文","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==AGE_ERROR){
                    JOptionPane.showMessageDialog(jf,"您输入的年龄有误，必须在8-40之间","错误提示",JOptionPane.PLAIN_MESSAGE);
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
    public Add_Stu(){
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(0,5,800,105);
        top_tip.setBounds(0,115,400,40);

        JPanel jp1=new JPanel(null);
        jp1.setBounds(0,160,800,460);
        stu_no_lb.setBounds(60,31,80,30);
        stu_no_jt.setBounds(190,31,150,30);
        stu_name_lb.setBounds(60,107,80,30);
        stu_name_jt.setBounds(190,107,150,30);
        age_lb.setBounds(60,183,80,30);
        age_jt.setBounds(190,183,60,30);

        stu_major_lb.setBounds(60,259,80,30);
        stu_major_jt.setBounds(190,259,150,30);

        academy_lb.setBounds(60,335,80,30);
        acadamy_jc.setBounds(190,335,150,30);

        sex_lb.setBounds(60,411,80,30);
        sex_jt.setBounds(190,411,150,30);
        right_img.setBounds(420,5,360,440);
        submit_lb.setBounds(0,625,800,25);
        exit_lb.setBounds(0,655,800,25);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        submit_lb.addMouseListener(myBtnLisen);
        exit_lb.addMouseListener(myBtnLisen);


        jp1.add(stu_major_jt);
        jp1.add(sex_jt);
        jp1.add(acadamy_jc);
        jp1.add(age_lb);
        jp1.add(age_jt);
        jp1.add(stu_name_lb);
        jp1.add(stu_no_jt);
        jp1.add(stu_name_jt);
        jp1.add(stu_no_lb);
        jp1.add(stu_name_lb);
        jp1.add(stu_major_lb);
        jp1.add(academy_lb);
        jp1.add(sex_lb);
        jp1.add(right_img);

        container.add(top_img);
        container.add(top_tip);
        container.add(jp1);
        container.add(submit_lb);
        container.add(exit_lb);

        setTitle("增加学生");
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
        Icon icon=new ImageIcon("src\\main\\resources\\addstu.jpg");
        top_img.setIcon(icon);
        right_img=new JLabel();
        icon=new ImageIcon("src\\main\\resources\\fafa.jpg");
        right_img.setIcon(icon);
        top_tip=new JLabel("请输入学生信息",SwingConstants.CENTER);
        stu_name_lb=new JLabel("姓名",SwingConstants.LEFT);
        stu_no_lb=new JLabel("学号",SwingConstants.LEFT);
        stu_major_lb=new JLabel("专业",SwingConstants.LEFT);
        academy_lb=new JLabel("学院",SwingConstants.LEFT);
        //home_lb=new JLabel("户籍",SwingConstants.LEFT);
        age_lb=new JLabel("年龄",SwingConstants.LEFT);
        sex_lb=new JLabel("性别",SwingConstants.LEFT);
        String[] sex_data={"男","女"};
        sex_jt=new JComboBox<>(sex_data);
        //String[] home_data={"北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区"};
        age_jt=new JTextField();
        submit_lb=new JLabel("提交",SwingConstants.CENTER);
        exit_lb=new JLabel("退出",SwingConstants.CENTER);
        acadamy_jc=new JComboBox<>(major_data);
        stu_major_jt=new JTextField();

        stu_no_jt=new JTextField();
        stu_name_jt=new JTextField();


        acadamy_jc.setFont(font3);
        submit_lb.setFont(font1);
        exit_lb.setFont(font1);
        top_tip.setFont(font);
        stu_no_lb.setFont(font1);
        stu_name_lb.setFont(font1);
        stu_major_lb.setFont(font1);
        age_lb.setFont(font1);
        academy_lb.setFont(font1);
        sex_lb.setFont(font1);
        sex_jt.setFont(font3);

        submit_lb.setOpaque(true);
        exit_lb.setOpaque(true);

        submit_lb.setBackground(color1);
        exit_lb.setBackground(color1);

        top_tip.setForeground(color1);
        submit_lb.setForeground(Color.white);
        exit_lb.setForeground(Color.white);
    }
}
