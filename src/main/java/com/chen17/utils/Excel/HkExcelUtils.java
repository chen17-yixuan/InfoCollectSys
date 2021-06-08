package com.chen17.utils.Excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.HkDeviceList;
import com.chen17.domain.HkIncident;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

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

    public static void exportToIncidentExcelLocal(OutputStream aimOutputStream, List<HkIncident> excelData ) throws IOException {
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(aimOutputStream);
        Sheet sheet = new Sheet(1,0,HkIncident.class);
        sheet.setSheetName("导出");
        excelWriter.write(excelData,sheet);
        excelWriter.finish();
    }

    public static void exportToDevListExcelLocal(OutputStream aimOutputStream, List<HkDeviceList> excelData ) throws IOException {
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(aimOutputStream);
        Sheet sheet = new Sheet(1,0,HkDeviceList.class);
        sheet.setSheetName("导出");
        excelWriter.write(excelData,sheet);
        excelWriter.finish();
    }

    public static void exportToExcelOnline(HttpServletResponse response , Map<String,List<HkIncident>> excelData ) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        for(String sheetname : excelData.keySet()){

            HSSFSheet hssfSheet = hssfWorkbook.createSheet(sheetname);
            HSSFRow indexrow = hssfSheet.createRow(0);
            indexrow.setHeight(TitleMap.dealColHeightshort(33));
            indexrow.createCell(0).setCellValue("故障SN");
            indexrow.createCell(1).setCellValue("故障位置");
            indexrow.createCell(2).setCellValue("故障现象");
            indexrow.createCell(3).setCellValue("详细现象");
            indexrow.createCell(4).setCellValue("故障备注");
            indexrow.createCell(5).setCellValue("检测时间");
            indexrow.createCell(6).setCellValue("维保单位");
            indexrow.createCell(7).setCellValue("故障状态");
            indexrow.createCell(8).setCellValue("反馈原因");
            indexrow.createCell(9).setCellValue("原因备注");

        }


        hssfWorkbook.write(response.getOutputStream());
        hssfWorkbook.close();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("海康故障点位导出.xlsx", "UTF-8"));
        response.flushBuffer();
    }


}
