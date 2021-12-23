package com.gyw.mapper;

import com.gyw.bean.BuyProductComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BuyProductCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyProductComment record);

    int insertSelective(BuyProductComment record);

    BuyProductComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyProductComment record);

    int updateByPrimaryKey(BuyProductComment record);

    HashMap<String, Object> findProductCommentNumBiPid(Integer pid);

    List<BuyProductComment> findAllCommentByPid(Integer pid);
}