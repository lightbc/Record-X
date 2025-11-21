package com.lightbc.recordx.util;

import com.lightbc.recordx.constant.App;
import com.lightbc.recordx.enums.Menu;
import com.lightbc.recordx.ui.About;
import com.lightbc.recordx.ui.RecordX;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单功能工具类
 */
public class MenuUtil {
    @Setter
    private RecordX recordX;
    // 读取数据下标
    private int dataIndex;
    // 数据
    private List<String> dataList;
    // 文件内容
    private String fileContent;
    private static MenuUtil ourInstance = new MenuUtil();

    public static MenuUtil getInstance() {
        return ourInstance;
    }

    private MenuUtil() {
    }

    /**
     * 获取菜单栏
     *
     * @return 菜单栏
     */
    public JMenuBar getMenuBar() {
        // 创建菜单栏
        JMenuBar bar = new JMenuBar();

        // 创建菜单
        JMenu file = new JMenu(App.MENU_FILE);
        JMenu edit = new JMenu(App.MENU_EDIT);
        JMenu help = new JMenu(App.MENU_HELP);

        // 添加菜单项
        addMenuItem(file, App.MENU_FILE_ITEMS_FIRST);
        addMenuItem(edit, App.MENU_EDIT_ITEMS_FIRST);
        addMenuItem(help, App.MENU_HELP_ITEMS_FIRST);

        // 将菜单添加到菜单栏
        bar.add(file);
        bar.add(edit);
        bar.add(help);
        return bar;
    }

    /**
     * 添加菜单菜单项
     *
     * @param menu  菜单对象
     * @param items 菜单项名称列表
     * @return 菜单对象
     */
    public JMenu addMenuItem(JMenu menu, String[] items) {
        for (String item : items) {
            JMenuItem menuItem = new JMenuItem(item);
            menu.add(menuItem);
            menuItem.addActionListener(e -> addListener(menuItem));
        }
        return menu;
    }

    /**
     * 菜单监听
     *
     * @param item 菜单项
     */
    private void addListener(JMenuItem item) {
        String name = item.getText();
        if (name.equals(Menu.FILE_OPEN.getName())) {// 打开
            open(name);
        } else if (name.equals(Menu.FILE_EXIT.getName())) {// 退出
            exit();
        } else if (name.equals(Menu.EDIT_BEGIN.getName())) {// 开始记录
            start();
        } else if (name.equals(Menu.EDIT_END.getName())) {// 结束记录
            end();
        } else if (name.equals(Menu.EDIT_NEXT.getName())) {// 下一条
            next();
        } else if (name.equals(Menu.EDIT_RUN.getName())) {// 运行
            run();
        } else if (name.equals(Menu.HELP_ABOUT.getName())) {// 关于
            About about = new About();
            DialogUtil.showDialog("关于", about.getMainPanel());
        }
    }

    /**
     * 重新加载数据信息
     */
    private void reloadData() {
        if (fileContent == null || fileContent.trim().equals("")) {
            return;
        }
        dataIndex = 0;
        dataList = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        String[] lines = fileContent.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            // 判断下一行是否为有效数据
            String nextLine = "";
            if (i + 1 < lines.length) {
                nextLine = lines[i + 1];
            }
            builder.append(line).append("\n");
            // 空行跳过，添加数据
            if ((line.trim().equals("") && !nextLine.trim().equals("")) || i == lines.length - 1) {
                dataList.add(builder.toString().trim());
                builder = new StringBuilder();
            }
        }
    }

    /**
     * 打开文件
     *
     * @param title 标题
     */
    private void open(String title) {
        dataIndex = 0;
        File file = DialogUtil.chooseFile(title, "txt");
        if (file == null) {
            return;
        }
        String filePath = file.getPath();
        // 读取数据
        fileContent = FileUtil.readLines(filePath);
        reloadData();
        showData();
    }

    /**
     * 开始记录
     */
    public void start() {
        recordX.initTable();
        recordX.setRecordType(1);
        // 开始计时
        TimerUtil.getInstance().init().start();
    }

    /**
     * 结束记录
     */
    public void end() {
        recordX.setRecordType(-1);
        // 结束计时
        TimerUtil.getInstance().stop();
        TimerUtil.getInstance().setTime(0);
    }

    /**
     * 下一条
     */
    public void next() {
        showData();
    }

    /**
     * 显示数据
     */
    public void showData() {
        if (recordX != null && dataList != null && dataList.size() > 0 && dataIndex < dataList.size()) {
            String data = dataList.get(dataIndex);
            recordX.getData().setText(data);
            dataIndex++;
        }
    }

    /**
     * 运行
     */
    public void run() {
        if (recordX != null) {
            if (recordX.getRecordType() == -1) {
                reloadData();
                SwingUtilities.invokeLater(() -> {
                    String input = JOptionPane.showInputDialog("请输入运行次数：");
                    TableModel model = recordX.getRecordTable().getModel();
                    int count = -1;
                    try {
                        count = Integer.parseInt(input);
                    } catch (Exception e) {
                        System.err.printf("运行次数类型错误：%s", e.getMessage());
                    }
                    RobotUtil.getInstance().robot(model, count);
                });
            } else if (recordX.getRecordType() > 0) {
                JOptionPane.showMessageDialog(null, "请先结束记录");
            } else {
                JOptionPane.showMessageDialog(null, "没有可以运行的记录信息");
            }
        }
    }

    /**
     * 退出
     */
    private void exit() {
        System.exit(0);
    }
}
