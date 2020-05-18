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
    <title>系统设置</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span>
    <i class="Hui-iconfont">&#xe62e;</i>
    系统管理
    <span class="c-gray en">&gt;</span>
    系统设置
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    <form class="form form-horizontal" id="form_setting">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <h4>基本设置：</h4></label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                网站名称：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yblaasTitle" placeholder="请输入网站名称" value="${(dbConfig.yblaasTitle)!}" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                底部版权信息：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yblaasCopyright" value="${(dbConfig.yblaasCopyright)!}" placeholder="&copy; 2016 H-ui.net" value="" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">备案号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yblaasBa" placeholder="桂ICP备00000000号" value="${(dbConfig.yblaasBa)!}" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <h4>易班设置：</h4></label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                易班AppID：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yibanAppId" placeholder="请输入易班轻应用的AppID" value="${(dbConfig.yibanAppId)!}" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                易班AppSecret：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yibanAppSecret" placeholder="请输入易班轻应用的AppSecret" value="${(dbConfig.yibanAppSecret)!}" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                易班站内地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yibanUrl" placeholder="请输入易班轻应用的站内地址" value="${(dbConfig.yibanUrl)!}" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                本校易班id：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="yibanThis" placeholder="为空默认不拦截非本校学生" value="${(dbConfig.yibanThis)!}" class="input-text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                是否具有校级权限：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="radio-box">
                    <input type="radio" value="true" name="yibanSchool" <#if dbConfig.yibanSchool?? && dbConfig.yibanSchool=="true">checked</#if>>
                    <label for="radio-1">是</label>
                </div>
                <div class="radio-box">
                    <input type="radio" value="false" name="yibanSchool" <#if dbConfig.yibanSchool?? && dbConfig.yibanSchool=="false">checked</#if>>
                    <label for="radio-2">否</label>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <h4>请假设置：</h4></label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                学院领导审核天数：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" placeholder="请假天数达到多少需要二级审批,无需请填0"  class="input-text" value="${(dbConfig.leaveXyld)!}" name="leaveXyld">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                学工处审核天数：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" placeholder="请假天数达到多少需要三级审批,无需请填0" value="${(dbConfig.leaveXgc)!}" class="input-text" name="leaveXgc">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <h4>考勤设置：</h4></label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                考勤精度：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="25" value="${(dbConfig.attendanceAccuracy)!}" name="attendanceAccuracy" >
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <button onClick="removeIframe();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
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
    /* 表单校验和提交*/
    $("#form_setting").validate({
        rules:{
            yblaasTitle:{
                required:true,
                minlength:2,
                maxlength:20,
            },
            yblaasCopyright:{
                required:true,
                minlength:2,
                maxlength:50,
            },
            yibanAppId:{
                required:true,
                minlength:10,
                maxlength:50,
            },
            yibanAppSecret:{
                required:true,
                minlength:10,
                maxlength:50,
            },
            yibanUrl:{
                required:true,
                minlength:10,
                maxlength:50,
            },
            yibanThis:{
                digits:true,
            },
            leaveXyld:{
                required: true,
                digits:true,
            },
            leaveXgc:{
                required: true,
                digits:true,
            },
            attendanceAccuracy:{
                required: true,
                digits:true,
            },
        },
        submitHandler:function(form){
            layer.confirm('确认要保存吗？',function(){
                $(form).ajaxSubmit({
                    type: 'post',
                    url: "/admin/system_base_ajax" ,
                    dataTyep:"text",
                    async: false,
                    success: function(data){
                        if(data=="success"){
                            layer.msg("保存成功！",{icon:1,time:1000});
                        }else{
                            layer.msg('保存失败!',{icon:5,time:1000});
                        }
                    },
                    error: function(XmlHttpRequest, textStatus, errorThrown){
                        layer.msg('服务器错误!',{icon:5,time:1000});
                    }
                });
            });
        }
    });

</script>
</body>
</html>
