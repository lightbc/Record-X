package com.lightbc.recordx.util;

import com.lightbc.recordx.constant.Type;
import com.lightbc.recordx.enums.KeyCode;

import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * 终端操作模拟工具类
 */
public class RobotUtil {
    // 操作自动化对象
    private static Robot ROBOT;
    private static RobotUtil ourInstance = new RobotUtil();

    public static RobotUtil getInstance() {
        return ourInstance;
    }

    private RobotUtil() {
    }

    static {
        try {
            ROBOT = new Robot();
        } catch (AWTException e) {
            System.err.printf("创建自动化对象失败：%s", e.getMessage());
        }
    }

    /**
     * 终端操作模拟
     *
     * @param model 日志记录数据
     * @param count 运行次数
     */
    public void robot(TableModel model, int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                // 行数
                int rows = model.getRowCount();
                for (int j = 0; j < rows; j++) {
                    // 获取第一列数据
                    String log = (String) model.getValueAt(j, 0);
                    executeRobot(log, getDelay(j, rows, model));
                }
                // 每次间隔500ms
                ROBOT.delay(500);
            }
        } else {
            while (true) {
                // 行数
                int rows = model.getRowCount();
                for (int j = 0; j < rows; j++) {
                    // 获取第一列数据
                    String log = (String) model.getValueAt(j, 0);
                    executeRobot(log, getDelay(j, rows, model));
                }
                // 每次间隔2s
                ROBOT.delay(2000);
            }
        }
    }

    /**
     * 延时时间
     *
     * @param j     当前行
     * @param rows  总行数
     * @param model 数据模型
     * @return 延时时间
     */
    private int getDelay(int j, int rows, TableModel model) {
        int delay = 0;
        if (j < rows - 1) {
            String time1 = (String) model.getValueAt(j, 1);
            String time2 = (String) model.getValueAt(j + 1, 1);
            delay = TimerUtil.getInstance().diffMs(time1, time2);
        }
        return delay;
    }

    /**
     * 执行终端操作模拟
     *
     * @param log   日志记录数据
     * @param delay 延时
     */
    private void executeRobot(String log, int delay) {
        if (log.startsWith("1")) {// 键盘操作模拟
            keyMock(log);
        } else if (log.startsWith("2")) {// 鼠标操作模拟
            mouseMock(log);
        }
        ROBOT.delay(delay > 0 ? delay : 0);
    }

    /**
     * 鼠标操作模拟
     *
     * @param log 日志
     */
    private void mouseMock(String log) {
        String value = log.split("：")[1];
        if (log.startsWith(Type.MOUSE_LEFT_PRESSED)) {// 左键按下
            ROBOT.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        } else if (log.startsWith(Type.MOUSE_LEFT_RELEASED)) {// 左键释放
            ROBOT.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } else if (log.startsWith(Type.MOUSE_RIGHT_PRESSED)) {// 右键按下
            ROBOT.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        } else if (log.startsWith(Type.MOUSE_RIGHT_RELEASED)) {// 右键释放
            ROBOT.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        } else if (log.startsWith(Type.MOUSE_MOVED)) {// 鼠标移动
            String[] point = value.split(",");
            ROBOT.mouseMove(Integer.parseInt(point[0]), Integer.parseInt(point[1]));
        } else if (log.startsWith(Type.MOUSE_WHEEL_MOVED)) {// 鼠标滚动
            ROBOT.mouseWheel(Integer.parseInt(value));
        }
    }

    /**
     * 键盘操作模拟
     *
     * @param log 日志
     */
    private void keyMock(String log) {
        String value = log.split("：")[1];
        int vc_keyCode = Integer.parseInt(value);
        int keyCode = KeyCode.getValue(vc_keyCode);
        if (keyCode > 0) {
            if (log.startsWith(Type.KEY_PRESSED)) {// 按键按下
                ROBOT.keyPress(keyCode);
            } else if (log.startsWith(Type.KEY_RELEASED)) {// 按键释放
                ROBOT.keyRelease(keyCode);
            }
        }
    }
}
