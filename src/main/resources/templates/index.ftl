<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>易班请假与考勤系统</title>
    <link rel="stylesheet" href="/weui/css/weui.min.css">
    <link rel="stylesheet" href="/weui/css/jquery-weui.min.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-waiting weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">请稍后</h2>
        <p class="weui-msg__desc">即将为您转跳。</p>
    </div>
</div>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>
<script>
    $(function () {
        $.ajax({
            type: "POST",
            url: "/public/yiban_url",
            dataType: "text",
            success: function(data){
                if(data===null || data == ""){
                    $.toast("未配置站内地址", "forbidden");
                }else{
                    window.location.replace(data);
                }
            },
            error:function() { $.toast("服务器错误", "forbidden");}
        });
    });
</script>
</body>
</html>