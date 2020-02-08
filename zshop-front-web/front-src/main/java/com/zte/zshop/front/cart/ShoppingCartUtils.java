package com.zte.zshop.front.cart;

import com.zte.zshop.cart.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:hellboy
 * Date:2019-06-12 11:04
 * Description:<描述>
 * 逻辑：
 * 从session作用域中获取购物车对象，当session中没有该对象，创建一个新的对象，放入到session作用域中，如果有，直接返回(单例)
 */
public class ShoppingCartUtils {

    public static ShoppingCart getShoppingCart(HttpSession session) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingCart");
        if(sc==null){
            sc = new ShoppingCart();
            session.setAttribute("shoppingCart",sc);
        }
        return  sc;

    }


    //根据时间+随机数，生成订单流水号
    public static String getOrderIdByItem(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


}















