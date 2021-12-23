package com.gyw.mapper;

import com.gyw.bean.BuySeckillGoods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuySeckillGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BuySeckillGoods record);

    int insertSelective(BuySeckillGoods record);

    BuySeckillGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BuySeckillGoods record);

    int updateByPrimaryKey(BuySeckillGoods record);
}