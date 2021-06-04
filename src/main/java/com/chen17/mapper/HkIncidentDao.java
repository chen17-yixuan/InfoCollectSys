package com.chen17.mapper;

import com.chen17.domain.HkDeviceList;
import com.chen17.domain.HkIncident;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author User
 */
@Mapper
@Repository
public interface HkIncidentDao {
    int deleteByPrimaryKey(Integer incidentId);

    int insert(HkIncident record);

    int insertSelective(HkIncident record);

    HkIncident selectByPrimaryKey(Integer incidentId);

    int deleteBySN(String deviceSn);

    int updateByPrimaryKeySelective(HkIncident record);

    int updateByPrimaryKey(HkIncident record);

    int updateBySN(HkIncident record);

    List<String> getAllIncidentSn();

    HkIncident selectByErrLimitOne();
}
