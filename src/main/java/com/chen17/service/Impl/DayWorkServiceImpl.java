//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.service.Impl;

import com.chen17.domain.Dayerrorwork;
import com.chen17.mapper.DayerrorworkDao;
import com.chen17.service.DayWorkService;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayWorkServiceImpl implements DayWorkService {
    private DayerrorworkDao dayerrorworkDao;

    public DayWorkServiceImpl() {
    }

    @Autowired
    public void DayWorkServiceImpl(DayerrorworkDao dayerrorworkDao) {
        this.dayerrorworkDao = dayerrorworkDao;
    }

    @Override
    public Dayerrorwork getInfoById(int id) {
        return this.dayerrorworkDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Dayerrorwork> selectAll() {
        return this.dayerrorworkDao.selectAll();
    }

    @Override
    public Date getUpLoadLastDate() {
        return this.dayerrorworkDao.getUpLoadLastDate();
    }

    @Override
    public Dayerrorwork selectByPrimaryKey(Integer id) {
        return this.dayerrorworkDao.selectByPrimaryKey(id);
    }

    @Override
    public List<String> getAllIpList() {
        return this.dayerrorworkDao.getAllIpList();
    }

    @Override
    public int insertUpLoaded(Dayerrorwork record) {
        return this.dayerrorworkDao.insert(record);
    }

    @Override
    public Boolean cleanAllNewCreate() {
        return this.dayerrorworkDao.cleanAllNewCreate();
    }

    @Override
    public int deleteByPrimaryKey(Integer errortableId) {
        return this.dayerrorworkDao.deleteByPrimaryKey(errortableId);
    }

    @Override
    public List<Dayerrorwork> selectByCompanyInProResaon(String companyName, String ifInPro, String reason) {
        return this.dayerrorworkDao.selectByCompanyInProResaon(companyName, ifInPro, reason);
    }

    @Override
    public List<Dayerrorwork> selectByCounty(String errortable_county) {
        return this.dayerrorworkDao.selectByCounty(errortable_county);
    }

    @Override
    public List<String> getAllServerCompany() {
        return this.dayerrorworkDao.getAllServerCompany();
    }

    @Override
    public List<Dayerrorwork> selectForInfoTable(String errortable_county, String errortable_errortabledianweiname, String errortable_haikangpname, String errortable_device_ip, String errortable_servercompany, String errortable_faultclassification, String errortable_belong_paltform, String errortable_newcreate) {
        return this.dayerrorworkDao.selectForInfoTable(errortable_county, errortable_errortabledianweiname, errortable_haikangpname, errortable_device_ip, errortable_servercompany, errortable_faultclassification, errortable_belong_paltform, errortable_newcreate);
    }

    @Override
    public int updateByPrimaryKey(Dayerrorwork record) {
        return this.dayerrorworkDao.updateByPrimaryKey(record);
    }

    @Override
    public List<Dayerrorwork> selectByIpAddress(Map<String, List<String>> IpAddressMap) {
        return dayerrorworkDao.selectByIpAddress(IpAddressMap);
    }
}
