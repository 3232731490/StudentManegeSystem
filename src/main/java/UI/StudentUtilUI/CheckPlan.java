package UI.StudentUtilUI;

import Dao.AudienceDaoImpl;
import Entity.Audience;
import UI.LoginUI;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/28-18:55
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class CheckPlan extends JFrame {

    private JLabel top_img; //顶部图片
    private JLabel top_tip;     //顶部标题
    private JPanel jPanel;  //装table
    private String[] headers={"计划id","学院","专业","学年","学期"};
    private JLabel exit_btn;    //退出
    private JTable jTable;
    Object[][] rowdata=null;
    private List<Audience> all;

    //不同字体
    private Font font= StyleUI.font;  //标题字体
    private Font font3=StyleUI.font3; //按钮

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    /**
     * 读入计划归属数据
     */
    private void init_data()
    {
        rowdata=new Object[all.size()][5];
        int i=0;
        Iterator<Audience> iterator=all.iterator();
        while(iterator.hasNext())
        {
            Audience cur=iterator.next();
            rowdata[i][0]=cur.getPlan_id();
            rowdata[i][1]=cur.getDept();
            rowdata[i][2]=cur.getMajor();
            rowdata[i][3]=cur.getYear();
            rowdata[i][4]=cur.getSemester();
            i++;
        }
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
                if(e.getSource()==exit_btn){
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
    public CheckPlan(){
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(5,5,770,150);
        top_tip.setBounds(5,160,770,40);
        jPanel.setBounds(5,210,770,400);
        exit_btn.setBounds(5,625,770,30);

        exit_btn.addMouseListener(new MyBtnLisen(this));

        container.add(top_img);
        container.add(top_tip);
        container.add(jPanel);
        container.add(exit_btn);

        setTitle("学生选课系统");
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
        all= AudienceDaoImpl.query_all();
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\checkplan.jpg");
        top_img.setIcon(icon);
        init_data();
        top_tip=new JLabel("所有选课计划信息如下",SwingConstants.CENTER);
        exit_btn=new JLabel("退出",SwingConstants.CENTER);
        jTable=new JTable(rowdata,headers);

        top_tip.setFont(font);
        exit_btn.setFont(font3);
        jPanel=new JPanel(new BorderLayout());
        jPanel.add(jTable.getTableHeader(),BorderLayout.NORTH);
        jPanel.add(jTable,BorderLayout.CENTER);
        exit_btn.setOpaque(true);
        top_tip.setForeground(color1);
        exit_btn.setBackground(color1);
        exit_btn.setForeground(Color.white);
    }
}
