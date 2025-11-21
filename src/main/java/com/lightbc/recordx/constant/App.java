package com.lightbc.recordx.constant;

import com.lightbc.recordx.enums.Menu;

/**
 * 程序常量
 */
public interface App {
    // 程序名称
    String NAME = "Record-X";
    String MENU_FILE = "文件";
    String MENU_EDIT = "编辑";
    String MENU_HELP = "帮助";

    // 文件一级菜单
    String[] MENU_FILE_ITEMS_FIRST = {Menu.FILE_OPEN.getName(), Menu.FILE_EXIT.getName()};
    // 编辑一级菜单
    String[] MENU_EDIT_ITEMS_FIRST = {Menu.EDIT_BEGIN.getName(), Menu.EDIT_END.getName(), Menu.EDIT_NEXT.getName(), Menu.EDIT_RUN.getName()};
    // 帮助一级菜单
    String[] MENU_HELP_ITEMS_FIRST = {Menu.HELP_ABOUT.getName()};
}
