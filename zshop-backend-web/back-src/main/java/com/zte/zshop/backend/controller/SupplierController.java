package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.Supplier;
import com.zte.zshop.service.SupplierService;
import com.zte.zshop.utils.ResponseResult;
import com.zte.zshop.vo.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:helloboy
 * Date:2019-07-02 9:55
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = Constants.PAGE_NO;
        }

        PageInfo<Supplier> pageInfo = supplierService.findAll(pageNum,4);
        model.addAttribute("data",pageInfo);


        return "supplierManager";
    }
    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(SupplierVO supplierVo){

        try {
            supplierService.add(supplierVo);
            return ResponseResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("添加失败");
        }
    }
    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult deleteById(Integer id){

        try {

            supplierService.removeById(id);
            return ResponseResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("删除失败");
        }

    }
    @RequestMapping("/showModifySupplier")
    @ResponseBody
    public ResponseResult showModifySupplier(Integer id){

        Supplier supplier = supplierService.findById(id);
        return ResponseResult.success(supplier);
    }
    @RequestMapping("/modifySupplier")
    @ResponseBody
    public ResponseResult modifySupplier(SupplierVO supplierVo){


        try {
            supplierService.modifySupplier(supplierVo);
            //System.out.println(sysuserVo);
            return ResponseResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("修改失败");
        }
    }
}
