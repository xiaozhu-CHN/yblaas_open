<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
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
        <h2 class="weui-msg__title">退出成功</h2>
        <p class="weui-msg__desc">感谢您的使用!</p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="https://www.yiban.cn/" class="weui-btn weui-btn_primary">返回易班首页</a>
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
<script>
    $(function () {
       back_fun();
    });

    /*
     函数名称：browser
     函数作用：判断访问终端
    */
    var browser = {
        versions: function() {
            var u = navigator.userAgent,
                app = navigator.appVersion;
            return {
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, //android终端
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    };

    /*
     函数名称：back
     函数作用：返回app
     */
    function back_fun() {
        if(browser.versions.android) {
            //android 调用方式
            window.local_obj.back();
        }else if(browser.versions.ios) {
            back();
        }else {
            $.toast("该终端类型暂不支持返回易班APP", "forbidden");
        }
    }
</script>
</body>
</html>