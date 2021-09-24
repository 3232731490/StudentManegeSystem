package UI;

/**
 * @author: 倪路
 * Time: 2021/6/24-8:42
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */

import javax.swing.border.Border;
import java.awt.*;

public class RoundBorderUI implements Border {

    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
    public boolean isBorderOpaque() {
        return false;
    }
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        //使用黑色在组件的外边缘绘制一个圆角矩形
        g.setColor(new Color(137, 203, 196));
        g.drawRoundRect(0, 0, c.getWidth()-1, c.getHeight()-1, 20, 20);
    }

}

