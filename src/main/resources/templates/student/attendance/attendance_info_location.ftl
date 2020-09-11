<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>定位考勤</title>
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
        <a class="icon icon-left pull-left" href="javascript:history.go(-1)"></a>
        <h1 class="title">定位考勤</h1>
    </div>
    </br/><br />
    <div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <span>注意：定位前请确认位于易班APP内部并打开手机GPS定位，否则无法有效进行定位考勤。</span>
            </div>
            <div id="container" style="height: 13rem">
            </div>
            <div class="weui-btn-area">
                <button class="weui-btn weui-btn_primary" onclick="gethtml5location_fun({action:'uuid',params:{},callback:'onlyid_back'})">定位</button>
                <button class="weui-btn weui-btn_primary" id="attendance_info_sumbit" style="display:none;" onclick="attendance_info_sumbit()">提交考勤</button>
                <a href="/student/attendance_index" class="weui-btn weui-btn_default external">返回考勤首页</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>
<!-- 如果使用了某些拓展插件还需要额外的JS -->
<script type="text/javascript" src="/weui/js/swiper.min.js"></script>
<script type="text/javascript" src="/weui/js/city-picker.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-form.min.js"></script>

<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=${gaodeKey!}"></script>
<script>
    var lng = ${(attendance.longitude)!"109.211526"};
    var lat = ${(attendance.latitude)!"23.772286"};
    var accuracy = ${(accuracy)!0};
    var lngUser;
    var latUser;
    var macUser;
    var attendanceId = ${(attendance.id)!0};

    //地图map
    var map = new AMap.Map('container',{
        zoom:18,
        center: new AMap.LngLat(lng, lat)
    });
    //比例尺，显示当前地图中心的比例尺
    AMap.plugin('AMap.Scale',function(){
        var scale = new AMap.Scale();
        map.addControl(scale);
    });
    //显示圈
    var circle = new AMap.Circle({
        zIndex:10,
        radius: accuracy, //半径
        strokeColor: "#FF33FF", //线颜色
        strokeOpacity: 0.7, //线透明度
        strokeWeight: 1.5,    //线宽
        fillColor: "#708090", //填充色
        fillOpacity: 0.5,//填充透明度
        map:map,
        center: new AMap.LngLat(lng, lat)
    });

    //是否首次定位
    var markerState = true;
    //默认标点
    var marker = new AMap.Marker({
        position: new AMap.LngLat(109.211526, 23.772286)
    });
    //增加定位标点
    function markerAdd(lng,lat) {
        marker.setPosition(new AMap.LngLat(lng, lat));
        if(markerState){
            map.add(marker);
            markerState = false;
            $("#attendance_info_sumbit").show();
        }
    }

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
     函数作用：获取地理位置和MAC
     */
    function gethtml5location_fun(jsonstr) {
        var tempJson = JSON.stringify(jsonstr);
        if(browser.versions.android) {
            //android 调用方式
            window.local_obj.yibanhtml5location();
            window.local_obj.js2mobile(tempJson);
        }else if(browser.versions.ios) {
            ios_yibanhtml5location();
            js2mobile(tempJson);
        }else {
            $.toast("该终端类型暂不支持使用", "forbidden");
        }
    }

    /*
     函数名称：yibanhtml5location
     函数作用：客户端获取地理位置，异步返回位置信息，html根据返回信息做界面内容处理
     参数说明：易班app回调设定，无需用户调用
        postion  Json  {"longitude":"经度坐标", "latitude":"纬度坐标", "address":"位置名称"}
     */
    function yibanhtml5location(postion) {
        if(postion.indexOf("定位失败") >= 0){
            $.toast("请打开定位服务(GPS)", "forbidden");
        }else{
            var obj = JSON.parse(postion);
            //获取经度坐标
            lngUser = obj.longitude;
            //获取纬度坐标
            latUser = obj.latitude;
            markerAdd(lngUser, latUser);
        }
    }

    /*
     函数名称：onlyid_back
     函数作用：返回设备相对唯一标示码
     参数说明：易班app回调设定，无需用户调用
        result  字符串
     */
    function onlyid_back(result) {
        var obj = JSON.parse(result);
        macUser = obj.value;
    }

    //提交定位考勤
    function attendance_info_sumbit() {
        if(circle.contains(new AMap.LngLat(lngUser, latUser))){
            if(attendanceId != 0 && lngUser!=null && lngUser !="" && latUser!=null && latUser !="" && macUser !=null && macUser!=""){
                $.confirm("您确定要提交考勤吗?", "确认提交", function() {
                    $.ajax({
                        type: 'POST',
                        url: "/student/attendance_info_location_ajax",
                        data:{id:attendanceId,longitude:lngUser,latitude:latUser,mac:macUser},
                        dataTyep:"text",
                        success: function(data){
                            if(data=="success"){
                                window.location.replace("/student/attendance_info_success");
                            }else{
                                $.toast("考勤失败", "cancel");
                            }
                        },
                        error: function(){
                            $.toast("服务器错误，请刷新尝试", "forbidden");
                        }
                    });
                });
            }else{
                $.toast("考勤参数错误", "forbidden");
            }
        }else{
            $.toast("您不在考勤位置范围", "forbidden");
        }
    }
</script>
</body>
</html>