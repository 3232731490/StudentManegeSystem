package UI.AdminUtilUI;

import Dao.CourseDaoImpl;
import Dao.StuDaoImpl;
import Entity.Course;
import Entity.Student;
import UI.RoundBorderUI;
import UI.StyleUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: 倪路
 * Time: 2021/6/27-8:50
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Del_Stu extends JFrame {


    private JLabel top_img; //顶部图片
    private JLabel top_tip; //顶部提示
    private JLabel left_tip;    //左部提示
    private JTextField del_jt;  //输入框
    private JLabel query;       //查询
    private JLabel exit_btn;    //退出
    private JLabel clear;       //清空
    private JPanel jPanel;      //存放查询结果面板
    private JLabel del_btn;     //删除按钮
    private JLabel show_all;    //显示全部课程
    private JRadioButton stu_no;    //课程号
    private JRadioButton stu_name;   //课程名
    private ButtonGroup buttonGroup;
    private JTable jTable;  //表格
    private DefaultTableModel defaultTableModel;
    private JTable empty_tb;    //空表
    private JTable all_tb;      //满表
    private boolean is_no = true;    //表示当前查询方式

    //展示查询到的课程详细信息
    String[] columnNames = {"是否选中", "学号", "姓名", "性别", "年龄", "学院", "专业"};
    Object[][] rowData = null;
    List<Student> studentList = new ArrayList<>();

    //不同字体
    private Font font2 = StyleUI.font2; //单选框
    private Font font3 = StyleUI.font3; //按钮
    private Font font4 = StyleUI.font4;   //加粗

    //不同颜色
    private Color color1 = StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2 = StyleUI.color2;   //灰色--按钮进入

    private void init_data_no() {
        String Sno = del_jt.getText();
        Student stu = StuDaoImpl.query_a_stu(Sno);
        if(stu!=null){
            rowData = new Object[1][7];
            int i = 0;
            rowData[i][0] = "";
            rowData[i][1] = stu.getSno();
            rowData[i][2] = stu.getSname();
            rowData[i][3] = stu.getSex();
            rowData[i][4] = stu.getAge();
            rowData[i][5] = stu.getDept();
            rowData[i][6] = stu.getMajor();
        }
        else{
            rowData=new Object[][]{};
        }
        update_tb();
    }

    private void init_data_name() {
        String sname = del_jt.getText();
        studentList = StuDaoImpl.query_name_stu(sname);
        if(studentList!=null){
            rowData = new Object[studentList.size()][7];
            int i = 0;
            Iterator<Student> iterator = studentList.iterator();
            while (iterator.hasNext()) {
                Student stu = iterator.next();
                rowData[i][0] = "";
                rowData[i][1] = stu.getSno();
                rowData[i][2] = stu.getSname();
                rowData[i][3] = stu.getSex();
                rowData[i][4] = stu.getAge();
                rowData[i][5] = stu.getDept();
                rowData[i][6] = stu.getMajor();
                i++;
            }
        }else {
            rowData=new Object[][]{};
        }
        update_tb();
    }

    private void init_all() {
        String sname = del_jt.getText();
        studentList = StuDaoImpl.query_stu();
        rowData = new Object[studentList.size()][7];
        int i = 0;
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student stu = iterator.next();
            rowData[i][0] = "";
            rowData[i][1] = stu.getSno();
            rowData[i][2] = stu.getSname();
            rowData[i][3] = stu.getSex();
            rowData[i][4] = stu.getAge();
            rowData[i][5] = stu.getDept();
            rowData[i][6] = stu.getMajor();
            i++;
        }
        update_tb();
    }


    private void update_tb() {
        defaultTableModel.setDataVector(rowData, columnNames);
        jTable = new JTable(defaultTableModel);
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
            if (e.getSource() == query) {
                if (is_no) {
                    init_data_no();
                    jPanel.removeAll();
                    jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
                    jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
                    jPanel.updateUI();
                } else {
                    init_data_name();
                    jPanel.removeAll();
                    jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
                    jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
                    jPanel.updateUI();
                }
            } else if (e.getSource() == clear) {
                del_jt.setText("");
                stu_no.setSelected(true);
                jPanel.removeAll();
                jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
                jPanel.updateUI();
            } else if (e.getSource() == del_btn) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(jf, "您还未进行选择...", "消息窗口", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (JOptionPane.showConfirmDialog(jf, "请问您是否确认要删除选中项？", "确认窗口", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    for (int rowindex : jTable.getSelectedRows()) {
                        StuDaoImpl.del_stu((String) jTable.getValueAt(rowindex, 1));
                    }
                }

            } else if (e.getSource() == exit_btn) {
                if (JOptionPane.showConfirmDialog(jf, "是否确认退出？", "确认窗口", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    jf.dispose();
                }
            } else if (e.getSource() == show_all) {
                jPanel.removeAll();
                init_all();
                jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
                jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
                jPanel.updateUI();
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
     * 监听单选框事件
     */
    private class MyRatioListen implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getItem() == stu_name) {
                if (stu_name.isSelected())
                    is_no = false;
            } else if (e.getItem() == stu_no) {
                if (stu_no.isSelected())
                    is_no = true;
            }
        }
    }

    /**
     * 构造函数，展示窗体
     */
    public Del_Stu() {
        Container container = this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(0, 0, 800, 150);

        left_tip.setBounds(60, 180, 140, 35);
        del_jt.setBounds(210, 180, 150, 30);
        stu_name.setBounds(370, 180, 80, 30);
        stu_no.setBounds(460, 180, 80, 30);
        query.setBounds(570, 180, 45, 30);
        clear.setBounds(620, 180, 45, 30);
        show_all.setBounds(670, 180, 90, 30);
        top_tip.setBounds(0, 220, 600, 30);
        jPanel.setBounds(0, 250, 800, 340);
        jPanel.setBorder(new RoundBorderUI());
        del_btn.setBounds(0, 615, 800, 30);
        exit_btn.setBounds(0, 650, 800, 30);

        MyBtnLisen myBtnLisen = new MyBtnLisen(this);
        query.addMouseListener(myBtnLisen);
        clear.addMouseListener(myBtnLisen);
        del_btn.addMouseListener(myBtnLisen);
        exit_btn.addMouseListener(myBtnLisen);
        show_all.addMouseListener(myBtnLisen);

        MyRatioListen myRatioListen = new MyRatioListen();
        stu_no.addItemListener(myRatioListen);
        stu_name.addItemListener(myRatioListen);
        container.add(show_all);
        container.add(stu_name);
        container.add(stu_no);
        container.add(del_btn);
        container.add(left_tip);
        container.add(top_tip);
        container.add(del_jt);
        container.add(jPanel);
        container.add(query);
        container.add(clear);
        container.add(exit_btn);
        container.add(top_img);

        setTitle("删除学生");
        setBackground(Color.LIGHT_GRAY);
        setSize(815, 720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }


    /**
     * 初始化组件
     */
    private void init() {
        top_img = new JLabel();
        Icon icon = new ImageIcon("src\\main\\resources\\del_stu.jpg");
        top_img.setIcon(icon);

        left_tip = new JLabel("请输入查询条件");
        del_jt = new JTextField();
        query = new JLabel("查询", SwingConstants.CENTER);
        exit_btn = new JLabel("退出", SwingConstants.CENTER);
        clear = new JLabel("清空", SwingConstants.CENTER);
        jPanel = new JPanel(new BorderLayout());
        top_tip = new JLabel("查询结果为： ", SwingConstants.LEFT);
        del_btn = new JLabel("删除", SwingConstants.CENTER);
        stu_name = new JRadioButton("姓名");
        stu_no = new JRadioButton("学号");
        show_all = new JLabel("显示全部", SwingConstants.CENTER);
        stu_no.setSelected(true);
        empty_tb = new JTable(rowData, columnNames);
        all_tb = new JTable(rowData, columnNames);
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
        jTable = new JTable(defaultTableModel);
        jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
        jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);

        stu_no.setForeground(Color.GRAY);
        stu_name.setForeground(Color.GRAY);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(stu_name);
        buttonGroup.add(stu_no);
        del_btn.setFont(font3);
        top_tip.setFont(font4);
        left_tip.setFont(font2);
        clear.setFont(font3);
        query.setFont(font3);
        exit_btn.setFont(font3);
        show_all.setFont(font3);

        show_all.setOpaque(true);
        del_btn.setOpaque(true);
        clear.setOpaque(true);
        query.setOpaque(true);
        exit_btn.setOpaque(true);

        show_all.setBackground(color1);
        del_btn.setBackground(color1);
        clear.setBackground(color1);
        query.setBackground(color1);
        exit_btn.setBackground(color1);

        show_all.setForeground(Color.white);
        del_btn.setForeground(Color.white);
        left_tip.setForeground(color1);
        clear.setForeground(Color.white);
        query.setForeground(Color.white);
        exit_btn.setForeground(Color.white);
    }

    public static void main(String[] args) {
        new Del_Stu();
    }
}
