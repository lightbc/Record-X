package com.lightbc.recordx.enums;

/**
 * 菜单
 */
public enum Menu {
    FILE_OPEN(0, "打开"),
    FILE_EXIT(1, "退出"),
    EDIT_BEGIN(2, "开始记录(Ctrl+B)"),
    EDIT_PAUSE(3, "暂停记录(Ctrl+S)"),
    EDIT_END(4, "结束记录(Ctrl+E)"),
    EDIT_RUN(5, "运行(Ctrl+R)"),
    EDIT_NEXT(6, "下一条"),
    HELP_ABOUT(7, "关于");

    private int code;
    private String name;

    Menu(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
