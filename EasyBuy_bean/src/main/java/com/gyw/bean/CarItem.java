package com.gyw.bean;

import java.io.Serializable;

/**
 * @author 高源蔚
 * @date 2021/12/19-20:37
 * @describe
 */
public class CarItem implements Serializable {
    private BuyProduct product;
    private Integer buyNum;

    @Override
    public String toString() {
        return "CarItem{" +
                "product=" + product +
                ", buyNum=" + buyNum +
                '}';
    }

    public CarItem(BuyProduct product, Integer buyNum) {
        this.product = product;
        this.buyNum = buyNum;
    }

    public CarItem() {
    }

    public BuyProduct getProduct() {
        return product;
    }

    public void setProduct(BuyProduct product) {
        this.product = product;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }
}
