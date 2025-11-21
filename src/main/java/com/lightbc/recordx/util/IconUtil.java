package com.lightbc.recordx.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 系统图标工具类
 */
public class IconUtil {
    private static final String SOURCE_PATH = "/images/";
    private static final String LOGO = "logo.png";

    public static final Image ICON_LOGO = getIcon(LOGO).getImage();

    /**
     * 获取图标
     *
     * @param name 图标名称
     * @return 图标对象
     */
    private static ImageIcon getIcon(String name) {
        String resourcePath = SOURCE_PATH + name;
        URL url = FileUtil.getResourceUrl(resourcePath);
        return new ImageIcon(url);
    }

}
