<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${(yblaas.title)!}</title>
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
        <h2 class="weui-msg__title">登陆成功</h2>
        <p class="weui-msg__desc">请选择您的角色；注意：首次登陆只能选择一次，请慎重选择。</p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <button class="weui-btn weui-btn_primary" onclick="student_register()">学生</button>
            <button class="weui-btn weui-btn_primary" onclick="teacher_register()">老师</button>
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
    var type;
    function teacher_register() {
        type="teacher";
        register();
    }

    function student_register() {
        type = "student";
        register();
    }

    function register() {
        $.ajax({
            type: "POST",
            url: "/public/user_register",
            data: {type:type},
            dataType: "text",
            success: function(data){
                if(data=="success"){
                    $.toast("登陆成功!", function() {
                        if(type == "teacher"){
                            window.location.replace("/teacher/index");
                        }else{
                            window.location.replace("/student/index");
                        }
                    });
                }else{
                    $.toast("登陆错误，请联系管理员!", "forbidden");
                }
            },
            error:function() {  $.toast("服务器错误,请稍后再试!!", "forbidden");}
        });
    }
</script>
</body>
</html>