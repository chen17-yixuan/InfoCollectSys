//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.utils.Excel;

import com.chen17.domain.Dayerrorwork;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class TitleMap {
    public TitleMap() {
    }

    public static Map<Integer, String> getContextMap(Dayerrorwork dr) {
        Map<Integer, String> map = new HashMap();
        if (dr.getErrortableCounty() == null) {
            dr.setErrortableCounty("");
        }

        if (dr.getErrortableDianweiname() == null) {
            dr.setErrortableDianweiname("");
        }

        if (dr.getErrortableHaikangpname() == null) {
            dr.setErrortableHaikangpname("");
        }

        if (dr.getErrortableHaixinpname() == null) {
            dr.setErrortableHaixinpname("");
        }

        if (dr.getErrortableDeviceType() == null) {
            dr.setErrortableDeviceType("");
        }

        if (dr.getErrortableDeviceIp() == null) {
            dr.setErrortableDeviceIp("");
        }

        if (dr.getErrortableBuildCompany() == null) {
            dr.setErrortableBuildCompany("");
        }

        if (dr.getErrortableDeviceExpriation() == null) {
            dr.setErrortableDeviceExpriation("");
        }

        if (dr.getErrortableRepairStatus() == null) {
            dr.setErrortableRepairStatus("");
        }

        if (dr.getErrortableShow() == null) {
            dr.setErrortableShow("");
        }

        if (dr.getErrortableLastestcheckTime() == null) {
            dr.setErrortableLastestcheckTime("");
        }

        if (dr.getErrortableNote() == null) {
            dr.setErrortableNote("");
        }

        map.put(0, String.valueOf(dr.getErrortableId()));
        map.put(1, String.valueOf(dr.getErrortableCounty()));
        map.put(2, String.valueOf(dr.getErrortableDianweiname()));
        map.put(3, String.valueOf(dr.getErrortableHaikangpname()));
        map.put(4, String.valueOf(dr.getErrortableHaixinpname()));
        map.put(5, String.valueOf(dr.getErrortableDeviceType()));
        map.put(6, String.valueOf(dr.getErrortableDeviceIp()));
        map.put(7, String.valueOf(dr.getErrortableBuildCompany()));
        map.put(8, String.valueOf(dr.getErrortableDeviceExpriation()));
        map.put(9, String.valueOf(dr.getErrortableRepairStatus()));
        map.put(10, String.valueOf(dr.getErrortableShow()));
        map.put(11, String.valueOf(dr.getErrortableLastestcheckTime()));
        map.put(1008, String.valueOf(dr.getErrortableFaultclassification()));
        map.put(1009, String.valueOf(dr.getErrortableNewcreate()));
        Date errortableRequestTime = dr.getErrortableRequestTime();
        DateFormat df = DateFormat.getDateTimeInstance();
        if (errortableRequestTime != null) {
            map.put(12, String.valueOf(df.format(errortableRequestTime)));
        } else {
            map.put(12, String.valueOf(""));
        }

        map.put(13, String.valueOf(dr.getErrortableNote()));
        return map;
    }

    public static Map<Integer, String> getTitleMap() {
        Map<Integer, String> map = new HashMap();
        map.put(0, "序号");
        map.put(1, "区县");
        map.put(2, "点位名称");
        map.put(3, "海康平台名称");
        map.put(4, "海信平台名称");
        map.put(5, "设备类型");
        map.put(6, "设备IP");
        map.put(7, "建设单位");
        map.put(8, "质保情况");
        map.put(9, "维修情况");
        map.put(10, "故障现象");
        map.put(11, "最新检测时间");
        map.put(12, "请求时间");
        map.put(13, "备注");
        return map;
    }

    public static List<String> getTitleorderMap() {
        List<String> list = new ArrayList();
        list.add("三棱");
        list.add("易华录");
        list.add("华迪");
        list.add("广宁");
        list.add("张店大队");
        list.add("淄川大队");
        list.add("博山大队");
        list.add("周村大队");
        list.add("临淄大队");
        list.add("桓台大队");
        list.add("沂源大队");
        list.add("高青大队");
        list.add("高新区大队");
        list.add("文昌湖大队");
        list.add("已出质保");
        return list;
    }

    public static List<String> getCountyList() {
        List<String> list = new ArrayList();
        list.add("张店区");
        list.add("淄川区");
        list.add("博山区");
        list.add("周村区");
        list.add("临淄区");
        list.add("桓台县");
        list.add("沂源县");
        list.add("高青县");
        list.add("高新区");
        list.add("文昌湖区");
        return list;
    }

    public static short dealColHeightshort(int num) {
        return (short)(num * 20);
    }

    public static int dealColWidthint(int num) {
        return num * 280;
    }

    public static Map<Integer, Integer> getColWidth() {
        Map<Integer, Integer> map = new HashMap();
        map.put(0, dealColWidthint(7));
        map.put(1, dealColWidthint(8));
        map.put(2, dealColWidthint(37));
        map.put(3, dealColWidthint(40));
        map.put(4, dealColWidthint(23));
        map.put(5, dealColWidthint(9));
        map.put(6, dealColWidthint(13));
        map.put(7, dealColWidthint(16));
        map.put(8, dealColWidthint(12));
        map.put(9, dealColWidthint(40));
        map.put(10, dealColWidthint(9));
        map.put(11, dealColWidthint(18));
        map.put(12, dealColWidthint(10));
        map.put(13, dealColWidthint(30));
        return map;
    }

    public static HSSFSheet getIndexSheetvalue(HSSFSheet indexSheet, Map<String, List<Dayerrorwork>> sheetAndValue) {
        List<String> orderList = getTitleorderMap();
        List<Integer> ordeList = new ArrayList();
        int rowIndex = 0;

        for(int i = 5; i <= 21; ++i) {
            int wlgd = 0;
            int wllt = 0;
            int wlyd = 0;
            int gd = 0;
            int sb = 0;
            int ywptxj = 0;
            int hkptxj = 0;
            int xzptxj = 0;
            int xzhkptxj = 0;
            int ddgzbx = 0;
            int ytydqr = 0;
            if (i != 9 && i != 20) {
                HSSFRow row = indexSheet.getRow(i);
                String companyName = (String)orderList.get(rowIndex);
                ++rowIndex;
                List<Dayerrorwork> aboutCompanyAllValue = (List)sheetAndValue.get(companyName);
                if (aboutCompanyAllValue != null) {
                    Iterator var20 = aboutCompanyAllValue.iterator();

                    while(var20.hasNext()) {
                        Dayerrorwork dayerrorwork = (Dayerrorwork)var20.next();
                        String reason = dayerrorwork.getErrortableFaultclassification();
                        if (reason.equals("停运")) {
                            ++ytydqr;
                        }

                        if (reason.equals("供电")) {
                            ++gd;
                        }

                        if (reason.equals("设备故障")) {
                            ++sb;
                        }

                        if (reason.equals("网络联通")) {
                            ++wllt;
                        }

                        if (reason.equals("网络移动")) {
                            ++wlyd;
                        }

                        if (reason.equals("网络广电")) {
                            ++wlgd;
                        }

                        boolean xz;
                        if (!dayerrorwork.getErrortableNewcreate().equals("新增")) {
                            xz = false;
                        } else {
                            xz = true;
                        }

                        if (dayerrorwork.getErrortableBelongPaltform() == null) {
                            dayerrorwork.setErrortableBelongPaltform("海信平台");
                        }

                        if (dayerrorwork.getErrortableFaultclassification() == null) {
                            dayerrorwork.setErrortableFaultclassification("正在维修");
                        }

                        boolean ishk = dayerrorwork.getErrortableBelongPaltform().equals("海康平台");
                        boolean iszdywb = dayerrorwork.getErrortableBelongPaltform().equals("支队运维办");
                        boolean repairStatus = dayerrorwork.getErrortableFaultclassification().equals("正在维修");
                        if (repairStatus) {
                            if (xz) {
                                if (ishk) {
                                    ++xzhkptxj;
                                } else if (iszdywb) {
                                    ++ddgzbx;
                                } else {
                                    ++xzptxj;
                                }
                            } else if (ishk) {
                                ++hkptxj;
                            } else {
                                ++ywptxj;
                            }
                        }
                    }

                    ordeList.add(wlgd);
                    ordeList.add(wllt);
                    ordeList.add(wlyd);
                    ordeList.add(gd);
                    ordeList.add(sb);
                    ordeList.add(ywptxj);
                    ordeList.add(hkptxj);
                    ordeList.add(xzptxj);
                    ordeList.add(xzhkptxj);
                    ordeList.add(ddgzbx);
                    ordeList.add(ytydqr);
                    int index = 0;

                    for(int j = 5; j <= 26; ++j) {
                        if (j != 10 && j != 13 && j != 14 && j != 25 && j != 11 && j != 12 && j != 16 && j != 18 && j != 19 && j != 21 && j != 24) {
                            int intvalue = (Integer)ordeList.get(index);
                            if (intvalue != 0) {
                                row.getCell(j).setCellValue((double)intvalue);
                            }

                            ++index;
                        }
                    }

                    ordeList.clear();
                }
            }
        }

        indexSheet.setForceFormulaRecalculation(true);
        return indexSheet;
    }
}
