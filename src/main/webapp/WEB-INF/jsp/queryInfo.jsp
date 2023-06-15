<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path = request.getContextPath(); %>
<html>
<head>
    <title>个人信息</title>
    <link rel="icon" href="<%=path%>/images/favicon.ico" sizes="32x32" />
    <link rel="stylesheet" href="<%=path%>/css/font.css">
    <link rel="stylesheet" href="<%=path%>/css/xadmin.css">
    <link rel="stylesheet" href="<%=path%>/css/pg_btn.css">
    <script type="text/javascript" src="<%=path%>/js/jquery-1.3.2.min.js"></script>
    <script src="<%=path%>/lib/layui/layui.js"></script>
    <script type="text/javascript" src="<%=path%>/js/xadmin.js"></script>
</head>
<body>
<div class="x-body">
    <form class="layui-form"  id="f_auto" method="post" >
        <input type="hidden" value="${sessionScope.ad.id}" name="id" id="id"/>

        <div class="layui-form-item">
            <label for="no" class="layui-form-label">
                <span class="">用户编号</span>
            </label>
            <div class="layui-input-inline">
                <input type="text" id="no" name="no" disabled
                       autocomplete="off" value="${sessionScope.ad.no}" class="layui-input">
            </div>
        </div>

        <c:if test="${sessionScope.type == '02' || sessionScope.type == '03'}">
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">姓名</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="name" disabled value="${sessionScope.ad.name}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">性别</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text"  name="sex" disabled
                           autocomplete="off" value="${sessionScope.ad.sex}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">年龄</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="age" disabled value="${sessionScope.ad.age}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">部门</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text"name="dept" disabled value="${sessionScope.ad.dept}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">职务</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="position" disabled value="${sessionScope.ad.position}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">地址</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="address" disabled value="${sessionScope.ad.address}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">电话</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="phone" disabled
                           autocomplete="off" value="${sessionScope.ad.phone}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="">状况</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="situation" disabled value="${sessionScope.ad.situation}" class="layui-input">
                </div>
            </div>
        </c:if>

    </form>
</div>
<script>
    $('#close').click(function() {
        //window.location.href='/findBook';
        return false;
    });
</script>
</body>
</html>