package com.gyw.mapper;

import com.gyw.bean.BuyUserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuyUserAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyUserAddress record);

    int insertSelective(BuyUserAddress record);

    BuyUserAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyUserAddress record);

    int updateByPrimaryKey(BuyUserAddress record);

    List<BuyUserAddress> findAddressByUid(Integer id);
}