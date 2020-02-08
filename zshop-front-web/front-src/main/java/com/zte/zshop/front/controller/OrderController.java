package com.zte.zshop.front.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.entity.Item;
import com.zte.zshop.service.ItemService;
import com.zte.zshop.service.OrderService;
import com.zte.zshop.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author:helloboy
 * Date:2019-06-20 10:15
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/orderDetail")
    public String orderDetail(Integer orderId,String orderNo,Model model){

        //PageInfo<Item> pageInfo = itemService.findItemsByOrderId(orderId);
        //model.addAttribute("itemList",pageInfo);

        //List<Item> items = itemService.findItemsByOrderId(orderId);
        //System.out.println(orderId);

        PageHelper.startPage(1, 3);
        List<Item> items = itemService.findItems(orderId);
        PageInfo<Item> pageInfo = new PageInfo<>(items);
        model.addAttribute("items",pageInfo);
        model.addAttribute("orderNo",orderNo);


        return "orderDetail";
    }

    @RequestMapping("/findOrderItem")
    @ResponseBody
    public ResponseResult findOrderItem(Integer orderId,Model model){

        //System.out.println(orderId);
      //  PageInfo<Item> pageInfo = itemService.findItemsByOrderId(orderId);
        //model.addAttribute("itemList",pageInfo);

        return ResponseResult.success("");
    }

    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult RemoveItemById(Integer id){

        try {
            itemService.delOrderById(id);
            orderService.delOrderById(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseResult.success("删除成功");
    }

}
