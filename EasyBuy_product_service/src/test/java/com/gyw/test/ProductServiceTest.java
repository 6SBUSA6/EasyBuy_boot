package com.gyw.test;

import com.gyw.ProductServiceStart;
import com.gyw.bean.BuyProductCategory;
import com.gyw.mapper.BuyProductCategoryMapper;
import com.gyw.service.ProductCategoryService;
import com.gyw.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 高源蔚
 * @date 2021/12/15-10:12
 * @describe
 */
@SpringBootTest(classes = ProductServiceStart.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    ProductCategoryService productCategoryService;

    @Test
    public void findChildCategoryByPid(){
        List<BuyProductCategory> list = null;
        try {
            list = productCategoryService.findCategoryByPid(660);
            System.out.println("---------------------这是660下面的子类--------------------");
            for (BuyProductCategory buyProductCategory : list) {
                System.out.println(buyProductCategory+"的子类有：");
                List<BuyProductCategory> threeCategory = productCategoryService.findCategoryByPid(buyProductCategory.getId());
                for (BuyProductCategory productCategory : threeCategory) {
                    System.out.println(productCategory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
