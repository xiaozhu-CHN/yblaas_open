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
    <form class="form form-horizontal" id="form-teacher-data">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${(teacher.name)!}" placeholder="请输入您的姓名" id="name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>机构：</label>
            <div class="formControls col-xs-8 col-sm-9" id="selectCollege"> <span class="select-box">
				<select class="select" size="1" name="collegeId" id="collegeId">
					<option value="">请选择机构</option>
				</select>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input class="input-text" value="${(teacher.teacherTell)!}" type="text" name="teacherTell" id="teacherTell" placeholder="请输入您的手机号" style="width:300px">
<#--                <button onclick="sendMessage();return false;" id="verification_button" class="btn btn-primary radius upload-btn"> 获取验证码</button>-->
            </div>
        </div>
<#--        <div class="row cl">-->
<#--            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>验证码：</label>-->
<#--            <div class="formControls col-xs-8 col-sm-9">-->
<#--                <input type="text" class="input-text" value="" placeholder="请输入您的手机验证码" name="verification">-->
<#--            </div>-->
<#--        </div>-->
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
        <br/>
<#--        <div class="row cl">-->
<#--            <label class="form-label col-xs-4 col-sm-3">QQ提醒：</label>-->
<#--            <div class="formControls col-xs-8 col-sm-9">-->
<#--                ${(teacher.teacherQq)!"未绑定"}-->
<#--                <button onclick="sendBinDingQq();return false;" id="verification_button" class="btn btn-primary radius upload-btn"> 更换绑定</button>-->
<#--            </div>-->
<#--        </div>-->
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">邮件提醒：</label>
            <div class="formControls col-xs-8 col-sm-9">
                ${(teacher.teacherEmail)!"未绑定"}
                <button onclick="sendBinDingEmail();return false;" id="verification_button" class="btn btn-primary radius upload-btn"> 更换绑定</button>
                <#if (teacher.teacherEmail)??><button onclick="cancelEmail();return false;" class="btn btn-danger radius"> 取消绑定</button></#if>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">注意：</label>
            <div class="formControls col-xs-8 col-sm-9">
                1.修改姓名成功后首页需要手动刷新才会变化。<br/>
<#--                2.绑定QQ请先添加QQ:<span style="color: red">99999999</span><br/><img src="图片链接" alt="QQ-1二维码" width="200" height="240" align="bottom" />-->
            </div>
        </div>
    </form>
</article>

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
    $(function(){
        //获取学院信息
        var teacherCollegeId = ${(teacher.collegeId)!0};

        var roles = "${roles!"black"}";
        $.ajax({
            type: "POST",
            url: "/public/get_college_list_ajax",
            dataType: "json",
            success: function(data){
                if(data.length>0){
                    $("#collegeId").empty();
                    $("#collegeId").append('<option value="">请选择机构</option>');
                }
                if(roles == "teacher"){
                    for (var i=0;i<data.length;i++){
                        if(teacherCollegeId == data[i].collegeId){
                            $("#collegeId").append("<option value='"+data[i].collegeId+"' selected>"+data[i].name+"</option>");
                        }else{
                            $("#collegeId").append("<option value='"+data[i].collegeId+"'>"+data[i].name+"</option>");
                        }
                    }
                }else{
                    for (var i=0;i<data.length;i++){
                        if(teacherCollegeId == data[i].collegeId){
                            $("#collegeId").append("<option value='"+data[i].collegeId+"' selected>"+data[i].name+"</option>");
                            // $("#selectCollege").html(data[i].name);
                        }
                    }
                }

            },
            error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
        });
        if(roles == "teacher"){

        }


        $("#form-teacher-data").validate({
            rules:{
                name:{
                    required:true,
                    minlength:1,
                    maxlength:16
                },
                // verification:{
                //     required:true,
                //     range:[1000,9999]
                // },
                teacherTell:{
                    required:true,
                    isMobile:true,
                },
                collegeId:{
                    required:true,
                }
            },
            submitHandler:function(form){
                layer.confirm('您确定要提交吗?',function(){
                    $(form).ajaxSubmit({
                        type: 'post',
                        url: "/teacher/my_data_ajax",
                        dataTyep:"text",
                        success: function(data){
                            if(data=="success"){
                                layer.msg('修改成功',{
                                    icon:1,
                                    time:1000,
                                    end: function () {
                                        layer_close();
                                    }
                                });
                            }
                            if(data == "error1"){
                                layer.msg("验证码错误",{icon:5,time:1000});
                            }
                            if(data == "error2"){
                                layer.msg("个人信息未发生变化",{icon:5,time:1000});
                            }
                        },
                        error: function(){
                            layer.msg('服务器错误，请刷新尝试',{icon:5,time:1000});
                        }
                    });
                });
            }
        });
    });

    //验证码处理
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    function sendMessage() {
        var teacherTell = $("#teacherTell").val();
        if(teacherTell==""||teacherTell==null||teacherTell.length!=11){
            layer.msg("手机号码有误",{icon:5,time:1000});
        }else{
            curCount= count;
            //设置button效果，开始计时
            $("#verification_button").attr("disabled", "true");
            $("#verification_button").text("重新发送(" + curCount + ")");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据
            $.ajax({
                type: "POST",
                url: "/public/user_send_message",
                data: {phone:teacherTell},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('发送成功，十分钟内有效',{icon:1, time:1000});
                    }else{
                        layer.msg("发送频率过快，请稍后",{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg("服务器错误",{icon:5,time:1000});}
            });
        }
    }

    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#verification_button").removeAttr("disabled");//启用按钮
            $("#verification_button").text("重新发送验证码");
        }
        else {
            curCount--;
            $("#verification_button").text("重新发送(" + curCount + ")");
        }
    }

    /*
        弹框绑定QQ
     */
    function sendBinDingQq(){
        window.clearInterval(InterValObj2);//停止计时器
        layer.open({
            type: 1,
            area: ['300px','170px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '绑定QQ',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>QQ:</td><td><input type="text" placeholder="请输入您的QQ号" id="teacherQq" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td>验证码:</td><td><input type="text" name="verification2" placeholder="请输入您的验证码" id="verification2" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td colspan="2"><button id="verification_button2" class="btn btn-success radius" onclick="sendQq();return false;">获取验证码</button>&nbsp;&nbsp;&nbsp;<input class="btn btn-success radius" type="button" onclick="sendQqAjax();return false;" value="绑定"></td></tr></table></div>'
        });
    }

    /*
        弹框绑定Email
     */
    function sendBinDingEmail(){
        window.clearInterval(InterValObj3);//停止计时器
        layer.open({
            type: 1,
            area: ['300px','170px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '绑定邮箱',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>邮箱:</td><td><input type="text" placeholder="请输入您的邮箱" id="teacherEmail" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td>验证码:</td><td><input type="text" name="verification3" placeholder="请输入您的验证码" id="verification3" class=".input-text radius" /></td></tr>'+
                '<tr class="text-c"><td colspan="2"><button id="verification_button3" class="btn btn-success radius" onclick="sendEmail();return false;">获取验证码</button>&nbsp;&nbsp;&nbsp;<input class="btn btn-success radius" type="button" onclick="sendEmailAjax();return false;" value="绑定"></td></tr></table></div>'
        });
    }

    //QQ验证码处理
    var InterValObj2; //timer变量，控制时间
    var count2 = 60; //间隔函数，1秒执行
    var curCount2;//当前剩余秒数
    function sendQq() {
        var teacherQq = $("#teacherQq").val();
        if(teacherQq==""||teacherQq==null){
            layer.msg("输入QQ号有误",{icon:5,time:1000});
        }else{
            curCount2= count2;
            //设置button效果，开始计时
            $("#verification_button2").attr("disabled", "true");
            $("#verification_button2").text("重新发送(" + curCount2 + ")");
            InterValObj2 = window.setInterval(SetRemainTime2, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据
            $.ajax({
                type: "POST",
                url: "/public/user_send_qq",
                data: {userQq:teacherQq},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('发送成功，半小时钟内有效',{icon:1, time:1000});
                    }else{
                        layer.msg("发送频率过快，请稍后",{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg("服务器错误",{icon:5,time:1000});}
            });
        }
    }

    //QQtimer处理函数
    function SetRemainTime2() {
        if (curCount2 == 0) {
            window.clearInterval(InterValObj2);//停止计时器
            $("#verification_button2").removeAttr("disabled");//启用按钮
            $("#verification_button2").text("重新发送验证码");
        }
        else {
            curCount2--;
            $("#verification_button2").text("重新发送(" + curCount2 + ")");
        }
    }

    //Email验证码处理
    var InterValObj3; //timer变量，控制时间
    var count3 = 60; //间隔函数，1秒执行
    var curCount3;//当前剩余秒数
    function sendEmail() {
        var teacherEmail = $("#teacherEmail").val();
        if(teacherEmail==""||teacherEmail==null){
            layer.msg("输入邮箱有误",{icon:5,time:1000});
        }else{
            curCount3= count3;
            //设置button效果，开始计时
            $("#verification_button3").attr("disabled", "true");
            $("#verification_button3").text("重新发送(" + curCount3 + ")");
            InterValObj3 = window.setInterval(SetRemainTime3, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据
            $.ajax({
                type: "POST",
                url: "/public/user_send_email",
                data: {userEmail:teacherEmail},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('发送成功，半小时内有效',{icon:1, time:1000});
                    }else{
                        layer.msg("发送频率过快，请稍后",{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg("服务器错误",{icon:5,time:1000});}
            });
        }
    }

    //Emailtimer处理函数
    function SetRemainTime3() {
        if (curCount3 == 0) {
            window.clearInterval(InterValObj3);//停止计时器
            $("#verification_button3").removeAttr("disabled");//启用按钮
            $("#verification_button3").text("重新发送验证码");
        }
        else {
            curCount3--;
            $("#verification_button3").text("重新发送(" + curCount3 + ")");
        }
    }

    //发送绑定QQ请求
    function sendQqAjax() {
        var teacherQq = $("#teacherQq").val();
        var verification2 = $("#verification2").val();
        if(teacherQq==""||verification2==""){
            layer.msg("输入有误",{icon:5,time:1000});
        }else{
            $.ajax({
                type: "POST",
                url: "/teacher/my_data_qq_ajax",
                data: {teacherQq:teacherQq,verification:verification2},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('绑定成功',{
                            icon:1,
                            time:1000,
                            end: function () {
                                window.location.reload();
                            }
                        });
                    }else{
                        layer.msg("验证码错误",{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg("服务器错误",{icon:5,time:1000});}
            });
        }
    }

    //发送绑定邮箱请求
    function sendEmailAjax() {
        var teacherEmail = $("#teacherEmail").val();
        var verification3 = $("#verification3").val();
        if(teacherEmail==""||verification3==""){
            layer.msg("输入有误",{icon:5,time:1000});
        }else{
            $.ajax({
                type: "POST",
                url: "/teacher/my_data_email_ajax",
                data: {teacherEmail:teacherEmail,verification:verification3},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('绑定成功',{
                            icon:1,
                            time:1000,
                            end: function () {
                                window.location.reload();
                            }
                        });
                    }else{
                        layer.msg("验证码错误",{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg("服务器错误",{icon:5,time:1000});}
            });
        }
    }

    //取消绑定邮箱
    function cancelEmail() {
        layer.confirm('您确定要取消绑定邮箱吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/my_data_cancel_email_ajax",
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('取消绑定成功',{
                            icon:1,
                            time:1000,
                            end: function () {
                                window.location.reload();
                            }
                        });
                    }else{
                        layer.msg("取消错误",{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg("服务器错误",{icon:5,time:1000});}
            });
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>