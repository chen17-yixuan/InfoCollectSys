package com.chen17.web.controller;

import com.chen17.service.HkDeviceListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yd
 * @version 1.0
 * @date 2021-03-26 16:19
 */
@RestController
@RequestMapping({"/hkabout"})
public class HKAboutController {

    private static final Logger log = LoggerFactory.getLogger(HKAboutController.class);
    HkDeviceListService hks;

    @RequestMapping("/docheck")
    public String doCheck(){
        System.out.println("Check Done");
        return  "成功";
    }
}
