package com.gyw.service;

import com.gyw.bean.BuyUser;
import com.gyw.bean.BuyUserAddress;

/**
 * @author 高源蔚
 * @date 2021/12/18-9:19
 * @describe
 */
public interface Userservice {
    BuyUser login(String loginname);

    void saveUserToRedis(BuyUser loginUser, String uuid);

    BuyUser getUserFromRedis(String uuid);

    int isVip(Integer id);

    //
    void addAddress(BuyUserAddress address);
}
