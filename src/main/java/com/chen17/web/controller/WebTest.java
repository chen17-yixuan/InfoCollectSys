package com.chen17.web.controller;

import com.chen17.domain.ErrTable;
import com.chen17.mapper.ErrTableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yd
 * @version 1.0
 * @date 2020-10-17 13:11
 */

@RequestMapping("/user")
@RestController
public class WebTest {

    @Autowired
    private ErrTableDao errTableDao;

    @RequestMapping("get/{id}")
    public String GetUser(@PathVariable int id) {
        return errTableDao.selectByPrimaryKey(id).toString();
    }
}
