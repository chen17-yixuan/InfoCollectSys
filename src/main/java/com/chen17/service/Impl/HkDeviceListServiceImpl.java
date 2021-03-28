package com.chen17.service.Impl;

import com.chen17.mapper.HkDeviceListDao;
import com.chen17.service.HkDeviceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
