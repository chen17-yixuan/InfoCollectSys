package com.chen17.utils.Excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.HkDeviceList;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author yd
 * @version 1.0
 * @date 2021-06-03 14:42
 */

public class HkExcelUtils {

    public static List<HkDeviceList> simpleReadBase(InputStream is, List<String> incidentSns) {
        try {
            Sheet sheet = new Sheet(1, 1, HkDeviceList.class);
            List<Object> readList = EasyExcelFactory.read(new BufferedInputStream(is), sheet);
            List<HkDeviceList> list = new ArrayList();
            System.out.println(list.toString());
            Iterator var4 = readList.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                HkDeviceList hkDeviceList = (HkDeviceList)obj;
                hkDeviceList.setDeviceIspriority("0");
                for(String incidentSn : incidentSns){
                    if(incidentSn.equals(hkDeviceList.getDeviceSn())){
                        hkDeviceList.setDeviceIspriority("2");
                    }
                }
                hkDeviceList.setSearchDatatime(new Date());
                list.add(hkDeviceList);
            }

            return list;
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }
}
