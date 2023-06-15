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
        <a href="<%=path%>/assets/findAssets2">资产信息查询列表</a>
      </span>
    <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right" href="<%=path%>/assets/findAssets2" title="刷新">
        <i class="layui-icon" style="line-height:30px">&#xe669;</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so" action="<%=path%>/assets/findAssets2" >
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
            <button class="layui-btn layui-btn-sm"><a style="color: #fff;" href="<%=path%>/assets/downExcel"> <i class="layui-icon">&#xe641;</i>导出 </a></button>
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
            <th>购买人</th>
            <th>资产类型</th>
            <th>状态</th>
            <th>创建时间</th>
            <c:if test="${sessionScope.type == '02'}">
             <th>操作</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageList.list}" var="di">
            <tr>
                <th>${di.no}</th>
                <th>${di.assetNo}</th>
                <th>${di.name}</th>
                <th>${di.mname}</th>
                <th>${di.assetType}</th>
                <th>${di.status}</th>
                <th>${di.createTime}</th>
                <c:if test="${sessionScope.type == '02'}">
                <td>
                    <a title="查看并编辑" onclick="editAssetsBtn('${di.id}')" href="#" >
                        <i class="layui-icon">&#xe641;</i>
                    </a>
                </td>
                </c:if>
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


    <%--更新--%>
    <div class="layui-row" id="edit" style="display: none;">
        <div class="layui-col-md10">
            <form class="layui-form" id="editAssetsForm">
                <div style="margin-top: 20px;"></div>
                <input type="hidden" id="id" name="id">
                <div class="layui-form-item">
                    <label class="layui-form-label">状态：</label>
                    <div class="layui-input-block">
                        <select name="status" id="status" lay-verify="required">
                            <option value="正常">正常</option>
                            <option value="维修">维修</option>
                            <option value="报废">报废</option>
                            <option value="折旧">折旧</option>
                        </select>
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


        /*弹出框*/
        function editAssetsBtn(id){
            $.ajax({
                url: '<%=path%>/assets/findAssetsById?id='+id,
                type: "get",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success:function(res){
                    layui.use(['jquery','form','layer','laydate'], function(){
                        var form = layui.form,$ = layui.jquery;
                        layer.open({
                            type:1,
                            title:"修改库存",
                            skin:"myclass",
                            area:["40%","50%"],
                            anim:2,
                            content:$("#edit"),
                            success: function() { }
                        });
                        $("#editAssetsForm")[0].reset();
                        document.getElementById("id").value = res.id;
                        document.getElementById("status").value = res.status;
                        form.on('submit(formEditDemo)', function(data) {
                            var param=data.field;
                            $.ajax({
                                url: '<%=path%>/assets/updateAssets',
                                type: "post",
                                data:JSON.stringify(param),
                                contentType: "application/json; charset=utf-8",
                                success:function(){
                                    layer.msg('库存修改成功', {icon: 1, time: 3000});
                                    setTimeout(function () {window.location.href='<%=path%>/assets/findAssets2';},2000);
                                },
                                error:function(){
                                    layer.msg('库存修改失败',{icon:0,time:3000});
                                    setTimeout(function () {window.location.href='<%=path%>/assets/findAssets2';},2000);
                                }
                            });
                        });
                    });
                },
                error:function(){
                    layer.msg('获取失败',{icon:0,time:3000});
                    setTimeout(function () {window.location.href='<%=path%>/assets/findAssets2';},2000);
                }
            });
        }

    </script>
</body>
</html>
