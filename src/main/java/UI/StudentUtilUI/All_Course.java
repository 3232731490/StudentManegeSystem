package UI.StudentUtilUI;

import Dao.AudienceDaoImpl;
import Dao.PlanDaoImpl;
import Dao.SCDaoImpl;
import Dao.StuDaoImpl;
import Entity.Audience;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/28-9:34
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class All_Course extends JFrame {
    private String id;
    private JLabel courselist_lb; //输入计划id
    private JLabel submit_lb;   //一键选课
    private JTextField courselist_jt;
    private JLabel top_img; //顶部图片
    private JLabel top_tip; //顶部提示
    private JLabel exit_btn;    //退出
    private JLabel bottom_img;  //底部图片
    private JPanel jPanel;  //组件面板
    private boolean is_existed=false; //是否存在计划

    //不同字体
    private Font font= StyleUI.font; //单选框
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    private static final String ID_MATCH="^\\d{6}$";
    private MyBtnLisen myBtnLisen=new MyBtnLisen(this);

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
            if(e.getSource()==submit_lb){
                String plan_id=courselist_jt.getText();
                if("".equals(plan_id)){
                    JOptionPane.showMessageDialog(jf,"您还未输入计划ID","输入提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }else if(!Pattern.matches(ID_MATCH,plan_id)){
                    JOptionPane.showMessageDialog(jf,"id格式错误，必须为6位数字","错误提示",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                is_existed= AudienceDaoImpl.is_existed(plan_id);
                if(is_existed){
                    List<String> cnos= PlanDaoImpl.query_all_course(plan_id);
                    Iterator<String> iterator=cnos.iterator();
                    String stu_major=StuDaoImpl.get_major(id).trim();
                    String audience_major=AudienceDaoImpl.get_major(plan_id).trim();
                    if(!stu_major.equals(audience_major))
                    {
                        if(JOptionPane.showConfirmDialog(jf,"专业"+audience_major+"与你的专业"+stu_major+"不一致，是否继续跨专业选课","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
                        {
                            return;
                        }
                    }
                    if(JOptionPane.showConfirmDialog(jf,"是否确认选修计划课程？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                    {
                        while(iterator.hasNext())
                        {
                            String cur=iterator.next();
                            if(!SCDaoImpl.is_existed(id,cur))   //没有选择该课才选上
                                SCDaoImpl.insert_course(id,cur);
                        }
                        jf.dispose();
                    }
                }else
                {
                    JOptionPane.showMessageDialog(jf,"未查询到计划ID为"+plan_id+"的选课计划计划","错误提示",JOptionPane.PLAIN_MESSAGE);
                }
            }else if(e.getSource()==exit_btn){
                if(JOptionPane.showConfirmDialog(jf,"是否确认退出？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    jf.dispose();
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
    public All_Course(String id){
        this.id=id;
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(0,5,800,150);
        top_tip.setBounds(0,160,800,40);
        courselist_lb.setBounds(150,40,150,30);
        courselist_jt.setBounds(400,40,200,30);
        submit_lb.setBounds(300,120,200,40);
        jPanel.setBounds(0,210,800,200);
        bottom_img.setBounds(0,420,800,200);
        exit_btn.setBounds(0,630,800,40);
        jPanel.add(courselist_jt);
        jPanel.add(courselist_lb);
        jPanel.add(submit_lb);

        submit_lb.addMouseListener(myBtnLisen);
        exit_btn.addMouseListener(myBtnLisen);

        container.add(top_img);
        container.add(top_tip);
        container.add(jPanel);
        container.add(bottom_img);
        container.add(exit_btn);
        setTitle("一键选课");
        setBackground(Color.LIGHT_GRAY);
        setSize(820,710);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * 初始化组件
     */
    private void init(){
        courselist_lb=new JLabel("请输入计划ID",SwingConstants.RIGHT);
        top_tip=new JLabel("欢迎使用一键选课功能",SwingConstants.CENTER);
        exit_btn=new JLabel("退出",SwingConstants.CENTER);
        submit_lb=new JLabel("一键选课",SwingConstants.CENTER);
        courselist_jt=new JTextField();
        jPanel=new JPanel(null);
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\allcourse1.jpg");
        top_img.setIcon(icon);
        bottom_img=new JLabel();
        icon=new ImageIcon("src\\main\\resources\\allcourse2.jpg");
        bottom_img.setIcon(icon);

        top_tip.setFont(font);
        submit_lb.setFont(font3);
        exit_btn.setFont(font3);
        courselist_lb.setFont(font4);


        exit_btn.setOpaque(true);
        submit_lb.setOpaque(true);

        exit_btn.setBackground(color1);
        submit_lb.setBackground(color1);
        top_tip.setForeground(color1);

        exit_btn.setForeground(Color.white);
        submit_lb.setForeground(Color.white);
    }

    public static void main(String[] args) {
        new All_Course("1910400701");
    }
}
