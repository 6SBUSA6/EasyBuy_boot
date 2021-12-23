package com.gyw.service;

import com.gyw.bean.BuyUserAddress;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/20-15:50
 * @describe
 */
public interface UserAddressService {
    List<BuyUserAddress> findAddressByUid(Integer id);
}
