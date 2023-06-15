<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath(); %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="<%=path%>/HTmoban/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="<%=path%>/HTmoban/css/font.css">
    <link rel="stylesheet" href="<%=path%>/HTmoban/css/xadmin.css">
    <script src="<%=path%>/js/jquery-1.11.2.min.js"></script>
    <script src="<%=path%>/HTmoban/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=path%>/HTmoban/js/xadmin.js"></script>

</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div  class="message">资产管理系统</div>
    <div id="darkbannerwrap"></div>

    <form method="post" class="layui-form" action="<%=path%>/login">
        <span style="color: red;">${msg}</span>
        <input name="no" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
        <hr class="hr15">
        <div>
            <select id="type" name="type">
                <option value="01">管理员</option>
                <option value="04">资产管理领导</option>
                <option value="02">资产管理员</option>
                <option value="03">员工</option>
            </select>
        </div>
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>
<script>
    if (window.top !== window.self) {
        window.top.location = window.location
    }
</script>

</body>
</html>
