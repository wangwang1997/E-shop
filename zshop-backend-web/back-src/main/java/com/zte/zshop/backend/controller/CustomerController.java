package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.params.CustomerParam;
import com.zte.zshop.service.CustomerService;
import com.zte.zshop.utils.ResponseResult;
import com.zte.zshop.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author:helloboy
 * Date:2019-05-24 11:57
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum= Constants.PAGE_NO;
        }
        PageInfo<Customer> pageInfo = customerService.findAll(pageNum,2);
        model.addAttribute("data",pageInfo);

        return "customerManager";
    }

    @RequestMapping("/showModify")
    @ResponseBody
    public ResponseResult showModify(Integer id){

        Customer customer = customerService.findById(id);
        return ResponseResult.success(customer);
    }

    @RequestMapping("/modifyCustomer")
    @ResponseBody
    public ResponseResult modifyCustomer(CustomerVo customerVo){

        //System.out.println(customerVo);

        try {
            customerService.modifyCustomer(customerVo);
            return ResponseResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("修改失败");
        }


    }

    @RequestMapping("/changeStatus")
    @ResponseBody
    public ResponseResult changeStatus(Integer id){

        try {
            customerService.changeStatus(id);
            return ResponseResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("修改失败");
        }

    }

    @RequestMapping("/findByParam")
    public String findByParam(CustomerParam customerParam,Integer pageNum,Model model){

        //System.out.println(customerParam);
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum=Constants.PAGE_NO;
        }
        PageHelper.startPage(pageNum,2);
        List<Customer> customers = customerService.findByParam(customerParam);
        PageInfo<Customer> pageInfo = new PageInfo<>(customers);
        model.addAttribute("data",pageInfo);
        model.addAttribute("customerParam",customerParam);

        return "customerManager";
    }


}










