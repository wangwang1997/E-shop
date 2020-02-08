<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>backend</title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/index.css" />
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/file.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/userSetting.js"></script>
    <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zshop.css"/>
    <script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css"/>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script>
        $(function(){
            //上传图像预览
            $('#product-image').on('change',function() {
                $('#img').attr('src', window.URL.createObjectURL(this.files[0]));
            });
            $('#pro-image').on('change',function() {
                $('#img2').attr('src', window.URL.createObjectURL(this.files[0]));
            });

            //服务器校验
            let successMsg="${successMsg}";
            let errorMsg="${errorMsg}";
            console.log(successMsg+"a1");
            console.log(errorMsg+"a2");
            if(successMsg!=''){
                layer.msg(
                        successMsg,
                        {
                            time:2000,
                            skin:"successMsg"
                        }
                );
            }
            if(errorMsg!=''){
                layer.msg(
                        errorMsg,
                        {
                            time:2000,
                            skin:"errorMsg"
                        }
                );
            }


            //使用bootatrap_validator插件进行客户端数据校验
            $('#frmAddProduct').bootstrapValidator(
                    {
                        feedbackIcons: {
                            valid: 'glyphicon glyphicon-ok',//成功后输出的图标
                            invalid: 'glyphicon glyphicon-remove',//失败后输出的图标
                            validating: 'glyphicon glyphicon-refresh'//长时间加载时输出的图标
                        },
                        fields:{
                            name: {
                                validators: {
                                    notEmpty: {
                                        message: '商品名称不能为空'
                                    },
                                    remote:{
                                        url:"${pageContext.request.contextPath}/backend/product/checkName"
                                    }
                                }
                            },
                            price:{
                                validators:{
                                    notEmpty: {
                                        message: '价格不能为空'
                                    },
                                    regexp:{
                                        regexp:/^\d+\.\d+$/,
                                        message:"价格必须为小数"

                                    }

                                }
                            },
                            costPrice:{
                                validators:{
                                    notEmpty: {
                                        message: '进货价格不能为空'
                                    },
                                    regexp:{
                                        regexp:/^\d+\.\d+$/,
                                        message:'价格必须为小数'
                                    }
                                }
                            },
                            file:{
                                validators:{
                                    notEmpty: {
                                        message: '选择需要上传的图片'
                                    }
                                }
                            },
                            productTypeId:{
                                validators:{
                                    notEmpty: {
                                        message: '请选择商品类型'
                                    }
                                }
                            },
                            supplierId:{
                                validators:{
                                    notEmpty:{
                                        message: '请选择供货商'
                                    }
                                }
                            },
                            stock:{
                                validators:{
                                    notEmpty:{
                                        message: '请输入进货数量'
                                    },
                                    regexp:{
                                        regexp:/^\d+$/,
                                        message:"价格必须为整数"
                                    }
                                }
                            },
                            info:{
                                validators:{
                                    notEmpty:{
                                        message:'信息不能为空'
                                    }
                                }
                            }
                        }

                    }
            );

            //分页
            $('#pagination').bootstrapPaginator({
                //主版本号
                bootstrapMajorVersion: 3,

                //当前页
                currentPage:${data.pageNum},
                //总页数
                totalPages:${data.pages},
                //url
                pageUrl: function (type, page, current) {

                    return "${pageContext.request.contextPath}/backend/product/findAll?pageNum=" + page;

                },
                //面板文字
                //type：类型
                //page:当前页
                itemTexts: function (type, page, current) {
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "尾页";
                        case "page":
                            return page;
                    }
                }


            });

            //修改模态框的客户端校验
            $('#frmModifyProduct').bootstrapValidator(
                    {
                        feedbackIcons: {
                            valid: 'glyphicon glyphicon-ok',//成功后输出的图标
                            invalid: 'glyphicon glyphicon-remove',//失败后输出的图标
                            validating: 'glyphicon glyphicon-refresh'//长时间加载时输出的图标
                        },
                        fields:{
                            name: {
                                validators: {
                                    notEmpty: {
                                        message: '商品名称不能为空'
                                    },
                                    remote:{
                                        url:"${pageContext.request.contextPath}/backend/product/checkName"
                                    }
                                }
                            },
                            price:{
                                validators:{
                                    notEmpty: {
                                        message: '价格不能为空'
                                    },
                                    regexp:{
                                        regexp:/^\d+\.\d+$/,
                                        message:"价格必须为小数"

                                    }

                                }
                            },
                            file:{
                                validators:{
                                    notEmpty: {
                                        message: '选择需要上传的图片'
                                    }
                                }
                            },
                            productTypeId:{
                                validators:{
                                    notEmpty: {
                                        message: '请选择商品类型'
                                    }
                                }
                            }




                        }

                    }
            );



        });

        //显示修改商品界面
        function showProduct(id){

            //alert(id);
            $.post('${pageContext.request.contextPath}/backend/product/findById',
                    {'id':id},function(result){
                        //console.log(result);
                        //如果查询成功
                        if(result.status==1){
                            //将查询出的结果写入修改商品界面
                            $('#pro-num').val(result.data.id);
                            $('#pro-name').val(result.data.name);
                            $('#pro-price').val(result.data.price);
                            //设置下拉列表的值
                            $('#pro-typeId').val(result.data.productType.id);
                            $('#pro-CostPrice').val(result.data.costPrice);
                            $('#product-info').val(result.data.info)
                            $('#Div'+result.data.productType.id).show().siblings().hide();

                            let strr = result.data.specifParam;

                            //添加新按钮

                            //赋给修改模块的隐藏表单域
                            $('#Proparamter').val(strr);
                            //alert($('#Proparamter').val());
                            strr = strr.split(" ");
                            $.each(strr,function(i,item){
                                if(item){
                                    let specParam = item.split("：");
                                    //以“：”为隔离符，将字符串分成几部分进行搜索

                                    $.each(specParam,function(i,Item){
                                        if(Item) {
                                            Item = Item.split(";");
                                            //以“；”为隔离符，将字符串分成几部分进行搜索
                                            for(var j=0;j<Item.length-1;j++){
                                                $("#modifySpecif input:button").each(function(){

                                                    if(Item[j]==$(this).val()) {
                                                        $(this).removeClass().addClass("btn btn-info");
                                                        console.log(Item[j]);
                                                        return true;
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            });



                            //设置图片预览
                            $('#img2').attr('src','${pageContext.request.contextPath}/backend/product/showPic?image='+result.data.image);




                        }
                    });


        }

        //显示删除模态框
        function showDelModel(id){

            //获取id,将值写入隐藏表单域
            $('#delProductId').val(id);
            //显示删除模态框
            $('#delProductModel').modal('show');




        }

        //删除
        function delProduct(){

            $.post('${pageContext.request.contextPath}/backend/product/removeById',
                    {'id':$('#delProductId').val()},
                    function(result){

                        if(result.status==1){
                            layer.msg(
                                    result.message,
                                    {
                                        time:2000,
                                        skin:'successMsg'
                                    },
                                    function(){
                                        //刷新当前页
                                        location.href='${pageContext.request.contextPath}/backend/product/findAll?pageNum='+${data.pageNum};
                                    }
                            );
                        }
                        else{
                            layer.msg(
                                    result.message,
                                    {
                                        time:2000,
                                        skin:'errorMsg'
                                    }
                            );
                        }

                    });
        }
        //显示商品规格参数
        function select(obj){
            //清空隐藏表单域内容
            $("#pro-paramter").val("");
            //复原选中按钮
            $(".btn.btn-info").removeClass().addClass("btn btn-default");
            if($("#div"+obj.value)) {
                $("#div" + obj.value).show().siblings().hide();

            }
        }
        function Select(obj){
            $("#Proparamter").val("");
            //复原选中按钮
            $(".btn.btn-info").removeClass().addClass("btn btn-default");
            if($("#Div"+obj.value)) {
                $("#Div" + obj.value).show().siblings().hide();

            }
        }
        //手动添加商品参数
        function addition(obj1,obj2) {
            $("#productSpec" + obj1 + "" + obj2 + "").before(
                    "<div class='col-xs-4'><input type='text' id='add-parameter" + obj1 + obj2 + "' class='form-control'></div>"
            ).hide();

        }
        function Addition(obj1,obj2) {
            $("#ProductSpec" + obj1 + "" + obj2 + "").before(
                    "<div class='col-xs-4'><input type='text' id='Add-parameter" + obj1 + obj2 + "' class='form-control'></div>"
            ).hide();

        }
        $(function(){
            //点击给添加模块的隐藏域赋值，并改变按钮颜色
            $("#addSpecif input:button").click(function(){


                let pro_paramter = "";

                if($(this).attr("class") == "btn btn-default"){
                    $(this).removeClass().addClass("btn btn-info");
                    $("#addSpecif input:button").each(function(){
                        if($(this).hasClass("btn btn-info")) {
                            //获得该规格参数标题
                            let specTitle = $(this).parent().parent().find("label").text();
                            let specAdd = $(this).parent().parent().find("input:text").val();
                            //alert(specAdd);
                            if(pro_paramter.indexOf(specTitle) < 0){
                                if(specAdd&&pro_paramter.indexOf(specAdd) < 0){
                                    pro_paramter = pro_paramter + specTitle + specAdd + ";" + $(this).val()+";";
                                }else {
                                    pro_paramter = pro_paramter + specTitle + $(this).val() + ";";
                                }
                            }else{
                                pro_paramter = pro_paramter + $(this).val()+";";
                            }
                        }
                    });
                    $("#pro-paramter").val(pro_paramter);

                }else if($(this).attr("class") == "btn btn-info"){
                    $(this).removeClass().addClass("btn btn-default");
                    $("#addSpecif input:button").each(function(){
                        if($(this).hasClass("btn btn-info")) {
                            //获得该规格参数标题
                            let specTitle = $(this).parent().parent().find("label").text();
                            let specAdd = $(this).parent().parent().find("input:text").val();
                            if(pro_paramter.indexOf(specTitle) < 0){
                                if(specAdd&&pro_paramter.indexOf(specAdd) < 0){
                                    pro_paramter = pro_paramter + specTitle + specAdd + ";" + $(this).val()+";";
                                }else {
                                    pro_paramter = pro_paramter + specTitle + $(this).val() + ";";
                                }
                            }else{
                                pro_paramter = pro_paramter + $(this).val()+";";
                            }
                        }
                    });
                    $("#pro-paramter").val(pro_paramter);
                }

            });


            //点击给修改模块的隐藏域赋值，并改变按钮颜色
            $("#modifySpecif input:button").click(function(){

                let pro_paramter = "";

                if($(this).attr("class") == "btn btn-default"){
                    $(this).removeClass().addClass("btn btn-info");
                    $("#modifySpecif input:button").each(function(){
                        if($(this).hasClass("btn btn-info")) {
                            //获得该规格参数标题
                            let specTitle = $(this).parent().parent().find("label").text();
                            let specAdd = $(this).parent().parent().find("input:text").val();

                            if(pro_paramter.indexOf(specTitle) < 0){
                                if(specAdd&&pro_paramter.indexOf(specAdd) < 0){
                                    pro_paramter = pro_paramter + specTitle + specAdd + ";" + $(this).val()+";";
                                }else {
                                    pro_paramter = pro_paramter + specTitle + $(this).val() + ";";
                                }
                            }else{
                                pro_paramter = pro_paramter + $(this).val()+";";
                            }
                        }
                    });
                    $("#Proparamter").val(pro_paramter);
                }else if($(this).attr("class") == "btn btn-info"){
                    $(this).removeClass().addClass("btn btn-default");
                    $("#modifySpecif input:button").each(function(){
                        if($(this).hasClass("btn btn-info")) {
                            //获得该规格参数标题
                            let specTitle = $(this).parent().parent().find("label").text();
                            let specAdd = $(this).parent().parent().find("input:text").val();
                            //alert(specAdd);
                            if(pro_paramter.indexOf(specTitle) < 0){
                                if(specAdd&&pro_paramter.indexOf(specAdd) < 0){
                                    pro_paramter = pro_paramter + specTitle + specAdd + ";" + $(this).val()+";";
                                }else {
                                    pro_paramter = pro_paramter + specTitle + $(this).val() + ";";
                                }
                            }else{
                                pro_paramter = pro_paramter + $(this).val()+";";
                            }
                        }
                    });
                    $("#Proparamter").val(pro_paramter);
                }

            });
            //给添加的参数规格设置失去焦点函数

        });

    </script>
</head>

<body>
<div class="panel panel-default" id="userPic">
    <div class="panel-heading">
        <h3 class="panel-title">商品管理</h3>
    </div>
    <div class="panel-body">
        <input type="button" value="添加商品" class="btn btn-primary" id="doAddPro">
        <input type="button" value="添加进货" class="btn btn-primary" id="doAddStock" data-toggle="modal" data-target="#Stock">
        <br>
        <br>
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">编号</th>
                    <th class="text-center">商品</th>
                    <th class="text-center">进货价格</th>
                    <th class="text-center">销售价格</th>
                    <th class="text-center">产品类型</th>
                    <th class="text-center">规格参数</th>
                    <th class="text-center">库存数量</th>
                    <th class="text-center">供货商</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${data.list}" var="product">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.costPrice}</td>
                        <td>${product.price}</td>
                        <td>${product.productType.name}</td>
                        <td>${product.specifParam}</td>
                        <td>${product.stock}</td>
                        <td>${product.supplier.name}</td>
                        <td>
                            <c:if test="${product.productType.status==1}">有效商品</c:if>
                            <c:if test="${product.productType.status==0}">无效商品</c:if>

                        </td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doProModify" value="修改"
                                   onclick="showProduct(${product.id})">
                            <input type="button" class="btn btn-warning btn-sm doProDelete" value="删除"
                                   onclick="showDelModel(${product.id})">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--使用bootstrap-paginator实现分页--%>
            <ul id="pagination"></ul>
        </div>
    </div>
</div>

<!-- 添加商品 start -->
<div class="modal fade" tabindex="-1" id="Product">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-lg">
        <!-- 内容声明 -->
        <form action="${pageContext.request.contextPath}/backend/product/add" class="form-horizontal"
              enctype="multipart/form-data" method="post" id="frmAddProduct">
            <input type="hidden" name="pageNum" value="${data.pageNum}">
            <div class="modal-content">
                <!-- 头部、主体、脚注 -->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">添加商品</h4>
                </div>
                <div class="modal-body text-center row">
                    <div class="col-sm-8">
                        <div class="form-group">
                            <label for="product-name" class="col-sm-4 control-label">商品名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="product-name" name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="product-price" class="col-sm-4 control-label">商品价格：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="product-price" name="price">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="product-price" class="col-sm-4 control-label">进货价格：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="product-costprice" name="costPrice">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="product-stock" class="col-sm-4 control-label">进货数量：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="product-stock" name="stock">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="product-image" class="col-sm-4 control-label">商品图片：</label>
                            <div class="col-sm-8">
                                <a href="javascript:;" class="file">选择文件
                                    <input type="file" name="file" id="product-image">
                                </a>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">商品类型：</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="productTypeId" onchange="select(this)">
                                    <option value="">--请选择--</option>
                                    <c:forEach items="${productTypes}" var="productType">

                                        <option value="${productType.id}">${productType.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <input type="hidden" value="" id="pro-paramter" name="specifParam">
                        <div id="addSpecif">
                            <c:forEach items="${productTypes}" var="productType">

                                <div id="div${productType.id}" style="display: none;">
                                    <c:forEach items="${productType.specification.split(';')}" var="product" varStatus="status">
                                        <c:if test="${not empty product}">
                                            <div class="form-group" >
                                                <label class="col-sm-4 control-label">${product.split(':')[0]}：</label>
                                                <div class="col-sm-8">
                                                    <c:forEach items="${product.split(':')[1].split(',')}" var="prospecif" varStatus="pos">

                                                        <input class="btn btn-default" type="button" value="${prospecif}">

                                                    </c:forEach>
                                                    <button class="btn btn-default" type="button" onclick="addition(${productType.id},${status.index})" id="productSpec${productType.id}${status.index}" ><span class="glyphicon glyphicon-plus"></span></button>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">供货商：</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="supplierId">
                                    <option value="">--请选择--</option>
                                    <c:forEach items="${productSuppliers}" var="supplier">

                                        <option value="${supplier.id}">${supplier.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">详细描述</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" rows="3" name="info"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- 显示图像预览 -->
                        <img style="width: 240px;height: 280px;" id="img">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary" type="submit">添加</button>
                    <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 添加商品 end -->

<!-- 修改商品 start -->
<div class="modal fade" tabindex="-1" id="myProduct">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-lg">
        <!-- 内容声明 -->
        <form action="${pageContext.request.contextPath}/backend/product/modify" class="form-horizontal"
              enctype="multipart/form-data" method="post" id="frmModifyProduct">
            <input type="hidden" name="pageNum" value="${data.pageNum}">
            <div class="modal-content">
                <!-- 头部、主体、脚注 -->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">修改商品</h4>
                </div>
                <div class="modal-body text-center row">
                    <div class="col-sm-8">
                        <div class="form-group">
                            <label for="pro-num" class="col-sm-4 control-label">商品编号：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="pro-num" name="id" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pro-name" class="col-sm-4 control-label">商品名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="name" id="pro-name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pro-price" class="col-sm-4 control-label">商品价格：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="price" id="pro-price">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pro-CostPrice" class="col-sm-4 control-label">进货价格：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="costPrice" id="pro-CostPrice">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pro-image" class="col-sm-4 control-label">商品图片：</label>
                            <div class="col-sm-8">
                                <a class="file">
                                    选择文件 <input type="file" name="file" id="pro-image">
                                </a>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="product-type" class="col-sm-4 control-label">商品类型：</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="pro-typeId" name="productTypeId" onchange="Select(this)">
                                    <option value="">--请选择--</option >
                                    <c:forEach items="${productTypes}" var="productType">

                                        <option value="${productType.id}">${productType.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <input type="hidden" value="" id="Proparamter" name="specifParam">
                        <div id="modifySpecif">
                            <c:forEach items="${productTypes}" var="productType">

                                <div id="Div${productType.id}" style="display: none;">
                                    <c:forEach items="${productType.specification.split(';')}" var="product" varStatus="status">
                                        <c:if test="${not empty product}">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">${product.split(':')[0]}：</label>
                                                <div class="col-sm-8">
                                                    <c:forEach items="${product.split(':')[1].split(',')}" var="prospecif" varStatus="pos">

                                                        <input class="btn btn-default" type="button" value="${prospecif}" id="btn${pos.index}">

                                                    </c:forEach>
                                                    <button class="btn btn-default" type="button" onclick="Addition(${productType.id},${status.index})" id="ProductSpec${productType.id}${status.index}" ><span class="glyphicon glyphicon-plus"></span></button>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">详细描述</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" rows="3" name="info" id="product-info"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <!-- 显示图像预览 -->
                        <img style="width: 240px;height: 280px;" id="img2">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary updatePro" type="submit">修改</button>
                    <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 修改商品 end -->

<!-- 显示“确认删除”提示框 start -->
<div class="modal fade" tabindex="-1" id="delProductModel">
    <input type="hidden" id="delProductId">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-sm">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">消息提示</h4>
            </div>
            <div class="modal-body text-left">
                <h4>确认要删除该商品吗？</h4>
                <input type="hidden" id="productTypeId">
            </div>
            <div class="modal-footer">
                <button class="btn btn-warning updateProType" data-dismiss="modal" onclick="delProduct()">删除
                </button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 显示“确认删除”提示框 end -->
<!--添加进货模态框 start -->
<div class="modal fade" tabindex="-1" id="Stock">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-lg">
        <form action="${pageContext.request.contextPath}/backend/product/addStock" class="form-horizontal"
              enctype="multipart/form-data" method="post" id="frmAddStock">
            <input type="hidden" name="pageNum" value="${data.pageNum}">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">添加商品进货</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="pro-Id" class="col-sm-4 control-label">商品名称：</label>

                    <div class="col-sm-4">
                        <select class="form-control" id="pro-Id" name="productId">
                            <option value="">--请选择--</option >
                            <c:forEach items="${products}" var="product">

                                <option value="${product.id}">${product.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <br>
                <div  class="row text-right">
                    <label for="AddStock" class="col-sm-4 control-label">进货数量：</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="AddStock" name="stock">
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary addProductStock">添加</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
        </form>
    </div>
</div>
<!--添加进货模态框 end -->
</body>

</html>