package com.chen17.service;

import com.chen17.domain.HkDeviceList;
import com.chen17.domain.HkIncident;

import java.util.List;

/**
 * @author User
 */
public interface HkIncidentServer {

    int insert(HkIncident record);

    List<String> getAllIncidentSn();

    int updateByPrimaryKeySelective(HkIncident record);

    HkIncident selectByErrLimitOne();

    int deleteBySN(String deviceSn);

    int updateBySN(HkIncident record);
}
