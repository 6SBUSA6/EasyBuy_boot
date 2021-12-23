package com.gyw.service.impl;

import com.gyw.bean.BuyUserAddress;
import com.gyw.mapper.BuyUserAddressMapper;
import com.gyw.service.UserAddressService;
import com.gyw.service.Userservice;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/20-15:49
 * @describe
 */
@Component
@Service(interfaceClass = UserAddressService.class)
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    BuyUserAddressMapper userAddressMapper;


    //更具用户id查找出用户所有的收获地址
    @Override
    public List<BuyUserAddress> findAddressByUid(Integer id) {
       List<BuyUserAddress> list = userAddressMapper.findAddressByUid(id);
        return list;
    }
}
