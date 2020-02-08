package com.zte.zshop.front.controller;

import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.exception.PhoneNotFoundException;
import com.zte.zshop.service.CustomerService;
import com.zte.zshop.utils.HttpClientUtils;
import com.zte.zshop.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:hellboy
 * Date:2019-06-19 14:28
 * Description:<描述>
 */
@Controller
@RequestMapping("/sms")
public class SMSController {

    @Value("${sms.url}")
    private String url;

    @Value("${sms.key}")
    private String key;

    @Value("${sms.tpl_id}")
    private String tplId;

    @Value("${sms.tpl_value}")
    private String tplValue;


    @Autowired
    private CustomerService customerService;



    @RequestMapping("/sendSerificationCode")
    @ResponseBody
    public ResponseResult sendSerificationCode(String phone,HttpSession session){

        try {
            //生成6位随机数
            //思路:Math.random() 范围：[0.0,1.0),那么小于1000000,大于100000的整数:
            // (int)((Math.random()*9+1)*100000)
            //Random random = new Random();
            int randCode = (int) ((Math.random() * 9 + 1) * 100000);
            session.setAttribute("randCode",randCode);

            Map<String,String> params = new HashMap<>();
            params.put("mobile",phone);
            params.put("tpl_id",tplId);
            params.put("tpl_value",tplValue+randCode);
            params.put("key",key);


            //发送消息
            HttpClientUtils.doPost(url, params);


            return ResponseResult.success("验证码发送成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseResult.fail("验证码发送失败");
        }
    }

    //短信快捷登录
    @RequestMapping("/loginBySMS")
    @ResponseBody
    public ResponseResult loginBySMS(String phone,int verificationCode,HttpSession session){

        ResponseResult result=  ResponseResult.fail("");

        try {
            //判断手机号是否注册
            Customer customer= customerService.findByPhone(phone);

            //判断验证码是否存在
            Object randCode = session.getAttribute("randCode");
            if(!ObjectUtils.isEmpty(randCode)){
                //判断验证码是否正确
                int code=(int)randCode;
                if(code==verificationCode){

                    //重新设置你好：XX
                    session.setAttribute("customer",customer);
                    result.setData(customer);
                    result.setStatus(Constants.RESPONSE_STATUS_SUCCESS);


                }
                else{
                    result.setMessage("验证码不正确");
                }


            }
            else{
                result.setMessage("验证码不存在或者已经过期，请重新输入");
            }



        } catch (PhoneNotFoundException e) {
            //e.printStackTrace();
            result.setMessage(e.getMessage());
        }
        return  result;


    }

}
