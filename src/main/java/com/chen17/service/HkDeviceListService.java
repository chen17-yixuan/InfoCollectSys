package com.chen17.service;

import com.chen17.domain.HkDeviceList;

import java.util.List;

public interface HkDeviceListService {

    HkDeviceList selectByPrimaryKey(String deviceSn);

     boolean testDo();

    List<String> getAllArea();

    int getORGAllNum(String ORGName);

    int getORGErrNum(String ORGName);

    int getORGNorNum(String ORGName);

    int updateByPrimaryKeySelective(HkDeviceList record);

    HkDeviceList selectByORGLimitOne(String ORGName);

    int truncateList();

    int insert(HkDeviceList record);

    int deleteByPrimaryKey(String deviceSn);



}
