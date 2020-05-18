<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="/hui/lib/html5shiv.js"></script>
    <script type="text/javascript" src="/hui/lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="/hui/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="/hui/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="/hui/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="/hui/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="/hui/static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="/hui/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>管理员端</title>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/admin/index">${(yblaas.title)!}</a>
            <a class="logo navbar-logo-m f-l mr-10 visible-xs">${(yblaas.title)!}</a>
            <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>

            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>超级管理员</li>
                    <li class="dropDown dropDown_hover">
                        <a href="#" class="dropDown_A">${adminUser!} <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" onClick="myselfinfo()">修改用户名</a></li>
                            <li><a href="javascript:;" onClick="mypassword()">修改密码</a></li>
                            <li><a href="/admin/exit">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <div class="menu_dropdown bk_2">
        <dl id="menu-admin">
            <dt><i class="Hui-iconfont">&#xe62d;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="/admin/user_xgc" data-title="学工任命" href="javascript:void(0)">学工任命</a></li>
                    <li><a data-href="/admin/user_role" data-title="角色管理" href="javascript:void(0)">角色管理</a></li>
                    <li><a data-href="/admin/user_permission" data-title="权限管理" href="javascript:void(0)">权限管理</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-system">
            <dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="/admin/system_base" data-title="系统设置" href="javascript:void(0)">系统设置</a></li>
                    <li><a data-href="/admin/system_email" data-title="邮件设置" href="javascript:void(0)">邮件设置</a></li>
                    <li><a data-href="/druid/index.html" data-title="Druid监控" href="javascript:void(0)">Druid监控</a></li>
                </ul>
            </dd>
        </dl>

    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active">
                    <span title="我的桌面" data-href="/admin/welcome">我的桌面</span>
                    <em></em></li>
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="/admin/welcome"></iframe>
        </div>
    </div>
</section>

<div class="contextMenu" id="Huiadminmenu">
    <ul>
        <li id="closethis">关闭当前 </li>
        <li id="closeall">关闭全部 </li>
    </ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/hui/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
    /*个人信息*/
    function myselfinfo(){
        layer.open({
            type: 1,
            area: ['300px','200px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '查看信息',
            content: '<div>管理员信息</div>'
        });
    }

    /*
        弹框修改密码
     */
    function mypassword(){
        layer.open({
            type: 1,
            area: ['300px','200px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '修改密码',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>原密码:</td><td><input type="password" name="old_password" placeholder="请输入原来密码" id="old_password" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td>新密码:</td><td><input type="password" name="new_password" placeholder="请输入新密码！" id="new_password" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td>确认密码:</td><td><input type="password" name="new_passwords" placeholder="请再次新密码！" id="new_passwords" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td colspan="2"><input class="btn btn-success radius" type="button" onclick="mypassword_submit()" value="提交更改"></td></tr></table></div>'
        });
    }

    /*
        修改密码方法
     */
    function mypassword_submit(){
        var old_password = $("#old_password").val();
        var new_password = $("#new_password").val();
        var new_passwords = $("#new_passwords").val();
        if(old_password==""){
            layer.msg('请输入原密码!',{icon:5,time:1000});
        }else{
            if(new_password==""){
                layer.msg('请输入新密码!',{icon:5,time:1000});
            }else{
                if(new_password!=new_passwords){
                    layer.msg('两次输入的新密码不一致!',{icon:5,time:1000});
                }else{
                    $.ajax({
                        type: "POST",
                        url: "/admin/change_password",
                        data: {oldPassword:old_password,newPassword:new_password},
                        dataType: "text",
                        success: function(data){
                            if(data=="success"){
                                layer.msg('修改密码成功!',{
                                    icon:1,
                                    time:1000,
                                    end: function () {
                                        window.location.replace("/admin/exit");
                                    }
                                });
                            }else{
                                layer.msg('原密码错误!',{icon:5,time:1000});
                            }
                        },
                        error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
                    });
                }
            }
        }
    }

    /*
        弹框修改用户名
     */
    function myselfinfo(){
        layer.open({
            type: 1,
            area: ['300px','200px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '修改用户名',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>用户名:</td><td><input type="text" placeholder="请输入更改的用户名" id="admin_user" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td>密码:</td><td><input type="password" placeholder="请输入账户密码！" id="user_password" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td colspan="2">注意：用户名也是登陆名，英文和数字组合。</td></tr>'+
                '<tr class="text-c"><td colspan="2"><input class="btn btn-success radius" type="button" onclick="myselfinfo_submit()" value="提交更改"></td></tr></table></div>'
        });
    }

    /*
        修改用户名方法
     */
    function myselfinfo_submit(){
        var admin_user = $("#admin_user").val();
        var user_password = $("#user_password").val();
        var reg =/^[a-zA-Z0-9]{5,20}$/;
        if(!reg.test(admin_user)){
            layer.msg('用户名不符合规则!',{icon:5,time:1000});
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/change_user",
                data: {adminUser:admin_user,userPassword:user_password},
                dataType: "text",
                success: function(data){
                    if(data == "success"){
                        layer.msg('修改用户名成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                window.location.replace("/admin/index");
                            }
                        });
                    }else{
                        layer.msg('密码错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        }
    }
</script>
</body>
</html>