//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.utils.Excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.UpLoadDayErrorWork;
import com.chen17.utils.FileUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    public ExcelUtils() {
    }

    public static List<UpLoadDayErrorWork> simpleRead(InputStream is) {
        try {
            Sheet sheet = new Sheet(1, 2, UpLoadDayErrorWork.class);
            List<Object> readList = EasyExcelFactory.read(new BufferedInputStream(is), sheet);
            List<UpLoadDayErrorWork> list = new ArrayList();
            Iterator var4 = readList.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                UpLoadDayErrorWork upLoadDayErrorWork = (UpLoadDayErrorWork)obj;
                if (upLoadDayErrorWork.getErrortableCounty() != null) {
                    try {
                        upLoadDayErrorWork.setErrortableLastestcheckTime(upLoadDayErrorWork.getErrortableLastestcheckTime().split("，")[0]);
                    } catch (Exception var8) {
                        log.info("大队自建");
                    }

                    list.add(upLoadDayErrorWork);
                }
            }

            return list;
        } catch (Exception var9) {
            log.info("取流失败，无法解析");
            var9.printStackTrace();
            return null;
        }
    }

    public static List<Dayerrorwork> simpleReadBase(InputStream is) {
        try {
            Sheet sheet = new Sheet(1, 1, Dayerrorwork.class);
            List<Object> readList = EasyExcelFactory.read(new BufferedInputStream(is), sheet);
            List<Dayerrorwork> list = new ArrayList();
            System.out.println(list.toString());
            Iterator var4 = readList.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                Dayerrorwork dayerrorwork = (Dayerrorwork)obj;
                if (dayerrorwork.getErrortableCounty() != null) {
                    list.add(dayerrorwork);
                }
            }

            return list;
        } catch (Exception var7) {
            log.info("取流失败，无法解析");
            var7.printStackTrace();
            return null;
        }
    }

    public static void exportExcel(HttpServletResponse httpServletResponse, Map<String, List<Dayerrorwork>> sheetAndValue, String fileName) {
        int flag = 0;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle mainStyle = workbook.createCellStyle();
            mainStyle.setAlignment(HorizontalAlignment.CENTER);
            mainStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainStyle.setBorderBottom(BorderStyle.THIN);
            mainStyle.setBorderLeft(BorderStyle.THIN);
            mainStyle.setBorderRight(BorderStyle.THIN);
            mainStyle.setBorderTop(BorderStyle.THIN);
            mainStyle.setWrapText(true);
            HSSFCellStyle redStyle = workbook.createCellStyle();
            redStyle.setAlignment(HorizontalAlignment.CENTER);
            redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            redStyle.setBorderBottom(BorderStyle.THIN);
            redStyle.setBorderLeft(BorderStyle.THIN);
            redStyle.setBorderRight(BorderStyle.THIN);
            redStyle.setBorderTop(BorderStyle.THIN);
            redStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setColor((short)10);
            redStyle.setFont(font);
            HSSFCellStyle mainTitleStyle = workbook.createCellStyle();
            mainTitleStyle.setAlignment(HorizontalAlignment.CENTER);
            mainTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainTitleStyle.setWrapText(true);
            Font mainTitlefont = workbook.createFont();
            mainTitlefont.setFontHeightInPoints((short)30);
            mainTitleStyle.setFont(mainTitlefont);
            Iterator var9 = TitleMap.getTitleorderMap().iterator();
            label131:
            while(true) {
                String sheetNames;
                do {
                    do {
                        if (!var9.hasNext()) {
                            httpServletResponse.setContentType("application/octet-stream");
                            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                            httpServletResponse.flushBuffer();
                            workbook.write(httpServletResponse.getOutputStream());
                            workbook.close();
                            return;
                        }
                        sheetNames = (String)var9.next();

                        if(sheetAndValue.keySet().contains("All") && flag == 0){
                            sheetNames = "All";
                            flag=1;
                        }
                        System.out.println(flag);
                    } while(sheetAndValue.get(sheetNames) == null );
                } while(((List)sheetAndValue.get(sheetNames)).size() == 0  );

                HSSFSheet sheet = workbook.createSheet(sheetNames);
                HSSFPrintSetup ps = sheet.getPrintSetup();
                ps.setLandscape(false);
                ps.setPaperSize((short)9);
                ps.setScale((short)45);
                ps.setLandscape(true);
                sheet.setMargin((short)3, 0.1D);
                sheet.setMargin((short)0, 0.1D);
                sheet.setMargin((short)1, 0.1D);
                sheet.setMargin((short)2, 0.1D);
                sheet.setHorizontallyCenter(true);
                sheet.setVerticallyCenter(false);
                Iterator var13 = TitleMap.getColWidth().keySet().iterator();

                while(var13.hasNext()) {
                    int s = (Integer)var13.next();
                    sheet.setColumnWidth(s, (Integer)TitleMap.getColWidth().get(s));
                }

                CellRangeAddress region = new CellRangeAddress(0, 0, 0, 13);
                sheet.addMergedRegion(region);
                HSSFRow mainRowTitle = sheet.createRow(0);
                mainRowTitle.setHeight(TitleMap.dealColHeightshort(66));
                HSSFCell titleCell = mainRowTitle.createCell(0);
                HSSFRichTextString titleText = new HSSFRichTextString(sheetNames + "故障明细(" + FileUtil.getDate() + ")");
                titleCell.setCellValue(titleText);
                titleCell.setCellStyle(mainTitleStyle);
                HSSFRow rowTitle = sheet.createRow(1);
                rowTitle.setHeight(TitleMap.dealColHeightshort(33));
                Map<Integer, String> titleMap = TitleMap.getTitleMap();
                Iterator var19 = titleMap.keySet().iterator();

                while(var19.hasNext()) {
                    Integer colnum = (Integer)var19.next();
                    int col = colnum;
                    HSSFCell cell = rowTitle.createCell(col);
                    HSSFRichTextString text = new HSSFRichTextString((String)titleMap.get(colnum));
                    cell.setCellValue(text);
                    cell.setCellStyle(mainStyle);
                }

                int rowIndex = 2;
                Iterator var35 = ((List)sheetAndValue.get(sheetNames)).iterator();

                while(true) {
                    boolean zzwx;
                    Iterator var26;
                    Integer colnum;
                    int col;
                    HSSFCell cell;
                    HSSFRichTextString text;
                    HSSFRow row;
                    Map contextMap;
                    do {
                        boolean xz;
                        Dayerrorwork data;
                        if (!var35.hasNext()) {
                            var35 = ((List)sheetAndValue.get(sheetNames)).iterator();

                            while(true) {
                                do {
                                    do {
                                        if (!var35.hasNext()) {
                                            var35 = ((List)sheetAndValue.get(sheetNames)).iterator();

                                            while(true) {
                                                do {
                                                    if (!var35.hasNext()) {
                                                        continue label131;
                                                    }

                                                    data = (Dayerrorwork)var35.next();
                                                    data.setErrortableId(rowIndex-1);
                                                    row = sheet.createRow(rowIndex);
                                                    row.setHeight(TitleMap.dealColHeightshort(33));
                                                    contextMap = TitleMap.getContextMap(data);
                                                    zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                                    xz = ((String)contextMap.get(1009)).equals("新增");
                                                } while(!xz);

                                                var26 = contextMap.keySet().iterator();

                                                while(var26.hasNext()) {
                                                    colnum = (Integer)var26.next();
                                                    if (colnum < 20) {
                                                        col = colnum;
                                                        cell = row.createCell(col);
                                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                                        cell.setCellValue(text);
                                                        cell.setCellStyle(redStyle);
                                                    }
                                                }

                                                ++rowIndex;
                                            }
                                        }

                                        data = (Dayerrorwork)var35.next();
                                        data.setErrortableId(rowIndex-1);
                                        row = sheet.createRow(rowIndex);
                                        row.setHeight(TitleMap.dealColHeightshort(33));
                                        contextMap = TitleMap.getContextMap(data);
                                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                        xz = ((String)contextMap.get(1009)).equals("新增");
                                    } while(!zzwx);
                                } while(xz);

                                var26 = contextMap.keySet().iterator();

                                while(var26.hasNext()) {
                                    colnum = (Integer)var26.next();
                                    if (colnum < 20) {
                                        col = colnum;
                                        cell = row.createCell(col);
                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                        cell.setCellValue(text);
                                        cell.setCellStyle(mainStyle);
                                    }
                                }

                                ++rowIndex;
                            }
                        }

                        data = (Dayerrorwork)var35.next();
                        data.setErrortableId(rowIndex-1);
                        row = sheet.createRow(rowIndex);
                        row.setHeight(TitleMap.dealColHeightshort(33));
                        contextMap = TitleMap.getContextMap(data);
                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                        xz = ((String)contextMap.get(1009)).equals("新增");
                    } while(zzwx);

                    var26 = contextMap.keySet().iterator();

                    while(var26.hasNext()) {
                        colnum = (Integer)var26.next();
                        if (colnum < 20) {
                            col = colnum;
                            cell = row.createCell(col);
                            text = new HSSFRichTextString((String)contextMap.get(colnum));
                            cell.setCellValue(text);
                            cell.setCellStyle(mainStyle);
                        }
                    }

                    ++rowIndex;
                }
            }
        } catch (IOException var31) {
            var31.printStackTrace();
        }
    }

    public static void exportExcelOderByCompany(HttpServletResponse httpServletResponse, Map<String, List<Dayerrorwork>> sheetAndValue, String fileName) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle mainStyle = workbook.createCellStyle();
            mainStyle.setAlignment(HorizontalAlignment.CENTER);
            mainStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainStyle.setBorderBottom(BorderStyle.THIN);
            mainStyle.setBorderLeft(BorderStyle.THIN);
            mainStyle.setBorderRight(BorderStyle.THIN);
            mainStyle.setBorderTop(BorderStyle.THIN);
            mainStyle.setWrapText(true);
            HSSFCellStyle redStyle = workbook.createCellStyle();
            redStyle.setAlignment(HorizontalAlignment.CENTER);
            redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            redStyle.setBorderBottom(BorderStyle.THIN);
            redStyle.setBorderLeft(BorderStyle.THIN);
            redStyle.setBorderRight(BorderStyle.THIN);
            redStyle.setBorderTop(BorderStyle.THIN);
            redStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setColor((short)10);
            redStyle.setFont(font);
            List<String> allServerCompany = TitleMap.getTitleorderMap();
            Iterator var8 = allServerCompany.iterator();

            label131:
            while(true) {
                String sheetNames;
                do {
                    do {
                        if (!var8.hasNext()) {
                            httpServletResponse.setContentType("application/octet-stream");
                            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                            httpServletResponse.flushBuffer();
                            workbook.write(httpServletResponse.getOutputStream());
                            workbook.close();
                            return;
                        }

                        sheetNames = (String)var8.next();
                    } while(sheetAndValue.get(sheetNames) == null);
                } while(((List)sheetAndValue.get(sheetNames)).size() == 0);

                HSSFSheet sheet = workbook.createSheet(sheetNames);
                Iterator var11 = TitleMap.getColWidth().keySet().iterator();

                while(var11.hasNext()) {
                    int s = (Integer)var11.next();
                    sheet.setColumnWidth(s, (Integer)TitleMap.getColWidth().get(s));
                }

                HSSFRow rowTitle = sheet.createRow(0);
                rowTitle.setHeight(TitleMap.dealColHeightshort(33));
                Map<Integer, String> titleMap = TitleMap.getTitleMap();
                Iterator var13 = titleMap.keySet().iterator();

                while(var13.hasNext()) {
                    Integer colnum = (Integer)var13.next();
                    int col = colnum;
                    HSSFCell cell = rowTitle.createCell(col);
                    HSSFRichTextString text = new HSSFRichTextString((String)titleMap.get(colnum));
                    cell.setCellValue(text);
                    cell.setCellStyle(mainStyle);
                }

                int rowIndex = 1;
                Iterator var29 = ((List)sheetAndValue.get(sheetNames)).iterator();

                while(true) {
                    boolean zzwx;
                    Iterator var20;
                    Integer colnum;
                    int col;
                    HSSFCell cell;
                    HSSFRichTextString text;
                    HSSFRow row;
                    Map contextMap;
                    do {
                        boolean xz;
                        Dayerrorwork data;
                        if (!var29.hasNext()) {
                            var29 = ((List)sheetAndValue.get(sheetNames)).iterator();

                            while(true) {
                                do {
                                    do {
                                        if (!var29.hasNext()) {
                                            var29 = ((List)sheetAndValue.get(sheetNames)).iterator();

                                            while(true) {
                                                do {
                                                    if (!var29.hasNext()) {
                                                        continue label131;
                                                    }

                                                    data = (Dayerrorwork)var29.next();
                                                    data.setErrortableId(rowIndex);
                                                    row = sheet.createRow(rowIndex);
                                                    row.setHeight(TitleMap.dealColHeightshort(33));
                                                    contextMap = TitleMap.getContextMap(data);
                                                    zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                                    xz = ((String)contextMap.get(1009)).equals("新增");
                                                } while(!xz);

                                                var20 = contextMap.keySet().iterator();

                                                while(var20.hasNext()) {
                                                    colnum = (Integer)var20.next();
                                                    if (colnum < 20) {
                                                        col = colnum;
                                                        cell = row.createCell(col);
                                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                                        cell.setCellValue(text);
                                                        cell.setCellStyle(redStyle);
                                                    }
                                                }

                                                ++rowIndex;
                                            }
                                        }

                                        data = (Dayerrorwork)var29.next();
                                        data.setErrortableId(rowIndex);
                                        row = sheet.createRow(rowIndex);
                                        row.setHeight(TitleMap.dealColHeightshort(33));
                                        contextMap = TitleMap.getContextMap(data);
                                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                        xz = ((String)contextMap.get(1009)).equals("新增");
                                    } while(!zzwx);
                                } while(xz);

                                var20 = contextMap.keySet().iterator();

                                while(var20.hasNext()) {
                                    colnum = (Integer)var20.next();
                                    if (colnum < 20) {
                                        col = colnum;
                                        cell = row.createCell(col);
                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                        cell.setCellValue(text);
                                        cell.setCellStyle(mainStyle);
                                    }
                                }

                                ++rowIndex;
                            }
                        }

                        data = (Dayerrorwork)var29.next();
                        data.setErrortableId(rowIndex);
                        row = sheet.createRow(rowIndex);
                        row.setHeight(TitleMap.dealColHeightshort(33));
                        contextMap = TitleMap.getContextMap(data);
                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                        xz = ((String)contextMap.get(1009)).equals("新增");
                    } while(zzwx);

                    var20 = contextMap.keySet().iterator();

                    while(var20.hasNext()) {
                        colnum = (Integer)var20.next();
                        if (colnum < 20) {
                            col = colnum;
                            cell = row.createCell(col);
                            text = new HSSFRichTextString((String)contextMap.get(colnum));
                            cell.setCellValue(text);
                            cell.setCellStyle(mainStyle);
                        }
                    }

                    ++rowIndex;
                }
            }
        } catch (IOException var25) {
            var25.printStackTrace();
        }
    }

    public static void exportMainExcel(HttpServletResponse httpServletResponse, Map<String, List<Dayerrorwork>> sheetAndValue, String fileName) {
        try {
            InputStream inputStream = (new ExcelUtils()).getClass().getResourceAsStream("/static/modelexcel/mainsheet.xlsx");
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            List<String> conpanyList = TitleMap.getTitleorderMap();
            HSSFCellStyle mainStyle = workbook.createCellStyle();
            mainStyle.setAlignment(HorizontalAlignment.CENTER);
            mainStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainStyle.setBorderBottom(BorderStyle.THIN);
            mainStyle.setBorderLeft(BorderStyle.THIN);
            mainStyle.setBorderRight(BorderStyle.THIN);
            mainStyle.setBorderTop(BorderStyle.THIN);
            mainStyle.setWrapText(true);
            HSSFCellStyle redStyle = workbook.createCellStyle();
            redStyle.setAlignment(HorizontalAlignment.CENTER);
            redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            redStyle.setBorderBottom(BorderStyle.THIN);
            redStyle.setBorderLeft(BorderStyle.THIN);
            redStyle.setBorderRight(BorderStyle.THIN);
            redStyle.setBorderTop(BorderStyle.THIN);
            redStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setColor((short)10);
            redStyle.setFont(font);
            HSSFSheet indexsheet = workbook.getSheet("故障统计表");
            HSSFPrintSetup ps = indexsheet.getPrintSetup();
            ps.setLandscape(false);
            ps.setPaperSize((short)9);
            ps.setScale((short)40);
            ps.setLandscape(true);
            indexsheet.setMargin((short)3, 0.1D);
            indexsheet.setMargin((short)0, 0.1D);
            indexsheet.setMargin((short)1, 0.1D);
            indexsheet.setMargin((short)2, 0.1D);
            indexsheet.setHorizontallyCenter(true);
            indexsheet.setVerticallyCenter(false);
            indexsheet.getRow(1).getCell(24).setCellValue(FileUtil.getDate());
            TitleMap.getIndexSheetvalue(indexsheet, sheetAndValue);
            Iterator var11 = conpanyList.iterator();

            label125:
            while(true) {
                String sheetNames;
                do {
                    if (!var11.hasNext()) {
                        httpServletResponse.setContentType("application/octet-stream");
                        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                        httpServletResponse.flushBuffer();
                        workbook.write(httpServletResponse.getOutputStream());
                        workbook.close();
                        return;
                    }

                    sheetNames = (String)var11.next();
                } while(sheetAndValue.get(sheetNames) == null);

                HSSFSheet sheet = workbook.createSheet(sheetNames);
                Iterator var14 = TitleMap.getColWidth().keySet().iterator();

                while(var14.hasNext()) {
                    int s = (Integer)var14.next();
                    sheet.setColumnWidth(s, (Integer)TitleMap.getColWidth().get(s));
                }

                HSSFRow rowTitle = sheet.createRow(0);
                rowTitle.setHeight(TitleMap.dealColHeightshort(33));
                Map<Integer, String> titleMap = TitleMap.getTitleMap();
                Iterator var16 = titleMap.keySet().iterator();

                while(var16.hasNext()) {
                    Integer colnum = (Integer)var16.next();
                    int col = colnum;
                    HSSFCell cell = rowTitle.createCell(col);
                    HSSFRichTextString text = new HSSFRichTextString((String)titleMap.get(colnum));
                    cell.setCellValue(text);
                    cell.setCellStyle(mainStyle);
                }

                int rowIndex = 1;
                Iterator var32 = ((List)sheetAndValue.get(sheetNames)).iterator();

                while(true) {
                    boolean zzwx;
                    Iterator var23;
                    Integer colnum;
                    int col;
                    HSSFCell cell;
                    HSSFRichTextString text;
                    HSSFRow row;
                    Map contextMap;
                    do {
                        boolean xz;
                        Dayerrorwork data;
                        if (!var32.hasNext()) {
                            var32 = ((List)sheetAndValue.get(sheetNames)).iterator();

                            while(true) {
                                do {
                                    do {
                                        if (!var32.hasNext()) {
                                            var32 = ((List)sheetAndValue.get(sheetNames)).iterator();

                                            while(true) {
                                                do {
                                                    if (!var32.hasNext()) {
                                                        continue label125;
                                                    }

                                                    data = (Dayerrorwork)var32.next();
                                                    data.setErrortableId(rowIndex);
                                                    row = sheet.createRow(rowIndex);
                                                    row.setHeight(TitleMap.dealColHeightshort(33));
                                                    contextMap = TitleMap.getContextMap(data);
                                                    zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                                    xz = ((String)contextMap.get(1009)).equals("新增");
                                                } while(!xz);

                                                var23 = contextMap.keySet().iterator();

                                                while(var23.hasNext()) {
                                                    colnum = (Integer)var23.next();
                                                    if (colnum < 20) {
                                                        col = colnum;
                                                        cell = row.createCell(col);
                                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                                        cell.setCellValue(text);
                                                        cell.setCellStyle(redStyle);
                                                    }
                                                }

                                                ++rowIndex;
                                            }
                                        }

                                        data = (Dayerrorwork)var32.next();
                                        data.setErrortableId(rowIndex);
                                        row = sheet.createRow(rowIndex);
                                        row.setHeight(TitleMap.dealColHeightshort(33));
                                        contextMap = TitleMap.getContextMap(data);
                                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                        xz = ((String)contextMap.get(1009)).equals("新增");
                                    } while(!zzwx);
                                } while(xz);

                                var23 = contextMap.keySet().iterator();

                                while(var23.hasNext()) {
                                    colnum = (Integer)var23.next();
                                    if (colnum < 20) {
                                        col = colnum;
                                        cell = row.createCell(col);
                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                        cell.setCellValue(text);
                                        cell.setCellStyle(mainStyle);
                                    }
                                }

                                ++rowIndex;
                            }
                        }

                        data = (Dayerrorwork)var32.next();
                        data.setErrortableId(rowIndex);
                        row = sheet.createRow(rowIndex);
                        row.setHeight(TitleMap.dealColHeightshort(33));
                        contextMap = TitleMap.getContextMap(data);
                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                        xz = ((String)contextMap.get(1009)).equals("新增");
                    } while(zzwx);

                    var23 = contextMap.keySet().iterator();

                    while(var23.hasNext()) {
                        colnum = (Integer)var23.next();
                        if (colnum < 20) {
                            col = colnum;
                            cell = row.createCell(col);
                            text = new HSSFRichTextString((String)contextMap.get(colnum));
                            cell.setCellValue(text);
                            cell.setCellStyle(mainStyle);
                        }
                    }

                    ++rowIndex;
                }
            }
        } catch (IOException var28) {
            var28.printStackTrace();
        }
    }

    public static void exportExcel3(HttpServletResponse httpServletResponse, Map<String, List<Dayerrorwork>> sheetAndValue, String fileName) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle mainStyle = workbook.createCellStyle();
            mainStyle.setAlignment(HorizontalAlignment.CENTER);
            mainStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainStyle.setBorderBottom(BorderStyle.THIN);
            mainStyle.setBorderLeft(BorderStyle.THIN);
            mainStyle.setBorderRight(BorderStyle.THIN);
            mainStyle.setBorderTop(BorderStyle.THIN);
            mainStyle.setWrapText(true);
            HSSFCellStyle redStyle = workbook.createCellStyle();
            redStyle.setAlignment(HorizontalAlignment.CENTER);
            redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            redStyle.setBorderBottom(BorderStyle.THIN);
            redStyle.setBorderLeft(BorderStyle.THIN);
            redStyle.setBorderRight(BorderStyle.THIN);
            redStyle.setBorderTop(BorderStyle.THIN);
            redStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setColor((short)10);
            redStyle.setFont(font);
            Iterator var7 = sheetAndValue.keySet().iterator();

            label125:
            while(true) {
                String sheetNames;
                do {
                    if (!var7.hasNext()) {
                        httpServletResponse.setContentType("application/octet-stream");
                        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                        httpServletResponse.flushBuffer();
                        workbook.write(httpServletResponse.getOutputStream());
                        workbook.close();
                        return;
                    }

                    sheetNames = (String)var7.next();
                } while(((List)sheetAndValue.get(sheetNames)).size() == 0);

                HSSFSheet sheet = workbook.createSheet(sheetNames);
                Iterator var10 = TitleMap.getColWidth().keySet().iterator();

                while(var10.hasNext()) {
                    int s = (Integer)var10.next();
                    sheet.setColumnWidth(s, (Integer)TitleMap.getColWidth().get(s));
                }

                HSSFRow rowTitle = sheet.createRow(0);
                rowTitle.setHeight(TitleMap.dealColHeightshort(33));
                Map<Integer, String> titleMap = TitleMap.getTitleMap();
                Iterator var12 = titleMap.keySet().iterator();

                while(var12.hasNext()) {
                    Integer colnum = (Integer)var12.next();
                    int col = colnum;
                    HSSFCell cell = rowTitle.createCell(col);
                    HSSFRichTextString text = new HSSFRichTextString((String)titleMap.get(colnum));
                    cell.setCellValue(text);
                    cell.setCellStyle(mainStyle);
                }

                int rowIndex = 1;
                Iterator var28 = ((List)sheetAndValue.get(sheetNames)).iterator();

                while(true) {
                    boolean zzwx;
                    Iterator var19;
                    Integer colnum;
                    int col;
                    HSSFCell cell;
                    HSSFRichTextString text;
                    HSSFRow row;
                    Map contextMap;
                    do {
                        boolean xz;
                        Dayerrorwork data;
                        if (!var28.hasNext()) {
                            var28 = ((List)sheetAndValue.get(sheetNames)).iterator();

                            while(true) {
                                do {
                                    do {
                                        if (!var28.hasNext()) {
                                            var28 = ((List)sheetAndValue.get(sheetNames)).iterator();

                                            while(true) {
                                                do {
                                                    if (!var28.hasNext()) {
                                                        continue label125;
                                                    }

                                                    data = (Dayerrorwork)var28.next();
                                                    data.setErrortableId(rowIndex);
                                                    row = sheet.createRow(rowIndex);
                                                    row.setHeight(TitleMap.dealColHeightshort(33));
                                                    contextMap = TitleMap.getContextMap(data);
                                                    zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                                    xz = ((String)contextMap.get(1009)).equals("新增");
                                                } while(!xz);

                                                var19 = contextMap.keySet().iterator();

                                                while(var19.hasNext()) {
                                                    colnum = (Integer)var19.next();
                                                    if (colnum < 20) {
                                                        col = colnum;
                                                        cell = row.createCell(col);
                                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                                        cell.setCellValue(text);
                                                        cell.setCellStyle(redStyle);
                                                    }
                                                }

                                                ++rowIndex;
                                            }
                                        }

                                        data = (Dayerrorwork)var28.next();
                                        data.setErrortableId(rowIndex);
                                        row = sheet.createRow(rowIndex);
                                        row.setHeight(TitleMap.dealColHeightshort(33));
                                        contextMap = TitleMap.getContextMap(data);
                                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                                        xz = ((String)contextMap.get(1009)).equals("新增");
                                    } while(!zzwx);
                                } while(xz);

                                var19 = contextMap.keySet().iterator();

                                while(var19.hasNext()) {
                                    colnum = (Integer)var19.next();
                                    if (colnum < 20) {
                                        col = colnum;
                                        cell = row.createCell(col);
                                        text = new HSSFRichTextString((String)contextMap.get(colnum));
                                        cell.setCellValue(text);
                                        cell.setCellStyle(mainStyle);
                                    }
                                }

                                ++rowIndex;
                            }
                        }

                        data = (Dayerrorwork)var28.next();
                        data.setErrortableId(rowIndex);
                        row = sheet.createRow(rowIndex);
                        row.setHeight(TitleMap.dealColHeightshort(33));
                        contextMap = TitleMap.getContextMap(data);
                        zzwx = ((String)contextMap.get(1008)).equals("正在维修");
                        xz = ((String)contextMap.get(1009)).equals("新增");
                    } while(zzwx);

                    var19 = contextMap.keySet().iterator();

                    while(var19.hasNext()) {
                        colnum = (Integer)var19.next();
                        if (colnum < 20) {
                            col = colnum;
                            cell = row.createCell(col);
                            text = new HSSFRichTextString((String)contextMap.get(colnum));
                            cell.setCellValue(text);
                            cell.setCellStyle(mainStyle);
                        }
                    }

                    ++rowIndex;
                }
            }
        } catch (IOException var24) {
            var24.printStackTrace();
        }
    }
}
