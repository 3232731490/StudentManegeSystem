package UI.StudentUtilUI;

import Dao.CourseDaoImpl;
import Dao.SCDaoImpl;
import Entity.Course;
import Service.CourseService;
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

/**
 * @author: 倪路
 * Time: 2021/6/27-13:41
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class Stu_Add_Course extends JFrame {
    private JLabel top_img; //顶部图片
    private JLabel top_tip; //顶部提示
    private JLabel exit_btn;    //退出
    private JLabel del_btn;     //删除按钮
    private JPanel jPanel;      //表格面板
    private JTable jTable;      //表格
    private DefaultTableModel defaultTableModel;
    private String id;  //学生id

    private String[] columnNames={"是否选中","课程序号", "课程名", "学分", "学时", "教师编号", "上课地点"};
    //所有课程
    private Object[][] rowData=null;
    //已选课程
    private List<Course> allData= null;
    private List<Course> seleceted_data=null;   //用户未选课

    //不同字体
    private Font font3=StyleUI.font3; //按钮
    private Font font4=StyleUI.font4;   //加粗

    //不同颜色
    private Color color1=StyleUI.color1;   //青蓝色-按钮-tip
    private Color color2=StyleUI.color2;   //灰色--按钮进入

    private boolean find_same(String cno)
    {
        for(Course i:seleceted_data)
        {
            if(i.getCno().equals(cno))
                return true;
        }
        return false;
    }
    //初始化用户未选课程数据
    private void init_data(){
        int row=allData.size()-seleceted_data.size();
        rowData=new Object[row][7];
        Iterator<Course> iterator=allData.iterator();
        int i=0;
        while(iterator.hasNext())
        {
            Course cur=iterator.next();
            if(find_same(cur.getCno()))
                continue;
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

    //把用户还未选的课程读入表格
    private void update_tb()
    {
        defaultTableModel.setDataVector(rowData,columnNames);
        jTable=new JTable(defaultTableModel);
        jTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer(){
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
            }});
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
            if(e.getSource()==del_btn){
                if(jTable.getSelectedRow()==-1)
                {
                    JOptionPane.showMessageDialog(jf,"您还未进行选择...","消息窗口",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(JOptionPane.showConfirmDialog(jf,"请问您是否确认要选修选中的课程？","确认窗口",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION)
                {
                    for(int rowindex : jTable.getSelectedRows()){
                        SCDaoImpl.insert_course(id,((String)jTable.getValueAt(rowindex, 1)).trim());
                    }
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
     * 构造方法 展示窗体
     */
    public Stu_Add_Course(String id){
        this.id=id;
        Container container=this.getContentPane();
        container.setLayout(null);
        init();

        top_img.setBounds(5,0,800,150);
        top_tip.setBounds(65,180,180,35);

        jPanel.setBounds(5,220,800,360);
        del_btn.setBounds(5,600,800,30);
        exit_btn.setBounds(5,640,800,30);

        MyBtnLisen myBtnLisen=new MyBtnLisen(this);
        del_btn.addMouseListener(myBtnLisen);
        exit_btn.addMouseListener(myBtnLisen);

        container.add(top_img);
        container.add(top_tip);
        container.add(jPanel);
        container.add(del_btn);
        container.add(exit_btn);

        setTitle("选修课程");
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
        top_img=new JLabel();
        Icon icon=new ImageIcon("src\\main\\resources\\add_course.jpg");
        top_img.setIcon(icon);
        exit_btn=new JLabel("退出",SwingConstants.CENTER);
        del_btn=new JLabel("选修",SwingConstants.CENTER);
        top_tip=new JLabel("请选择您要选修的课程",SwingConstants.LEFT);
        jPanel=new JPanel(new BorderLayout());
        defaultTableModel=new DefaultTableModel(rowData,columnNames){
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
        //初始化用户未选课程
        allData= CourseDaoImpl.query_all_course();
        seleceted_data=CourseDaoImpl.query_special_course(id);   //用户未选课
        init_data();
        //将学生所选课读入
        update_tb();
        jPanel.add(jTable.getTableHeader(),BorderLayout.NORTH);
        jPanel.add(new JScrollPane(jTable),BorderLayout.CENTER);

        exit_btn.setFont(font3);
        del_btn.setFont(font3);
        top_tip.setFont(font4);

        exit_btn.setOpaque(true);
        del_btn.setOpaque(true);

        exit_btn.setBackground(color1);
        del_btn.setBackground(color1);

        exit_btn.setForeground(Color.white);
        del_btn.setForeground(Color.white);
    }
}
