package com.gyw.controller;

import com.gyw.bean.BuyUser;
import com.gyw.service.Userservice;
import com.gyw.util.CookieUtil;
import com.gyw.util.UUIDUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 高源蔚
 * @date 2021/12/18-9:25
 * @describe
 */
@RequestMapping("/user")
@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Reference(url = "dubbo://localhost:20883")
    Userservice userservice;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/login")
    public String login(BuyUser user, HttpServletRequest request, HttpServletResponse response){
        BuyUser loginUser = new BuyUser();
        try {
            logger.info("进入了用户登录:"+user);
            loginUser = userservice.login(user.getLoginname());
            //System.out.println("user"+user);
            //System.out.println("loginUser"+loginUser);
            if (loginUser == null){
                return "/Login.html";
            }
            if(user.getPassword().equals(loginUser.getPassword())){
                logger.info(user.toString()+"用户登录成功!");
                String uuid = UUIDUtil.getUUID();
                userservice.saveUserToRedis(loginUser,uuid);//把登录用户信息保存到redis中
                //把用户信息放到cookie中
                CookieUtil.setCookie(request,response,"uuid",uuid);
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户登录异常!");
    }
        return "/Login.html";
    }
}
