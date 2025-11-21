package com.lightbc.recordx.listener;

import com.lightbc.recordx.constant.Type;
import com.lightbc.recordx.ui.RecordX;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

/**
 * 鼠标操作监听
 */
public class MouseListener implements NativeMouseInputListener, NativeMouseWheelListener {
    private RecordX recordX;
    private static MouseListener INSTANCE = new MouseListener();

    public static MouseListener getInstance() {
        return INSTANCE;
    }

    private MouseListener() {
    }

    public void init(RecordX recordX) {
        this.recordX = recordX;
        try {
            // 注册全局钩子
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("=========================================" + MouseListener.class.getName() + "=========================================");
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeMouseListener(MouseListener.getInstance());
        GlobalScreen.addNativeMouseMotionListener(MouseListener.getInstance());
        GlobalScreen.addNativeMouseWheelListener(MouseListener.getInstance());
        // 注销钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                System.err.println("=========================================" + MouseListener.class.getName() + "=========================================");
                System.err.println("There was a problem unregistering the native hook.");
            }
        }));
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        String point = String.format("%s,%s", e.getX(), e.getY());
        String buttonType = e.getButton() == 1 ? "左键" : e.getButton() == 2 ? "右键" : "";
        String bType = e.getButton() == 1 ? Type.MOUSE_LEFT_CLICKED : e.getButton() == 2 ? Type.MOUSE_RIGHT_CLICKED : "";
        String click = String.format("%s-鼠标%s【%s】在(%s)点击", bType, buttonType, e.getButton(), point);
        String msg = String.format("%s：%s", click, point);
        recordX.showRecord(msg);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        String point = String.format("%s,%s", e.getX(), e.getY());
        String pressed = e.getButton() == 1 ? Type.MOUSE_LEFT_PRESSED : e.getButton() == 2 ? Type.MOUSE_RIGHT_PRESSED : "";
        String msg = String.format("%s-鼠标【%s】在(%s)按下：%s", pressed, e.getButton(), point, point);
        recordX.showRecord(msg);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        String point = String.format("%s,%s", e.getX(), e.getY());
        String released = e.getButton() == 1 ? Type.MOUSE_LEFT_RELEASED : e.getButton() == 2 ? Type.MOUSE_RIGHT_RELEASED : "";
        String msg = String.format("%s-鼠标【%s】在(%s)释放：%s", released, e.getButton(), point, point);
        recordX.showRecord(msg);
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        String point = String.format("%s,%s", e.getX(), e.getY());
        String msg = String.format("%s-鼠标移动到(%s)：%s", Type.MOUSE_MOVED, point, point);
        recordX.showRecord(msg);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        String point = String.format("%s,%s", e.getX(), e.getY());
        String msg = String.format("%s-鼠标拖拽到(%s)：%s", Type.MOUSE_DRAGGED, point, point);
        recordX.showRecord(msg);
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        String scroll;
        if (rotation > 0) {
            scroll = String.format("%s-鼠标向上滚动", Type.MOUSE_WHEEL_MOVED);
        } else {
            scroll = String.format("%s-鼠标向下滚动", Type.MOUSE_WHEEL_MOVED);
        }
        String msg = String.format(scroll + "：%s", rotation);
        recordX.showRecord(msg);
    }
}
