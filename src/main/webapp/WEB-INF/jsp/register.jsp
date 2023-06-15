<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath(); %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
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
        <div  class="message">管理系统</div>
        <div id="darkbannerwrap"></div>

        <form method="post" class="layui-form" action="<%=path%>/registerSub">
            <span style="color: red;">${msg}</span>
            <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <input name="phone" lay-verify="phone" type="text" value="" placeholder="手机号" class="layui-input">
            <hr class="hr15">
            <input value="注册" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
            <div style="text-align: center;text-align: center;font-size: smaller;">已有账号？去<a href="<%=path%>/gologin" >登录</a></div>
           <%-- <input class="layui-input" type="checkbox" name="ckbox">记住我--%>
        </form>
    </div>
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location
        }
    </script>

    <!-- 底部结束 -->
    <div class="footer">
        <div align="center">
           MTY
        </div>
    </div>
</body>
</html>
