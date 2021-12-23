package com.gyw.mapper;

import com.gyw.bean.BuyNews;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyNews record);

    int insertSelective(BuyNews record);

    BuyNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyNews record);

    int updateByPrimaryKey(BuyNews record);
}