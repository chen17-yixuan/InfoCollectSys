//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.service;

import com.chen17.domain.Dayerrorwork;
import java.util.Date;
import java.util.List;

public interface DayWorkService {

    Dayerrorwork getInfoById(int id);

    List<Dayerrorwork> selectAll();

    Date getUpLoadLastDate();

    Dayerrorwork selectByPrimaryKey(Integer id);

    List<String> getAllIpList();

    int insertUpLoaded(Dayerrorwork record);

    Boolean cleanAllNewCreate();

    int deleteByPrimaryKey(Integer errortableId);

    List<Dayerrorwork> selectByCompanyInProResaon(String companyName, String ifInPro, String reason);

    List<Dayerrorwork> selectByCounty(String errortable_county);

    List<String> getAllServerCompany();

    List<Dayerrorwork> selectForInfoTable(String errortable_county, String errortable_errortabledianweiname, String errortable_haikangpname, String errortable_device_ip, String errortable_servercompany, String errortable_faultclassification, String errortable_belong_paltform, String errortable_newcreate);

    int updateByPrimaryKey(Dayerrorwork record);
}
