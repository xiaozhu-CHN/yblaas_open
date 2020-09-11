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
    <title>发起考勤</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="/teacher/index">首页</a>
    <span class="c-gray en">&gt;</span>
    <i class="Hui-iconfont">&#xe627;</i>
    考勤管理
    <span class="c-gray en">&gt;</span>
    发起考勤
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    <form class="form form-horizontal" id="form_attendance_add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>学院：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <span class="select-box radius">
				<select class="select" size="1" name="collegeId" id="collegeId">
					<option value="">请选择学院</option>
				</select>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>班级：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <span class="select-box radius">
				<select class="select" size="1" name="eclassId" id="eclassId">
					<option value="">请选择班级</option>
				</select>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                开始时间：</label>
            <div class="formControls col-xs-8 col-sm-9">
<#--                <input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timeEnd\')||\'%y-%M-%d\'}' })" id="timeStart" name="timeStart" class="input-text Wdate">-->
                <input type="text" placeholder="请选择开始时间" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'commentdatemax\')||\'%y-%M-%d\'}' })" id="commentdatemin" name="timeStart" class="input-text Wdate">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                结束时间：</label>
            <div class="formControls col-xs-8 col-sm-9">
<#--                <input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timeStart\')}' })" id="timeEnd" name="timeEnd" class="input-text Wdate">-->
                <input type="text" placeholder="请先选择结束时间" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'commentdatemin\')}' })" id="commentdatemax" name="timeEnd" class="input-text Wdate">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>标题：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" name="name" placeholder="请输入考勤标题" class="input-text radius">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>备注：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <textarea id="beiz" name="beiz" cols="" rows="" class="textarea radius"  placeholder="请输入您的考勤事项"></textarea>
                <p class="textarea-numberbar"><span id="beiz_length" class="textarea-length">0</span>/200</p>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>考勤地点：</label>
            <span class="formControls col-xs-8 col-sm-9">
                经度：<input type="text" name="longitude" id="longitude" placeholder="经度" class="input-text radius" style="width:150px">
                纬度：<input type="text" name="latitude" id="latitude" placeholder="纬度" class="input-text radius" style="width:150px">
                <a class="btn btn-success radius" onClick="gethtml5location_fun();return false;">定位</a><br/>
                <span>注意：精确定位只支持易班APP，不支持第三方浏览器。</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-0 col-sm-1"></label>
            <div class="formControls col-xs-12 col-sm-10" id="container" style="height: 250px">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <br/>
                <button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe603;</i> 发起考勤</button>
                <button onClick="javascript:window.location.replace('/teacher/index');" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
</div>

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

<!--高德API -->
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=${gaodeKey!}"></script>
<script src="https://webapi.amap.com/ui/1.0/main.js"></script>
<script type="text/javascript">

    $(function () {
        //获取学院信息
        $.ajax({
            type: "POST",
            url: "/public/get_college_list_ajax",
            dataType: "json",
            success: function(data){
                if(data.length>0){
                    $("#collegeId").empty();
                    $("#collegeId").append('<option value="">请选择学院</option>');
                }
                for (var i=0;i<data.length;i++){
                    $("#collegeId").append("<option value='"+data[i].collegeId+"'>"+data[i].name+"</option>");
                }
            },
            error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
        });
    });

    /* 表单校验和提交*/
    $("#form_attendance_add").validate({
        rules:{
            collegeId:{
                required:true
            },
            eclassId:{
                required:true
            },
            timeStart:{
                required:true
            },
            timeEnd:{
                required:true
            },
            name:{
                required:true
            },
            beiz:{
                required: true,
                maxlength:200
            },
            longitude:{
                required: true
            },
            latitude:{
                required: true
            }
        },
        submitHandler:function(form){
            layer.confirm('确认要发起考勤吗？',function(){
                $(form).ajaxSubmit({
                    type: 'post',
                    url: "/teacher/attendance_add_ajax" ,
                    dataTyep:"text",
                    async: false,
                    success: function(data){
                        if(data=="success"){
                            layer.msg("发起考勤成功！",{
                                icon:1,
                                time:1000,
                                end: function () {
                                    window.location.replace("/teacher/index");
                                }
                            });
                        }else{
                            layer.msg('发起考勤失败!',{icon:5,time:1000});
                        }
                    },
                    error: function(XmlHttpRequest, textStatus, errorThrown){
                        layer.msg('服务器错误!',{icon:5,time:1000});
                    }
                });
            });
        }
    });

    // 动态备注的字数
    $("#beiz").on('input',function(){
        var whereabouts_length = $(this).val().length;
        $("#beiz_length").replaceWith('<span id="beiz_length">'+whereabouts_length+'</span>');
    });

    //动态加载学院
    $("#collegeId").change(function(){
        var collegeId = $("#collegeId").val();
        if(collegeId == null ||collegeId ==""){
            layer.msg('请先选择学院!',{icon:5,time:1000});
        }else{
            $.ajax({
                type: "POST",
                url: "/public/get_eclass_list_ajax",
                data: {collegeId:parseInt(collegeId)},
                dataType: "json",
                success: function(data){
                    if(data.length>0){
                        $("#eclassId").empty();
                        $("#eclassId").append('<option value="">请选择班级</option>');
                    }
                    for (var i=0;i<data.length;i++){
                        $("#eclassId").append("<option value='"+data[i].eclassId+"'>"+data[i].name+"</option>");
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        }
    });

    //地图map
    var map = new AMap.Map('container',{
        zoom:18,
        resizeEnable: true
    });
    //比例尺，显示当前地图中心的比例尺
    AMap.plugin('AMap.Scale',function(){
        var scale = new AMap.Scale();
        map.addControl(scale);
    });

    var accuracy = parseInt(${accuracy!30});
    //拖拽选经纬度
    AMapUI.loadUI(['misc/PositionPicker'], function(PositionPicker) {
        var circle = new AMap.Circle({
            zIndex:10,
            radius: accuracy, //半径
            strokeColor: "#FF33FF", //线颜色
            strokeOpacity: 0.7, //线透明度
            strokeWeight: 1.5,    //线宽
            fillColor: "#708090", //填充色
            fillOpacity: 0.5,//填充透明度
            map:map
        });
        var positionPicker = new PositionPicker({
            mode:'dragMap',//设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
            map:map//依赖地图对象
        });
        //TODO:事件绑定、结果处理等
        positionPicker.on('success', function(positionResult) {
            $("#longitude").val(positionResult.position.lng);
            $("#latitude").val(positionResult.position.lat);
            circle.setCenter(positionResult.position);
        });
        positionPicker.on('fail', function(positionResult) {
            // 海上或海外无法获得地址信息
            layer.msg('获取地址信息错误!',{icon:5,time:1000});
        });
        positionPicker.start();
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
     函数名称：getLocation
     函数作用：获取地理位置
     */
    function gethtml5location_fun() {
        if(browser.versions.android) {
            //android 调用方式
            window.local_obj.yibanhtml5location();
        }else if(browser.versions.ios) {
            ios_yibanhtml5location();
        }else {
            layer.msg('该终端类型暂不支持使用!',{icon:5,time:1000});
        }
    }

    /*
     函数名称：yibanhtml5location
     函数作用：客户端获取地理位置，异步返回位置信息，html根据返回信息做界面内容处理
     参数说明：易班app回调设定，无需用户调用
        postion  Json  {"longitude":"经度坐标", "latitude":"纬度坐标", "address":"位置名称"}
     */
    function yibanhtml5location(postion) {
        var obj = JSON.parse(postion);
        //获取经度坐标
        var lng = obj.longitude;
        //获取纬度坐标
        var lat = obj.latitude;
        map.setCenter(new AMap.LngLat(lng, lat));
    }
</script>
</body>
</html>
