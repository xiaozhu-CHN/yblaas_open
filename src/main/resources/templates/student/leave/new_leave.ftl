<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>请假申请</title>
    <link href="/light7/css/light7.min.css" rel="stylesheet">
    <link href="/light7/css/light7-swiper.min.css" rel="stylesheet">
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
        <a class="icon icon-left pull-left external" href="javascript:history.go(-1)"></a>
        <h1 class="title">请假申请</h1>
    </header>
    <!--表单 -->
    <div class="content native-scroll">
        <div class="content-block">
            <div class="list-block">
                <form action="" method="post" id="newleave">
                    <ul>
                        <!-- name -->
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">姓名</div>
                                    <div class="item-input">
                                        ${studentName!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假开始时间 -->
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-calendar"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">开始时间</div>
                                    <div class="item-input">
                                        <input type="text" data-toggle='date' name="timeStart" id="timeStart"/>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假结束时间 -->
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-calendar"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">结束时间</div>
                                    <div class="item-input">
                                        <input type="text" data-toggle='date' name="timeEnd" id="timeEnd" />
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假去向 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-comment"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">请假去向</div>
                                    <div class="item-input">
                                        <!-- <textarea id="address" placeholder="请输入您的请假去向！200字以内。" name="whereabouts"></textarea> -->
                                        <textarea class="weui-textarea" placeholder="请输入您的请假去向！" rows="3" name="whereabouts" id="whereabouts"></textarea>
                                        <div class="weui-textarea-counter"><span id="whereabouts_length">0</span>/200</div>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假事由 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-comment"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">请假事由</div>
                                    <div class="item-input">
                                        <!-- <textarea id="address" placeholder="请输入您的请假事由！200字以内。" name="cause"></textarea> -->
                                        <textarea class="weui-textarea" placeholder="请输入您的请假事由！" rows="3" name="cause" id="cause"></textarea>
                                        <div class="weui-textarea-counter"><span id="cause_length">0</span>/200</div>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 承诺 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-password"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">请假承诺</div>
                                    <div class="item-input">
                                        <div class="weui-cells__tips">本次请假去向和事由真实，父母知晓并同意本人去向，请假后离校后果自负。假满返校后到最后审批老师处销假。</div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="weui-btn-area">
                        <button class="weui-btn weui-btn_primary" id="showTooltips" type="submit">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>

</script>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="/light7/js/light7.min.js"></script>
<!--如果你用到了拓展包中的组件，还需要引用对应的JS文件-->
<script type="text/javascript" src="/weui/js/jquery-form.min.js"></script>
<script type="text/javascript" src="/light7/js/light7-cn.min.js"></script>



<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="/hui/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script>
    $(function(){
        // 初始化可选择的日期
        var newDate = new Date();
        newDate.setDate(newDate.getDate()-1);
        $("#timeStart").calendar({
            minDate:newDate
        });
        $("#timeEnd").calendar({
            minDate:newDate
        });

        // 动态显示请假去向的字数
        $("#whereabouts").on('input',function(){
            var whereabouts_length = $(this).val().length;
            $("#whereabouts_length").replaceWith('<span id="whereabouts_length">'+whereabouts_length+'</span>');
        });
        // 动态显示请假事由的字数
        $("#cause").on('input',function(){
            var cause_length = $(this).val().length;
            $("#cause_length").replaceWith('<span id="cause_length">'+cause_length+'</span>');
        });
    });

    $("#newleave").validate({
        rules:{
            timeStart:{
                required:true,
                dateISO:true,
            },
            timeEnd:{
                required:true,
                dateISO:true,
            },
            whereabouts:{
                required:true,
                maxlength:200,
            },
            cause:{
                required:true,
                maxlength:200,
            },
        },
        submitHandler:function(form){
            $.confirm("您确定要申请请假吗?", "确认请假", function() {
                $(form).ajaxSubmit({
                    type: 'post',
                    url: "/student/leave_new_leave_ajax" ,
                    dataTyep:"text",
                    success: function(data){
                        if(data=="success"){
                            window.location.replace("/student/leave_new_leave_success");
                        }else{
                            if(data == "error_date"){
                                $.toast("结束日期需要大于等于开始日期");
                            }else{
                                window.location.replace("/student/leave_new_leave_error");
                            }
                        }
                    },
                    error: function(XmlHttpRequest, textStatus, errorThrown){
                        $.toast("服务器错误");
                    }
                });
            });
        }
    });
</script>
</body>
</html>