package com.gyw.mapper;

import com.gyw.bean.BuyOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyOrder record);

    int insertSelective(BuyOrder record);

    BuyOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyOrder record);

    int updateByPrimaryKey(BuyOrder record);
}