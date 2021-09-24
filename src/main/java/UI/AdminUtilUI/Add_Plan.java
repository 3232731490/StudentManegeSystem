package UI.AdminUtilUI;

import Dao.AudienceDaoImpl;
import Dao.CourseDaoImpl;
import Dao.PlanDaoImpl;
import Entity.Audience;
import Entity.Course;
import Entity.Plan;
import UI.StyleUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: 倪路
 * Time: 2021/6/28-10:40
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Add_Plan extends JFrame {

    private JLabel top_img; //顶部图片
    private JLabel top_tip; //顶部提示
    private JLabel left_tip;    //左部提示
    private JLabel dept_jl; //学院
    private JLabel major_jl;  //专业
    private JLabel courseListID_jl; //课程序号
    private JLabel schoolYear_jl; // 学年
    private JLabel semester_jl; //学期

    private JTextField courseListID_jt; //课程编号
    private JTextField major_jt;  //专业
    private JTextField schoolYear_jt;   //学年
    private JComboBox<String> semester_jt;   //学期
    private JLabel exit_btn;    //退出
    private JTable jTable;  //表格
    private JLabel submit_btn;     //提交按钮
    private JPanel jPanel;      //表格面板
    private DefaultTableModel defaultTableModel;
    private JComboBox<String> acadamy_jc; //学院

    String[] major_data={"计算机","轨道交通","香精香料","公共管理学院","环境学院","经济管理学院","建筑学院","新雅书院","临床医学院","药学院","电机工程与应用电子技术系","工程物理系","生命科学学院","美术学院","化学工程系","体育部"};

    private String[] columnNames = {"是否选中", "课程序号", "课程名", "学分", "学时", "教师编号", "上课地点"};
    //所有课程
    private Object[][] rowData = null;
    List<Course> allData= CourseDaoImpl.query_all_course();

    final static int SUCCESS=0;     //输入正确
    final static int COURSE_NO_ERROR=1;     //课程号输入有误
    final static int MAJOR_ERROR=2; //专业输入有误
    final static int YEAR_ERROR=3;  //学年输入有误
    final static int TABLE_EMPTY_ERROR=4; //未选课程

    final static String MAJOR_MATCH="^[\\u4e00-\\u9fa5]{2,}$";  //匹配专业  中文
    final static String COURSE_NO_MATCH="^\\d{6}$";  //匹配计划id  6位数字
    final static String YEAR_MATCH="^\\d{4}$";  //匹配学年    格式如 2019

    //不同字体
    private Font font = StyleUI.font; //标题
    private Font font3 = StyleUI.font3; //按钮

    //不同颜色
    private Color color1 = StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2 = StyleUI.color2;   //灰色--按钮进入

    private void init_data(){
        rowData=new Object[allData.size()][7];
        Iterator<Course> iterator=allData.iterator();
        int i=0;
        while(iterator.hasNext())
        {
            Course cur=iterator.next();
            rowData[i][0]="";
            rowData[i][1]=cur.getCno();
            rowData[i][2]=cur.getCname();
            rowData[i][3]=cur.getCt();
            rowData[i][4]=cur.getTime();
            rowData[i][5]=cur.getT_no();
            rowData[i][6]=cur.getLocation();
            i++;
        }
    }

    private int check(){
        String no=courseListID_jt.getText();
        String Year=schoolYear_jt.getText();
        String major=major_jt.getText();
        if (jTable.getSelectedRow() == -1) {
            return TABLE_EMPTY_ERROR;
        }else if(!Pattern.matches(COURSE_NO_MATCH,no))
        {
            return COURSE_NO_ERROR;
        }else if(!Pattern.matches(YEAR_MATCH,Year)||Year.equals("")||Integer.parseInt(Year)<2018||Integer.parseInt(Year)>2024){
            return YEAR_ERROR;
        }else if(!Pattern.matches(MAJOR_MATCH,major)){
            return MAJOR_ERROR;
        }
        return SUCCESS;
    }

    /**
     * 监听按钮事件
     */
    private class MyBtnLisen extends MouseAdapter {
        JFrame jf;

        public MyBtnLisen(JFrame jf) {
            this.jf = jf;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == submit_btn) {
                int result=check();
                if(result==SUCCESS){
                    String plan_id=courseListID_jt.getText();
                    String dept=(String)acadamy_jc.getSelectedItem();
                    String major=major_jt.getText();
                    String year=schoolYear_jt.getText();
                    String sema=(String)semester_jt.getSelectedItem();
                    if(AudienceDaoImpl.is_existed(plan_id))
                    {
                        JOptionPane.showConfirmDialog(jf, "当前计划编号已存在，请检查后再添加", "错误提示", JOptionPane.YES_NO_OPTION);
                        return;
                    }
                    if (JOptionPane.showConfirmDialog(jf, "请问您是否确认要提交表单信息？", "确认窗口", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                        Audience audience=new Audience(plan_id,dept,major,year,sema);
                        AudienceDaoImpl.insert_audience(audience);
                        for (int rowindex : jTable.getSelectedRows()) {
                            PlanDaoImpl.insert_plan(new Plan(plan_id.trim(),((String)jTable.getValueAt(rowindex, 1)).trim()));
                        }
                        JOptionPane.showMessageDialog(jf, "当前计划添加成功", "成功提示", JOptionPane.PLAIN_MESSAGE);
                    }
                }else if(result==TABLE_EMPTY_ERROR){
                    JOptionPane.showMessageDialog(jf,"您还未选择课程","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==COURSE_NO_ERROR)
                {
                    JOptionPane.showMessageDialog(jf,"您输入的计划id有误，必须为6位数字 如191004","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==MAJOR_ERROR){
                    JOptionPane.showMessageDialog(jf,"您的专业输入有误，专业必须为中文","错误提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==YEAR_ERROR){
                    JOptionPane.showMessageDialog(jf,"您的学年输入有误，专业必须为真实年份且只包含近三年计划 如2019","错误提示",JOptionPane.PLAIN_MESSAGE);
                }
            } else if (e.getSource() == exit_btn) {
                if (JOptionPane.showConfirmDialog(jf, "是否确认退出？", "确认窗口", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    jf.dispose();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel jl = (JLabel) e.getSource();
            jl.setBackground(color2);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel jl = (JLabel) e.getSource();
            jl.setBackground(color1);
        }
    }


    /**
     * 构造方法 展示窗体
     */
    public Add_Plan() {
        Container container = this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(5, 0, 800, 150);
        top_tip.setBounds(5, 160, 800, 30);
        jPanel.setBounds(5, 240, 800, 150);
        left_tip.setBounds(5,195,800,40);
        courseListID_jt.setBounds(450, 400, 180, 30);
        courseListID_jl.setBounds(250, 400, 180, 30);
        dept_jl.setBounds(250, 440, 180, 30);
        acadamy_jc.setBounds(450, 440, 180, 30);
        major_jt.setBounds(450, 480, 180, 30);
        major_jl.setBounds(250, 480, 180, 30);
        schoolYear_jt.setBounds(450, 520, 180, 30);
        schoolYear_jl.setBounds(250, 520, 180, 30);
        semester_jl.setBounds(250, 560, 180, 30);
        semester_jt.setBounds(450, 560, 180, 30);

        submit_btn.setBounds(0, 600, 800, 30);
        exit_btn.setBounds(0, 640, 800, 30);

        MyBtnLisen myBtnLisen = new MyBtnLisen(this);
        submit_btn.addMouseListener(myBtnLisen);
        exit_btn.addMouseListener(myBtnLisen);

        container.add(acadamy_jc);
        container.add(top_img);
        container.add(top_tip);
        container.add(left_tip);
        container.add(courseListID_jt);
        container.add(courseListID_jl);
        container.add(dept_jl);
        container.add(major_jt);
        container.add(major_jl);
        container.add(schoolYear_jt);
        container.add(schoolYear_jl);
        container.add(semester_jl);
        container.add(semester_jt);
        container.add(jPanel);
        container.add(submit_btn);
        container.add(exit_btn);

        setTitle("选课计划");
        setBackground(Color.LIGHT_GRAY);
        setSize(820, 710);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);
    }

    /**
     * 初始化组件
     */
    private void init() {
        top_img = new JLabel();
        Icon icon = new ImageIcon("src\\main\\resources\\add_course.jpg");
        top_img.setIcon(icon);
        exit_btn = new JLabel("退出", SwingConstants.CENTER);
        submit_btn = new JLabel("提交", SwingConstants.CENTER);
        top_tip = new JLabel("请输入计划信息", SwingConstants.CENTER);
        courseListID_jl = new JLabel("计划id");
        dept_jl = new JLabel("学院");
        major_jl = new JLabel("专业");
        schoolYear_jl = new JLabel("学年");
        semester_jl = new JLabel("学期");
        left_tip=new JLabel("请选择计划包含课程",SwingConstants.LEFT);
        courseListID_jt = new JTextField();
        major_jt = new JTextField();
        schoolYear_jt = new JTextField();
        semester_jt = new JComboBox<>(new String[]{"一","二"});

        init_data();
        semester_jt.setFont(font3);
        jPanel = new JPanel(new BorderLayout());
        defaultTableModel = new DefaultTableModel(rowData, columnNames) {
            /* (non-Javadoc)
             * 重写方法，判断表单元格是否可编辑
             * 可以通过row和column索引判断某一个单元格是否可编辑
             * 此处设为都不可编辑
             * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable=new JTable(defaultTableModel);
        jTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            /*(non-Javadoc)
                    * 此方法用于向方法调用者返回某一单元格的渲染器（即显示数据的组建--或控件）
                    * 可以为JCheckBox JComboBox JTextArea 等
             * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
             */
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                // 创建用于返回的渲染组件
                JCheckBox ck = new JCheckBox();
                // 使具有焦点的行对应的复选框选中
                ck.setSelected(isSelected);
                // 设置单选box.setSelected(hasFocus);
                // 使复选框在单元格内居中显示
                ck.setHorizontalAlignment((int) 0.5f);
                return ck;
            }
        });
        jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
        jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);

        exit_btn.setFont(font3);
        submit_btn.setFont(font3);
        top_tip.setFont(font);
        courseListID_jl.setFont(new Font("华文细黑", 1, 18));
        dept_jl.setFont(new Font("华文细黑", 1, 18));
        major_jl.setFont(new Font("华文细黑", 1, 18));
        schoolYear_jl.setFont(new Font("华文细黑", 1, 18));
        semester_jl.setFont(new Font("华文细黑", 1, 18));
        left_tip.setFont(new Font("华文细黑", 1, 18));
        acadamy_jc=new JComboBox<>(major_data);
        acadamy_jc.setFont(font3);

        exit_btn.setOpaque(true);
        submit_btn.setOpaque(true);

        top_tip.setForeground(color1);
        exit_btn.setBackground(color1);
        submit_btn.setBackground(color1);
        courseListID_jl.setForeground(color1);
        dept_jl.setForeground(color1);
        major_jl.setForeground(color1);
        schoolYear_jl.setForeground(color1);
        semester_jl.setForeground(color1);
        left_tip.setForeground(color1);

        exit_btn.setForeground(Color.white);
        submit_btn.setForeground(Color.white);
    }

    public static void main(String[] args) {
        new Add_Plan();
    }
}
