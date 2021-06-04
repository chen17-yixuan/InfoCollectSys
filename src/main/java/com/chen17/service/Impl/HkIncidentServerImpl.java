package com.chen17.service.Impl;

import com.chen17.domain.HkDeviceList;
import com.chen17.domain.HkIncident;
import com.chen17.mapper.HkIncidentDao;
import com.chen17.service.HkIncidentServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yd
 * @version 1.0
 * @date 2021-06-03 10:10
 */
@Service
public class HkIncidentServerImpl implements HkIncidentServer {

    HkIncidentDao hkic;

    public HkIncidentServerImpl(){}

    @Autowired
    public HkIncidentServerImpl(HkIncidentDao hkic){ this.hkic = hkic; }

    @Override
    public int insert(HkIncident record) {
        return hkic.insert(record);
    }

    @Override
    public List<String> getAllIncidentSn() {
        return hkic.getAllIncidentSn();
    }

    @Override
    public int updateByPrimaryKeySelective(HkIncident record) {
        return hkic.updateByPrimaryKeySelective(record);
    }

    @Override
    public HkIncident selectByErrLimitOne() {
        return hkic.selectByErrLimitOne();
    }

    @Override
    public int deleteBySN(String deviceSn) {
        return hkic.deleteBySN(deviceSn);
    }

    @Override
    public int updateBySN(HkIncident record) {
        return hkic.updateBySN(record);
    }

    @Override
    public List<HkIncident> searchHkIncidentBySomeFiled(String problem, String repairStatus, String company, String reason) {
        return hkic.searchHkIncidentBySomeFiled(problem,repairStatus,company,reason);
    }
}
