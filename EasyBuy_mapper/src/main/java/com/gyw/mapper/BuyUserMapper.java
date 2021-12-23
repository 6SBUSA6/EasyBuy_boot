package com.gyw.mapper;

import com.gyw.bean.BuyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BuyUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyUser record);

    int insertSelective(BuyUser record);

    BuyUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyUser record);

    int updateByPrimaryKey(BuyUser record);

    BuyUser selectUserByLoginName(String loginname);

    int isVip(@Param("uid") Integer id, @Param("ym") String ymDate);
}