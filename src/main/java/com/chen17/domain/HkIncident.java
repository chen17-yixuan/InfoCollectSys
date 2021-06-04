package com.chen17.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * hk_incident
 * @author
 */
@Data
@Component
public class HkIncident implements Serializable {
    private Integer incidentId;

    /**
     * 设备SN
     */
    private String deviceSn;

    /**
     * 监控点名称
     */
    private String incidentDevname;

    /**
     * 故障现象
     */
    private String incidentProblem;

    /**
     * 故障详细现象
     */
    private String incidentSubproblem;

    /**
     * 故障备注
     */
    private String incidentNote;

    /**
     * 故障发现时间
     */
    private Date incidentFindtime;

    /**
     * 故障状态:1:修复；0：未修复
     */
    private Object incidentRepairStatus;

    /**
     * 维保单位
     */
    private String incidentWarrintyCompany;

    /**
     * 故障原因类别
     */
    private String incidentReason;

    /**
     * 故障详细原因
     */
    private String incidentReasonNote;

    private static final long serialVersionUID = 1L;
}
