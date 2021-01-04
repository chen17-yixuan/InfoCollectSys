package com.chen17.utils;

import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.UpLoadDayErrorWork;
import com.chen17.domain.temporary.TemporaryWorkDomain;
import com.chen17.service.DayWorkService;
import com.chen17.utils.Excel.TitleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author yd
 * @version 1.0
 * @date 2020-11-29 10:01
 */
@Component
public class TemporaryWork {

    static Logger logger = LogManager.getLogger(TemporaryWork.class.getName());
    DayWorkService ds;

    @Autowired
    public TemporaryWork(DayWorkService ds) {
        this.ds = ds;
    }

    public List<TemporaryWorkDomain> dealOnlineRateTable(List<UpLoadDayErrorWork> upLoadDayErrorWorkList) {
        Map<String, List<String>> ipListMap = new HashMap<>();

        Set<String> staffsSet = new HashSet<>();
        for (UpLoadDayErrorWork upLoadDayErrorWork : upLoadDayErrorWorkList) {
            staffsSet.add(upLoadDayErrorWork.getErrortableDeviceIp());
        }
        List<String> ipList = new ArrayList<>(staffsSet);
        ipListMap.put("IpAddress", ipList);

        List<Dayerrorwork> dayerrorworks = this.ds.selectByIpAddress(ipListMap);
        //两个数据源全了,以上的list - dayerrorworks是 得出表中所有已存在的故障集合
        //下面开始处理数据
        //填写常量处理
        String jarFilePath = PathUtils.getJarDir();
        File propFile = new File(jarFilePath + "\\countyNum.properties");
        System.out.println(propFile);

        if (!propFile.exists()) {
            try {
                propFile.createNewFile();
                //录入初始数据
                String propertiesPath = propFile.getAbsolutePath();
                PropertiesUtils.writeProperties(propertiesPath, "zhangdian", "1692");
                PropertiesUtils.writeProperties(propertiesPath, "zichuan", "1146");
                PropertiesUtils.writeProperties(propertiesPath, "boshan", "736");
                PropertiesUtils.writeProperties(propertiesPath, "zhoucun", "1211");
                PropertiesUtils.writeProperties(propertiesPath, "linzi", "994");
                PropertiesUtils.writeProperties(propertiesPath, "huantai", "1470");
                PropertiesUtils.writeProperties(propertiesPath, "yiyuan", "1239");
                PropertiesUtils.writeProperties(propertiesPath, "gaoqing", "1012");
                PropertiesUtils.writeProperties(propertiesPath, "gaoxinqu", "1150");
                PropertiesUtils.writeProperties(propertiesPath, "wenchanghu", "496");
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
            }
        }
        //以上已经创建好键值对对象]
        String propertiesPath = propFile.getAbsolutePath();


        List<String> countyList = TitleMap.getCountyList();

        List<TemporaryWorkDomain> temporaryWorkList = new ArrayList<>();


        //开始遍历将要录入的行
        for (String county : countyList) {
            TemporaryWorkDomain temporaryWorkDomain = new TemporaryWorkDomain();
            temporaryWorkDomain.setCounty(county);
            int allDevNum = 0;
            int zswfwxxiaoji = 0;
            int zswfwxGuangxian = 0;
            int zswfwxGongdian = 0;
            int zswfwxShebei = 0;
            int zswfwxTingyun = 0;
            int zswfwxQita = 0;

            int wxzZongji = 0;

            int wxzXiaoji = 0;
            int wxzDzjc = 0;
            int wxzDsjk = 0;
            int wxzKk = 0;

            int cbWxzXiaoJi = 0;
            int cbWxzDzjc = 0;
            int cbWxzDsjk = 0;
            int cbWxzKK = 0;

            //开始机器内部处理通过IP取得的故障
            for (Dayerrorwork dayerrorwork : dayerrorworks) {
                if (!dayerrorwork.getErrortableCounty().equals(county)) {
                    continue;
                }
                String errclass = dayerrorwork.getErrortableFaultclassification();
                if (errclass.equals("正在维修")) {
                    if (dayerrorwork.getErrortableDeviceType() == null) {
                        dayerrorwork.setErrortableDeviceType("");
                    }
                    String devType = dayerrorwork.getErrortableDeviceType();
                    boolean isKk = devType.equals("卡口") || devType.equals("反向卡口");
                    boolean isDj = devType.equals("电警") || devType.equals("微电警") || devType.equals("电子警察");

                    if (dayerrorwork.getErrortableServercompany().equals("已出质保")) {
                        if (isKk) {
                            cbWxzKK++;
                        }else if(isDj){
                            cbWxzDzjc++;
                        }else {
                            cbWxzDsjk++;
                        }
                    } else {
                        if (isKk) {
                            wxzKk++;
                        } else if (isDj) {
                            wxzDzjc++;
                        } else {
                            wxzDsjk++;
                        }
                    }
                } else {
                    switch (errclass) {
                        case "停运":
                            zswfwxTingyun++;
                            break;
                        case "供电":
                            zswfwxGongdian++;
                            break;
                        case "设备故障":
                            zswfwxShebei++;
                            break;
                        case "网络广电":
                            zswfwxGuangxian++;
                            break;
                        case "网络移动":
                            zswfwxGuangxian++;
                            break;
                        case "网络联通":
                            zswfwxGuangxian++;
                            break;
                        default:
                            zswfwxQita++;
                    }


                }
            }
            Map<String, Integer> countyallnum = null;
            try {
                countyallnum = PropertiesUtils.getAllProperties(propFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (county) {
                case "张店区":
                    allDevNum = countyallnum.get("zhangdian");
                    break;
                case "淄川区":
                    allDevNum = countyallnum.get("zichuan");
                    break;
                case "博山区":
                    allDevNum = countyallnum.get("boshan");
                    break;
                case "周村区":
                    allDevNum = countyallnum.get("zhoucun");
                    break;
                case "临淄区":
                    allDevNum = countyallnum.get("linzi");
                    break;
                case "桓台县":
                    allDevNum = countyallnum.get("huantai");
                    break;
                case "沂源县":
                    allDevNum = countyallnum.get("yiyuan");
                    break;
                case "高青县":
                    allDevNum = countyallnum.get("gaoqing");
                    break;
                case "高新区":
                    allDevNum = countyallnum.get("gaoxinqu");
                    break;
                case "文昌湖区":
                    allDevNum = countyallnum.get("wenchanghu");
                    break;
                default:
                    System.out.println("存在其他数据--获取总数量数量");
            }

            temporaryWorkDomain.setZswfwxGuangxian(zswfwxGuangxian);
            temporaryWorkDomain.setZswfwxGongdian(zswfwxGongdian);
            temporaryWorkDomain.setZswfwxShebei(zswfwxShebei);
            temporaryWorkDomain.setZswfwxTingyun(zswfwxTingyun);
            temporaryWorkDomain.setZswfwxQita(zswfwxQita);

            temporaryWorkDomain.setWxzDzjc(wxzDzjc);
            temporaryWorkDomain.setWxzDsjk(wxzDsjk);
            temporaryWorkDomain.setWxzKk(wxzKk);

            temporaryWorkDomain.setWxzCbDsjk(cbWxzDsjk);
            temporaryWorkDomain.setWxzCbDzjc(cbWxzDzjc);
            temporaryWorkDomain.setWxzCbKk(cbWxzKK);

            zswfwxxiaoji = zswfwxGuangxian + zswfwxGongdian + zswfwxShebei + zswfwxTingyun + zswfwxQita;
            temporaryWorkDomain.setZswfwxxiaoji(zswfwxxiaoji);
            wxzXiaoji = wxzDzjc + wxzDsjk + wxzKk;
            cbWxzXiaoJi = cbWxzDsjk+cbWxzDzjc+cbWxzKK;
            temporaryWorkDomain.setWxzCbXiaoji(cbWxzXiaoJi);
            wxzZongji = wxzXiaoji+cbWxzXiaoJi;
            temporaryWorkDomain.setWxzZongji(wxzZongji);
            temporaryWorkDomain.setWxzXiaoji(wxzXiaoji);

            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String zxl = numberFormat.format((((float) allDevNum - (float) wxzZongji) / (float) allDevNum) * 100) + "%";
            temporaryWorkDomain.setZxl(zxl);

            temporaryWorkDomain.setAllDevNum(allDevNum);

            temporaryWorkList.add(temporaryWorkDomain);

        }

        return temporaryWorkList;
    }

}
