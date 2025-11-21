package com.lightbc.recordx.ui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 关于面板
 */
public class About {
    // 博客地址
    private static final String BLOG_URL = "https://www.cnblogs.com/lightbc";
    @Getter
    private JPanel mainPanel;
    private JLabel blog;

    public About() {
        init();
    }

    private void init() {
        blog();
    }

    /**
     * 博客
     */
    private void blog() {
        blog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openBlog();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                blog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                blog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    /**
     * 打开博客
     */
    private void openBlog() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URL url = new URL(BLOG_URL);
            desktop.browse(url.toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
