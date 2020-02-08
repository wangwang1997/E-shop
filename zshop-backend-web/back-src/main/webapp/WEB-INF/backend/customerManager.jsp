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

                 return "${pageContext.request.contextPath}/backend/customer/findAll?pageNum=" + page;

                 },*/
                onPageClicked:function(event,originalEvent,type,page){

                    $('#pageNum').val(page);
                    $('#frmDoQuery').submit();

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

        });

        //显示修改页面
        function showModify(id){
            //alert(id);
            $.post(
                    '${pageContext.request.contextPath}/backend/customer/showModify',{"id":id},
                    function(result){
                        $('#customer_id').val(result.data.id);
                        $('#customerName').val(result.data.name);
                        $('#customerLoginName').val(result.data.loginName);
                        $('#customerPhone').val(result.data.phone);
                        $('#customerAdrees').val(result.data.address);
                    }
            );

        }

        //修改客户信息
        function modifyCustomer(){

            //alert($('#frmModifyCustomer').serialize());
            $.post(
                    '${pageContext.request.contextPath}/backend/customer/modifyCustomer',
                    $('#frmModifyCustomer').serialize(),
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
                                        location.href='${pageContext.request.contextPath}/backend/customer/findAll?pageNum='+${data.pageNum};
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

        //修改客户状态
        function modifyStatus(id,btn){
            //alert(id);
            $.post(
                    '${pageContext.request.contextPath}/backend/customer/changeStatus',{"id":id},
                    function(result){
                        if(result.status == 1){
                            let $td = $(btn).parent().prev();
                            if($td.text().trim()=='有效'){
                                $td.text('无效');
                                $(btn).val('启用').removeClass("btn-danger").addClass("btn-success");
                            }else{
                                $td.text('有效');
                                $(btn).val('禁用').removeClass("btn-success").addClass("btn-danger");
                            }
                        }
                    }
            );

        }

        //按条件查询
        function doQuery(){

           // alert(1);
            //将当前页面重置
            $('#pageNum').val(1);
            //提交表单
            $('#frmDoQuery').submit();

        }

    </script>

</head>

<body>
<div class="panel panel-default" id="userInfo" id="homeSet">
    <div class="panel-heading">
        <h3 class="panel-title">客户管理</h3>
    </div>
    <div class="panel-body">
        <div class="showusersearch">
            <form class="form-inline" action="${pageContext.request.contextPath}/backend/customer/findByParam" method="post" id="frmDoQuery">

                <input type="hidden" name="pageNum" id="pageNum">
                <div class="form-group">
                    <label for="customer_name">姓名:</label>
                    <input type="text" class="form-control"id="customer_name" name="name" value="${customerParam.name}" placeholder="请输入姓名" size="15px">
                </div>
                <div class="form-group">
                    <label for="customer_loginName">帐号:</label>
                    <input type="text" class="form-control" id="customer_loginName" name="loginName" value="${customerParam.loginName}" placeholder="请输入帐号" size="15px">
                </div>
                <div class="form-group">
                    <label for="customer_phone">电话:</label>
                    <input type="text" class="form-control" id="customer_phone" name="phone" value="${customerParam.phone}" placeholder="请输入电话" size="15px">
                </div>
                <div class="form-group">
                    <label for="customer_address">地址:</label>
                    <input type="text" class="form-control" id="customer_address" name="address" value="${customerParam.address}" placeholder="请输入地址">
                </div>
                <div class="form-group">
                    <label for="customer_isValid">状态:</label>
                    <select class="form-control" id="customer_isValid" name="isValid">
                        <option value="-1">全部</option>
                        <option value="1" <c:if test="${customerParam.isValid==1}">selected</c:if> >---有效---</option>
                        <option value="0" <c:if test="${customerParam.isValid==0}">selected</c:if> >---无效---</option>
                    </select>
                </div>
                <input type="button" value="查询" class="btn btn-primary" id="doSearch" onclick="doQuery()">
            </form>
        </div>

        <div class="show-list text-center" style="position: relative;top: 30px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">帐号</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">地址</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${data.list}" var="customer">
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>${customer.loginName}</td>
                    <td>${customer.phone}</td>
                    <td>${customer.address}</td>
                    <td>
                        <c:if test="${customer.isValid==1}">有效</c:if>
                        <c:if test="${customer.isValid==0}">无效</c:if>
                    </td>
                    <td class="text-center">
                        <input type="button" class="btn btn-primary btn-sm doModify" onclick="showModify(${customer.id})" value="修改">

                        <c:if test="${customer.isValid==1}">
                            <input type="button" class="btn btn-danger btn-sm doDisable" value="禁用" onclick="modifyStatus(${customer.id},this)">
                        </c:if>
                        <c:if test="${customer.isValid==0}">
                            <input type="button" class="btn btn-success btn-sm doDisable" value="启用" onclick="modifyStatus(${customer.id},this)">
                        </c:if>
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

<!-- 修改客户信息 start -->
<div class="modal fade" tabindex="-1" id="myModal">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->

        <form id="frmModifyCustomer">
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改客户</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="customer_id" class="col-sm-4 control-label">编号：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="customer_id" name="id" readonly="readonly">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="customerName" class="col-sm-4 control-label">姓名：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="customerName" name="name">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="customerLoginName" class="col-sm-4 control-label">帐号：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="customerLoginName" name="loginName" readonly="readonly">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="customerPhone" class="col-sm-4 control-label">电话：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="customerPhone" name="phone">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="customerAdrees" class="col-sm-4 control-label">地址：</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="customerAdrees" name="address">
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary updateOne" onclick="modifyCustomer()">修改</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
        </form>
    </div>
</div>
<!-- 修改客户信息 end -->
</body>

</html>