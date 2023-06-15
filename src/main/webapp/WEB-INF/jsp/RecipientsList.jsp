<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path = request.getContextPath(); %>
<html>
<head>
    <title>asset</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="icon" href="<%=path%>/images/favicon.ico" sizes="32x32" />
    <link rel="stylesheet" href="<%=path%>/css/font.css">
    <link rel="stylesheet" href="<%=path%>/css/xadmin.css">
    <script type="text/javascript" src="<%=path%>/js/jquery-1.3.2.min.js"></script>
    <script src="<%=path%>/lib/layui/layui.js"></script>
    <script type="text/javascript" src="<%=path%>/js/xadmin.js"></script>
    <script src="<%=path%>/layui_exts/excel.js"></script>
    <style type="text/css">
        .layui-table{
            text-align: center;
        }
        .layui-table th{
            text-align: center;
        }
    </style>
</head>

<body>
<div class="x-nav">
      <span class="layui-breadcrumb">
       <a href="">首页</a>
        <a href="<%=path%>/recipients/findRecipients">资产领用信息查询列表</a>
      </span>
    <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right" href="<%=path%>/recipients/findRecipients" title="刷新">
        <i class="layui-icon" style="line-height:30px">&#xe669;</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so" action="<%=path%>/recipients/findRecipients" >
            <input class="layui-input" type="hidden" name="pageIndex" value="1">
            <input class="layui-input" type="hidden" name="pageSize" value="5">
            <div class="layui-inline layui-show-xs-block">
                <select name="aid" lay-verify="required">
                    <option value="">请选择资产</option>
                    <c:forEach items="${assetsList}" var="assets">
                        <option value="${assets.id}">${assets.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="layui-inline layui-show-xs-block">
                <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
            </div>
        </form>
    </div>
    <xblock>
        <c:if test="${sessionScope.type == '03'}">
        <button id="addRecipientsBtn" class="layui-btn layui-btn-sm"> <i class="layui-icon">&#xe654;</i>申请 </button>
        </c:if>
        <c:if test="${sessionScope.type != '03'}">
            <button class="layui-btn layui-btn-sm"><a style="color: #fff;" href="<%=path%>/recipients/downExcel"> <i class="layui-icon">&#xe641;</i>导出 </a></button>
        </c:if>
        <span class="x-right" style="line-height:40px">共有数据：${pageList.totalCount} 条</span>
    </xblock>


    <%--表格数据--%>
    <table class="layui-table">
        <thead>
        <tr>
            <th>资产</th>
            <th>申请人</th>
            <th>申请时间</th>
            <th>状态</th>
            <th>创建时间</th>
             <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageList.list}" var="di">
            <tr>
                <th>${di.aname}</th>
                <th>${di.uname}</th>
                <th>${di.applyTime}</th>
                <th>
                    <c:if test="${di.status == '01'}">
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal">已申请</button>
                    </c:if>
                    <c:if test="${di.status == '02'}">
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-radius layui-btn-success">已同意</button>
                    </c:if>
                    <c:if test="${di.status == '03'}">
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-radius layui-btn-warm">已拒绝</button>
                    </c:if>
                    <c:if test="${di.status == '04'}">
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-radius layui-btn-warm">已归还</button>
                    </c:if>
                </th>
                <th>${di.createTime}</th>
                <td>
                    <c:if test="${sessionScope.type == '02' && di.status == '01'}">
                        <a title="审批" onclick="shenpi(this,'${di.id}')" href="javascript:;">
                            <i class="layui-icon">&#xe6b2;</i>
                        </a>
                    </c:if>
                    <c:if test="${di.status == '01'}">
                        <a title="查看并编辑" onclick="editRecipientsBtn('${di.id}')" href="#" >
                            <i class="layui-icon">&#xe642;</i>
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.type == '03' && di.status == '02'}">
                        <a title="归还" onclick="guihuan(this,'${di.id}')" href="javascript:;">
                            <i class="layui-icon">&#xe609;</i>
                        </a>
                    </c:if>
                    <a title="删除" onclick="member_del(this,'${di.id}')" href="javascript:;">
                        <i class="layui-icon">&#xe640;</i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="" >
        <input type="hidden" id="totalPageCount" value="${pageList.pageTotalCount}"/>
        <c:import url="pageBtn.jsp">
            <c:param name="totalCount" value="${pageList.totalCount}"/>
            <c:param name="currentPageNo" value="${pageList.pageIndex}"/>
            <c:param name="totalPageCount" value="${pageList.pageTotalCount}"/>
        </c:import>
    </div>

    <%--添加模态框--%>
    <div class="layui-row" id="test" style="display: none;">
        <div class="layui-col-md10">
            <form class="layui-form" id="addRecipientsForm">
                <div style="margin-top: 20px;"></div>
                <div class="layui-form-item">
                    <label class="layui-form-label">资产：</label>
                    <div class="layui-input-block">
                        <select name="aid" lay-verify="required">
                            <c:forEach items="${assetsList}" var="assets">
                                <option value="${assets.id}">${assets.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">申请时间：</label>
                    <div class="layui-input-block">
                        <input type="text" name="applyTime" id="start" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="button" class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo">提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
             </form>
        </div>
    </div>


    <%--更新--%>
    <div class="layui-row" id="edit" style="display: none;">
        <div class="layui-col-md10">
            <form class="layui-form" id="editRecipientsForm">
                <div style="margin-top: 20px;"></div>
                <input type="hidden" id="id" name="id">
                <div class="layui-form-item">
                    <label class="layui-form-label">资产：</label>
                    <div class="layui-input-block">
                        <select name="aid" id="aid" lay-verify="required">
                            <c:forEach items="${assetsList}" var="assets">
                                <option value="${assets.id}">${assets.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">申请时间：</label>
                    <div class="layui-input-block">
                        <input type="text" id="applyTime" name="applyTime" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="button" class="layui-btn layui-btn-normal" lay-submit lay-filter="formEditDemo">提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
        </form>
    </div>
    </div>


<script>
        layui.config({
            base: 'layui_exts/',
        });

        layui.use(['jquery','form','layer','laydate'], function(){
            var form = layui.form,
                $ = layui.jquery,
                laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#start' //指定元素
            });
            laydate.render({
                elem: '#applyTime' //指定元素
            });


            /*添加弹出框*/
            $("#addRecipientsBtn").click(function () {
                layer.open({
                    type:1,
                    title:"申请",
                    skin:"myclass",
                    area:["50%","60%"],
                    anim:2,
                    content:$("#test"),
                    success: function() { }
                });
                $("#addRecipientsForm")[0].reset();
                form.on('submit(formDemo)', function(data) {
                    var param=data.field;
                    $.ajax({
                        url: '<%=path%>/recipients/addRecipients',
                        type: "post",
                        data:JSON.stringify(param),
                        contentType: "application/json; charset=utf-8",
                        success:function(res){
                            if(res == '202'){
                                layer.msg('该资产已领用，请更换！', {icon: 2, time: 3000});
                            }else{
                                layer.msg('领用成功', {icon: 1, time: 3000});
                                setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                            }
                        },
                        error:function(){
                            layer.msg('添加失败',{icon:0,time:3000});
                            setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                        }
                    });
                    // return false;
                });
            });
        });


        /*编辑弹出框*/
        function editRecipientsBtn(id){
            $.ajax({
                url: '<%=path%>/recipients/findRecipientsById?id='+id,
                type: "get",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success:function(res){
                    layui.use(['jquery','form','layer','laydate'], function(){
                        var form = layui.form,$ = layui.jquery;
                        layer.open({
                            type:1,
                            title:"编辑",
                            skin:"myclass",
                            area:["50%","60%"],
                            anim:2,
                            content:$("#edit"),
                            success: function() { }
                        });
                        $("#editRecipientsForm")[0].reset();
                        document.getElementById("id").value = res.id;
                        document.getElementById("aid").value = res.aid;
                        document.getElementById("applyTime").value = res.applyTime;
                        form.on('submit(formEditDemo)', function(data) {
                            var param=data.field;
                            $.ajax({
                                url: '<%=path%>/recipients/updateRecipients',
                                type: "post",
                                data:JSON.stringify(param),
                                contentType: "application/json; charset=utf-8",
                                success:function(res){
                                    if(res == '202'){
                                        layer.msg('该资产已领用，请更换！', {icon: 2, time: 3000});
                                    }else{
                                        layer.msg('修改成功', {icon: 1, time: 3000});
                                        setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                                    }
                                },
                                error:function(){
                                    layer.msg('修改失败',{icon:0,time:3000});
                                    setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                                }
                            });
                        });
                    });
                },
                error:function(){
                    layer.msg('获取失败',{icon:0,time:3000});
                    setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                }
            });
        }


        /*删除*/
        function member_del(obj,id){
            layer.confirm('确认要删除吗？',function(index){
                //发异步删除数据
                $.get("<%=path%>/recipients/deleteRecipients",{"id":id},function (data) {
                    if(data =true){
                        layer.msg('删除成功!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);

                    }else {
                        layer.msg('删除失败!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                    }
                });
            });
        }


        /*审批*/
        function shenpi(obj,id){
            layer.confirm("确定同意审核吗？",{
                btn: ['同意', '拒绝']
            }, function (index) {
                $.ajax({
                    url:"/recipients/shenpi",
                    type:"post",
                    data:{id:id,status:'02'},
                    success:function(data){
                        layer.msg('审核成功!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                    }
                })
            }, function(index){
                $.ajax({
                    url:"/recipients/shenpi",
                    type:"post",
                    data:{id:id,status:'03'},
                    success:function(data){
                        layer.msg('已拒绝!',{icon:2,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                    }
                })
            });
        }


        /*审批*/
        function guihuan(obj,id){
            $.ajax({
                url:"/recipients/shenpi",
                type:"post",
                data:{id:id,status:'04'},
                success:function(data){
                    layer.msg('归还成功!',{icon:1,time:2000});
                    setTimeout(function () {window.location.href='<%=path%>/recipients/findRecipients';},2000);
                }
            })
        }
    </script>
</body>
</html>
