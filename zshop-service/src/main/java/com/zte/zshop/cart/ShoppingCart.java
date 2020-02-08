package com.zte.zshop.cart;

import com.zte.zshop.entity.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:hellboy
 * Date:2019-06-06 16:39
 * Description:<描述>
 * 购物车对象，主要封装了购物车明细，用于完成购物功能
 */
public class ShoppingCart {

    private Map<String,ShoppingCartItem> products = new HashMap<>();

    public Map<String, ShoppingCartItem> getProducts() {
        return products;
    }

    /**
     * 向购物车添加一个商品
     * 逻辑：
     * 查看当前购物车中是否有该商品，如果有，不增加记录，只是数量+1,如果没有，在购物车中新增一条记录,数量初始化为1
     *
     *
     */
    /*public void addProduct(Product product){

        ShoppingCartItem sci = products.get(product.getId());
        if(sci==null){
            sci = new ShoppingCartItem(product);
            products.put(product.getId(),sci);

        }
        else{
            sci.increment();

        }
    }*/

    //public boolean addProduct(Product product){
    //
    //    ShoppingCartItem sci = products.get(product.getId());
    //    if(sci == null){
    //        sci = new ShoppingCartItem(product);
    //        products.put(product.getId(),sci);
    //
    //        return true;
    //    }
    //    else{
    //        return sci.increment();
    //    }
    //}

    public boolean addSomeProducts(Product product,int quantity,String specifParams){

        ShoppingCartItem sci = products.get(product.getId().toString()+specifParams);
        if (sci == null||!sci.getSpecifParams().equals(specifParams)){
            sci = new ShoppingCartItem(product,quantity,specifParams);
            products.put(product.getId().toString() + specifParams ,sci);

            return true;
        }else {

            return sci.incrementProducts(quantity);

        }

    }



    //查看购物车中是否有该商品
    public boolean hasProduct(int id){

        return products.containsKey(id);


    }

    /*

        获取购物车中的商品总数
        逻辑：
        遍历购物车集合，获取所有购物车明细，求数量之和
      */

    public int getProductNumber(){

        int total=0;
        for(ShoppingCartItem sci:products.values()){

            total+=sci.getQuantity();


        }
        return  total;
    }

    /**
     * 获取购物车中所有商品明细集合
     *
     */
    public Collection<ShoppingCartItem> getItems(){
        return products.values();

    }

    /**
     * 获取购物车中所有商品的总价
     */
    public Double getTotalMoney(){

        Double total=0.0;
        for(ShoppingCartItem sci:getItems()){

            total+=sci.getItemMoney();
        }
        return  total;



    }

    /**
     * 判断购物车是否为空
     */
    public boolean isEmpty(){

        return products.isEmpty();
    }

    /**
     * 清空购物车
     */
    public void clear(){
        products.clear();

    }

    /*
    移除指定id的购物明细
     */
    public void removeItem(Integer id,String specif){

        products.remove(id.toString() + specif);
    }

    /**
     * 修改指定购物车明细的数量
     */
    public void updateItemQuantity(int id,String specifParams,int quantity){

        Integer Id = id;
        ShoppingCartItem sci = products.get(Id.toString()+specifParams);
        if(sci!=null){
            sci.setQuantity(quantity);
        }
    }


    public void removeCartItem(String specif) {
        products.remove(specif);
    }
}
