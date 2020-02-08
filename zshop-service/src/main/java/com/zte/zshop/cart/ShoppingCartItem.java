package com.zte.zshop.cart;

import com.zte.zshop.entity.Product;

/**
 * Author:hellboy
 * Date:2019-06-06 16:42
 * Description:<描述>
 * 购物车明细对象
 * 用于封装存放在购物车中的产品条目信息
 *
 *
 */
public class ShoppingCartItem {

    //产品对象
    private Product product;

    //数量
    private int quantity;

    //规格参数
    private String specifparams;

    public ShoppingCartItem() {
    }

    /**
     * 默认一次只能买一件商品
     * @param product
     */
    public ShoppingCartItem(Product product) {
        this.product = product;
        this.quantity=1;
    }

    public ShoppingCartItem(Product product, int quantity , String specifparams) {
        this.product = product;
        this.quantity = quantity;
        this.specifparams = specifparams;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSpecifParams() {
        return specifparams;
    }

    public void setSpecifparams(String specifparams) {
        this.specifparams = specifparams;
    }

    //获取该商品在购物车中的总价
    public double getItemMoney(){
        return product.getPrice()*this.quantity;

    }

    //获得购物车唯一标志
    public String getItemPos(){

        return product.getId() + specifparams;
    }
    /*//商品数量+1
    public void increment(){
        this.quantity++;

    }
*/
    //商品数量+1
    public boolean increment(){
        int sum = this.quantity + 1;
        if (sum > 5){
            return false;
        }else {
            this.quantity=sum;
            return true;
        }

    }

    //添加选择的商品数量
    public boolean incrementProducts(int num){
        int sum = this.quantity + num;
        if (sum >5){
            return false;
        }else {
            this.quantity=sum;
            return true;
        }
    }



}
