package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.backend.vo.ProductVO;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.entity.Supplier;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.service.SupplierService;
import com.zte.zshop.utils.ResponseResult;
import org.apache.commons.beanutils.PropertyUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2019-05-18 9:43
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/product")
public class ProductController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("products")
    public List<Product> loadProducts(){
        List<Product> products = productService.findAll();
        return products;
    }

    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){
        List<ProductType> productTypes = productTypeService.findEnable(Constants.PRODUCT_TYPE_ENABLE);
        return productTypes;
    }
    @ModelAttribute("productSuppliers")
    public List<Supplier> loadSuppliers(){

        List<Supplier> suppliers = supplierService.findAllSuppliers();
        return suppliers;
    }


    @RequestMapping("/add")
    public String add(ProductVO productVO,Integer pageNum,HttpSession session,Model model){
        //创建文件的保存路径
        //注意：保证在物理路径上该目录存在
        //String uploadPath = session.getServletContext().getRealPath("/WEB-INF/upload");

        //vo---->dto
        ProductDto productDto = new ProductDto();
        try {

            PropertyUtils.copyProperties(productDto,productVO);
            productDto.setFileName(productVO.getFile().getOriginalFilename());
            productDto.setInputStream(productVO.getFile().getInputStream());
            //productDto.setUploadPath(uploadPath);
            System.out.println(productVO.getSupplierId());
            productService.add(productDto);

            model.addAttribute("successMsg","添加成功");
        } catch (Exception e) {
            model.addAttribute("errorMsg",e.getMessage());
        }

        //用service的add方法保存dto
        return "forward:findAll?pageNum="+pageNum;
    }

    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String,Object> checkName(String name){

        Map<String,Object> map = new HashMap<>();
        boolean res = productService.checkName(name);

        //如果名称不存在,则可以使用
        if (res){
            map.put("valid",true);
        }
        else {
            map.put("valid",false);
            map.put("message","商品("+name+")已经存在");
        }
        return map;
    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = Constants.PAGE_NO;
        }
        PageInfo<Product> pageInfo = productService.findAll(pageNum,Constants.PAGE_SIZE);


        model.addAttribute("data",pageInfo);

        return "productManager";
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){

        Product product = productService.findById(id);
        return ResponseResult.success(product);
    }

    @RequestMapping("/getImg")
    public void getImg(String path,OutputStream out){
        productService.getImage(path,out);
    }

    @RequestMapping("/modify")
    public String modify(ProductVO productVO,Integer pageNum,HttpSession session,Model model){
        //创建文件的保存路径
        //注意：保证在物理路径上该目录存在
        //String uploadPath = session.getServletContext().getRealPath("/WEB-INF/upload");
        //vo-->dto
        ProductDto productDto = new ProductDto();
        //productDto.setName(productVO.getName());
        //productDto.setPrice(productVO.getPrice());
        //当未选择图片时，值为true,否则为false
        //System.out.println("".equals(productVO.getFile().getOriginalFilename()));
        //使用第三方工具完成对象间属性值的拷贝,注意：属性名称一致
        try {
            PropertyUtils.copyProperties(productDto,productVO);
            if(!"".equals(productVO.getFile().getOriginalFilename())) {
                productDto.setFileName(productVO.getFile().getOriginalFilename());
                productDto.setInputStream(productVO.getFile().getInputStream());
                //productDto.setUploadPath(uploadPath);
            }
            productService.modifyProduct(productDto);

            model.addAttribute("successMsg","修改成功");
        } catch (Exception e) {
            model.addAttribute("errorMsg",e.getMessage());
        }
        //调用service的add方法保存dto
        return "forward:findAll?pageNum="+pageNum;
    }

    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult deleteById(Integer id){

        try {

            productService.removeById(id);
            return ResponseResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("删除失败");
        }

    }

    //显示图片
    //逻辑：将图片输出到页面级输出流
    @RequestMapping("/showPic")
    public void showPic(String image,OutputStream out)throws IOException{

        URL url = new URL(image);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        //创建一个缓冲块，一个读取4k
        byte[] data = new byte[4096];
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
    @RequestMapping("addStock")
    public String UpdateStock(Integer productId, Integer stock,Integer pageNum,Model model){

        try {
            //System.out.println(productId + " "+stock);
            productService.addStock(productId, stock);
            model.addAttribute("successMsg","修改成功");
        }catch (Exception e){
            model.addAttribute("errorMsg",e.getMessage());
        }

        return "forward:findAll?pageNum="+pageNum;
    }
}
















