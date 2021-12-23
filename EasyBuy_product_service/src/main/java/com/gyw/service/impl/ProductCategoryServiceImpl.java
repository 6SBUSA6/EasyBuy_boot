package com.gyw.service.impl;

import com.gyw.bean.BuyProductCategory;
import com.gyw.mapper.BuyProductCategoryMapper;
import com.gyw.service.ProductCategoryService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/15-10:04
 * @describe
 */
@Service(interfaceClass = ProductCategoryService.class)
@Component
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    BuyProductCategoryMapper productCategoryMapper;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Override
    public List<BuyProductCategory> findCategoryByPid(Integer pid) throws Exception {

        if(redisTemplate.boundHashOps("categoryList").get("category_"+pid) == null){
            //redis数据库中存的父标签的子标签是空的，就从子标签中取出
            List<BuyProductCategory> childCategoryList = productCategoryMapper.findChildCategoryByPid(pid);
            logger.info("从数据库中取出的子标签:"+childCategoryList);
            redisTemplate.boundHashOps("categoryList").put("category_"+pid,childCategoryList);
            return childCategoryList;
        }
        //redis数据库中不为空的话，直接从数据库中取出来
        List<BuyProductCategory> childCategoryList =(List<BuyProductCategory>) redisTemplate.boundHashOps("categoryList").get("category_" + pid);
        logger.info("从redis里面取出来的集合："+childCategoryList);
        return childCategoryList;




        /*logger.info("这是查找子标签的实现类"+pid);
        List<BuyProductCategory> childCategoryList =  productCategoryMapper.findChildCategoryByPid(pid);*/
    }
}
