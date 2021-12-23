package com.gyw.service;

import com.github.pagehelper.PageInfo;
import com.gyw.bean.BuyProductComment;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/19-19:16
 * @describe
 */
public interface ProductCommentService {
    PageInfo<BuyProductComment> findAllCommentByPid(Integer pid, Integer pageNum);
    List<BuyProductComment> test(Integer pid);
}
