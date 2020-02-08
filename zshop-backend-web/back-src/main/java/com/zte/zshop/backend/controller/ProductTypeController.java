package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.service.ProductTypeService;
import com.zte.zshop.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:helloboy
 * Date:2019-05-16 9:41
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/productType")
public class ProductTypeController {


    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = Constants.PAGE_NO;
        }
        PageInfo<ProductType> pageInfo = productTypeService.findAll(pageNum, Constants.PAGE_SIZE);

        /*for (ProductType productType : productTypes) {
            System.out.println(productType);
        }*/
        model.addAttribute("data",pageInfo);

        return "productTypeManager";
    }


    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(@RequestParam("name") String productTypeName, @RequestParam("Specification") String specification){

        ResponseResult result = new ResponseResult();
        try {

            productTypeService.add(productTypeName,specification);
            result.setStatus(Constants.RESPONSE_STATUS_SUCCESS);
            result.setMessage("添加成功");
        } catch (Exception e) {
           // e.printStackTrace();
            result.setStatus(Constants.RESPONSE_STATUS_FAILURE);
            result.setMessage("商品类型已经存在");
        }
        return result;
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){
        ProductType productType = productTypeService.findById(id);

        return ResponseResult.success(productType);
    }

    @RequestMapping("/modifyName")
    @ResponseBody
    public ResponseResult modifyName(Integer id,String name){

        try {
            productTypeService.modifyName(id,name);
            return ResponseResult.success("修改商品类型成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public ResponseResult deleteById(Integer id){
        productTypeService.removeById(id);
        return ResponseResult.success("删除成功");
    }

    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(Integer id){
        productTypeService.modifyStatus(id);
        return ResponseResult.success("修改状态成功");
    }
}

















