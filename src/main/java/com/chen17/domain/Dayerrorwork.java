//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.domain;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Xuan Yi
 */
@Component
@Data
public class Dayerrorwork extends BaseRowModel implements Serializable {
    @ExcelProperty(
            value = {"序号"},
            index = 0
    )
    private Integer errortableId;
    @ExcelProperty(
            value = {"区县"},
            index = 1
    )
    private String errortableCounty;
    @ExcelProperty(
            value = {"点位名称"},
            index = 2
    )
    private String errortableDianweiname;
    @ExcelProperty(
            value = {"海康平台名称"},
            index = 3
    )
    private String errortableHaikangpname;
    @ExcelProperty(
            value = {"海信平台名称"},
            index = 4
    )
    private String errortableHaixinpname;
    @ExcelProperty(
            value = {"设备类型"},
            index = 5
    )
    private String errortableDeviceType;
    @ExcelProperty(
            value = {"设备IP"},
            index = 6
    )
    private String errortableDeviceIp;
    @ExcelProperty(
            value = {"建设单位"},
            index = 7
    )
    private String errortableBuildCompany;
    @ExcelProperty(
            value = {"质保情况"},
            index = 8
    )
    private String errortableDeviceExpriation;
    @ExcelProperty(
            value = {"维修情况"},
            index = 9
    )
    private String errortableRepairStatus;
    @ExcelProperty(
            value = {"故障现象"},
            index = 10
    )
    private String errortableShow;
    @ExcelProperty(
            value = {"最新检测时间"},
            index = 11
    )
    private String errortableLastestcheckTime;
    @ExcelProperty(
            value = {"请求时间"},
            index = 12
    )
    private Date errortableRequestTime;
    @ExcelProperty(
            value = {"备注"},
            index = 13
    )
    private String errortableNote;
    @ExcelProperty(
            value = {"服务公司"},
            index = 14
    )
    private String errortableServercompany;
    @ExcelProperty(
            value = {"故障分类"},
            index = 15
    )
    private String errortableFaultclassification;
    @ExcelProperty(
            value = {"平台归属"},
            index = 16
    )
    private String errortableBelongPaltform;
    @ExcelProperty(
            value = {"是否新增"},
            index = 17
    )
    private String errortableNewcreate;
    private static final long serialVersionUID = 1L;

}
