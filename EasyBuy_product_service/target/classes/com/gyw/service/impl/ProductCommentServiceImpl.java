package com.gyw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gyw.bean.BuyProductComment;
import com.gyw.mapper.BuyProductCommentMapper;
import com.gyw.service.ProductCommentService;
import com.gyw.service.ProductService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/19-19:15
 * @describe
 */
@Component
@Service(interfaceClass = ProductCommentService.class)
public class ProductCommentServiceImpl implements ProductCommentService {
    @Autowired
    BuyProductCommentMapper productCommentMapper;


    @Override
    public PageInfo<BuyProductComment> findAllCommentByPid(Integer pid, Integer pageNum) {
        //找出所有商品的评论
        if(pageNum == null){
            pageNum = 1;
        }
        System.out.println("当前页："+pageNum);
        PageHelper.startPage(pageNum,2);
        List<BuyProductComment> list = productCommentMapper.findAllCommentByPid(pid);
        PageInfo<BuyProductComment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<BuyProductComment> test(Integer pid) {
        List<BuyProductComment> list = productCommentMapper.findAllCommentByPid(pid);
        return list;
    }

}
