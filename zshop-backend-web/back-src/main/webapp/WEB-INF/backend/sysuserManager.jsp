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

                    return "${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum=" + page;

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
            $('#frmAddSysuser').bootstrapValidator(
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
                                        message: '用户姓名不能为空'
                                    }
                                }
                            },
                            loginName:{
                                validators: {
                                    notEmpty: {
                                        message: '登录账户不能为空'
                                    },
                                    remote:{
                                        url:'${pageContext.request.contextPath}/backend/sysuser/checkLoginName'
                                    }
                                }
                            },
                            password:{
                                validators: {
                                    notEmpty: {
                                        message: '密码不能为空'
                                    }
                                }
                            },
                            phone:{
                                validators: {
                                    notEmpty: {
                                        message: '电话号码不能为空'
                                    }
                                }
                            },
                            email:{
                                validators: {
                                    notEmpty: {
                                        message: '邮箱不能为空'
                                    },
                                    emailAddress:{
                                        message:'邮箱格式不正确'
                                    }
                                }
                            },
                            roleId:{
                                validators: {
                                    notEmpty: {
                                        message: '请选择角色'
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
                                        message: '用户姓名不能为空'
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
                            email:{
                                validators: {
                                    notEmpty: {
                                        message: '联系邮箱不能为空'
                                    },
                                    emailAddress:{
                                        message:'邮箱格式不正确'
                                    }
                                }
                            },
                            roleId:{
                                validators: {
                                    notEmpty: {
                                        message: '请选择角色'
                                    }
                                }
                            }
                        }
                    }
            );

        });


        //添加系统用户
        function addUser(){

            let bv=$('#frmAddSysuser').data('bootstrapValidator');
            bv.validate();

            if(bv.isValid()){

                //alert($('#frmAddSysuser').serialize());
                $.post(
                    '${pageContext.request.contextPath}/backend/sysuser/add',
                        $('#frmAddSysuser').serialize(),
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
                                            location.href='${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum='+${data.pageNum};
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
        function showModifySysuser(id){

            //alert(id);
            $.post(
                    '${pageContext.request.contextPath}/backend/sysuser/showModifySysuser',
                    {"id":id},
                    function(result){
                        //console.log(result);
                        $('#MargerStaffId').val(result.data.id);
                        $('#MargerStaffname').val(result.data.name);
                        $('#MargerLoginName').val(result.data.loginName);
                        $('#MargerPhone').val(result.data.phone);
                        $('#MargerAdrees').val(result.data.email);
                        $('#MargerRole').val(result.data.role.id);
                    }
            );
        }

        function modifySysuser(){

            //alert(1);
            //alert()
            $.post(
                    '${pageContext.request.contextPath}/backend/sysuser/modifySysuser',
                    $('#frmModifySysuser').serialize(),
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
                                        location.href='${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum='+${data.pageNum};
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

        //修改系统用户状态
        function modifyStatus(id,btn){

            //alert(id);
            $.post(
                    "${pageContext.request.contextPath}/backend/sysuser/modifyStatus",
                    {"id":id},
                    function(result){
                        if(result.status==1){
                            let $td=$(btn).parent().parent().children().eq(5);
                            if($td.text().trim() == '有效'){
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

        //查询函数
        function doQuery(){
            //将当前页重置
            $('#pageNum').val(1);
            //提交查询表单
            $('#frmQuery').submit();
        }

    </script>


</head>

<body>
<!-- 系统用户管理 -->
<div class="panel panel-default" id="adminSet">
    <div class="panel-heading">
        <h3 class="panel-title">系统用户管理</h3>
    </div>
    <div class="panel-body">
        <div class="showmargersearch" >
            <form class="form-inline" action="${pageContext.request.contextPath}/backend/sysuser/findByParam" method="post" id="frmQuery">

                <input type="hidden" name="pageNum" id="pageNum" value="${data.pageNum}">
                <div class="form-group">
                    <label for="userName">姓名:</label>
                    <input type="text" class="form-control" id="userName" name="name" value="${sysuserParam.name}" placeholder="请输入姓名">
                </div>
                <div class="form-group">
                    <label for="loginName">帐号:</label>
                    <input type="text" class="form-control" id="loginName" name="loginName" value="${sysuserParam.loginName}" placeholder="请输入帐号">
                </div>
                <div class="form-group">
                    <label for="phone">电话:</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="${sysuserParam.phone}" placeholder="请输入电话">
                </div>
                <div class="form-group">
                    <label for="role">角色</label>
                    <select class="form-control" name="roleId" id="role">
                        <option value="-1">全部</option>
                        <c:forEach items="${roles}" var="role">
                            <option value="${role.id}" <c:if test="${role.id==sysuserParam.roleId}">selected</c:if>  >${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status">状态</label>
                    <select class="form-control" name="isValid" id="status">
                        <option value="-1">全部</option>
                        <option value="1" <c:if test="${sysuserParam.isValid==1}">selected</c:if>>---有效---</option>
                        <option value="0" <c:if test="${sysuserParam.isValid==0}">selected</c:if> >---无效---</option>
                    </select>
                </div>
                <input type="button" value="查询" class="btn btn-primary" id="doSearch" onclick="doQuery()">
            </form>
        </div>
        <br>
        <input type="button" value="添加系统用户" class="btn btn-primary" id="doAddManger">
        <div class="show-list text-center" style="position: relative; top: 10px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">帐号</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">邮箱</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">注册时间</th>
                    <th class="text-center">角色</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${data.list}" var="sysuser">
                <tr>
                    <td>${sysuser.id}</td>
                    <td>${sysuser.name}</td>
                    <td>${sysuser.loginName}</td>
                    <td>${sysuser.phone}</td>
                    <td>${sysuser.email}</td>
                    <td>
                        <c:if test="${sysuser.isValid==1}">有效</c:if>
                        <c:if test="${sysuser.isValid==0}">无效</c:if>

                    </td>
                    <td>${sysuser.date}</td>
                    <td>${sysuser.role.roleName}</td>
                    <td class="text-center">
                        <input type="button" class="btn btn-primary btn-sm doMangerModify" onclick="showModifySysuser(${sysuser.id})" value="修改">
                        <c:if test="${sysuser.isValid==1}">
                        <input type="button" class="btn btn-danger btn-sm doMangerDisable" onclick="modifyStatus(${sysuser.id},this)" value="禁用">
                        </c:if>
                        <c:if test="${sysuser.isValid==0}">
                        <input type="button" class="btn btn-success btn-sm doMangerDisable" onclick="modifyStatus(${sysuser.id},this)" value="启用">
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

<!-- 添加系统用户 start -->
<div class="modal fade" tabindex="-1" id="myMangerUser">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <form id="frmAddSysuser">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">添加系统用户</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="marger-username" class="col-sm-4 control-label">用户姓名：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="marger-username" name="name">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="marger-loginName" class="col-sm-4 control-label">登录帐号：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="marger-loginName" name="loginName">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="marger-password" class="col-sm-4 control-label">登录密码：</label>
                    <div class="col-sm-4">
                        <input type="password" class="form-control" id="marger-password" name="password">
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
                <div class="row text-right">
                    <label for="marger-email" class="col-sm-4 control-label">联系邮箱：</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="marger-email" name="email">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="role" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>
                    <div class=" col-sm-4">
                        <select class="form-control" name="roleId">
                            <option value="">请选择</option>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary " type="button" onclick="addUser()">添加</button>
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
        <form id="frmModifySysuser">

        <input type="hidden" name="pageNum" value="${data.pageNum}">
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">系统用户修改</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="MargerStaffId" class="col-sm-4 control-label">用户编号：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerStaffId" name="id" readonly="readonly" >
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerStaffname" class="col-sm-4 control-label">用户姓名：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerStaffname" name="name">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerLoginName" class="col-sm-4 control-label">登录帐号：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerLoginName" name="loginName" readonly="readonly">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerPhone" class="col-sm-4 control-label">联系电话：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerPhone" name="phone">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerAdrees" class="col-sm-4 control-label">联系邮箱：</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="MargerAdrees" name="email">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerRole" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>
                    <div class=" col-sm-4">
                        <select class="form-control" id="MargerRole" name="roleId">
                            <option value="">请选择</option>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary " type="button" onclick="modifySysuser()">修改</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
        </form>
    </div>
</div>
<!-- 修改系统用户 end -->

</body>

</html>
