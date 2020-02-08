package com.zte.zshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2019-05-18 15:27
 * Description:<描述>
 */
public class StringUtils {

    /*
    * 重新生成一个随机的文件名
    * 文件格式：年月日+1-100随机数.后缀
    * */
    public static String renameFileName(String fileName){

        int dotIndex = fileName.lastIndexOf(".");
        //后缀
        String suffix = fileName.substring(dotIndex);

        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ new Random().nextInt(100)+suffix;
    }

    //使用hash算法随机生成一个二级目录,例如/2/3
    public static String generateRandomDir(String fileName){
        int hashCode = fileName.hashCode();
        int dir1=hashCode & 0xf;//得到名称为1-16的一级目录
        int dir2=(hashCode & 0xF0)>>4; //得到名称为1-16的二级目录

        return "/"+dir1+"/"+dir2;

    }

    public static void main(String[] args) {
        /*String fileName = renameFileName("dsss.jpg");
        System.out.println(fileName);*/
        String filePath = generateRandomDir("mike");
        System.out.println(filePath);

    }

}
