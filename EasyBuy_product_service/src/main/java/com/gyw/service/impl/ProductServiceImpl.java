package com.gyw.service.impl;

import com.gyw.bean.*;
import com.gyw.mapper.BuyProductCommentMapper;
import com.gyw.mapper.BuyProductImagesMapper;
import com.gyw.mapper.BuyProductMapper;
import com.gyw.service.ProductService;
import com.gyw.util.CookieUtil;
import com.gyw.util.JsonUtil;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/15-9:49
 * @describe
 */
@Service(interfaceClass = ProductService.class)
@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductService productService;

    @Autowired
    BuyProductMapper productMapper;

    @Autowired
    BuyProductImagesMapper imagesMapper;

    @Autowired
    BuyProductCommentMapper productCommentMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;






    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addProduct(BuyProduct product, List<String> imgStr) throws Exception {
        logger.info("这是添加商品的实现类"+product);

        BuyProduct newProduct = new BuyProduct();
        newProduct.setId(product.getId());
        newProduct.setSellerId(product.getSellerId());
        newProduct.setFilename(product.getFilename());
        newProduct.setPrice(product.getPrice());
        newProduct.setDescription(product.getDescription());
        newProduct.setCategorylevel1id(product.getCategorylevel1id());
        newProduct.setCategorylevel2id(product.getCategorylevel2id());
        newProduct.setCategorylevel3id(product.getCategorylevel3id());
        newProduct.setName(product.getName());
        newProduct.setIsdelete(product.getIsdelete());
        newProduct.setStock(product.getStock());

        productMapper.insertSelective(newProduct);
        logger.info("这是添加商品图片实现类"+imgStr);
        System.out.println("这是添加商品的Pid"+newProduct.getId());
        for (String imgName : imgStr) {
            BuyProductImages productImages = new BuyProductImages();
            productImages.setFilename(imgName);
            productImages.setPid(newProduct.getId());
            imagesMapper.insertSelective(productImages);
        }

    }

    @Override
    public List<BuyProduct> findProductByFirstCategory(Integer id) {
        List<BuyProduct> productList = productMapper.findProductByFirstCategory(id);
        return productList;
    }

    //更具商品id查找商品
    @Override
    public BuyProduct findProductByPid(Integer pid) {
        BuyProduct product = productMapper.selectByPrimaryKey(pid);
        return product;
    }

    //更具商品id查询商品的照片
    @Override
    public ArrayList<BuyProductImages> findProductImgByPid(Integer pid) {
        ArrayList<BuyProductImages> list = imagesMapper.findProductImgByPid(pid);
        return list;
    }

    //根据商品id查询好评差评价的数量
    @Override
    public HashMap<String, Object> findProductCommentNumBiPid(Integer pid) {
        HashMap<String, Object> map = productCommentMapper.findProductCommentNumBiPid(pid);
        return map;
    }

    //登陆过并且把购物车放到redis中
    @Override
    public void addCarToRedis(BuyProduct product, CarItem carItem,  BuyUser loginUser)throws Exception {
        //key是用户的uuid,valu的key是商品的idvalue是商品的信息
        /*String cookieCarListStr = CookieUtil.getCookieValue(request, "carList", true);
        List<CarItem> carList = null;
        if(cookieCarListStr != null && !"".equals(cookieCarListStr)){
            //在未登录状态下添加过购物车
            System.out.println("在未登录状态下添加过购物车");
        }*/
        //logger.error("从controller传过来的product"+product);
        //logger.error("从controller传过来的比较"+redisTemplate.boundHashOps("car_"+loginUser.getId()).hasKey(product.getId()+""));
        if(redisTemplate.boundHashOps("car_"+loginUser.getId()).hasKey(product.getId()+"")){
            //redis中已经有此商品了
            logger.error("redis中已经有此商品了");
            String carItemStr = (String) redisTemplate.boundHashOps("car_" + loginUser.getId()).get(product.getId() + "");
            CarItem redisCarItem = JsonUtil.jsonToPojo(carItemStr, CarItem.class);
            redisCarItem.setBuyNum(carItem.getBuyNum()+redisCarItem.getBuyNum());
            carItemStr = JsonUtil.objectToJson(redisCarItem);
            redisTemplate.boundHashOps("car_" + loginUser.getId()).put(product.getId() + "",carItemStr);

        }else {
            //第一次购买此商品
            logger.error("第一次购买此商品");
            String carItemStr = JsonUtil.objectToJson(carItem);
            redisTemplate.boundHashOps("car_" + loginUser.getId()).put(product.getId() + "",carItemStr);
        }



    }

    @Override
    public List<CarItem> findCarListFromRedis(BuyUser loginUser)throws Exception {
        List<Object> carItem = redisTemplate.boundHashOps("car_" + loginUser.getId()).values();
        ArrayList<CarItem> carList = new ArrayList<>();
        for (Object o : carItem) {
            carList.add(JsonUtil.jsonToPojo(o.toString(),CarItem.class));
        }
        return carList;
    }

    @Override
    public void updateCarNumFromRedis(BuyUser loginUser, Integer num, BuyProduct product)throws Exception {
        List<Object> carListStr = redisTemplate.boundHashOps("user_" + loginUser.getId()).values();
        List<CarItem> carList = JsonUtil.jsonToList(carListStr.toString(), CarItem.class);
        for (CarItem item : carList) {
            if(product.getId().equals(item.getProduct().getId())){
                item.setBuyNum(item.getBuyNum()+num);
                break;
            }
        }
    }

    @Override
    public void deleteFromCar(Integer pid, Integer uid) throws Exception {
        redisTemplate.boundHashOps("car_"+uid).delete(pid+"");
    }

    //从redis中获取caritem
    @Override
    public CarItem findCarItemFromRedisByPidAndUid(Integer pid, Integer uid) {
        String carItemStr = (String) redisTemplate.boundHashOps("car_" + uid).get(pid+"");
        CarItem carItem = JsonUtil.jsonToPojo(carItemStr, CarItem.class);
        return carItem;
    }


}
