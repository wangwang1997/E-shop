package com.zte.zshop.front.controller;

import com.zte.zshop.entity.Customer;
import com.zte.zshop.entity.Order;
import com.zte.zshop.exception.CustomerHasRegistException;
import com.zte.zshop.exception.LoginErrorExcpetion;
import com.zte.zshop.service.CustomerService;
import com.zte.zshop.service.OrderService;
import com.zte.zshop.utils.ResponseResult;
import com.zte.zshop.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2019-06-11 20:45
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/loginByAccount")
    @ResponseBody
    public ResponseResult login(String loginName,String password,HttpSession session){

        //System.out.println(loginName+password);
        try {
            Customer customer = customerService.loginByNameAndPass(loginName, password);
            customer.setId(customerService.findByLoginName(loginName).getId());
            session.setAttribute("customer", customer);
            return ResponseResult.success(customer);
        } catch (LoginErrorExcpetion e) {
           // e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/loginOut")
    @ResponseBody
    public ResponseResult loginOut(HttpSession session){

        //注销会话
        session.invalidate();
        return ResponseResult.success("退出成功");
    }

    @RequestMapping("/Regist")
    public String RegistCustomer(CustomerVo customerVo,HttpSession session) throws CustomerHasRegistException {

        Customer customer = customerService.findById(customerVo.getId());
        customerService.addCustomer(customerVo);
        session.setAttribute("customer",customer);

        return "redirect:/front/product/main";
    }
    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String,Object> checkName(String loginName){

        Map<String,Object> map = new HashMap<>();
        boolean res = customerService.checkName(loginName);

        //如果名称不存在,则可以使用
        if (res){
            map.put("valid",true);
        }
        else {
            map.put("valid",false);
            map.put("message","用户("+loginName+")已经存在");
        }
        return map;
    }
    @RequestMapping("/checkOldPassword")
    @ResponseBody
    public Map<String,Object> checkOldPwd(String oldpwd,HttpSession session){

        Map<String,Object> map = new HashMap<>();
        Customer customer = (Customer) session.getAttribute("customer");
        boolean res = customerService.checkOldPwd(oldpwd,customer.getId());

        //如果名称不存在,则可以使用
        if (res){
            map.put("valid",true);
        }
        else {
            map.put("valid",false);
            map.put("message","与原密码不一致");
        }
        return map;
    }
    @RequestMapping("/checkNewPassword")
    @ResponseBody
    public Map<String,Object> checkNewPwd(String newpwd,HttpSession session){

        Map<String,Object> map = new HashMap<>();
        Customer customer = (Customer) session.getAttribute("customer");
        boolean res = customerService.checkNewPwd(newpwd, customer.getId());

        //如果名称不存在,则可以使用
        if (res){
            map.put("valid",true);
        }
        else {
            map.put("valid",false);
            map.put("message","与原密码一致");
        }
        return map;
    }

    @RequestMapping("/ModifyPwd")
    public String ModifyPassword(String newpwd,HttpSession session){

        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println(newpwd);
        customerService.modifyPassword(newpwd,customer.getId());

        session.removeAttribute("customer");
        return "redirect:/front/product/main";
    }
    @RequestMapping("/toCenter")
    public String toCenter(Model model,HttpSession session){

        Double totalMoney =0.0;
        List<Order> orders = orderService.findAll();
        Customer customer = (Customer) session.getAttribute("customer");

        for(Order order:orders) {
            //    System.out.println(customer.getId()+" "+customer.getName() +" " +order.getCustomer().getLogin_name() + order.getCustomer().getId());
            //}
            if(customer.getId() == order.getCustomer().getId()){
                totalMoney+=order.getPrice();
            }
        }
        model.addAttribute("totalMoney",totalMoney);
        return "center";
    }

    @RequestMapping("/ModifyCustomer")
    public String ModifyCustomer(CustomerVo customerVo,Model model ,HttpSession session){

        System.out.println(customerVo.getName() + " "+ customerVo.getLoginName() + " " +customerVo.getPhone() + " " + customerVo.getAddress());
        try {
            Customer customer = customerService.modifyCustomer(customerVo);
            session.setAttribute("customer",customer);
            model.addAttribute("successMsg","修改成功");
        }catch (Exception e){
            model.addAttribute("errorMsg",e.getMessage());
        }
        return "center";
    }
    @RequestMapping("/BeMember")
    @ResponseBody
    public ResponseResult BeMember(Model model,HttpSession session,Integer id){

        try{
            System.out.println(id);
            Customer customer = customerService.modifyMemberById(id);
            session.setAttribute("customer",customer);
            return ResponseResult.success("修改成功");

        }catch (Exception e){
            e.getStackTrace();
        }

        return ResponseResult.fail("修改失败");
    }


}
