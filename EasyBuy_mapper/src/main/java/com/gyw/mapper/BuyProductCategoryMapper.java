package com.gyw.mapper;

import com.gyw.bean.BuyProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuyProductCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyProductCategory record);

    int insertSelective(BuyProductCategory record);

    BuyProductCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyProductCategory record);

    int updateByPrimaryKey(BuyProductCategory record);

    List<BuyProductCategory> findChildCategoryByPid(Integer pid);
}