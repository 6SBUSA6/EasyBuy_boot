package com.gyw.service;

import com.gyw.bean.BuyOrder;
import com.gyw.bean.BuyOrderDetail;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/20-21:36
 * @describe
 */
public interface OrderService {
    Integer addOrder(BuyOrder order, List<BuyOrderDetail> orderDetailList) throws  Exception;

    void updateOrder(BuyOrder order);
}
