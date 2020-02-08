package com.zte.zshop.entity;

import java.util.Date;

/**
 * Author:helloboy
 * Date:2019-07-03 20:10
 * Description:<描述>
 */
public class Stock {
    private Integer id;

    private Product product;

    private Integer purchase;

    private Date date;


    public Stock(){}
    public Stock(Integer id, Product product, Date date, Integer purchase) {
        this.id = id;
        this.product = product;
        this.date = date;
        this.purchase = purchase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", product=" + product +
                ", purchase=" + purchase +
                ", date=" + date +
                '}';
    }
}

