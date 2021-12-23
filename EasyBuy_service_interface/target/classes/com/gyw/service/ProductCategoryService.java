package com.gyw.service;

import com.gyw.bean.BuyProduct;
import com.gyw.bean.BuyProductCategory;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/15-9:42
 * @describe
 */
public interface ProductCategoryService {
    //根据父标签找到下面所有的子标签
    public List<BuyProductCategory> findCategoryByPid(Integer pid) throws  Exception;


}
