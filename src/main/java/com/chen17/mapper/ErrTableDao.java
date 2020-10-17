package com.chen17.mapper;

import com.chen17.domain.ErrTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 15553
 */
@Repository
@Mapper
public interface ErrTableDao {

    int deleteByPrimaryKey(Integer errortableId);

    int insert(ErrTable record);

    int insertSelective(ErrTable record);

    ErrTable selectByPrimaryKey(Integer errortableId);

    int updateByPrimaryKeySelective(ErrTable record);

    int updateByPrimaryKey(ErrTable record);
}