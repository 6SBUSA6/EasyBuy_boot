package com.gyw.mapper;

import com.gyw.bean.BuyMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyMenu record);

    int insertSelective(BuyMenu record);

    BuyMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyMenu record);

    int updateByPrimaryKey(BuyMenu record);
}