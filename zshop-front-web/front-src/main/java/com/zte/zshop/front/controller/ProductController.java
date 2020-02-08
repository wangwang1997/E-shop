package com.zte.zshop.front.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.*;
import com.zte.zshop.front.cart.ShoppingCartUtils;
import com.zte.zshop.params.ProductParam;
import com.zte.zshop.service.ItemService;
import com.zte.zshop.service.OrderService;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import com.zte.zshop.utils.ResponseResult;
import com.zte.zshop.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Author:helloboy
 * Date:2019-05-30 14:49
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){
        List<ProductType> productTypes = productTypeService.findEnable(Constants.PRODUCT_TYPE_ENABLE);
        return productTypes;
    }
    @RequestMapping("/main")
    public String main(Integer pageNum,ProductParam productParam,Model model){

        String name = productParam.getName();
        Integer productTypeId = productParam.getProductTypeId();
        Double minPrice = productParam.getMinPrice();
        Double maxPrice = productParam.getMaxPrice();

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum= Constants.PAGE_NO;
        }
        //设置前台每页显示8条记录
        PageHelper.startPage(pageNum,Constants.PAGE_SIZE_FRONT);
        List<Product> products = productService.findByParam(productParam);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        model.addAttribute("data",pageInfo);
        model.addAttribute("productParam",productParam);

        //System.out.println("name:"+name+"productTypeId:"+productTypeId+"minPrice:"+minPrice+"maxPrice:"+maxPrice);

        return "main";
    }

    //显示图片
    //逻辑：将图片输出到页面级输出流
    @RequestMapping("/showPic")
    public void showPic(String image,OutputStream out)throws IOException {

        URL url = new URL(image);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        //创建一个缓冲块，一个读取4k
        byte[] data = new byte[8192];
        int size = 0;
        size=is.read(data);
        while (size!=-1){
            bos.write(data,0,size);
            size=is.read(data);
        }
        is.close();
        bos.flush();
        bos.close();
    }

    @RequestMapping("/toOrders")
    public String toOrders(Integer pageNum,Model model,HttpSession session){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum=Constants.PAGE_NO;
        }

        Integer customerId = ((Customer)session.getAttribute("customer")).getId();
        PageHelper.startPage(pageNum,Constants.PAGE_SIZE_ORDER);
        PageInfo<Order> pageInfo = orderService.findOrderByCustomerId(customerId);

        if(!pageInfo.getList().isEmpty()){
            model.addAttribute("data",pageInfo);
        }


        List<Item> items = itemService.findAllItems();
        System.out.println(items.size());
        model.addAttribute("items",items);

        return "myOrders";
    }

    //显示购物车页面
    @RequestMapping("/toCart")
    public String toCart(){

        return "cart";

    }

    //显示购物车页面
    //添加购物车功能




    //删除购物车,根据选中项
    @RequestMapping("/removeCartByIds")
    @ResponseBody
    public ResponseResult removeCartByIds(Integer ids,String specifParams,HttpSession session){

        ShoppingCart shoppingCart = ShoppingCartUtils.getShoppingCart(session);




            productService.removeItemById(shoppingCart, ids.toString() + specifParams);




        if (shoppingCart.isEmpty()){
            return ResponseResult.fail("购物车为空");
        }

        //重新计算总价
        Double totalMoney = shoppingCart.getTotalMoney();

        /*for (int id:ids){
            System.out.println(id);
        }*/

        return ResponseResult.success("删除成功",totalMoney);

    }

    //修改购物车
    @RequestMapping("/modifyCart")
    @ResponseBody
    public Map<String,Object> modifyCart(Integer id,String specifParams,Integer quantity,HttpSession session){

        ShoppingCart shoppingCart = ShoppingCartUtils.getShoppingCart(session);

        productService.modifyCart(shoppingCart, id, specifParams,quantity);

        //System.out.println("id:"+id+"q52  uantity:"+quantity);

        //返回json数据
        //设置两个值，单个商品的总价和总价
        Map<String,Object> result = new HashMap<>();
        result.put("itemMoney",shoppingCart.getProducts().get(id.toString()+specifParams).getItemMoney());
        result.put("totalMoney",shoppingCart.getTotalMoney());
        return result;

    }

    @RequestMapping("/clearShoppingCart")
    @ResponseBody
    public ResponseResult clearShoppingCart(HttpSession session){

        ShoppingCart shoppingCart = ShoppingCartUtils.getShoppingCart(session);
        productService.removeShoppingCart(shoppingCart);
        return ResponseResult.success("清空购物车成功");

    }

    @RequestMapping("/showOrder")
    public String showOrder(){

        return "order";
    }

    //生成购物车结算订单
    @RequestMapping("/generateOrder")
    @ResponseBody
    public ResponseResult generateOrder(OrderVo orderVo,String Ids,String priceList,String specif,String quantity, HttpSession session){

        try {
            //删除购物车里的对应商品
            ShoppingCart shoppingCart = ShoppingCartUtils.getShoppingCart(session);

            for(String Specif:specif.split(",")) {
                productService.removeItemById(shoppingCart, Specif);
            }

            //生成订单
            String orderNo = ShoppingCartUtils.getOrderIdByItem();
            orderVo.setNo(orderNo);
            orderVo.setCreateDate(new Date());

            //重新计算总价
            Double totalMoney = shoppingCart.getTotalMoney();
            //数据插入数据库
            orderService.addOder(orderVo);

            //更新product库存数量
            int i=0;
            Item item = new Item();
            for(String Id:Ids.split(",")) {

                int pro_id = Integer.parseInt(Id);
                int pro_quantity = Integer.parseInt(quantity.split(",")[i]);
                productService.updateStock(pro_id,pro_quantity );
                Product product = productService.findById(pro_id);
                Order order = orderService.findOrderByNo(orderNo);
                item.setOrder(order);
                item.setProduct(product);
                item.setNum(pro_quantity);
                item.setPrice(Double.parseDouble(priceList.split(",")[i]));
                item.setProductParam(specif.split(",")[i].split(Id)[1]);
                itemService.insert(item);


                i++;
            }


            return ResponseResult.success(orderNo);
        } catch (Exception e) {
            //e.printStackTrace();

            return ResponseResult.fail("生成订单失败");
        }
    }

    @RequestMapping("/toDetail")
    public String toDetail(){
        return "new";
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){

        Product product = productService.findByFrontId(id);
        return ResponseResult.success(product);

    }

    //
    @RequestMapping("/addProductsToCart")
    @ResponseBody
    public ResponseResult addProductsToCart(Integer id,Integer quantity,String specifParams,HttpSession session){

        //获取购物车对象
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        /*ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        System.out.println(shoppingCart.getProductNumber());*/


        //调用service方法
        //boolean flag = productService.addToCart(id,sc);
        boolean flag = productService.addProductsToCart(id,quantity,specifParams,sc);

        //System.out.println(sc.getProductNumber());

        if (flag){
            return ResponseResult.success("添加成功");
        }
        return ResponseResult.fail("商品购买数量不能超过5件");


    }


}











