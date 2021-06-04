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
     * 1:高优先级；2，普通优先级
     */
    private Object deviceIspriority;

    /**
     * 巡检时间
     */
    private Date searchDatatime;

    private static final long serialVersionUID = 1L;
}
