package com.gyw.mapper;

import com.gyw.bean.BuyProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuyProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyProduct record);

    int insertSelective(BuyProduct record);

    BuyProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyProduct record);

    int updateByPrimaryKey(BuyProduct record);

    List<BuyProduct> findProductByFirstCategory(Integer id);
}