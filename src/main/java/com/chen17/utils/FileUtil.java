//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.utils;

import com.chen17.domain.Dayerrorwork;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileUtil {
    public FileUtil() {
    }

    public static String downloadFile(HttpServletResponse response, InputStream inputStream, String downloadName) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadName, "UTF-8"));
        } catch (UnsupportedEncodingException var20) {
            var20.printStackTrace();
        }

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        ServletOutputStream os = null;

        String var7;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(inputStream);

            for(int i = bis.read(buff); i != -1; i = bis.read(buff)) {
                os.write(buff, 0, buff.length);
                os.flush();
            }

            return "success";
        } catch (FileNotFoundException var21) {
            var7 = "fail";
        } catch (IOException var22) {
            var22.printStackTrace();
            return "success";
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return var7;
    }

    public static String getTime() {
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        Date date = new Date();
        return bf.format(date);
    }

    public static String getDate() {
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return bf.format(date);
    }

    public static void dealIps(HttpServletResponse httpServletResponse, List<Dayerrorwork> list) throws IOException {
        String ipsjson = JacksonUtil.toJSon(list);
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("deal.json", "UTF-8"));
        BufferedOutputStream bo = new BufferedOutputStream(httpServletResponse.getOutputStream());
        bo.write(ipsjson.getBytes("UTF-8"));
        bo.close();
        httpServletResponse.flushBuffer();
    }
}
