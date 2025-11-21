package com.lightbc.recordx.ui;

import com.lightbc.recordx.listener.KeyListener;
import com.lightbc.recordx.listener.MouseListener;
import com.lightbc.recordx.util.TimerUtil;
import lombok.Getter;
import lombok.Setter;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 主面板
 */
public class RecordX {
    @Getter
    private JPanel mainPanel;
    // 数据显示组件
    @Getter
    private JTextArea data;
    // 操作记录组件
    @Getter
    private JTable recordTable;
    private JScrollPane recordScrollPane;
    private DefaultTableModel tableModel;
    // 1-开始记录，-1-停止记录
    @Setter
    @Getter
    private int recordType;

    public RecordX() {
        init();
    }

    private void init() {
        monitor();
        initTable();
    }

    /**
     * 初始化操作记录组件
     */
    public void initTable() {
        tableModel = new DefaultTableModel(new Object[][]{}, new Object[]{"msg", "time"});
        recordTable.setModel(tableModel);
        // 设置行高
        recordTable.setRowHeight(24);
        // 表头不显示
        recordTable.getTableHeader().setVisible(false);
        // 不显示网格
        recordTable.setShowGrid(false);
        // 时间列右对齐
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        recordTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }

    /**
     * 监听线程
     */
    private void monitor() {
        SwingUtilities.invokeLater(() -> {
            // 关闭日志打印
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            // 键盘监听
            KeyListener key = KeyListener.getInstance();
            key.init(this);
            // 鼠标监听
            MouseListener mouse = MouseListener.getInstance();
            mouse.init(this);
        });
    }

    /**
     * 显示操作记录
     *
     * @param msg 操作记录
     */
    public void showRecord(String msg) {
        // 开始记录
        if (recordType == 1) {
            tableModel.addRow(new Object[]{msg, TimerUtil.getInstance().getTime()});
            // 始终显示最新记录
            int rh = recordTable.getRowHeight();
            int rows = recordTable.getRowCount();
            recordScrollPane.getVerticalScrollBar().setValue(rows * rh);
        }
    }
}
