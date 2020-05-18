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

    <title>个人信息</title>
</head>
<body>
<article class="page-container">
    <div class="text-c">
        ${(leaveTeacherStep.timeChange?string("yyyy-MM-dd HH:mm:ss"))!}：发起假条申请<br/>
        <#if (leaveTeacherStep.fdyName) ??>${(leaveTeacherStep.fdyTime?string("yyyy-MM-dd HH:mm:ss"))!}:辅导员${leaveTeacherStep.fdyName!}审核完成<br/></#if>
        <#if (leaveTeacherStep.xyldName) ??>${(leaveTeacherStep.xyldTime?string("yyyy-MM-dd HH:mm:ss"))!}:学院领导${leaveTeacherStep.xyldName!}审核完成<br/></#if>
        <#if (leaveTeacherStep.xgcName) ??>${(leaveTeacherStep.xgcTime?string("yyyy-MM-dd HH:mm:ss"))!}:学工处${leaveTeacherStep.xgcName!}审核完成<br/></#if>
        <#if (leaveTeacherStep.xjName) ??>${(leaveTeacherStep.xjtTime?string("yyyy-MM-dd HH:mm:ss"))!}:${leaveTeacherStep.xjName!}老师已帮销假<br/></#if>
        <#if (leaveTeacherStep.state) ?? &&  (leaveTeacherStep.state)=="7">假条流程结束</#if>
        <#if (leaveTeacherStep.state) ?? &&  (leaveTeacherStep.state)=="10">学生取消申请请假</#if>
    </div>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->
</body>
</html>