<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
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
    <link href="/hui/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="/hui/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="/hui/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <link href="/hui/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="/hui/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>管理员登陆</title>
</head>
<body>
<div class="header2"></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <div class="form form-horizontal">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="adminUser" type="text" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="adminPassword" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
               <div class="formControls col-xs-8 col-xs-offset-3">
                 <input class="input-text size-L" id="verification" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
                 <img src="/public/admin_verification" id="image" onclick="getVerify(this);"> <a href="javascript:void(0);" onclick="getVerify2()">看不清，换一张</a>
               </div>
             </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input id="submit_admin" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">

                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">Copyright © ${(yblaas.copyright)!}</div>
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script>
    $("#submit_admin").click(function(){
        var name=$("#adminUser").val();
        var pwd=$("#adminPassword").val();
        var verification = $("#verification").val();
        console.log(verification);
        if(name == ""){
            layer.msg('请输入账号',{icon:5,time:1000});
        }else if(pwd == ""){
            layer.msg('请输入密码',{icon:5,time:1000});
        }else if(verification == ""||verification == "验证码:") {
            layer.msg('请输入验证码',{icon:5,time:1000});
        }else {
                $.ajax({
                    type: "POST",
                    url: "/public/admin_login_ajax",
                    data: {adminUser:name,adminPassword:pwd,verification:verification},
                    dataType: "text",
                    success: function(data){
                        if(data=="success"){
                            window.location.replace("/admin/index");
                            //alert("登陆成功！")
                        }else if(data == "error1") {
                            layer.msg('验证码错误',{icon:5,time:1000});
                        }else {
                            layer.msg('账号或者密码错误',{icon:5,time:1000});
                        }
                    },
                    error:function() { layer.msg('服务器错误',{icon:5,time:1000});}
                });
            }
    });

    //获取验证码1
    function getVerify(obj){
        obj.src = "/public/admin_verification?"+Math.random();
    }

    //获取验证码2
    function getVerify2(){
        $("#image").trigger("click");
    }
</script>
</body>
</html>