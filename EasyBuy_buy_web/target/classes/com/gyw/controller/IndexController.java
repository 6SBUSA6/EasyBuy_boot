package com.gyw.controller;

import com.gyw.bean.BuyProduct;
import com.gyw.bean.BuyProductCategory;
import com.gyw.bean.BuyUser;
import com.gyw.service.ProductCategoryService;
import com.gyw.service.ProductService;
import com.gyw.service.Userservice;
import com.gyw.util.CookieUtil;
import com.gyw.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 高源蔚
 * @date 2021/12/17-16:03
 * @describe
 */
@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Reference(url = "dubbo://localhost:20882")
    ProductCategoryService productCategoryService;

    @Reference(url = "dubbo://localhost:20882")
    ProductService productService;

    @Reference(url = "dubbo://localhost:20883")
    Userservice userservice;

    @Autowired
    RedisTemplate redisTemplate;


    //查找所有的标签
    @RequestMapping("/index")
    public String findAllCategory(Model model, HttpServletRequest request, HttpServletResponse response){
        logger.info("查找所有标签:");
        //一级标签
        List<BuyProductCategory> categoryListLevel1 = new ArrayList<>();
        //二级标签(父标签id,所有子标签)
        HashMap<Integer, List<BuyProductCategory>> categoryMapLevel2 = new HashMap<>();
        //三级标签(二级标签id,所有对应的子标签)
        HashMap<Integer, List<BuyProductCategory>> categoryMapLevel3 = new HashMap<>();
        //一级标签的六个商品(标签id,商品)
        Map<Integer, List<BuyProduct>> productMapByFirstCategory = new HashMap<>();
        try {
            categoryListLevel1 = productCategoryService.findCategoryByPid(0);//所有的一级标签
            //找到所有一级标签下的二级标签
            for (BuyProductCategory firstCategory : categoryListLevel1) {
                //找到各一级标签下的六个商品
                productMapByFirstCategory.put(firstCategory.getId(),productService.findProductByFirstCategory(firstCategory.getId()));
                //找到一级标签下的二级标签
                List<BuyProductCategory> categoryListLevel2 = productCategoryService.findCategoryByPid(firstCategory.getId());
                categoryMapLevel2.put(firstCategory.getId(),categoryListLevel2);
                //找到所有二级标签下的三级标签
                for (BuyProductCategory secondCategory : categoryListLevel2) {
                    List<BuyProductCategory> categoryListLevel3 = productCategoryService.findCategoryByPid(secondCategory.getId());
                    categoryMapLevel3.put(secondCategory.getId(),categoryListLevel3);
                }
            }
            //从cookie里获取用户的uuid
            String uuid = CookieUtil.getCookieValue(request, "uuid");
            //从redis中取出user对象
            BuyUser loginUser = userservice.getUserFromRedis(uuid);
            model.addAttribute("categoryListLevel1",categoryListLevel1);
            model.addAttribute("categoryMapLevel2",categoryMapLevel2);
            model.addAttribute("categoryMapLevel3",categoryMapLevel3);
            model.addAttribute("productMapByFirstCategory",productMapByFirstCategory);
            model.addAttribute("loginUser",loginUser);
            System.out.println("一级标签"+categoryListLevel1.size());
            System.out.println("二级标签"+categoryMapLevel2.size());
            System.out.println("三级标签"+categoryMapLevel3.size());
            return "/Index";
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查找所有标签异常");
        }
        return "/login.html";
    }

}
