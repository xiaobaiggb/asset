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
        <a href="<%=path%>/acquisition/findAcquisition">资产购置查询列表</a>
      </span>
    <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right" href="<%=path%>/acquisition/findAcquisition" title="刷新">
        <i class="layui-icon" style="line-height:30px">&#xe669;</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so" action="<%=path%>/acquisition/findAcquisition" >
            <input class="layui-input" type="hidden" name="pageIndex" value="1">
            <input class="layui-input" type="hidden" name="pageSize" value="5">
            <div class="layui-inline layui-show-xs-block">
                <input type="text" name="no" placeholder="资产编号"  class="layui-input">
            </div>
            <div class="layui-inline layui-show-xs-block">
                <input type="text" name="name" placeholder="资产名称"  class="layui-input">
            </div>
            <div class="layui-inline layui-show-xs-block">
                <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
            </div>
        </form>
    </div>
    <xblock>
        <c:if test="${sessionScope.type == '02'}">
        <button id="addAcquisitionBtn" class="layui-btn layui-btn-sm"> <i class="layui-icon">&#xe654;</i>购置 </button>
        <button class="layui-btn layui-btn-sm"><a style="color: #fff;" href="<%=path%>/acquisition/downExcel"> <i class="layui-icon">&#xe641;</i>导出 </a></button>
        </c:if>
        <c:if test="${sessionScope.type != '02'}">
            <button class="layui-btn layui-btn-sm"> 查看数据 </button>
        </c:if>
        <span class="x-right" style="line-height:40px">共有数据：${pageList.totalCount} 条</span>
    </xblock>


    <%--表格数据--%>
    <table class="layui-table">
        <thead>
        <tr>
            <th>资产编号</th>
            <th>资产型号</th>
            <th>资产名称</th>
            <th>单价</th>
            <th>购置人</th>
            <th>申请状态</th>
            <th>申请人</th>
            <th>创建时间</th>
             <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageList.list}" var="di">
            <tr>
                <th>${di.no}</th>
                <th>${di.assetNo}</th>
                <th>${di.name}</th>
                <th>${di.money}</th>
                <th>${di.mid}</th>
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
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-radius layui-btn-danger">已撤销</button>
                    </c:if>
                </th>
                <th>${di.mname}</th>
                <th>${di.createTime}</th>
                <td>
                    <c:if test="${sessionScope.type == '04' && di.status == '01'}">
                        <a title="审批" onclick="shenpi(this,'${di.id}')" href="javascript:;">
                            <i class="layui-icon">&#xe6b2;</i>
                        </a>
                    </c:if>
                    <a title="查看并编辑" onclick="editAcquisitionBtn('${di.id}')" href="#" >
                        <i class="layui-icon">&#xe615;</i>
                    </a>
                    <a title="删除" onclick="member_del(this,'${di.id}')" href="javascript:;">
                        <i class="layui-icon">&#xe640;</i>
                    </a>
                    <c:if test="${sessionScope.type == '04'}">
                        <a title="撤销" onclick="shenpi2(this,'${di.id}')" href="javascript:;">
                            <i class="layui-icon">&#x1006;</i>
                        </a>
                    </c:if>
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

    <%--购置模态框--%>
    <div class="layui-row" id="test" style="display: none;">
        <div class="layui-col-md12">
            <form class="layui-form" id="addAcquisitionForm">
                <div style="margin-top: 20px;"></div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">资产编号：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="no" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">资产型号：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="assetNo" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">资产名称：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">单价：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="money" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">生产厂商：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="vendor" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">生产日期：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="productTime" id="start" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">入库时间：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="intoTime" id="start2" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">购置人：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="mid" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">资产类型：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="assetType" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>

                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
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
        <div class="layui-col-md12">
            <form class="layui-form" id="editAcquisitionForm">
                <div style="margin-top: 20px;"></div>
                <input type="hidden" id="id" name="id">
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">资产编号：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="no" id="no" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">资产型号：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="assetNo" id="assetNo" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">资产名称：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" id="name" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">单价：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="money" id="money" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">生产厂商：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="vendor" id="vendor" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">生产日期：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="productTime" id="productTime" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">入库时间：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="intoTime" id="intoTime" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">购置人：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="mid" id="mid" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">资产类型：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="assetType" id="assetType" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;" id="operation">
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
                elem: '#start2' //指定元素
            });

            /*购置弹出框*/
            $("#addAcquisitionBtn").click(function () {
                layer.open({
                    type:1,
                    title:"购置",
                    skin:"myclass",
                    area:["80%","60%"],
                    anim:2,
                    content:$("#test"),
                    success: function() { }
                });
                $("#addAcquisitionForm")[0].reset();
                form.on('submit(formDemo)', function(data) {
                    var param=data.field;
                    $.ajax({
                        url: '<%=path%>/acquisition/addAcquisition',
                        type: "post",
                        data:JSON.stringify(param),
                        contentType: "application/json; charset=utf-8",
                        success:function(){
                            layer.msg('购置成功', {icon: 1, time: 3000});
                            setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);

                        },
                        error:function(){
                            layer.msg('购置失败',{icon:0,time:3000});
                            setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                        }
                    });
                    // return false;
                });
            });
        });


        /*编辑弹出框*/
        function editAcquisitionBtn(id){
            $.ajax({
                url: '<%=path%>/acquisition/findAcquisitionById?id='+id,
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
                            area:["80%","60%"],
                            anim:2,
                            content:$("#edit"),
                            success: function() { }
                        });
                        $("#editAcquisitionForm")[0].reset();
                        document.getElementById("id").value = res.id;
                        document.getElementById("no").value = res.no;
                        document.getElementById("assetNo").value = res.assetNo;
                        document.getElementById("name").value = res.name;
                        document.getElementById("money").value = res.money;
                        document.getElementById("mid").value = res.mid;
                        document.getElementById("vendor").value = res.vendor;
                        document.getElementById("productTime").value = res.productTime;
                        document.getElementById("intoTime").value = res.intoTime;
                        document.getElementById("assetType").value = res.assetType;
                        if(res.status!='01'){
                            document.getElementById('operation').style.display = "none";
                        }
                        form.on('submit(formEditDemo)', function(data) {
                            var param=data.field;
                            $.ajax({
                                url: '<%=path%>/acquisition/updateAcquisition',
                                type: "post",
                                data:JSON.stringify(param),
                                contentType: "application/json; charset=utf-8",
                                success:function(){
                                    layer.msg('修改成功', {icon: 1, time: 3000});
                                    setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                                },
                                error:function(){
                                    layer.msg('修改失败',{icon:0,time:3000});
                                    setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                                }
                            });
                        });
                    });
                },
                error:function(){
                    layer.msg('获取失败',{icon:0,time:3000});
                    setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                }
            });
        }


        /*删除*/
        function member_del(obj,id){
            layer.confirm('确认要删除吗？',function(index){
                //发异步删除数据
                $.get("<%=path%>/acquisition/deleteAcquisition",{"id":id},function (data) {
                    if(data =true){
                        layer.msg('删除成功!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);

                    }else {
                        layer.msg('删除失败!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
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
                    url:"/acquisition/shenpi",
                    type:"post",
                    data:{id:id,status:'02'},
                    success:function(data){
                        layer.msg('审核成功!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                    }
                })
            }, function(index){
                $.ajax({
                    url:"/acquisition/shenpi",
                    type:"post",
                    data:{id:id,status:'03'},
                    success:function(data){
                        layer.msg('已拒绝!',{icon:2,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                    }
                })
            });
        }

        function shenpi2(obj,id){

            layer.confirm('确认要撤销吗？',function(index){
                //发异步删除数据
                $.get("<%=path%>/acquisition/shenpi",{id:id,status:'04'},function (data) {
                    if(data =true){
                        layer.msg('撤销成功!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);

                    }else {
                        layer.msg('撤销失败!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/acquisition/findAcquisition';},2000);
                    }
                });
            });
        }

    </script>
</body>
</html>
