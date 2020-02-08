package com.zte.zshop.entity;

import java.io.Serializable;

/**
 * Author:helloboy
 * Date:2019-05-18 15:40
 * Description:<描述>
 */
public class Product implements Serializable{

    private Integer id;

    private String name;

    private Double price;

    private Double costPrice;

    private String info;

    private String image;

    private Integer status;

    private ProductType productType;

    private String specifParam;

    private Supplier supplier;

    private Integer stock;

    public Product() {
    }

    public Product(Integer id, String name, Double price, String info, String image, Integer status, ProductType productType ,String specifParam,Supplier supplier,Integer stock,Double costPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.image = image;
        this.status = status;
        this.productType = productType;
        this.specifParam = specifParam;
        this.supplier = supplier;
        this.stock = stock;
        this.costPrice = costPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSpecifParam() {
        return specifParam;
    }

    public void setSpecifParam(String specifParam) {
        this.specifParam = specifParam;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCostPrice(){
        return costPrice;
    }
    public void setCostPrice(Double costPrice){
        this.costPrice = costPrice;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
