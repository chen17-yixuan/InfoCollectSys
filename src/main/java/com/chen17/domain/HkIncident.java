package com.chen17.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * hk_incident
 * @author
 */
@Data
@Component
public class HkIncident extends BaseRowModel implements Serializable {
    private Integer incidentId;

    /**
     * 设备SN
     */
    @ExcelProperty(value = "设备SN",index = 0)
    private String deviceSn;

    /**
     * 监控点名称
     */
    @ExcelProperty(value = "监控点名称",index = 1)
    private String incidentDevname;

    /**
     * 故障现象
     */
    @ExcelProperty(value = "故障现象",index = 2)
    private String incidentProblem;

    /**
     * 故障详细现象
     */
    @ExcelProperty(value = "故障详细现象",index = 3)
    private String incidentSubproblem;

    /**
     * 故障备注
     */
    @ExcelProperty(value = "故障备注",index = 4)
    private String incidentNote;

    /**
     * 故障发现时间
     */
    @ExcelProperty(value = "故障发现时间",index = 5)
    private Date incidentFindtime;

    /**
     * 故障状态:1:修复；0：未修复
     */
    @ExcelProperty(value = "故障状态",index = 6)
    private Object incidentRepairStatus;

    /**
     * 维保单位
     */
    @ExcelProperty(value = "维保单位",index = 7)
    private String incidentWarrintyCompany;

    /**
     * 故障原因类别
     */
    @ExcelProperty(value = "故障原因类别",index = 7)
    private String incidentReason;

    /**
     * 故障详细原因
     */
    @ExcelProperty(value = "故障详细原因",index = 8)
    private String incidentReasonNote;

    private static final long serialVersionUID = 1L;
}
