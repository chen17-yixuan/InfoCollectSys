package com.chen17.domain.temporary;

import com.alibaba.excel.annotation.ExcelColumnNum;
import lombok.Data;

/**
 * @author yd
 * @version 1.0
 * @date 2020-11-29 11:29
 */

@Data
public class TemporaryWorkDomain {
    //全市道路交通前端科技设备在线率统计表
    @ExcelColumnNum(1)
    private String county;
    @ExcelColumnNum(2)
    private Integer allDevNum;

    @ExcelColumnNum(3)
    private Integer zswfwxxiaoji;

    @ExcelColumnNum(4)
    private Integer zswfwxGuangxian;
    @ExcelColumnNum(5)
    private Integer zswfwxGongdian;
    @ExcelColumnNum(6)
    private Integer zswfwxShebei;
    @ExcelColumnNum(7)
    private Integer zswfwxTingyun;
    @ExcelColumnNum(8)
    private Integer zswfwxQita;

    @ExcelColumnNum(9)
    private Integer wxzZongji;

    @ExcelColumnNum(10)
    private Integer wxzXiaoji;
    @ExcelColumnNum(11)
    private Integer wxzDzjc;
    @ExcelColumnNum(12)
    private Integer wxzDsjk;
    @ExcelColumnNum(13)
    private Integer wxzKk;

    @ExcelColumnNum(14)
    private Integer wxzCbXiaoji;
    @ExcelColumnNum(15)
    private Integer wxzCbDzjc;
    @ExcelColumnNum(16)
    private Integer wxzCbDsjk;
    @ExcelColumnNum(17)
    private Integer wxzCbKk;

    @ExcelColumnNum(18)
    private String zxl;
}
