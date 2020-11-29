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

    public static void onlineRateTable(HttpServletResponse httpServletResponse,  List<TemporaryWorkDomain> temporaryWorkDomainList, String fileName) throws IOException, InvalidFormatException {
        InputStream inputStream = (new ExcelUtils()).getClass().getResourceAsStream("/static/modelexcel/temonlinerate.xlsx");

        Workbook workbook = WorkbookFactory.create(new BufferedInputStream(inputStream));

        Sheet sheet = workbook.getSheetAt(0);

        Row timerow = sheet.getRow(1);
        Cell timecell = timerow.getCell(13);
        timecell.setCellValue(FileUtil.getDate());




        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("D:\\cc.xlsx")));

       // workbook.write(httpServletResponse.getOutputStream());

        workbook.write(bufferedOutputStream);
        workbook.close();

        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        httpServletResponse.flushBuffer();
        return;
    }
}
