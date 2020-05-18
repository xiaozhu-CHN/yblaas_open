<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>个人信息</title>
    <link rel="stylesheet" href="/weui/css/weui.min.css">
    <link rel="stylesheet" href="/weui/css/jquery-weui.min.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <style type="text/css">
        .bar{position:absolute;right:0;left:0;z-index:10;height:2.2rem;padding-right:.5rem;padding-left:.5rem;background-color:#f7f7f8;border-bottom:1px solid #e7e7e7;-webkit-backface-visibility:hidden;backface-visibility:hidden}
        .bar-nav{top:0}
        .title{position:absolute;display:block;width:100%;padding:0;margin:0 -.5rem;font-size:.85rem;font-weight:500;line-height:2.2rem;color:#3d4145;text-align:center;white-space:nowrap}
        .title a{color:inherit}
        .bar .icon{position:relative;z-index:20;padding:.5rem .1rem;font-size:1rem;line-height:1.2rem}
        .icon-left:before{content:"\e614"}
        .pull-left{float:left}
        @font-face{font-family:iconfont-sm;src:url(https://at.alicdn.com/t/font_1433401008_2229297.eot);src:url(https://at.alicdn.com/t/font_1433401008_2229297.eot?#iefix) format('embedded-opentype'),url(https://at.alicdn.com/t/font_1433401008_2229297.woff) format('woff'),url(https://at.alicdn.com/t/font_1433401008_2229297.ttf) format('truetype'),url(https://at.alicdn.com/t/font_1433401008_2229297.svg#iconfont) format('svg')}
        .icon{font-family:iconfont-sm!important;font-style:normal;-webkit-font-smoothing:antialiased;-webkit-text-stroke-width:.2px;-moz-osx-font-smoothing:grayscale}
        .page{background:#eee}
    </style>
</head>
<body>
<div class="page">
    <!-- 标题栏 -->
    <div class="bar bar-nav">
        <a class="icon icon-left pull-left external" href="javascript:history.go(-1)"></a>
        <h1 class="title">个人信息</h1>
    </div>
    </br/><br />
    <form id="my_data">
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <p style="color: #00B83F">
                    注意:您的个人信息
                    <#if (student.examine) ?? &&  student.examine=="1">待审核</#if>
                    <#if (student.examine) ?? &&  student.examine=="2">审核通过</#if>
                    <#if (student.examine) ?? &&  student.examine=="3">审核未通过</#if>
                </p>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">姓名</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" value="${(student.name)!}" name="name" placeholder="请输入您的姓名">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">学号</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" value="${(student.numberId)!}" name="numberId" placeholder="请输入您的学号">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">性别</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="sex" name="sex" placeholder="请选择您的性别" value="${(student.sex)!}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">学院</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="college" name="college" placeholder="请选择您的学院" value="${collegeName!}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">班级</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="eclass" name="eclass" placeholder="请选择您的班级" value="${(eclass.name)!}">
                    <div style="display: none;"><input type="text" id="eclassid" name="eclassId" value="${(eclass.eclassId)!}"> </div>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">家长姓名</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" value="${(student.parenName)!}" name="parenName" placeholder="请输入您的家长姓名">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">家长电话</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" value="${(student.parentTell)!}" name="parentTell" placeholder="请输入您的家长电话">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">地址区域</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="city" type="text" name="city" value="${(student.city)!}" placeholder="请选择您的地址区域">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">详细地址</label></div>
                <div class="weui-cell__bd">
                    <textarea class="weui-textarea" placeholder="请输入您的详细地址" rows="3" name="address" id="address">${(student.address)!}</textarea>
                    <div class="weui-textarea-counter"><span id="address_length">0</span>/200</div>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label for="name" class="weui-label">邮编</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" value="${(student.ems)!}" name="ems" placeholder="请输入您的地址邮编">
                </div>
            </div>
            <div class="weui-cell weui-cell_vcode">
                <div class="weui-cell__hd">
                    <label class="weui-label">学生电话</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" placeholder="请输入您的手机号码" id="studentTell" name="studentTell" value="${(student.studentTell)!}">
                </div>
<#--                <div class="weui-cell__ft">-->
<#--                    <button class="weui-vcode-btn" onclick="sendMessage();return false;" id="verification_button" <#if (student.examine) ?? &&  student.examine=="1">disabled="disabled"</#if> >获取验证码</button>-->
<#--                </div>-->
            </div>
<#--            <#if (student.examine) ?? &&  student.examine!="1">-->
<#--            <div class="weui-cell">-->
<#--                <div class="weui-cell__hd"><label for="name" class="weui-label">验证码</label></div>-->
<#--                <div class="weui-cell__bd">-->
<#--                    <input class="weui-input" type="text" name="verification" placeholder="请输入4位验证码">-->
<#--                </div>-->
<#--            </div>-->
<#--            </#if>-->
            <div class="weui-cells__tips">您的信息将提交给辅导员进行审核，信息仅限于请假与考勤使用，请确保您的信息的准确性。<br/>注意：待审核期间无法修改个人信息。</div>
        </div>
        <div class="weui-btn-area">
            <#if (student.examine) ?? &&  student.examine!="1"><button class="weui-btn weui-btn_primary">提交修改</button></#if>
            <a href="/student/my_index" class="weui-btn weui-btn_default">取消</a>
        </div>
    </form>
</div>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>
<!-- 如果使用了某些拓展插件还需要额外的JS -->
<script type="text/javascript" src="/weui/js/swiper.min.js"></script>
<script type="text/javascript" src="/weui/js/city-picker.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-form.min.js"></script>

<!--表单验证-->
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script>
    //定义学院id和班级id
    var college = ${(eclass.collegeId)!0};
    var eclass = ${(eclass.eclassId)!0};
    //定义学院的对象
    var collegeValue = new Array();
    var collegeId = new Array();
    //定义班级的对象
    var eclassValue = new Array();
    var eclassId = new Array();

    //首次打开页面获取学院的信息
    $(function(){
        //获取学院的信息
        $.ajax({
            type: "POST",
            url: "/public/get_college_list_ajax",
            dataType: "json",
            success: function(data){
                for (var i=0;i<data.length;i++){
                    collegeValue.push(data[i].name);
                    collegeId.push(data[i].collegeId);
                }
            },
            error:function() { $.toast("获取学院信息失败，请刷新尝试", "forbidden");}
        });

        //如果班级不为空就获取班级的数据，防止学生在原来学院的基础上直接选择班级
        if(eclass!=0){
            $.ajax({
                type: "POST",
                url: "/public/get_eclass_list_ajax",
                data:{collegeId:college},
                dataType: "json",
                success: function(data){
                    for (var i=0;i<data.length;i++){
                        eclassValue.push(data[i].name);
                        eclassId.push(data[i].eclassId);
                    }
                },
                error:function() { $.toast("获取班级信息失败，请刷新尝试", "forbidden");}
            });
        }
    });

    //初始化地址的picker
    $("#city").cityPicker({
        title: "选择地址区域"
    });

    //初始化性别的picker
    $("#sex").picker({
        title: "请选择您的性别",
        cols: [
            {
                textAlign: 'center',
                values: ['男', '女']
            }
        ]
    });

    //初始化学院的picker
    $("#college").picker({
        title: "请选择您的学院",
        cols: [
            {
                textAlign: 'center',
                values: collegeId,
                displayValues: collegeValue
            }
        ],
        formatValue: function (picker, value, displayValue){
            //value 和 displayValue都是数组，长度为你的cols的长度，值为当前选中值
            college = value;
            return displayValue;
        },
        onClose: function () {
            $.ajax({
                type: 'POST',
                url: '/public/get_eclass_list_ajax',
                data:{collegeId:parseInt(college)},
                dataType: 'json',
                success: function(data){
                    eclassValue.length=0;
                    eclassId.length=0;
                    for (var i=0;i<data.length;i++){
                        eclassValue.push(data[i].name);
                        eclassId.push(data[i].eclassId);
                    }
                },
                error: function(){
                    $.toast("获取班级信息失败，请刷新尝试", "forbidden");
                }
            });
        }
    });

    //初始化班级的picker
    $("#eclass").picker({
        title: "请选择您的班级",
        cols: [
            {
                textAlign: 'center',
                values: eclassId,
                displayValues: eclassValue
            }
        ],
        formatValue: function (picker, value, displayValue) {
            //value 和 displayValue都是数组，长度为你的cols的长度，值为当前选中值
            $("#eclassid").attr('value', value);
            return displayValue;
        }
    });

    //表单校验和提交
    $("#my_data").validate({
        rules:{
            name:{
                required:true,
            },
            numberId:{
                required:true,
            },
            sex:{
                required:true,
                rangelength:[1,1]
            },
            college:{
                required:true,
            },
            eclass:{
                required:true,
            },
            parenName:{
                required:true,
            },
            parentTell:{
                required:true,
                isMobile:true,
            },
            city:{
                required:true,
            },
            address:{
                required:true,
                maxlength:200
            },
            ems:{
                required:true,
                digits:true
            },
            studentTell:{
                required:true,
                isMobile:true,
            },
            // verification:{
            //     required:true,
            //     range:[1000,9999]
            // }
        },
        submitHandler:function(form){
            $.confirm("您确定要提交审核吗?", "确认提交", function() {
                $(form).ajaxSubmit({
                    type: 'post',
                    url: "/student/my_data_ajax",
                    dataTyep:"text",
                    success: function(data){
                        if(data=="success"){
                            window.location.replace("/student/my_data_success");
                        }
                        if(data == "error1"){
                            $.toast("个人信息待审核", "forbidden");
                        }
                        if(data == "error2"){
                            $.toast("验证码错误", "forbidden");
                        }
                        if(data == "error3"){
                            $.toast("资料未变化", "forbidden");
                        }
                    },
                    error: function(){
                        $.toast("服务器错误，请刷新尝试", "forbidden");
                    }
                });
            });
        }
    });


    //验证码处理
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    function sendMessage() {
        var studentTell = $("#studentTell").val();
        if(studentTell==""||studentTell==null||studentTell.length!=11){
            $.toast("手机号码有误", "forbidden");
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
                data: {phone:studentTell},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        $.toast("发送成功，十分钟内有效");
                    }else{
                        $.toast("发送频率过快，请稍后", "forbidden");
                    }
                },
                error:function() { $.toast("服务器错误", "forbidden");}
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

    // 动态显示详细地址的字数
    $("#address").on('input',function(){
        var whereabouts_length = $(this).val().length;
        $("#address_length").replaceWith('<span id="address_length">'+whereabouts_length+'</span>');
    });
</script>
</body>
</html>