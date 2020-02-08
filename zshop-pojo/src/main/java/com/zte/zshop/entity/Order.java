package com.zte.zshop.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:helloboy
 * Date:2019-06-16 11:05
 * Description:<描述>
 */
public class Order implements Serializable {

    private Integer id;

    private String no;

    private Double price;

    private Date createDate;

    private Customer customer;

    public Order() {
    }

    public Order(Integer id, Customer customer, String no, Double price, Date createDate) {
        this.id = id;
        this.customer = customer;
        this.no = no;
        this.price = price;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
