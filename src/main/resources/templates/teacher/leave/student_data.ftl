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

    <title>学生信息</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">学生电话：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.studentTell)!}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">学生邮箱：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.studentEmail)!"未绑定"}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">学生QQ：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.studentQq)!"未绑定"}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">家长姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.parenName)!}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">家长电话：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.parentTell)!}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">地址区域：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.city)!}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">详细地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.address)!}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">邮编：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(student.ems)!}
            </div>
        </div>
    </form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->
</body>
</html>