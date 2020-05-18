<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的</title>
    <link href="/light7/css/light7.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/weui/css/weui.min.css">
    <link rel="stylesheet" href="/weui/css/jquery-weui.min.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
</head>
<body>
<div class="page">
    <!-- 标题栏 -->
    <header class="bar bar-nav">
        <a class="icon icon-refresh pull-right external" href="javascript:location.reload()"></a>
        <h1 class="title">欢迎您，${studentName!}</h1>
    </header>
    <!--工具栏 -->
    <nav class="bar bar-tab">
        <a class="tab-item external" href="/student/index">
            <span class="icon icon-me"></span>
            <span class="tab-label">请假</span>
        </a>
        <a class="tab-item external" href="/student/attendance_index">
            <span class="icon icon-clock"></span>
            <span class="tab-label">考勤</span>
        </a>
        <a class="tab-item active" href="/student/my_index">
            <span class="icon icon-menu"></span>
            <span class="tab-label">我的</span>
        </a>
    </nav>
    <div class="content">
        <!-- 这里是页面内容区 -->
        <div class="weui-cells">
            <a class="weui-cell weui-cell_access external" href="/student/my_data">
                <div class="weui-cell__bd">
                    <p>个人信息</p>
                </div>
                <div class="weui-cell__ft">
                    <p>
                        <#if examine ?? &&  examine=="1">待审核</#if>
                        <#if examine ?? &&  examine=="2">审核通过</#if>
                        <#if examine ?? &&  examine=="3">审核未通过</#if>
                    </p>
                </div>
            </a>
        </div>

        <div class="weui-cells__title">消息提醒</div>
        <div class="weui-cells">
<#--            <a class="weui-cell weui-cell_access external" href="/student/my_data_qq">-->
<#--                <div class="weui-cell__bd">-->
<#--                    <p>QQ提醒</p>-->
<#--                </div>-->
<#--                <div class="weui-cell__ft">-->
<#--                    <p>-->
<#--                        ${studentQq!"未绑定"}-->
<#--                    </p>-->
<#--                </div>-->
<#--            </a>-->
            <a class="weui-cell weui-cell_access external" href="/student/my_data_email">
                <div class="weui-cell__bd">
                    <p>邮箱提醒</p>
                </div>
                <div class="weui-cell__ft">
                    <p>
                        ${studentEmail!"未绑定"}
                    </p>
                </div>
            </a>
        </div>
    </div>
</div>
<script>

</script>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>s
<script type="text/javascript" src="/light7/js/light7.min.js"></script>
<!--导入light7中文语言包-->
<script type="text/javascript" src="/light7/js/light7-cn.min.js"></script>
</body>
</html>

