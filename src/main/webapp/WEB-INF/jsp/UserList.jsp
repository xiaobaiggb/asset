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
        <a href="<%=path%>/user/findUser">员工查询列表</a>
      </span>
    <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right" href="<%=path%>/user/findUser" title="刷新">
        <i class="layui-icon" style="line-height:30px">&#xe669;</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so" action="<%=path%>/user/findUser" >
            <input class="layui-input" type="hidden" name="pageIndex" value="1">
            <input class="layui-input" type="hidden" name="pageSize" value="5">
            <div class="layui-inline layui-show-xs-block">
                <input type="text" name="no" placeholder="员工编号"  class="layui-input">
            </div>
            <div class="layui-inline layui-show-xs-block">
                <input type="text" name="name" placeholder="姓名"  class="layui-input">
            </div>
            <div class="layui-inline layui-show-xs-block">
                <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
            </div>
        </form>
    </div>
    <xblock>
        <button id="addUserBtn" class="layui-btn layui-btn-sm"> <i class="layui-icon">&#xe654;</i>添加 </button>
        <span class="x-right" style="line-height:40px">共有数据：${pageList.totalCount} 条</span>
    </xblock>


    <%--表格数据--%>
    <table class="layui-table">
        <thead>
        <tr>
            <th>员工编号</th>
            <th>头像</th><%--新增--%>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>密码</th>
            <th>联系方式</th>
            <th>状况</th>
            <th>创建时间</th>
             <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageList.list}" var="di">
            <tr>
                <th>${di.no}</th>
                <th>
                    <img style="width: auto; height: 60px; border: none; cursor: pointer" src="${di.image}"/>
                </th><%--新增--%>
                <th>${di.name}</th>
                <th>${di.sex}</th>
                <th>${di.age}</th>
                <th>******</th>
                <th>${di.phone}</th>
                <th>${di.situation}</th>
                <th>${di.createTime}</th>
                <td>
                    <a title="查看并编辑" onclick="editUserBtn('${di.id}')" href="#" >
                        <i class="layui-icon">&#xe642;</i>
                    </a>
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
        <div class="layui-col-md12">
            <form class="layui-form" id="addUserForm">
                <div style="margin-top: 20px;"></div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">员工编号：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="no" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">姓名：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">性别：</label>
                    <div class="layui-input-inline">
                        <select name="sex"  lay-verify="required">
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">年龄：</label>
                    <div class="layui-input-inline">
                        <input type="number" name="age" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">所属部门：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dept" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">职务：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="position" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">地址：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="address" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">联系方式：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" lay-verify="required|phone" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">密码：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="password" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">状况：</label>
                    <div class="layui-input-inline">
                        <select name="situation"  lay-verify="required">
                            <option value="在职">在职</option>
                            <option value="休假">休假</option>
                            <option value="离职">离职</option>
                        </select>
                    </div>

                    <%--新增图片上传1--%>
                    <label class="layui-form-label">员工头像：</label>
                    <div class="layui-input-inline">
                        <button type="button" class="layui-btn" id="ID-upload-demo-btn">
                            <i class="layui-icon layui-icon-upload"></i> 图片上传
                        </button>
                        <div style="width: 132px;">
                            <div class="layui-upload-list">
                                <%--关键，用隐藏的文本框获取img标签的url--%>
                                <input type="hidden" id="img_url" name="image" value="" lay-verify="required" class="layui-input" placeholder=""/>
                                <img class="layui-upload-img" id="ID-upload-demo-img" style="width: 100%; height: 92px;" >
                                <div id="ID-upload-demo-text"></div>
                            </div>
                            <div class="layui-progress layui-progress-big" lay-showPercent="yes" lay-filter="filter-demo">
                                <div class="layui-progress-bar" lay-percent="0%"></div>
                            </div>
                        </div>
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
        <div class="layui-col-md12">
            <form class="layui-form" id="editUserForm">
                <div style="margin-top: 20px;"></div>
                <input type="hidden" id="id" name="id">
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">员工编号：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="no" id="no" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">姓名：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" id="name" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">性别：</label>
                    <div class="layui-input-inline">
                        <select name="sex" id="sex"  lay-verify="required">
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">年龄：</label>
                    <div class="layui-input-inline">
                        <input type="number" name="age" id="age" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">所属部门：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dept" id="dept" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">职务：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="position" id="position" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">地址：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="address" id="address" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">联系方式：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" id="phone" lay-verify="required|phone" class="layui-input" placeholder="">
                    </div>
                    <label class="layui-form-label">密码：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="password" id="password" lay-verify="required" class="layui-input" placeholder="">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-top: 15px;margin-left: 30px;">
                    <label class="layui-form-label">状况：</label>
                    <div class="layui-input-inline">
                        <select name="situation" id="situation"  lay-verify="required">
                            <option value="在职">在职</option>
                            <option value="休假">休假</option>
                            <option value="离职">离职</option>
                        </select>
                    </div>

                    <%--新增图片上传2--%>
                    <label class="layui-form-label">员工头像：</label>
                    <div class="layui-input-inline">
                        <button type="button" class="layui-btn" id="ID-upload-demo-btn1">
                            <i class="layui-icon layui-icon-upload"></i> 图片上传
                        </button>
                        <div style="width: 132px;">
                            <div class="layui-upload-list">
                                <%--关键，用隐藏的文本框获取img标签的url--%>
                                <input type="hidden" id="img_url1" name="image" value="" lay-verify="required" class="layui-input" placeholder=""/>
                                <img class="layui-upload-img" id="ID-upload-demo-img1" style="width: 100%; height: 92px;" >
                                <div id="ID-upload-demo-text1"></div>
                            </div>
                            <div class="layui-progress layui-progress-big" lay-showPercent="yes" lay-filter="filter-demo">
                                <div class="layui-progress-bar" lay-percent="0%"></div>
                            </div>
                        </div>
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

        layui.use(['jquery','form','layer','laydate','upload','element'], function(){
            var form = layui.form,
                $ = layui.$,
                laydate = layui.laydate,
                upload = layui.upload,<%--新增--%>
                layer = layui.layer,<%--新增--%>
                element = layui.element;<%--新增--%>

            //执行一个laydate实例
            laydate.render({
                elem: '#start' //指定元素
            });

            /*添加弹出框*/
            $("#addUserBtn").click(function () {
                layer.open({
                    type:1,
                    title:"添加",
                    skin:"myclass",
                    area:["80%","80%"],
                    anim:2,
                    content:$("#test"),
                    success: function() { }
                });
                $("#addUserForm")[0].reset();
                form.on('submit(formDemo)', function(data) {
                    var param=data.field;
                    $.ajax({
                        url: '<%=path%>/user/addUser',
                        type: "post",
                        data:JSON.stringify(param),
                        contentType: "application/json; charset=utf-8",
                        success:function(res){
                            if(res == '202'){
                                layer.msg('员工编号重复，请重试！', {icon: 2, time: 3000});
                            }else{
                                layer.msg('添加成功', {icon: 1, time: 3000});
                                setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);
                            }
                        },
                        error:function(){
                            layer.msg('添加失败',{icon:0,time:3000});
                            setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);
                        }
                    });
                    // return false;
                });
            });

            <%--新增文件上传功能--%>
            var uploadInst = upload.render({
                elem: '#ID-upload-demo-btn',
                url: '<%=path%>/user/upload', // 此处用的是第三方的 http 请求演示，实际使用时改成您自己的上传接口即可。
                before: function(obj){
                    // 预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#ID-upload-demo-img').attr('src', result); // 图片链接（base64）
                    });

                    element.progress('filter-demo', '0%'); // 进度条复位
                    layer.msg('上传中', {icon: 16, time: 0});//0->1
                },
                done: function(res){
                    // 若上传失败
                    if(res.code != 1){
                        element.progress('filter-demo', '0%'); // 进度条复位
                        return layer.msg('上传失败');
                    }
                    // 上传成功的一些操作
                    if(res.code == 1){
                        element.progress('filter-demo', '100%'); // 进度条复位
                        document.getElementById("img_url").value = res.data;
                        return layer.msg('上传成功');
                    }
                    $('#ID-upload-demo-text').html(''); // 置空上传失败的状态
                },
                error: function(){
                    // 演示失败状态，并实现重传
                    var demoText = $('#ID-upload-demo-text');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function(){
                        uploadInst.upload();
                    });
                },
                // 进度条
                progress: function(n, elem, e){
                    element.progress('filter-demo', n + '%'); // 可配合 layui 进度条元素使用
                    if(n == 100){
                        layer.msg('上传完毕', {icon: 1});
                    }
                }
            });

            var uploadInst1 = upload.render({
                elem: '#ID-upload-demo-btn1',
                url: '<%=path%>/user/upload', // 此处用的是第三方的 http 请求演示，实际使用时改成您自己的上传接口即可。
                before: function(obj){
                    // 预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#ID-upload-demo-img1').attr('src', result); // 图片链接（base64）
                    });

                    element.progress('filter-demo', '0%'); // 进度条复位
                    layer.msg('上传中', {icon: 16, time: 0});//0->1
                },
                done: function(res){
                    // 若上传失败
                    if(res.code != 1){
                        element.progress('filter-demo', '0%'); // 进度条复位
                        return layer.msg('上传失败');
                    }
                    // 上传成功的一些操作
                    if(res.code == 1){
                        element.progress('filter-demo', '100%'); // 进度条复位
                        document.getElementById("img_url1").value = res.data;
                        return layer.msg('上传成功');
                    }
                    $('#ID-upload-demo-text1').html(''); // 置空上传失败的状态
                },
                error: function(){
                    // 演示失败状态，并实现重传
                    var demoText = $('#ID-upload-demo-text1');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function(){
                        uploadInst1.upload();
                    });
                },
                // 进度条
                progress: function(n, elem, e){
                    element.progress('filter-demo', n + '%'); // 可配合 layui 进度条元素使用
                    if(n == 100){
                        layer.msg('上传完毕', {icon: 1});
                    }
                }
            });

        });


        /*编辑弹出框*/
        function editUserBtn(id){
            $.ajax({
                url: '<%=path%>/user/findUserById?id='+id,
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
                            area:["80%","80%"],
                            anim:2,
                            content:$("#edit"),
                            success: function() { }
                        });
                        $("#editUserForm")[0].reset();
                        document.getElementById("id").value = res.id;
                        document.getElementById("no").value = res.no;
                        document.getElementById("name").value = res.name;
                        document.getElementById("sex").value = res.sex;
                        document.getElementById("age").value = res.age;
                        document.getElementById("password").value = res.password;
                        document.getElementById("dept").value = res.dept;
                        document.getElementById("position").value = res.position;
                        document.getElementById("address").value = res.address;
                        document.getElementById("phone").value = res.phone;
                        document.getElementById("situation").value = res.situation;
                        // document.getElementById("image1").value = res.image;
                        form.on('submit(formEditDemo)', function(data) {
                            var param=data.field;
                            $.ajax({
                                url: '<%=path%>/user/updateUser',
                                type: "post",
                                data:JSON.stringify(param),
                                contentType: "application/json; charset=utf-8",
                                success:function(res){
                                    if(res == '202'){
                                        layer.msg('员工编号重复，请重试！', {icon: 2, time: 3000});
                                    }else{
                                        layer.msg('修改成功', {icon: 1, time: 3000});
                                        setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);
                                    }
                                },
                                error:function(){
                                    layer.msg('修改失败',{icon:0,time:3000});
                                    setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);
                                }
                            });
                        });
                    });
                },
                error:function(){
                    layer.msg('获取失败',{icon:0,time:3000});
                    setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);
                }
            });
        }



        /*删除*/
        function member_del(obj,id){
            layer.confirm('确认要删除吗？',function(index){
                //发异步删除数据
                $.get("<%=path%>/user/deleteUser",{"id":id},function (data) {
                    if(data =true){
                        layer.msg('删除成功!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);

                    }else {
                        layer.msg('删除失败!',{icon:1,time:2000});
                        setTimeout(function () {window.location.href='<%=path%>/user/findUser';},2000);
                    }
                });
            });
        }

    </script>
</body>
</html>
