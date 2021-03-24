//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.utils.Excel;

import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.UpLoadDayErrorWork;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UploadUtil {
    public UploadUtil() {
    }

    public static List<Dayerrorwork> upFillToMod(List<UpLoadDayErrorWork> upList, List<String> idList, Date lastestTime) {
        if (upList == null) {
            return null;
        } else {
            List<Dayerrorwork> list = new ArrayList();
            if (list == null) {
                return null;
            } else {
                Iterator var4 = upList.iterator();

                while(var4.hasNext()) {
                    UpLoadDayErrorWork ued = (UpLoadDayErrorWork)var4.next();
                    if (ued.getErrortableRequestTime() == null || ued.getErrortableRequestTime().equals("")) {
                        ued.setErrortableRequestTime(new Date());
                    }


                    if (!idList.contains(ued.getErrortableDeviceIp()) ){
                        Dayerrorwork dw = new Dayerrorwork();
                        dw.setErrortableBuildCompany(ued.getErrortableBuildCompany());
                        dw.setErrortableLastestcheckTime(ued.getErrortableLastestcheckTime());
                        dw.setErrortableCounty(ued.getErrortableCounty());
                        dw.setErrortableDeviceIp(ued.getErrortableDeviceIp());
                        dw.setErrortableNewcreate("新增");
                        dw.setErrortableDeviceExpriation(ued.getErrortableDeviceExpriation());
                        dw.setErrortableDianweiname(ued.getErrortableDianweiname());
                        dw.setErrortableRequestTime(ued.getErrortableRequestTime());
                        dw.setErrortableHaikangpname(ued.getErrortableHaikangpname());
                        dw.setErrortableHaixinpname(ued.getErrortableHaixinpname());
                        dw.setErrortableDeviceType(ued.getErrortableDeviceType());
                        dw.setErrortableRepairStatus("正在维修");
                        dw.setErrortableServercompany(ued.getErrortableServerCompany());
                        dw.setErrortableFaultclassification(ued.getErrortableErrorReason());
                        dw.setErrortableBelongPaltform(ued.getErrortableErrorFindPlatform());
                        dw.setErrortableShow("设备离线");
                        dw.setErrortableFaultclassification("正在维修");
                        list.add(dw);
                    }
                }

                return list;
            }
        }
    }
}
