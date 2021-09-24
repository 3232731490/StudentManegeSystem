package UI;

import java.awt.*;

/**
 * @author: 倪路
 * Time: 2021/6/26-7:42
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class StyleUI {
    //不同字体
    public static Font font;  //标题字体
    public static Font font1; //输入提示
    public static Font font2; //单选框
    public static Font font3; //按钮
    public static Font font4; //黑体提示
    //不同颜色
    public static Color color1;   //青蓝色-按钮
    public static Color color2;   //灰色--按钮进入
    static{
        font = new Font("华文细黑", 1, 28);
        font1 = new Font("华文细黑", 0, 20);
        font2 = new Font("华文细黑", 1, 14);
        font3=new Font("华文细黑", 0, 16);
        font4=new Font("华文细黑", 1, 16);
        color1=new Color(137, 203, 196);
        color2=Color.lightGray;
    }
}
