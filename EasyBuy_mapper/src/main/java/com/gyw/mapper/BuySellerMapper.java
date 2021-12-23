package com.gyw.mapper;

import com.gyw.bean.BuySeller;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuySellerMapper {
    int deleteByPrimaryKey(String sellerId);

    int insert(BuySeller record);

    int insertSelective(BuySeller record);

    BuySeller selectByPrimaryKey(String sellerId);

    int updateByPrimaryKeySelective(BuySeller record);

    int updateByPrimaryKey(BuySeller record);
}