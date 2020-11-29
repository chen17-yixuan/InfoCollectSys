package com.chen17.utils;

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
        String path = PathUtils.class.getProtectionDomain().getCodeSource()
                .getLocation().getFile();
        try {
            //转换处理中文及空格
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return new File(path);
    }
}
