package UI.StudentUtilUI;

import Dao.StuDaoImpl;
import Dao.UserDaoImpl;
import Entity.Student;
import Service.UserService;
import UI.LoginUI;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/29-10:20
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Complete_Info extends JFrame {
    private JLabel top_img;     //顶部图片
    private JLabel top_tip;     //顶部提示
    private String id;  //当前学生id
    private JLabel stu_major_lb;      //专业
    private JLabel academy_lb;      //学院
    private JLabel submit_lb;   //提交
    private JLabel exit_lb;     //退出
    private JComboBox<String> acadamy_jc; //学院
    String[] major_data={"计算机","轨道交通","香精香料","公共管理学院","环境学院","经济管理学院","建筑学院","新雅书院","临床医学院","药学院","电机工程与应用电子技术系","工程物理系","生命科学学院","美术学院","化学工程系","体育部"};
    private JTextField stu_major_jt;    //专业
    final static String MAJOR_MATCH="^[\\u4e00-\\u9fa5]{2,}$";  //匹配专业  中文

    //不同字体
    private Font font= StyleUI.font;  //标题字体
    private Font font1=StyleUI.font1; //输入提示
    private Font font3=StyleUI.font3; //按钮

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
            if(e.getSource()==exit_lb)
            {
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
                }
            }else if(e.getSource()==submit_lb)
            {
                String dept=(String)acadamy_jc.getSelectedItem();
                String major=stu_major_jt.getText();
                if(!Pattern.matches(MAJOR_MATCH,major))
                {
                    JOptionPane.showMessageDialog(jf,"您的专业信息输入有误，必须为中文 如高等数学","错误提示",JOptionPane.PLAIN_MESSAGE);
                }
                Student stu= StuDaoImpl.query_a_stu(id);
                stu.setDept(dept);
                stu.setMajor(major);
                if(JOptionPane.showConfirmDialog(jf,"是否确认提交表单信息？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    String pass= UserDaoImpl.get_pass(id);
                    StuDaoImpl.update_stu(stu,id);
                    UserService.del_user(id);
                    UserService.insert_user(id,pass);
                    if(JOptionPane.showConfirmDialog(jf,"表单信息已提交，即将返回重新登陆","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                    {
                        jf.dispose();
                        new LoginUI();
                    }
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
    public Complete_Info(String id){
        this.id=id;
        Container container=this.getContentPane();
        container.setLayout(null);
        init();
        JPanel jPanel=new JPanel(null);

        top_img.setBounds(0,5,800,150);
        top_tip.setBounds(0,155,800,40);
        jPanel.setBounds(0,200,800,400);
        academy_lb.setBounds(200,150,200,30);
        acadamy_jc.setBounds(450,150,200,30);
        stu_major_lb.setBounds(200,250,200,30);
        stu_major_jt.setBounds(450,250,200,30);
        submit_lb.setBounds(0,610,800,30);
        exit_lb.setBounds(0,645,800,30);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        submit_lb.addMouseListener(myBtnLisen);
        exit_lb.addMouseListener(myBtnLisen);

        jPanel.add(acadamy_jc);
        jPanel.add(academy_lb);
        jPanel.add(stu_major_jt);
        jPanel.add(stu_major_lb);

        container.add(top_img);
        container.add(top_tip);
        container.add(jPanel);
        container.add(submit_lb);
        container.add(exit_lb);

        setTitle("完善信息");
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
    private void init()
    {
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\addstu.jpg");
        top_img.setIcon(icon);

        top_tip=new JLabel("请输入您的信息",SwingConstants.CENTER);
        stu_major_lb=new JLabel("专业",SwingConstants.LEFT);
        academy_lb=new JLabel("学院",SwingConstants.LEFT);
        submit_lb=new JLabel("提交",SwingConstants.CENTER);
        exit_lb=new JLabel("退出",SwingConstants.CENTER);
        acadamy_jc=new JComboBox<>(major_data);
        stu_major_jt=new JTextField();

        acadamy_jc.setFont(font3);
        submit_lb.setFont(font1);
        exit_lb.setFont(font1);
        top_tip.setFont(font);
        academy_lb.setFont(font1);
        stu_major_lb.setFont(font1);

        submit_lb.setOpaque(true);
        exit_lb.setOpaque(true);

        submit_lb.setBackground(color1);
        exit_lb.setBackground(color1);

        top_tip.setForeground(color1);
        submit_lb.setForeground(Color.white);
        exit_lb.setForeground(Color.white);
    }

    public static void main(String[] args) {
        new Complete_Info("1910400733");
    }
}
