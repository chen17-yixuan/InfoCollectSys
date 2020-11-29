package com.chen17.domain;

import com.chen17.utils.PathUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author yd
 * @version 1.0
 * @date 2020-10-17 12:49
 */

public class Test {

    @org.junit.Test
    public void test_(){
        String jarFilePath = PathUtils.getJarDir();

        File propFile = new File(jarFilePath,"countyNum.properties");

        if(!propFile.exists()){
            try {
                propFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
