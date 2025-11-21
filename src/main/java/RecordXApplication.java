import com.lightbc.recordx.constant.App;
import com.lightbc.recordx.ui.RecordX;
import com.lightbc.recordx.util.IconUtil;
import com.lightbc.recordx.util.MenuUtil;

import javax.swing.*;

/**
 * 主程序
 */
public class RecordXApplication {

    /**
     * 显示程序面板
     */
    private static void run() {
        JFrame frame = new JFrame(App.NAME);
        // 设置程序LOGO
        frame.setIconImage(IconUtil.ICON_LOGO);
        // 初始大小
        frame.setSize(600, 900);
        // 屏幕左侧显示
        frame.setLocation(50, 50);
        // 默认关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加主面板
        RecordX recordX = new RecordX();
        frame.setContentPane(recordX.getMainPanel());
        // 添加菜单栏
        MenuUtil menuUtil = MenuUtil.getInstance();
        menuUtil.setRecordX(recordX);
        frame.setJMenuBar(menuUtil.getMenuBar());
        // 显示
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        run();
    }
}
