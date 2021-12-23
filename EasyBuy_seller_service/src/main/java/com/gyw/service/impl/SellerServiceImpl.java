package com.gyw.service.impl;

import com.gyw.bean.BuySeller;
import com.gyw.mapper.BuySellerMapper;
import com.gyw.service.SellerService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 高源蔚
 * @date 2021/12/14-14:49
 * @describe
 */
@Service(interfaceClass = SellerService.class)
@Component
public class SellerServiceImpl implements SellerService {

    @Autowired
    BuySellerMapper buySellerMapper;

    private final Logger logger = LoggerFactory.getLogger(SellerService.class);

    @Override
    public BuySeller login(String name) throws Exception {
        logger.info("调用了sellerservic的login"+name);
        BuySeller buySeller = buySellerMapper.selectByPrimaryKey(name);

        return buySeller;
    }
}
