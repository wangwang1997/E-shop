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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zshop.css">
    <script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css"/>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>

    <script>
        $(function(){

            //服务器校验
            let successMsg="${successMsg}";
            let errorMsg="${errorMsg}";
            //console.log(successMsg+"a1")
            if(successMsg != ''){
                layer.msg(
                        successMsg, {
                            time:2000,
                            skin:"successMsg"
                        });
            }
            if(errorMsg != ''){
                layer.msg(
                        errorMsg, {
                            time:2000,
                            skin:"errorMsg"
                        });
            }


            //分页
            $('#pagination').bootstrapPaginator({
                //主版本号
                bootstrapMajorVersion: 3,

                //当前页
                currentPage:${data.pageNum},
                //总页数
                totalPages:${data.pages},
                //url
                /*pageUrl: function (type, page, current) {

                 return "${pageContext.request.contextPath}/backend/supplier/findAll?pageNum=" + page;

                 },*/
                onPageClicked:function(event,originalEvent,type,page){

                    $('#pageNum').val(page);
                    $('#frmQuery').submit();

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


            //添加系统用户校验
            $('#frmAddsupplier').bootstrapValidator(
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
                                        message: '名称不能为空'
                                    }
                                }
                            },
                            address:{
                                validators:{
                                    notEmpty:{
                                        message: '地址不能为空'
                                    }
                                }
                            },
                            phone:{
                                validators: {
                                    notEmpty: {
                                        message: '电话号码不能为空'
                                    }
                                }
                            }
                        }
                    }
            );

            //使用bootatrap_validator插件进行客户端数据校验
            //修改系统用户校验
            $('#myModal-Manger').bootstrapValidator(
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
                                        message: '名称不能为空'
                                    }
                                }
                            },
                            phone:{
                                validators: {
                                    notEmpty: {
                                        message: '联系电话不能为空'
                                    }
                                }
                            },
                            address:{
                                validators:{
                                    notEmpty:{
                                        message: '地址不能为空'
                                    }
                                }
                            }
                        }


                    }
            );

        });


        //添加系统用户
        function addSupplier(){

            let bv=$('#frmAddsupplier').data('bootstrapValidator');
            bv.validate();

            if(bv.isValid()){

                alert($('#frmAddsupplier').serialize());
                $.post(
                        '${pageContext.request.contextPath}/backend/supplier/add',
                        $('#frmAddsupplier').serialize(),
                        function(result){
                            //console.log(result);
                            if(result.status == 1){
                                layer.msg(
                                        result.message,
                                        {
                                            time:1000,
                                            skin:'successMsg'
                                        },
                                        function(){
                                            location.href='${pageContext.request.contextPath}/backend/supplier/findAll?pageNum='+${data.pageNum};
                                        }
                                );
                            }
                            else{
                                layer.msg(
                                        result.message,
                                        {
                                            time:1000,
                                            skin:'errorMsg'
                                        }
                                );
                            }
                        }
                );
            }
            else{
                $('#myMangerUser').modal('true');
            }
        }

        //修改系用户
        function showModifysupplier(id){

            //alert(id);
            $.post(
                    '${pageContext.request.contextPath}/backend/supplier/showModifySupplier',
                    {"id":id},
                    function(result){
                        //console.log(result);
                        $('#SupplierId').val(result.data.id);
                        $('#SupplierName').val(result.data.name);
                        $('#SupplierPhone').val(result.data.phone);
                        $('#SupplierAddress').val(result.data.address);
                    }
            );
        }

        function modifysupplier(){

            //alert(1);
            //alert()
            $.post(
                    '${pageContext.request.contextPath}/backend/supplier/modifySupplier',
                    $('#frmModifysupplier').serialize(),
                    function(result){
                        //console.log(result);
                        if(result.status==1){
                            layer.msg(
                                    result.message,
                                    {
                                        time:1000,
                                        skin:'successMsg'
                                    },
                                    function(){
                                        location.href='${pageContext.request.contextPath}/backend/supplier/findAll?pageNum='+${data.pageNum};
                                    }
                            );
                        }else{
                            layer.msg(
                                    result.message,
                                    {
                                        time:1000,
                                        skin:'errorMsg'
                                    }
                            );
                        }
                    }
            );

        }


        //显示删除模态框
        function showDelModel(id){

            //获取id,将值写入隐藏表单域
            $('#delSupplierId').val(id);
            //显示删除模态框
            $('#delSupplierModel').modal('show');




        }

        //删除
        function delsupplier(){

            $.post('${pageContext.request.contextPath}/backend/supplier/removeById',
                    {'id':$('#delSupplierId').val()},
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
                                        location.href='${pageContext.request.contextPath}/backend/supplier/findAll?pageNum='+${data.pageNum};
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
    </script>


</head>

<body>
<!-- 系统用户管理 -->
<div class="panel panel-default" id="adminSet">
    <div class="panel-heading">
        <h3 class="panel-title">供货商管理</h3>
    </div>
    <div class="panel-body">
        <input type="button" value="添加供货商" class="btn btn-primary" id="doAddManger">
        <div class="show-list text-center" style="position: relative; top: 10px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">供货商</th>
                    <th class="text-center">地址</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${data.list}" var="supplier">
                    <tr>
                        <td>${supplier.id}</td>
                        <td>${supplier.name}</td>
                        <td>${supplier.address}</td>
                        <td>${supplier.phone}</td>
                        <td class="text-center">
                            <input type="button" class="btn btn-primary btn-sm doMangerModify" onclick="showModifysupplier(${supplier.id})" value="修改">
                            <input type="button" class="btn btn-danger" value="删除" onclick="showDelModel(${supplier.id})">
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

<!-- 添加系统用户 start -->
<div class="modal fade" tabindex="-1" id="myMangerUser">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <form id="frmAddsupplier">
            <!-- 内容声明 -->
            <div class="modal-content">
                <!-- 头部、主体、脚注 -->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">添加供货商</h4>
                </div>
                <div class="modal-body text-center">
                    <div class="row text-right">
                        <label for="marger-username" class="col-sm-4 control-label">供货商名：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="marger-username" name="name">
                        </div>
                    </div>
                    <br>
                    <div class="row text-right">
                        <label for="supplier-address" class="col-sm-4 control-label">详细地址：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="supplier-address" name="address">
                        </div>
                    </div>
                    <br>
                    <div class="row text-right">
                        <label for="marger-phone" class="col-sm-4 control-label">联系电话：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="marger-phone" name="phone">
                        </div>
                    </div>
                    <br>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary " type="button" onclick="addSupplier()">添加</button>
                    <button class="btn btn-primary cancel" data-dismiss="modal" type="button">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 添加系统用户 end -->

<!-- 修改系统用户 start -->
<div class="modal fade" tabindex="-1" id="myModal-Manger">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <form id="frmModifysupplier">

            <input type="hidden" name="pageNum" value="${data.pageNum}">
            <div class="modal-content">
                <!-- 头部、主体、脚注 -->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">供货商修改</h4>
                </div>
                <div class="modal-body text-center">
                    <div class="row text-right">
                        <label for="SupplierId" class="col-sm-4 control-label">编号：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="SupplierId" name="id" readonly="readonly" >
                        </div>
                    </div>
                    <br>
                    <div class="row text-right">
                        <label for="SupplierName" class="col-sm-4 control-label">供货商名：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="SupplierName" name="name">
                        </div>
                    </div>
                    <br>
                    <div class="row text-right">
                        <label for="SupplierAddress" class="col-sm-4 control-label">详细地址：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="SupplierAddress" name="address" readonly="readonly">
                        </div>
                    </div>
                    <br>
                    <div class="row text-right">
                        <label for="SupplierPhone" class="col-sm-4 control-label">联系电话：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="SupplierPhone" name="phone">
                        </div>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary " type="button" onclick="modifysupplier()">修改</button>
                    <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 修改系统用户 end -->

<!-- 显示“确认删除”提示框 start -->
<div class="modal fade" tabindex="-1" id="delSupplierModel">
    <input type="hidden" id="delSupplierId">
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
                <input type="hidden" id="supplierTypeId">
            </div>
            <div class="modal-footer">
                <button class="btn btn-warning updateProType" data-dismiss="modal" onclick="delsupplier()">删除
                </button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 显示“确认删除”提示框 end -->
</body>

</html>
