package com.zte.zshop.entity;

import java.io.Serializable;

/**
 * Author:helloboy
 * Date:2019-06-20 15:00
 * Description:<描述>
 */
public class Item implements Serializable{

    private Integer id;

    private Integer num;

    private String productParam;

    private Double price;

    private Order order;

    private Product product;

    public Item(){}

    public Item(Integer id, Integer num, String productParam, Double price, Order order, Product product) {
        this.id = id;
        this.num = num;
        this.productParam = productParam;
        this.price = price;
        this.order = order;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getProductParam() {
        return productParam;
    }

    public void setProductParam(String productParam) {
        this.productParam = productParam;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
