package com.gyw.mapper;

import com.gyw.bean.BuyOrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyOrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyOrderDetail record);

    int insertSelective(BuyOrderDetail record);

    BuyOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyOrderDetail record);

    int updateByPrimaryKey(BuyOrderDetail record);
}