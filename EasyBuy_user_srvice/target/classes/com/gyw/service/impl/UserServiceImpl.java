package com.gyw.service.impl;

import com.gyw.bean.BuyUser;
import com.gyw.bean.BuyUserAddress;
import com.gyw.mapper.BuyUserAddressMapper;
import com.gyw.mapper.BuyUserMapper;
import com.gyw.service.Userservice;
import com.gyw.util.DateUtil;
import com.gyw.util.JsonUtil;
import net.minidev.json.JSONUtil;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 高源蔚
 * @date 2021/12/18-9:19
 * @describe
 */
@Component
@Service(interfaceClass = Userservice.class)
public class UserServiceImpl implements Userservice {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    BuyUserMapper userMapper;

    @Autowired
    BuyUserAddressMapper userAddressMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BuyUser login(String loginname) {
        logger.info("用户登录的实现层");
        BuyUser loginUser = userMapper.selectUserByLoginName(loginname);
        return loginUser;
    }

    //把登录的用户信息保存到redis中
    @Override
    public void saveUserToRedis(BuyUser loginUser, String uuid) {
        logger.info("把用户数据存到refdis中");
        String jsonUser = JsonUtil.objectToJson(loginUser);
        redisTemplate.boundValueOps("loginUser_"+uuid).set(jsonUser);
        redisTemplate.boundValueOps("loginUser_"+uuid).expire(60, TimeUnit.MINUTES);
    }

    //从redis里面取出user
    @Override
    public BuyUser getUserFromRedis(String uuid) {
        String loginUserStr =(String) redisTemplate.boundValueOps("loginUser_" + uuid).get();
        BuyUser loginUser = JsonUtil.jsonToPojo(loginUserStr, BuyUser.class);
        return loginUser;
    }

    //判断用户是不是vip
    @Override
    public int isVip(Integer id) {
        String ymDate = DateUtil.getYMDate();
        System.out.println("数据库查询的"+id+";"+ymDate);
        int vipConut = userMapper.isVip(id, ymDate);
        System.out.println("vipCount"+vipConut);
        return vipConut;
    }

    @Override
    public void addAddress(BuyUserAddress address) {
        userAddressMapper.insertSelective(address);
    }


}
