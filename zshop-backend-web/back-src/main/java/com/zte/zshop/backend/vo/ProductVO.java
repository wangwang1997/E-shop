package com.zte.zshop.backend.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Author:helloboy
 * Date:2019-05-18 12:03
 * Description:<描述>
 *     用于封装表单中的值
 */
public class ProductVO {

    private Integer id;
    private String name;
    private Double price;
    private Integer productTypeId;
    private String info;
    private String specifParam;
    private Integer supplierId;
    private Integer stock;
    private Double costPrice;
    private CommonsMultipartFile file;

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
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getSpecifParam() {
        return specifParam;
    }

    public void setSpecifParam(String specifParam) {
        this.specifParam = specifParam;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public Integer getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
