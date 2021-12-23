package com.gyw.test;

import com.gyw.SellerServiceStart;
import com.gyw.bean.BuySeller;
import com.gyw.service.SellerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 高源蔚
 * @date 2021/12/14-15:03
 * @describe
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SellerServiceStart.class)
public class SellerServiceImplTest {
    @Autowired
    SellerService sellerService;


    @Test
    public void testLogin(){
        try {
            System.out.println("进来了登录测试");
            BuySeller admin = sellerService.login("baidu");
            System.out.println("登录测试找到了"+admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
