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
    <title>待考勤</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>  <i class="Hui-iconfont">&#xe627;</i> 考勤管理 <span class="c-gray en">&gt;</span> 待考勤 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">此页面只展示待考勤和考勤中的记录。</span> </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort""></table>
    </div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/hui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    /* datatable参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 0, "asc" ]],//默认第几个排序
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "aoColumnDefs": [
            {"orderable":false,"aTargets":[6,7,8,9]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/teacher/attendance_dkq_ajax',
            dataSrc: '',
            type: 'GET',
            error:function() {
                layer.msg('权限不足!',{icon:5,time:1000});
            }
        },
        "columns":[
            {	data: 'id',
                title: '编号',
                class: 'text-c',
                width: '40'
            },
            {	data: 'collegeName',
                title: '学院',
                class: 'text-c',
                width: '150'
            },
            { 	data: 'eclassName',
                title: '班级',
                class: 'text-c',
                width: '150'
            },
            { 	data: 'timeStart',
                title: '开始时间',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'timeEnd',
                title: '结束时间',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'timeChange',
                title: '发起时间',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'name',
                title: '标题',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'beiz',
                title: '备注',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'state',
                title: '假条状态',
                class: 'text-c',
                width: '100',
                render: function(data, type, row, meta) {
                    var dateNow = new Date();
                    var timeStart = new Date(row.timeStart);
                    var timeEnd = new Date(row.timeEnd);
                    if(data == "2"){
                        return "停止考勤";
                    }
                    if(dateNow<timeStart){
                        return "待考勤";
                    }else if(dateNow<=timeEnd){
                        return "考勤中";
                    }else if(dateNow>timeEnd){
                        return "考勤结束";
                    }else{
                        return "时间错误";
                    }
                }
            },
            { 	data: null,
                title: '操作',
                class: 'text-c',
                width: '70',
                render: function(data, type, row, meta) {
                    if(row.state == "1"){
                        return "<a title=\"查看考勤结果\" href=\"javascript:;\" onclick=\"attendance_info("+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe665;</i></a> <a title=\"停止考勤\" href=\"javascript:;\" onclick=\"attendance_stop("+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe631;</i></a> <a title=\"删除考勤\" href=\"javascript:;\" onclick=\"attendance_del("+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe609;</i></a>";
                    }else {
                        return "<a title=\"查看考勤结果\" href=\"javascript:;\" onclick=\"attendance_info("+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe665;</i></a> <a title=\"启用考勤\" href=\"javascript:;\" onclick=\"attendance_enable("+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe615;</i></a> <a title=\"删除考勤\" href=\"javascript:;\" onclick=\"attendance_del("+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe609;</i></a>";
                    }
                }
            }
        ]
    });

    //假条查询流程
    function attendance_info(id) {
        layer_show(+id+'：考勤结果','/teacher/attendance_info?attendanceId='+id,'','550');
    }

    //停止考勤
    function attendance_stop(id) {
        layer.confirm('您确定停止考勤吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/attendance_dkq_stop_ajax",
                data: {attendanceId:id},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('停止考勤成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('停止考勤错误',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误',{icon:5,time:1000});}
            });
        });
    }

    //启用考勤
    function attendance_enable(id) {
        layer.confirm('您确定启用考勤吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/attendance_dkq_enable_ajax",
                data: {attendanceId:id},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('启用考勤成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('停止考勤错误',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误',{icon:5,time:1000});}
            });
        });
    }

    //删除考勤
    function attendance_del(id) {
        layer.confirm('您确定删除考勤吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/attendance_dkq_del_ajax",
                data: {attendanceId:id},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('删除考勤成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('删除考勤错误',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误',{icon:5,time:1000});}
            });
        });
    }
</script>
</body>
</html>