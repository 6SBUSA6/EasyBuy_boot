    package com.gyw.mapper;

import com.gyw.bean.BuyContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyContent record);

    int insertSelective(BuyContent record);

    BuyContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyContent record);

    int updateByPrimaryKey(BuyContent record);
}