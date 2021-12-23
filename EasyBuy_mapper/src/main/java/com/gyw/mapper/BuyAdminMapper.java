package com.gyw.mapper;

import com.gyw.bean.BuyAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyAdminMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(BuyAdmin record);

    int insertSelective(BuyAdmin record);

    BuyAdmin selectByPrimaryKey(Integer aId);

    int updateByPrimaryKeySelective(BuyAdmin record);

    int updateByPrimaryKey(BuyAdmin record);
}