package com.zte.zshop.entity;

/**
 * Author:helloboy
 * Date:2019-07-02 10:00
 * Description:<描述>
 */
public class Supplier {
    private Integer id;
    private String name;
    private String address;
    private String phone;

    public Supplier(){

    }
    public Supplier(Integer id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
