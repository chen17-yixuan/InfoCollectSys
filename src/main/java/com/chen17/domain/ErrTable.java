package com.chen17.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * err_table
 * @author 
 */
@Data
@Component
public class ErrTable implements Serializable {
    private Integer errortableId;

    private String errortableCounty;

    private String errortableDianweiname;

    private String errortableHaikangpname;

    private String errortableHaixinpname;

    private String errortableDeviceType;

    private String errortableBelongPaltform;

    private String errortableDeviceIp;

    private String errortableMaintenance;

    private String errortableDeviceExpriation;

    private String errortableRepairStatus;

    private String errortableFaultclassification;

    private String errortableShow;

    private String errortableLastestcheckTime;

    private Date errortableRequestTime;

    private String errortableNote;

    private static final long serialVersionUID = 1L;
}