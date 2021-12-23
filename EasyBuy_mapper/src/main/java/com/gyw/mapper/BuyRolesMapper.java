package com.gyw.mapper;

import com.gyw.bean.BuyRoles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyRolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyRoles record);

    int insertSelective(BuyRoles record);

    BuyRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyRoles record);

    int updateByPrimaryKey(BuyRoles record);
}