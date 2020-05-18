<!--_meta 作为公共模版分离出去-->
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
    <!--/meta 作为公共模版分离出去-->
    <title>邮箱设置</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span>
    <i class="Hui-iconfont">&#xe62e;</i>
    系统管理
    <span class="c-gray en">&gt;</span>
    邮箱设置
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    <form class="form form-horizontal" id="form_setting">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                是否开启邮件：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="radio-box">
                    <input type="radio" value="true" name="email" <#if email.email?? && email.email=="true">checked</#if>>
                    <label for="radio-1">是</label>
                </div>
                <div class="radio-box">
                    <input type="radio" value="false" name="email" <#if email.email?? && email.email=="false">checked</#if>>
                    <label for="radio-2">否</label>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">发信昵称：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text"  class="input-text" name="emailCall" placeholder="请输入发信的昵称" value="${(email.emailCall)!}">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">SMTP服务器：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" id="" value="${(email.emailHost)!}" placeholder="请输入SMTP服务器" class="input-text" name="emailHost">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">SMTP端口：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(email.emailPort)!}" placeholder="请输入SMTP端口" name="emailPort" >
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">邮箱帐号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="emailName" name="emailName" placeholder="请输入邮件账号" value="${(email.emailName)!}">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">邮箱密码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="password" value="${(email.emailPassword)!}" class="input-text" placeholder="请输入邮箱密码" name="emailPassword">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <button onClick="removeIframe();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
    <br>
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">邮件测试：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="sendEmail" placeholder="请输入收信邮箱">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">注意：</label>
            <div class="formControls col-xs-8 col-sm-9">
                请先保存邮箱设置成功后再进行邮件测试。
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button class="btn btn-success radius"  onClick="demoEmail();">发送</button>
            </div>
        </div>
    </div>

</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/hui/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
    //是否开启邮件
    var emailSetting = "${(email.email)!}";

    /* 表单校验和提交*/
    $("#form_setting").validate({
        rules:{
            email:{
                required:true,
            }
        },
        submitHandler:function(form){
            layer.confirm('确认要保存吗？',function(){
                $(form).ajaxSubmit({
                    type: 'post',
                    url: "/admin/system_email_ajax" ,
                    dataTyep:"text",
                    async: false,
                    success: function(data){
                        emailSetting = $("input:radio:checked").val();
                        if(data=="success"){
                            layer.msg("保存成功！",{icon:1,time:1000});
                        }else{
                            layer.msg('保存失败!',{icon:5,time:1000});
                        }
                    },
                    error: function(){
                        layer.msg('服务器错误!',{icon:5,time:1000});
                    }
                });
            });
        }
    });

    //邮件测试发送
    function demoEmail() {
        var sendEmail = $("#sendEmail").val();
        if(emailSetting == "false"){
            layer.msg('请先开启邮件功能!',{icon:5,time:1000});
        }else if(sendEmail == null || sendEmail == ""){
            layer.msg('请输入收信邮箱!',{icon:5,time:1000});
        }else{
            $.ajax({
                type: 'post',
                url: "/admin/system_send_email_ajax" ,
                data:{sendEmail:sendEmail},
                dataTyep:"text",
                success: function(data){
                    if(data=="success"){
                        layer.msg("发送成功！",{icon:1,time:1000});
                    }else{
                        layer.msg('发送失败!',{icon:5,time:1000});
                    }
                },
                error: function(){
                    layer.msg('服务器错误!',{icon:5,time:1000});
                }
            });
        }
    }
</script>
</body>
</html>
