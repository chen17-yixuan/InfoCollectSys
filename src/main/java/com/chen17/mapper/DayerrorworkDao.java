//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.mapper;

import com.chen17.domain.Dayerrorwork;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DayerrorworkDao {
    int deleteByPrimaryKey(Integer errortableId);

    int insert(Dayerrorwork record);

    int insertSelective(Dayerrorwork record);

    Dayerrorwork selectByPrimaryKey(Integer errortableId);

    int updateByPrimaryKeySelective(Dayerrorwork record);

    int updateByPrimaryKey(Dayerrorwork record);

    List<Dayerrorwork> selectAll();

    List<Dayerrorwork> selectByCompanyInProResaon(String errortable_servercompany, String errortable_device_expriation, String errortable_faultclassification);

    List<Dayerrorwork> selectByCounty(String errortable_county);

    List<String> getAllServerCompany();

    Date getUpLoadLastDate();

    List<String> getAllIpList();

    Boolean cleanAllNewCreate();

    List<Dayerrorwork> selectForInfoTable(String errortable_county, String errortable_dianwei, String errortable_haikangpname, String errortable_device_ip, String errortable_servercompany, String errortable_faultclassification, String errortable_belong_paltform, String errortable_newcreate);

    List<Dayerrorwork> selectByIpAddress(Map<String, List<String>> IpAddressMap);
}
