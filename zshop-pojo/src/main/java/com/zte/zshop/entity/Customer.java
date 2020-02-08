package com.zte.zshop.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:helloboy
 * Date:2019-06-05 11:16
 * Description:<描述>
 */
public class Customer implements Serializable{

    private Integer id;

    private String name;

    private String login_name;

    private String password;

    private String phone;

    private String address;

    private Integer isValid;

    private Date registDate;

    private Integer member;

    public Customer() {
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Customer(Integer id, String name, String loginName, String password, String phone, String address, Integer isValid, Date registDate,Integer member) {
        this.id = id;
        this.name = name;
        this.login_name = loginName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.isValid = isValid;
        this.registDate = registDate;
        this.member = member;

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

    public String getLoginName() {
        return login_name;
    }

    public void setLoginName(String loginName) {
        this.login_name = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public String getDate(){
        return new SimpleDateFormat("yyyy年MM月dd日").format(registDate);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginName='" + login_name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isValid=" + isValid +
                ", registDate=" + registDate +
                '}';
    }
}
