package com.gyw.service;

import com.gyw.bean.BuySeller;

/**
 * @author 高源蔚
 * @date 2021/12/14-14:45
 * @describe
 */
public interface SellerService {
    public BuySeller login(String name) throws Exception;
}
