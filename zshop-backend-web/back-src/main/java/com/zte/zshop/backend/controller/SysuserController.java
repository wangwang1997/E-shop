package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.Role;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.exception.SysuserNotExistException;
import com.zte.zshop.params.SysuserParam;
import com.zte.zshop.service.RoleService;
import com.zte.zshop.service.SysuserService;
import com.zte.zshop.utils.ResponseResult;
import com.zte.zshop.vo.SysuserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2019-05-11 17:06
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/sysuser")
public class SysuserController {

    @Autowired
    private SysuserService sysuserService;

    @Autowired
    private RoleService roleService;


    @RequestMapping("/login")
    public String login(String loginName,String password,HttpSession session,Model model){

        try {
            Sysuser sysuser = sysuserService.login(loginName, password);
            session.setAttribute("sysuser",sysuser);

            return "main";
        } catch (SysuserNotExistException e) {
            /*e.printStackTrace();*/
            model.addAttribute("errorMsg",e.getMessage());
            return "login";
        }
    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = Constants.PAGE_NO;
        }

        PageInfo<Sysuser> pageInfo = sysuserService.findAll(pageNum,2);
        model.addAttribute("data",pageInfo);


        return "sysuserManager";
    }

    @ModelAttribute("roles")
    public List<Role> loadRoles(){

        List<Role> roles = roleService.findAll();
        /*for (Role role : roles) {
            System.out.println(role);
        }*/
        return roles;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(SysuserVo sysuserVo){

        try {
            sysuserService.add(sysuserVo);
            return ResponseResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("添加失败");
        }
    }

    @RequestMapping("/checkLoginName")
    @ResponseBody
    public Map<String,Object> checkLoginName(String loginName){

        Map<String,Object> map = new HashMap<>();
        boolean res = sysuserService.checkLoginName(loginName);

        if (res){
            map.put("valid",true);
        }
        else {
            map.put("valid",false);
            map.put("message","账户("+loginName+")已经存在");
        }
        return map;
    }

    @RequestMapping("/showModifySysuser")
    @ResponseBody
    public ResponseResult showModifySysuser(Integer id){

        Sysuser sysuser = sysuserService.findById(id);
        return ResponseResult.success(sysuser);
    }

    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(Integer id){

        try {
            sysuserService.modifyStatus(id);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("更新失败");
        }
    }

    @RequestMapping("/findByParam")
    public String findByParam(SysuserParam sysuserParam,Integer pageNum,Model model){

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum=Constants.PAGE_NO;
        }
        PageHelper.startPage(pageNum, 2);
        List<Sysuser> sysusers = sysuserService.findByParam(sysuserParam);
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);
        model.addAttribute("sysuserParam",sysuserParam);
        //System.out.println(sysuserParam);


        return "sysuserManager";
    }

    @RequestMapping("/modifySysuser")
    @ResponseBody
    public ResponseResult modifySysuser(SysuserVo sysuserVo){


        try {
            sysuserService.modifySysuser(sysuserVo);
            //System.out.println(sysuserVo);
            return ResponseResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("修改失败");
        }
    }

    @RequestMapping("/loginOut")
    public String loginOut(){
        return "login";
    }


}












