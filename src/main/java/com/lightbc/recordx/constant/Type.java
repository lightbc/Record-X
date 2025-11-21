package com.lightbc.recordx.constant;

/**
 * 终端操作类型
 */
public interface Type {
    // 按键按下
    String KEY_PRESSED = "10";
    // 按键释放
    String KEY_RELEASED = "11";
    // 鼠标左键点击
    String MOUSE_LEFT_CLICKED = "20";
    // 鼠标左键双击
    String MOUSE_LEFT_DOUBLE_CLICKED = "21";
    // 鼠标左键按下
    String MOUSE_LEFT_PRESSED = "22-1";
    // 鼠标左键释放
    String MOUSE_LEFT_RELEASED = "23-1";
    // 鼠标右键按下
    String MOUSE_RIGHT_PRESSED = "22-2";
    // 鼠标右键释放
    String MOUSE_RIGHT_RELEASED = "23-2";
    // 鼠标移动
    String MOUSE_MOVED = "24";
    // 鼠标拖拽
    String MOUSE_DRAGGED = "25";
    // 鼠标右键点击
    String MOUSE_RIGHT_CLICKED = "26";
    // 鼠标滚轮滚动
    String MOUSE_WHEEL_MOVED = "27";
}
