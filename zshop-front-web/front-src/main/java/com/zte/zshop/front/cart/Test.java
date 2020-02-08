package com.zte.zshop.front.cart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2019-06-14 16:18
 * Description:<描述>
 */
public class Test {

    public static void main(String[] args) {

        //根据时间，三位随机数生成订单流水号
        String result = getOrderIdByItem();
        System.out.println(result);
    }

    public static String getOrderIdByItem() {
        //生成六位随机数+日期

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result="";
        Random random = new Random();
        for (int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
}
