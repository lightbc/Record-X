package com.lightbc.recordx.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * 对话框工具类
 */
public class DialogUtil {
    /**
     * 选择文件
     *
     * @param title 标题
     * @param ext   过滤文件拓展名
     * @return 文件对象
     */
    public static File chooseFile(String title, String... ext) {
        return chooseFile(null, title, ext);
    }

    /**
     * 选择文件
     *
     * @param parent 父级组件
     * @param title  标题
     * @param ext    过滤文件拓展名
     * @return 文件对象
     */
    public static File chooseFile(Component parent, String title, String... ext) {
        JFileChooser chooser = new JFileChooser();
        //标题
        chooser.setDialogTitle(title);
        // 只选择文件
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 关闭默认过滤器
        chooser.setAcceptAllFileFilterUsed(false);
        //  只过滤txt文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件", ext);
        chooser.setFileFilter(filter);
        // 设置确认按钮文本
        chooser.setApproveButtonText("打开");
        // 设置单选
        chooser.setMultiSelectionEnabled(false);
        // 返回选择文件
        int re = chooser.showOpenDialog(parent);
        return re == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile() : null;
    }

    /**
     * 显示对话框
     *
     * @param title     标题
     * @param component 面板内容
     */
    public static void showDialog(String title, JComponent component) {
        showDialog(title, component, new Dimension(300, 150));
    }

    /**
     * 显示对话框
     *
     * @param title     标题
     * @param component 面板内容
     * @param size      默认大小
     */
    public static void showDialog(String title, JComponent component, Dimension size) {
        JDialog dialog = new JDialog();
        // 设置图标
        dialog.setIconImage(IconUtil.ICON_LOGO);
        dialog.setSize(size);
        dialog.setTitle(title);
        // 设置模态
        dialog.setModal(true);
        // 屏幕居中
        dialog.setLocationRelativeTo(null);
        // 默认关闭操作
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(component);
        dialog.setVisible(true);
    }
}
