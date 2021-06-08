package com.chen17.service.Impl;

import com.chen17.domain.HkDeviceList;
import com.chen17.mapper.HkDeviceListDao;
import com.chen17.service.HkDeviceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yd
 * @version 1.0
 * @date 2021-03-26 15:47
 */
@Service
public class HkDeviceListServiceImpl implements HkDeviceListService {
    HkDeviceListDao hkDeviceListDao;
    public HkDeviceListServiceImpl(){
    }
    @Autowired
    public HkDeviceListServiceImpl (HkDeviceListDao hkDeviceListDao) {
        this.hkDeviceListDao = hkDeviceListDao;
    }


    @Override
    public HkDeviceList selectByPrimaryKey(String deviceSn) {
        return hkDeviceListDao.selectByPrimaryKey(deviceSn);
    }

    @Override
    public boolean testDo(){
        System.out.println("内部无问题");
        return false;
    }

    @Override
    public List<String> getAllArea() {
        return hkDeviceListDao.getAllArea();
    }

    @Override
    public int getORGAllNum(String ORGName) {
        return hkDeviceListDao.getORGAllNum(ORGName);
    }

    @Override
    public int getORGErrNum(String ORGName) {
        return hkDeviceListDao.getORGErrNum(ORGName);
    }

    @Override
    public int getORGNorNum(String ORGName) {
        return hkDeviceListDao.getORGNorNum(ORGName);
    }

    @Override
    public int updateByPrimaryKeySelective(HkDeviceList record) {
        return hkDeviceListDao.updateByPrimaryKey(record);
    }

    @Override
    public HkDeviceList selectByORGLimitOne(String ORGName) {
        return hkDeviceListDao.selectByORGLimitOne(ORGName);
    }

    @Override
    public int truncateList() {
        return hkDeviceListDao.truncateList();
    }

    @Override
    public int insert(HkDeviceList record) {
        return hkDeviceListDao.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(String deviceSn) {
        return hkDeviceListDao.deleteByPrimaryKey(deviceSn);
    }

    @Override
    public List<HkDeviceList> selectAllByORG(String ORGName) {
        return hkDeviceListDao.selectAllByORG(ORGName);
    }

}
