package com.lightbc.recordx.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件处理工具类
 */
public class FileUtil {
    /**
     * 获取资源文件流
     *
     * @param resourcePath 资源路径
     * @return 资源流
     */
    public static InputStream getResourceStream(String resourcePath) {
        return FileUtil.class.getResourceAsStream(resourcePath);
    }

    /**
     * 获取资源URL
     *
     * @param resourcePath 资源路径
     * @return 资源URL
     */
    public static URL getResourceUrl(String resourcePath) {
        return FileUtil.class.getResource(resourcePath);
    }

    /**
     * 读取文本
     *
     * @param filePath 文件路径
     * @return 文本内容
     */
    public static String readLines(String filePath) {
        return readLines(filePath, 1, -1);
    }

    /**
     * 读取文本
     *
     * @param filePath 文件路径
     * @param start    读取开始位置
     * @return 文本内容
     */
    public static String readLines(String filePath, int start) {
        return readLines(filePath, start, -1);
    }

    /**
     * 读取文本
     *
     * @param filePath 文件路径
     * @param start    读取开始位置
     * @param len      读取长度
     * @return 文本内容
     */
    public static String readLines(String filePath, int start, int len) {
        StringBuilder builder = new StringBuilder();
        int end = start + len;

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {

            List<String> specificLines;
            if (len != -1) {
                specificLines = lines.skip(start - 1) // 跳过前面的行（从0开始计数）
                        .limit(end) // 限制读取的行数
                        .collect(Collectors.toList()); // 收集结果到列表中
            } else {
                specificLines = lines.skip(start - 1) // 跳过前面的行（从0开始计数）
                        .collect(Collectors.toList()); // 收集结果到列表中
            }
            specificLines.forEach(line -> builder.append(line).append("\n"));
        } catch (IOException e) {
            System.err.printf("数据读取失败：%s", e.getMessage());
        }
        return builder.toString();
    }
}
