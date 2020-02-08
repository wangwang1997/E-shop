package com.zte.zshop.dto;

import java.io.InputStream;

/**
 * Author:helloboy
 * Date:2019-05-18 12:07
 * Description:<描述>
 */
public class ProductDto {

    private Integer id;
    private String name;
    private Double price;
    private Integer productTypeId;
    private Integer supplierId;
    private String specifParam;
    private Integer stock;
    private Double costPrice;
    private String info;
    private InputStream inputStream;

    private String fileName;

    private String uploadPath;

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

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public Double getCostPrice(){
        return costPrice;
    }
    public void setCostPrice(Double costPrice){
        this.costPrice = costPrice;
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

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
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
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
