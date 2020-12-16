package com.chen17.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * @author yd
 * @version 1.0
 * @date 2020-11-29 10:47
 */

public class PathUtils {

    //获取jar绝对路径
    public static String getJarPath() {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return file.getAbsolutePath();
    }

    //获取jar目录
    public static String getJarDir() {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return getFile().getParent();
    }

    //获取jar包名
    public static String getJarName() {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return getFile().getName();
    }

    private static File getFile() {
        //关键是这行...

        ApplicationHome h = new ApplicationHome(PathUtils.class);

        File jarF = h.getSource();
        return jarF;
    }
}
