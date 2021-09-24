package UI.StudentUtilUI;

import Service.UserService;
import UI.AdminUtilUI.*;
import UI.LoginUI;
import UI.StyleUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.regex.Pattern;


/**
 * @author: 倪路
 * Time: 2021/6/27-14:23
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Modify_User extends JFrame {
    private JLabel top_img; //顶部图片
    private JLabel top_tip;     //顶部标题
    private JLabel Exit_btn;        //退出系统
    private JLabel modi_btn;        //修改按钮
    private String id;  //学生id

    private JTextField vadi_ran;    //验证码
    private JTextField vadi_jt;     //验证码
    private JLabel vadi_lb; //验证码
    private JPasswordField password_jp; //密码
    private JLabel password_lb;     //密码
    private JPasswordField confirm_jp;  //确认密码
    private JLabel confirm_lb;      //确认密码
    Object[] data={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "0","1","2","3","4","5","5","7","8","9"};
    private  String cur_vadi;    //当前验证码
    private JLabel bottom_img;


    //不同字体
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗
    private Font font1=StyleUI.font1; //输入提示

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    final static int SUCCESS=0;     //输入正确
    final static int PASS_ERROR=1;  //密码输入有误
    final static int CONFIRM_ERROR=2;   //确认密码输入有误
    final static int VADI_ERROR=3;  //验证码有误

    final static String PASS_MATCH="^[a-zA-Z]\\w{5,17}$";  //匹配密码

    /**
     * 更新验证码
     */
    private void create_vadi()
    {
        StringBuilder S=new StringBuilder("    ");
        S.append((String)data[new Random(System.currentTimeMillis()).nextInt(data.length)]);
        S.append((String)data[new Random(System.currentTimeMillis()+1).nextInt(data.length)]);
        S.append((String)data[new Random(System.currentTimeMillis()+2).nextInt(data.length)]);
        S.append((String)data[new Random(System.currentTimeMillis()+3).nextInt(data.length)]);
        cur_vadi=S.toString();
        vadi_ran.setText(cur_vadi);
    }

    /**
     * 检查用户输入是否合法
     * @return  返回错误信息
     */
    private int check()
    {
        String password=new String(password_jp.getPassword());
        String confirm=new String(confirm_jp.getPassword());
        String vadi=vadi_jt.getText();
        if(!Pattern.matches(PASS_MATCH,password))
        {
            return PASS_ERROR;
        }else if(!Pattern.matches(PASS_MATCH,confirm)||!password.equals(confirm))
        {
            return PASS_ERROR;
        }else if(!cur_vadi.trim().equals(vadi))
        {
            return VADI_ERROR;
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
            if(e.getSource()==modi_btn)
            {
                int result=check();
                create_vadi();  //一次用完就更新验证码
                if(result==SUCCESS)
                {
                    if(JOptionPane.showConfirmDialog(jf,"是否确认修改密码？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                    {
                        String pass=new String(password_jp.getPassword());
                        UserService.update_user(id,pass);
                    }
                }else if(result==PASS_ERROR)
                {
                    JOptionPane.showConfirmDialog(jf,"密码输入不合法!\n(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)","密码不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==CONFIRM_ERROR)
                {
                    JOptionPane.showConfirmDialog(jf,"确认密码输入不合法!\n(密码本身不合法或两次密码不相同)","确认密码不合法",JOptionPane.OK_CANCEL_OPTION);
                }else if(result==VADI_ERROR)
                {
                    JOptionPane.showConfirmDialog(jf,"验证码输入不正确!","验证码错误",JOptionPane.OK_CANCEL_OPTION);
                }
            }else if(e.getSource()==Exit_btn){
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
     * 构造方法 展示窗体
     */
    public Modify_User(String id){
        this.id=id;
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(0,5,800,150);
        top_tip.setBounds(0,160,800,30);
        password_lb.setBounds(200,210,80,30);
        password_jp.setBounds(400,210,150,30);
        confirm_lb.setBounds(200,285,80,30);
        confirm_jp.setBounds(400,285,150,30);
        vadi_lb.setBounds(200,360,80,30);
        vadi_jt.setBounds(400,360,75,30);
        vadi_ran.setBounds(480,360,75,30);
        bottom_img.setBounds(0,410,800,200);
        modi_btn.setBounds(0,615,800,30);
        Exit_btn.setBounds(0,650,800,30);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        modi_btn.addMouseListener(myBtnLisen);
        Exit_btn.addMouseListener(myBtnLisen);

        container.add(top_img);
        container.add(top_tip);
        container.add(password_jp);
        container.add(password_lb);
        container.add(confirm_jp);
        container.add(confirm_lb);
        container.add(vadi_jt);
        container.add(vadi_lb);
        container.add(vadi_ran);
        container.add(modi_btn);
        container.add(Exit_btn);
        container.add(bottom_img);

        setTitle("修改密码");
        setBackground(Color.LIGHT_GRAY);
        setSize(820,720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * 初始化组件
     */
    public void init(){
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\modi_user.jpg");
        bottom_img=new JLabel();
        bottom_img.setIcon(icon);
        icon=new ImageIcon("src\\main\\resources\\modi_bt.jpg");
        top_img.setIcon(icon);
        Exit_btn=new JLabel("退出",SwingConstants.CENTER);
        modi_btn=new JLabel("修改",SwingConstants.CENTER);
        vadi_ran=new JTextField(10);
        vadi_jt=new JTextField();
        vadi_lb=new JLabel("验证码");
        password_jp=new JPasswordField();
        password_lb=new JLabel("密码",SwingConstants.LEFT);
        confirm_jp=new JPasswordField();
        confirm_lb=new JLabel("确认密码",SwingConstants.LEFT);
        top_tip=new JLabel("请输入您的新密码",SwingConstants.LEFT);
        create_vadi();  //初始化验证码
        Exit_btn.setFont(font3);
        modi_btn.setFont(font3);
        password_lb.setFont(font1);
        confirm_lb.setFont(font1);
        vadi_lb.setFont(font1);
        password_lb.setForeground(color1);
        confirm_lb.setForeground(color1);
        vadi_lb.setForeground(color1);
        vadi_ran.setEnabled(false);
        vadi_ran.setFont(font4);
        Exit_btn.setOpaque(true);
        modi_btn.setOpaque(true);
        top_tip.setFont(font1);
        Exit_btn.setBackground(color1);
        modi_btn.setBackground(color1);

        Exit_btn.setForeground(Color.white);
        modi_btn.setForeground(Color.white);
    }
}
