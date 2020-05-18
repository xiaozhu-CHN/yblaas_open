<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>取消成功</title>
    <link rel="stylesheet" href="/weui/css/weui.min.css">
    <link rel="stylesheet" href="/weui/css/jquery-weui.min.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">取消成功</h2>
        <p class="weui-msg__desc">您的假条申请已经成功取消。</p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="/student/index" class="weui-btn weui-btn_primary external">返回请假首页</a>
        </p>
    </div>
    <div class="weui-msg__extra-area">
        <div class="weui-footer">
            <p class="weui-footer__links">
                <span  class="weui-footer__link">${(yblaas.title)!}</span>
            </p>
            <p class="weui-footer__text">
                Copyright © ${(yblaas.copyright)!} </br>
                <#if (yblaas.ba) ??><a href="http://www.beian.miit.gov.cn/">${(yblaas.ba)!}</a></#if>
            </p>
        </div>
    </div>
</div>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>
</body>
</html>