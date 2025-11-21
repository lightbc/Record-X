package com.lightbc.recordx.util;

import lombok.Setter;

import javax.swing.*;

/**
 * 计时器工具类
 */
public class TimerUtil {
    // 计时初始时间
    @Setter
    private int time;
    // 计时器对象
    private Timer timer;
    // 延时毫秒数
    private int delay = 10;
    private static TimerUtil ourInstance = new TimerUtil();

    public static TimerUtil getInstance() {
        return ourInstance;
    }

    private TimerUtil() {
    }

    /**
     * 初始化时间计时器
     */
    public TimerUtil init() {
        timer = new Timer(delay, e -> time += delay);
        return this;
    }

    /**
     * 开始计时
     */
    public void start() {
        if (timer != null) {
            timer.start();
        }
    }

    /**
     * 停止计时
     */
    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * 获取终端设备操作时间字符串格式
     *
     * @return 时间字符串格式
     */
    public String getTime() {
        String result = null;
        // 示例：860ms
        if (time < 1000) {
            result = time + "ms";
        }
        // 示例：5s600ms
        if (time < 60 * 1000 && time >= 1000) {
            result = time / 1000 + "s" + time % 1000 + "ms";
        }
        // 示例：3m18s660ms
        if (time < 60 * 60 * 1000 && time >= 60 * 1000) {
            result = time / (60 * 1000) + "m" + (time % (60 * 1000)) / 1000 + "s" + (time % (60 * 1000)) % 1000 + "ms";
        }
        return result;
    }

    /**
     * 将时间字符串进行类型转换
     *
     * @param time 时间字符串
     * @return 转换类型
     */
    public int getMs(String time) {
        String sTime = time.substring(0, time.length() - 2);
        String[] split = sTime.split("[ms]");
        int result = 0;
        if (split.length == 3) {
            result = Integer.parseInt(split[0]) + Integer.parseInt(split[1]) + Integer.parseInt(split[2]);
        }
        if (split.length == 2) {
            result = Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
        }
        if (split.length == 1) {
            result = Integer.parseInt(sTime);
        }
        return result;
    }

    /**
     * 获取时间差值
     *
     * @param t1 第一个时间
     * @param t2 第二个时间（比第一个时间长）
     * @return 时间差值
     */
    public int diffMs(String t1, String t2) {
        int time1 = getMs(t1);
        int time2 = getMs(t2);
        return time2 - time1;
    }
}
