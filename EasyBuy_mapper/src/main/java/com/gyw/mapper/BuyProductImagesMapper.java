package com.gyw.mapper;

import com.gyw.bean.BuyProductImages;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BuyProductImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyProductImages record);

    int insertSelective(BuyProductImages record);

    BuyProductImages selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyProductImages record);

    int updateByPrimaryKey(BuyProductImages record);

    ArrayList<BuyProductImages> findProductImgByPid(Integer pid);
}