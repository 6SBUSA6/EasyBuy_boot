package com.gyw.service;

import com.gyw.bean.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/15-9:43
 * @describe
 */
public interface ProductService {
    public void addProduct(BuyProduct product, List<String> imgStr) throws Exception;
    //根据一级标签的id查找商品
    List<BuyProduct> findProductByFirstCategory(Integer id) throws Exception;

    BuyProduct findProductByPid(Integer pid);

    ArrayList<BuyProductImages> findProductImgByPid(Integer pid);

    HashMap<String, Object> findProductCommentNumBiPid(Integer pid);

    void addCarToRedis(BuyProduct product, CarItem carItem,  BuyUser loginUser) throws Exception;

    List<CarItem> findCarListFromRedis(BuyUser loginUser) throws Exception;

    void updateCarNumFromRedis(BuyUser loginUser, Integer num, BuyProduct product)throws Exception;


    void deleteFromCar(Integer pid, Integer uid) throws Exception;

    CarItem findCarItemFromRedisByPidAndUid(Integer id, Integer id1);
}
