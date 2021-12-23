package com.gyw.mapper;

import com.gyw.bean.BuySeckillOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuySeckillOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BuySeckillOrder record);

    int insertSelective(BuySeckillOrder record);

    BuySeckillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BuySeckillOrder record);

    int updateByPrimaryKey(BuySeckillOrder record);
}