<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta http-equiv="refresh" content="60">
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
    <title>我的桌面</title>
</head>
<body>
<div class="page-container">
    <p class="f-20 text-success">欢迎使用${(yblaas.title)!}！</p>
    <p>欢迎您，${teacherName!}老师 </p>
    <p>当前时间为：<span class="time"></span></p>
    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <th colspan="7" scope="col">代办事项</th>
        </tr>
        <tr class="text-c">
            <th>事项</th>
            <th>数量</th>
        </tr>
        </thead>
        <tbody>
        <tr class="text-c">
            <td>假条待审核</td>
            <td>${leaveDsh!}</td>
        </tr>
        <#if studentDsh ??>
            <tr class="text-c">
                <td>学生信息待审核</td>
                <td>${studentDsh!}</td>
            </tr>
        </#if>

        </tbody>
    </table>
</div>
<footer class="footer mt-20">
    <div class="container">
        <p>感谢H-ui前端框架、Spring Boot、Apache Shiro、Spring、Mybatis、Druid.<br>
            Copyright &copy; ${(yblaas.copyright)!}<br>
            <#if (yblaas.ba) ??><a href="http://www.beian.miit.gov.cn/">${(yblaas.ba)!}</a></#if>
        </p>
    </div>
</footer>
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script>
    function getDate() {
        var today = new Date();
        var date = today.getFullYear() + "-" + twoDigits(today.getMonth() + 1) + "-" + twoDigits(today.getDate()) + " ";
        var week = " 星期" + "日一二三四五六 ".charAt(today.getDay());
        var time = twoDigits(today.getHours()) + ": " + twoDigits(today.getMinutes()) + ": " + twoDigits(today.getSeconds());
        $(".time").html(date +" "+time+" "+week);
    }
    function twoDigits(val) {
        if (val < 10) return "0" + val; return val;
    }
    $(function () {
        setInterval(getDate, 1000);
    });
</script>
</body>
</html>