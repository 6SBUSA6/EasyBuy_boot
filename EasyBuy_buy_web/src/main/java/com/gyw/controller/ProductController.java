package com.gyw.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gyw.bean.*;
import com.gyw.service.*;
import com.gyw.util.CookieUtil;
import com.gyw.util.JsonUtil;
import com.gyw.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author 高源蔚
 * @date 2021/12/18-14:06
 * @describe
 */
@RequestMapping("/product")
@Controller
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Reference(url = "dubbo://localhost:20883")
    Userservice userservice;

    @Reference(url = "dubbo://localhost:20882")
    ProductService productService;

    @Reference(url = "dubbo://localhost:20882")
    ProductCategoryService productCategoryService;

    @Reference(url = "dubbo://localhost:20882")
    ProductCommentService productCommentService;

    @Reference(url = "dubbo://localhost:20883")
    UserAddressService userAddressService;

    @RequestMapping("/productDetail")
    public String findProductByPid(Integer pid, Model model, Integer pageNum)
    {
        logger.info("查询商品的详细:"+pid);
        BuyProduct product = null;
        ArrayList<BuyProductImages> productImageList = new ArrayList<>();
        try {
            product = productService.findProductByPid(pid); //通过商品id查找商品信息
            //System.out.println("查询的商品"+product);
            productImageList = productService.findProductImgByPid(pid);//通过商品id查找商品的照片
            HashMap<String, Object> productCommentNum = new HashMap<>();
            productCommentNum = productService.findProductCommentNumBiPid(pid);//找到商品的评论数
            Number qpl = 0;
            Number hps = 0;
            Number zps = 0;
            Number cps = 0;
            qpl =(Number) productCommentNum.get("qpl") ;
            hps =(Number) productCommentNum.get("hps") ;
            zps =(Number) productCommentNum.get("zps") ;
            cps =(Number) productCommentNum.get("cps") ;
            hps = (int) (( hps.intValue()*1.0/qpl.intValue())*100);
            zps = (int) ((zps.intValue()*1.0/qpl.intValue())*100);
            cps = (int) ((cps.intValue()*1.0/qpl.intValue())*100);
            //商品评论数处理完毕
            PageInfo<BuyProductComment> pageInfo = productCommentService.findAllCommentByPid(pid, pageNum);

            model.addAttribute("productImageList",productImageList);
            model.addAttribute("product",product);
            model.addAttribute("qpl",qpl);
            model.addAttribute("hps",hps);
            model.addAttribute("zps",zps);
            model.addAttribute("cps",cps);
            model.addAttribute("pid",pid);
            model.addAttribute("pageInfo",pageInfo);

            return "/Product.html";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询商品详细失败");
        }

        return "redirect:/e.html";
    }

    @RequestMapping("/addCar")
    public String addCar(Integer pid, @CookieValue(required = false) String uuid, HttpServletResponse response, HttpServletRequest request, Integer buyNum){
        logger.info("添加到购物车中");
        if(buyNum == null)
        {
            buyNum = 1;
        }
        BuyUser loginUser = userservice.getUserFromRedis(uuid);
        BuyProduct product = productService.findProductByPid(pid);
        CarItem carItem = new CarItem(product,buyNum);
        //ArrayList<CarItem> carList = new ArrayList<>();

        if(loginUser == null)
        {
            //没有登录,吧购物车放到cookie中
            System.out.println("没有登录:"+carItem);
            String carListStr = CookieUtil.getCookieValue(request, "carList",true);
            List<CarItem> carList = null;
            if(carListStr == null || "".equals(carListStr)){
                //没有添加购物车
                System.out.println("没有添加购物车");
                carList = new ArrayList<>();
                carList.add(carItem);
            }else {
                //添加过购物车
                System.out.println("添加过购物车");
                boolean falg = false;
                carList = JsonUtil.jsonToList(carListStr, CarItem.class);
                for (CarItem item : carList) {
                    if(pid.equals(item.getProduct().getId()) ){
                        //购物车中有此商品
                        System.out.println("购物车中有此商品");
                        item.setBuyNum(item.getBuyNum()+buyNum);
                        falg = true;
                        break;
                    }
                }
                if(!falg){
                    //购物车里面没有此商品
                    System.out.println("购物车里面没有此商品");
                    carList.add(carItem);
                }
            }
            carListStr = JsonUtil.objectToJson(carList);
            CookieUtil.setCookie(request,response,"carList",carListStr,true);
            System.out.println("把商品添加到购物车中了");

            /*//测试
            String carList1 = CookieUtil.getCookieValue(request, "carList", true);
            List<CarItem> carItems = JsonUtil.jsonToList(carList1, CarItem.class);
            System.out.println("从cookie中取出来的是"+carItems);*/


            return "redirect:/product/showCar";
        }
        //登录了,把购物车放到redis中
        System.out.println("登录了");
        try {
            String carListStr = CookieUtil.getCookieValue(request, "carList", true);
            if(carListStr == null || "".equals(carListStr)){
                //没有在未登录状态下添加购物车
                productService.addCarToRedis(product,carItem,loginUser);
                System.out.println("没有在未登录状态下添加购物车添加到了redis");
                return "redirect:/product/showCar";
            }else {
                //在kookie中添加过购物车
                List<CarItem> carItemList = JsonUtil.jsonToList(carListStr, CarItem.class);
                //先把kookie中的购物车放到redis中
                for (CarItem item : carItemList) {
                    System.out.println("从cookie中取出来的item"+item);
                    productService.addCarToRedis(product,item,loginUser);
                }
                CookieUtil.deleteCookie(request,response,"carList");//把cookie删除
                productService.addCarToRedis(product,carItem,loginUser);
                System.out.println("cookie中有购物车添加购物车添加到了redis");
                return "redirect:/product/showCar";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加到redis异常");
        }
        return "redirect:/ere.html";
    }

    //展示购物车
    @RequestMapping("/showCar")
    public String showCar(HttpServletRequest request, HttpServletResponse response, Model model){

        try {
            String uuid = CookieUtil.getCookieValue(request, "uuid");
            String cookieCarListStr = CookieUtil.getCookieValue(request, "carList", true);
            if (uuid == null || "".equals(uuid)){
                //用户没有登录
                List<CarItem> cookieCarList = JsonUtil.jsonToList(cookieCarListStr, CarItem.class);
                model.addAttribute("carList",cookieCarList);
                return "/BuyCar.html";
            }else {
                BuyUser loginUser = userservice.getUserFromRedis(uuid);
                //用户登录过了
                List<CarItem> redisCarList = productService.findCarListFromRedis(loginUser);
                model.addAttribute("carList",redisCarList);
                return "/BuyCar.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "err";
    }

    @RequestMapping("/toBuy2")
    public String toBuy2(HttpServletRequest request, HttpServletResponse response, @CookieValue(required = false) String uuid, Model model){
        logger.info("这是从购物车去到订单页面");
        try {
            BuyUser loginUser = userservice.getUserFromRedis(uuid);
            if(loginUser == null ){
                //没有登陆
                System.out.println("没有登录");
                return "/Login.html";
            }
            //登录了
            System.out.println("得到的user"+loginUser);
            List<CarItem> carList = productService.findCarListFromRedis(loginUser);//找到购物车中的商品
            List<BuyUserAddress> addressList = userAddressService.findAddressByUid(loginUser.getId());
            int vipCount = userservice.isVip(loginUser.getId());
            double countPrice = 0;
            double discountPrice = 0;
            for (CarItem carItem : carList) {
                countPrice+=carItem.getBuyNum()*carItem.getProduct().getPrice();
            }
            if(vipCount>0){
                //是vip
                discountPrice = countPrice*0.8;
            }

            model.addAttribute("carList",carList);
            model.addAttribute("loginUser",loginUser);
            model.addAttribute("addressList",addressList);
            model.addAttribute("countPrice",String.format("%.2f",countPrice));
            model.addAttribute("discountPrice",String.format("%.2f",discountPrice));
            model.addAttribute("vipCount",vipCount);
            return "/BuyCar_Two.html";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("这是从购物车去到订单页面异常");
        }
        return "/error.html";
    }

    @RequestMapping("/deleteFromCar")
    public String deleteFromCar(Integer pid, @CookieValue(required = false) String uuid){
        logger.info("从购物车中删除");
        try {
            BuyUser loginUser = userservice.getUserFromRedis(uuid);
            productService.deleteFromCar(pid,loginUser.getId());
            return "redirect:/product/showCar";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("从购物车里删除异常",e);
        }
        return "/error.html";
    }



}
