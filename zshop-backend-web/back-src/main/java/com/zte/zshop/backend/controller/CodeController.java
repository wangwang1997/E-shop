package com.zte.zshop.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2019-05-30 11:03
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/code")
public class CodeController {

    @RequestMapping("/image")
    public void image(HttpServletRequest request,HttpServletResponse response)throws IOException{
        //绘图
        //创建图像缓冲器（创建画板）
        BufferedImage bi = new BufferedImage(68,28,BufferedImage.TYPE_INT_RGB);

        //创建画刷
        Graphics g = bi.getGraphics();
        Color c = new Color(200,200,200);
        g.setColor(c);

        //填充面板底色
        g.fillRect(0,0,68,28);


        char[] ch="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random r = new Random();
        int len = ch.length;
        int index;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<4;i++){
            //随机获取（0-len）之间任意整数
            index=r.nextInt(len);
            sb.append(ch[index]);
            //设置颜色
            g.setColor(new Color(r.nextInt(200),r.nextInt(200),r.nextInt(200)));
            g.drawString(ch[index]+"",(i*15)+3,18);
        }

        //将该数字放入session作用域
        request.getSession().setAttribute("piccode",sb.toString());

        //绘制图片到页面
        ImageIO.write(bi,"JPG",response.getOutputStream());
    }


    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String,Object> checkCode(String code,HttpSession session){

        Map<String,Object> map = new HashMap<>();
        String piccode = (String)session.getAttribute("piccode");
        if (piccode.equalsIgnoreCase(code)){
            map.put("valid",true);
        }else {
            map.put("valid",false);
        }
        return map;
    }

}















