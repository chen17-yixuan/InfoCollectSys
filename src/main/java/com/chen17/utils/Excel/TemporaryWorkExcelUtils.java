package com.chen17.utils.Excel;

import com.chen17.domain.temporary.TemporaryWorkDomain;
import com.chen17.utils.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author yd
 * @version 1.0
 * @date 2020-11-29 15:17
 */

public class TemporaryWorkExcelUtils {

    public static void onlineRateTable(HttpServletResponse httpServletResponse, List<TemporaryWorkDomain> temporaryWorkDomainList, String fileName) throws IOException, InvalidFormatException {
        InputStream inputStream = (new ExcelUtils()).getClass().getResourceAsStream("/static/modelexcel/temonlinerate.xlsx");

        Workbook workbook = WorkbookFactory.create(new BufferedInputStream(inputStream));

        Sheet sheet = workbook.getSheetAt(0);

        Row timerow = sheet.getRow(1);
        Cell timecell = timerow.getCell(13);
        timecell.setCellValue(FileUtil.getDate());

        for (int i = 0; i < temporaryWorkDomainList.size(); i++) {
            TemporaryWorkDomain temporaryWorkDomain = temporaryWorkDomainList.get(i);
            Row curow = sheet.getRow(i + 4);

            Cell cell2 = curow.getCell(2);
            cell2.setCellValue(temporaryWorkDomain.getAllDevNum());
            Cell cell3 = curow.getCell(3);
            cell3.setCellValue(temporaryWorkDomain.getZswfwxxiaoji());
            if (temporaryWorkDomain.getZswfwxGuangxian() != 0) {
                Cell cell4 = curow.getCell(4);
                cell4.setCellValue(temporaryWorkDomain.getZswfwxGuangxian());
            }
            if (temporaryWorkDomain.getZswfwxGongdian() != 0) {
                Cell cell5 = curow.getCell(5);
                cell5.setCellValue(temporaryWorkDomain.getZswfwxGongdian());
            }
            if (temporaryWorkDomain.getZswfwxShebei() != 0) {
                Cell cell6 = curow.getCell(6);
                cell6.setCellValue(temporaryWorkDomain.getZswfwxShebei());
            }
            if (temporaryWorkDomain.getZswfwxTingyun() != 0) {
                Cell cell7 = curow.getCell(7);
                cell7.setCellValue(temporaryWorkDomain.getZswfwxTingyun());
            }
            if (temporaryWorkDomain.getZswfwxQita() != 0) {
                Cell cell8 = curow.getCell(8);
                cell8.setCellValue(temporaryWorkDomain.getZswfwxQita());
            }
            Cell cell9 = curow.getCell(9);
            cell9.setCellValue(temporaryWorkDomain.getWxzXiaoji());

            if (temporaryWorkDomain.getWxzDzjc() != 0) {
                Cell cell10 = curow.getCell(10);
                cell10.setCellValue(temporaryWorkDomain.getWxzDzjc());
            }
            if (temporaryWorkDomain.getWxzDsjk() != 0) {
                Cell cell11 = curow.getCell(11);
                cell11.setCellValue(temporaryWorkDomain.getWxzDsjk());
            }
            if (temporaryWorkDomain.getWxzKk() != 0) {
                Cell cell12 = curow.getCell(12);
                cell12.setCellValue(temporaryWorkDomain.getWxzKk());
            }
            Cell cell13 = curow.getCell(13);
            cell13.setCellValue(temporaryWorkDomain.getZxl());
        }

        //BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("D:\\cc.xlsx")));
        workbook.setForceFormulaRecalculation(true);
        workbook.write(httpServletResponse.getOutputStream());
        //workbook.write(bufferedOutputStream);
        workbook.close();

        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        httpServletResponse.flushBuffer();
        return;
    }
}
