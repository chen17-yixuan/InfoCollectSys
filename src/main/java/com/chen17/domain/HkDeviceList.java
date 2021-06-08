package com.chen17.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * hk_device_list
 * @author
 */
@Component
@Data
public class HkDeviceList extends BaseRowModel implements Serializable  {
    /**
     * 设备SN
     */
    @ExcelProperty(value = {"监控点编号"} , index = 2)
    private String deviceSn;

    /**
     * 监控点名称
     */
    @ExcelProperty(value = {"监控点名称"} , index = 1)
    private String deviceName;

    /**
     * 所属组织
     */
    @ExcelProperty(value = {"所属组织"} , index = 3)
    private String deviceOrg;

    /**
     * 所属区域
     */
    @ExcelProperty(value = {"所属区域"} , index = 4)
    private String deviceArea;

    /**
     * 1:正常；2，异常； 0.未巡检
     */

    private Object deviceIspriority;

    /**
     * 巡检时间
     */
    private Date searchDatatime;

    @ExcelProperty(value = {"状态分类"} , index = 5)
    private String problem;
    @ExcelProperty(value = {"详细原因"} , index = 6)
    private String subproblem;
    @ExcelProperty(value = {"问题备注"} , index = 7)
    private String problemnote;


    private static final long serialVersionUID = 1L;
}
