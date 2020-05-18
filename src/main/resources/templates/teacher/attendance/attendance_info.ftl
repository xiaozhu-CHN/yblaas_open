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

    <title>考勤结果</title>
</head>
<body>
<article class="page-container">
    <div class="row cl">
        <label class="form-label col-xs-0 col-sm-1"></label>
        <div class="formControls col-xs-12 col-sm-10" id="container" style="height: 15rem">
        </div>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">考勤地点如图上所示。班级总人数：${(attendanceInfoData.eclassNumber)!}人，已考勤：${(attendanceInfoData.attendanceNumber)!}人，未考勤：${(attendanceInfoData.attendanceNumberNo)!}人。</span> </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort""></table>
    </div>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<script type="text/javascript" src="/hui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=c7aef2c066b99bd01c7c024d85671b9b"></script>
<script src="https://webapi.amap.com/ui/1.0/main.js"></script>
<script type="text/javascript">

    var attendanceId = ${attendanceId!};

    /* datatable参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 0, "asc" ]],//默认第几个排序
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "aoColumnDefs": [
            {"orderable":false,"aTargets":[4]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/teacher/attendance_info_ajax',
            data:{attendanceId:attendanceId},
            dataSrc: '',
            type: 'GET',
            error:function() {
                layer.msg('权限不足!',{icon:5,time:1000});
            }
        },
        "columns":[
            {	data: 'name',
                title: '姓名',
                class: 'text-c',
                width: '100'
            },
            {	data: 'numberId',
                title: '学号',
                class: 'text-c',
                width: '150'
            },
            { 	data: 'time',
                title: '时间',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'type',
                title: '类型',
                class: 'text-c',
                width: '150',
                render: function ( data, type, row, meta ) {
                    if(data == null || data == ""){
                        return "缺勤";
                    }else if(data == "1"){
                        return "学生考勤";
                    }else if(data == "2"){
                        return "老师考勤";
                    }else{
                        return "考勤错误";
                    }
                }
            },
            { 	data: 'student',
                title: '操作',
                class: 'text-c',
                width: '60',
                render: function ( data, type, row, meta ) {
                    if(row.type == null || row.type == ""){
                        return "<a title=\"考勤\" onclick=\"attendance_info_success('"+data+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e1;</i></a>";
                    }else{
                        return "<a title=\"取消考勤\" onclick=\"attendance_info_error('"+data+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6dd;</i></a>";
                    }
                }
            }
        ]
    });

    var lng = ${(attendanceInfoData.longitude)!"109.211526"};
    var lat = ${(attendanceInfoData.latitude)!"23.772286"};
    var accuracy = ${(attendanceInfoData.attendanceAccuracy)!0};

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

    //老师手动考勤
    function attendance_info_success(studentId) {
        layer.confirm('您确定考勤通过吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/attendance_info_add_ajax",
                data: {studentId:studentId,attendanceId:attendanceId},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        location.replace(location.href);
                    }else{
                        layer.msg('考勤错误',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误',{icon:5,time:1000});}
            });
        });
    }

    //老师取消学生考勤
    function attendance_info_error(studentId) {
        layer.confirm('您确定取消考勤吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/attendance_info_del_ajax",
                data: {studentId:studentId,attendanceId:attendanceId},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        location.replace(location.href);
                    }else{
                        layer.msg('取消考勤错误',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误',{icon:5,time:1000});}
            });
        });
    }
</script>
</body>
</html>