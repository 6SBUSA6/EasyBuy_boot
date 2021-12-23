package com.gyw.service.impl;

import com.gyw.bean.BuyOrder;
import com.gyw.bean.BuyOrderDetail;
import com.gyw.mapper.BuyOrderDetailMapper;
import com.gyw.mapper.BuyOrderMapper;
import com.gyw.mapper.BuyUserMapper;
import com.gyw.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/20-21:36
 * @describe
 */
@Service(interfaceClass = OrderService.class)
@Component
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    BuyUserMapper userMapper;

    @Autowired
    BuyOrderMapper orderMapper;

    @Autowired
    BuyOrderDetailMapper orderDetailMapper;

    //添加订单信息
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addOrder(BuyOrder order, List<BuyOrderDetail> orderDetailList) throws Exception {
        orderMapper.insertSelective(order);
        for (BuyOrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderid(order.getId());
            orderDetailMapper.insertSelective(orderDetail);
        }
        return order.getId();
    }

    //更新订单的支付状态
    @Override
    public void updateOrder(BuyOrder order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
