//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * @author Xuan Yi
 */
@Component
@Data
public class UpLoadDayErrorWork extends BaseRowModel {
    private Integer errortableId;
    @ExcelProperty(
            value = {"行政区域"},
            index = 0
    )
    private String errortableCounty;
    @ExcelProperty(
            value = {"发生位置"},
            index = 1
    )
    private String errortableDianweiname;
    @ExcelProperty(
            value = {"设备名称"},
            index = 5
    )
    private String errortableHaikangpname;
    @ExcelProperty(
            value = {"视频平台设备名称"},
            index = 7
    )
    private String errortableHaixinpname;
    @ExcelProperty(
            value = {"设备类型"},
            index = 2
    )
    private String errortableDeviceType;
    @ExcelProperty(
            value = {"设备IP"},
            index = 9
    )
    private String errortableDeviceIp;
    @ExcelProperty(
            value = {"维护单位"},
            index = 8
    )
    private String errortableBuildCompany;
    @ExcelProperty(
            value = {"质保情况"},
            index = 3
    )
    private String errortableDeviceExpriation;
    @ExcelProperty(
            value = {"最新检测时间"},
            index = 11
    )
    private String errortableLastestcheckTime;
    @ExcelProperty(
            value = {"初次初次检测时间"},
            index = 10
    )
    private Date errortableRequestTime;
    @ExcelProperty(
            value = {"维护单位"},
            index = 13
    )
    private String errortableServerCompany;
    @ExcelProperty(
            value = {"故障原因"},
            index = 14
    )
    private String errortableErrorReason;
    @ExcelProperty(
            value = {"故障发现平台"},
            index = 15
    )
    private String errortableErrorFindPlatform;
    @ExcelProperty(
            value = {"是否新增"},
            index = 16
    )
    private String errortable_newcreate;

}
