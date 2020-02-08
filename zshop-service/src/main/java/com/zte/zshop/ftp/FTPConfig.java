package com.zte.zshop.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author:hellboy
 * Date:2019-06-05 16:54
 * Description:<描述>
 * 将ftp.properties文件中的数据初始化到FTPConfig对象
 */

@Component
public class FTPConfig {

    @Value("${ftp_address}")
    private String ftp_address;

    @Value("${ftp_port}")
    private String ftp_port;

    @Value("${ftp_username}")
    private String ftp_username;

    @Value("${ftp_password}")
    private String ftp_password;

    @Value("${ftp_basepath}")
    private String ftp_basepath;

    @Value("${image_base_url}")
    private String image_base_url;

    public String getFtp_address() {
        return ftp_address;
    }

    public void setFtp_address(String ftp_address) {
        this.ftp_address = ftp_address;
    }

    public String getFtp_port() {
        return ftp_port;
    }

    public void setFtp_port(String ftp_port) {
        this.ftp_port = ftp_port;
    }

    public String getFtp_username() {
        return ftp_username;
    }

    public void setFtp_username(String ftp_username) {
        this.ftp_username = ftp_username;
    }

    public String getFtp_password() {
        return ftp_password;
    }

    public void setFtp_password(String ftp_password) {
        this.ftp_password = ftp_password;
    }

    public String getFtp_basepath() {
        return ftp_basepath;
    }

    public void setFtp_basepath(String ftp_basepath) {
        this.ftp_basepath = ftp_basepath;
    }

    public String getImage_base_url() {
        return image_base_url;
    }

    public void setImage_base_url(String image_base_url) {
        this.image_base_url = image_base_url;
    }
}
