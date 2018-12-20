package com.struggle.service.model.bean;

/**
 * 产品信息类
 *
 * @author nancy.wang
 * @Time 2018/12/9
 */
public class ProductInfo {
    private String prodName;
    private String prodDesc;
    private String prodMerchant;
    private int prodStar;
    private int prodType;
    private double price;
    private int maxAge;
    private int minAge;
    private double discount;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdMerchant() {
        return prodMerchant;
    }

    public void setProdMerchant(String prodMerchant) {
        this.prodMerchant = prodMerchant;
    }

    public int getProdStar() {
        return prodStar;
    }

    public void setProdStar(int prodStar) {
        this.prodStar = prodStar;
    }

    public int getProdType() {
        return prodType;
    }

    public void setProdType(int prodType) {
        this.prodType = prodType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
