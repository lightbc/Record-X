package com.lightbc.recordx.listener;

import com.lightbc.recordx.constant.Type;
import com.lightbc.recordx.ui.RecordX;
import com.lightbc.recordx.util.MenuUtil;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * 键盘操作监听
 */
public class KeyListener implements NativeKeyListener {
    private boolean isCtrl;
    private boolean isShift;
    private boolean isAlt;
    private RecordX recordX;
    private MenuUtil menuUtil = MenuUtil.getInstance();
    private static KeyListener INSTANCE = new KeyListener();

    private KeyListener() {

    }

    public static KeyListener getInstance() {
        return INSTANCE;
    }

    public void init(RecordX recordX) {
        this.recordX = recordX;
        try {
            // 注册全局钩子
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("=========================================" + KeyListener.class.getName() + "=========================================");
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(KeyListener.getInstance());
        // 注销钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                System.err.println("=========================================" + KeyListener.class.getName() + "=========================================");
                System.err.println("There was a problem unregistering the native hook.");
            }
        }));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        appKeyPressed(e);
        String msg = String.format("%s-按键【%s】按下：%s", Type.KEY_PRESSED, NativeKeyEvent.getKeyText(e.getKeyCode()), e.getKeyCode());
        recordX.showRecord(msg);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        appKeyReleased(e);
        String msg = String.format("%s-按键【%s】释放：%s", Type.KEY_RELEASED, NativeKeyEvent.getKeyText(e.getKeyCode()), e.getKeyCode());
        recordX.showRecord(msg);
    }

    /**
     * 系统热键按下监听
     *
     * @param e 键盘事件
     */
    private void appKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {// 退出程序
            System.exit(0);
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {// 按下ctrl
            isCtrl = true;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {// 按下shift
            isShift = true;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {// 按下alt
            isAlt = true;
        }

        // 程序快捷键
        if (isCtrl && (e.getKeyCode() == NativeKeyEvent.VC_B)) {// Ctrl+B 开始记录
            menuUtil.start();
        } else if (isCtrl && (e.getKeyCode() == NativeKeyEvent.VC_E)) {// Ctrl+E 结束记录
            menuUtil.end();
        } else if (isCtrl && (e.getKeyCode() == NativeKeyEvent.VC_R)) {// Ctrl+R 运行
            menuUtil.run();
        }
    }

    /**
     * 系统热键释放监听
     *
     * @param e 键盘事件
     */
    private void appKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {// 释放ctrl
            isCtrl = false;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {// 释放shift
            isShift = false;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {// 释放alt
            isAlt = false;
        }
    }
}
