package com.zte.zshop.ftp;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author:hellboy
 * Date:2019-06-05 17:01
 * Description:<描述>
 */
public class Test {



    public static void main(String[] args) {

        testFtp();

    }

    private static void testFtp() {

        //创建客户端对象
        FTPClient ftp = new FTPClient();
        InputStream local=null;

        try {
            //连接ftp服务器
            ftp.connect("localhost",21);
            //登录
            ftp.login("zjw","123");


            //设置上传路径
            String path = "/2/3";

            //检查上传路径是否已经存在，如果不存在，创建该路径
            boolean flag = ftp.changeWorkingDirectory(path);
            if(!flag){

                ftp.makeDirectory(path);

            }




            //指定上传路径
            ftp.changeWorkingDirectory(path);



            //指定上传文件的类型：二进制文件
            ftp.setFileType(FTP.BINARY_FILE_TYPE);


            //读取本地文件
            File file = new File("e:\\kaoyan.png");
            local = new FileInputStream(file);


            ftp.setControlEncoding("utf-8");
            String name = file.getName();
            ftp.storeFile(name,local);



        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
