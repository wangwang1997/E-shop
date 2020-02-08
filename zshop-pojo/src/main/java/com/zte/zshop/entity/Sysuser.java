package com.zte.zshop.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:helloboy
 * Date:2019-05-24 12:10
 * Description:<描述>
 */
public class Sysuser implements Serializable{

    private Integer id;

    private String name;

    private String loginName;

    private String password;

    private String phone;

    private String email;

    private Integer isValid;

    private Date createDate;

    private Role role;

    public Sysuser() {
    }

    public Sysuser(Integer id, String name, String loginName, String phone, String password, String email, Integer isValid, Date createDate, Role role) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.isValid = isValid;
        this.createDate = createDate;
        this.role = role;
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
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDate(){
        return new SimpleDateFormat("yyyy年MM月dd日").format(createDate);
    }

    @Override
    public String toString() {
        return "Sysuser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isValid=" + isValid +
                ", createDate=" + createDate +
                ", role=" + role +
                '}';
    }
}
