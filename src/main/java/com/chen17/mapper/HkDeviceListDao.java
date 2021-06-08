package com.chen17.mapper;

import com.chen17.domain.HkDeviceList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HkDeviceListDao {
    int deleteByPrimaryKey(String deviceSn);

    int insert(HkDeviceList record);

    int insertSelective(HkDeviceList record);

    HkDeviceList selectByPrimaryKey(String deviceSn);

    int updateByPrimaryKeySelective(HkDeviceList record);

    int updateByPrimaryKey(HkDeviceList record);

    List<String> getAllArea();

    int getORGAllNum(String ORGName);

    int getORGErrNum(String ORGName);

    int getORGNorNum(String ORGName);

    HkDeviceList selectByORGLimitOne(String ORGName);

    int truncateList();

    List<HkDeviceList> selectAllByORG(String ORGName);
}
