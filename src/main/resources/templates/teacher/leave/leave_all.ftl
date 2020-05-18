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
    <title>全部假条</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 请假管理 <span class="c-gray en">&gt;</span> 全部假条 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <input type="text" class="input-text" style="width:250px" placeholder="学号" id="numberId">
        <button type="submit" class="btn btn-success" id="numberIdSearch"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        <input type="text" class="input-text" style="width:250px" placeholder="假条编号" id="leaveId">
        <button type="submit" class="btn btn-success" id="leaveIdSearch"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        <input type="text" class="input-text" style="width:250px" placeholder="姓名" id="studentName">
        <button type="submit" class="btn btn-success" id="studentNameSearch"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">如需请假审核和销假请前往待审核和待销假模块。</span> </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
        </table>
    </div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/hui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">

    var type = 0;
    var seachVar;

    /* datatable参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 0, "desc" ]],//默认第几个排序
        "searching": false, //是否显示搜索框
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "serverSide":true,
        "aoColumnDefs": [
            {"orderable":false,"aTargets":[9,10,12]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/teacher/leave_all_ajax',
            type: 'POST',
            data: function (param) {
                param.type = type;
                param.seachVar = seachVar;
                param.column = param.order[0].column;
                param.dir = param.order[0].dir;
                return param;
            },
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
            {	data: 'studentName',
                title: '姓名',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    return "<u style=\"cursor:pointer\" class=\"text-primary\" onclick=\"leave_student_data('"+data+"',"+row.id+")\">"+data+"</u>";
                }
            },
            { 	data: 'collegeName',
                title: '学院',
                class: 'text-c',
                width: '150'
            },
            { 	data: 'eclassName',
                title: '班级',
                class: 'text-c',
                width: '150'
            },
            { 	data: 'sex',
                title: '性别',
                class: 'text-c',
                width: '50'
            },
            { 	data: 'numberId',
                title: '学号',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'timeStart',
                title: '开始时间',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    return date_show(data);
                }
            },
            { 	data: 'timeEnd',
                title: '结束时间',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    return date_show(data);
                }
            },
            { 	data: 'day',
                title: '天数',
                class: 'text-c',
                width: '50'
            },
            { 	data: 'whereabouts',
                title: '去向',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'cause',
                title: '事由',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'state',
                title: '假条状态',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    if(data=="0"){return "待审核";}
                    if(data=="1"){return "辅导员审核不通过";}
                    if(data=="2"){return "待学院审核";}
                    if(data=="3"){return "学院审核不通过";}
                    if(data=="4"){return "待学工处审核";}
                    if(data=="5"){return "学工处审核不通过";}
                    if(data=="6"){return "待销假";}
                    if(data=="7"){return "已完成";}
                    if(data=="10"){return "已取消";}
                }
            },
            { 	data: 'id',
                title: '流程',
                class: 'text-c',
                width: '30',
                render: function ( data, type, row, meta ) {
                    return "<a title=\"查询审核流程\" href=\"javascript:;\" onclick=\"leave_get_step("+data+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe665;</i></a>";
                }
            }
        ]
    });

    /*时间的格式转换*/
    function date_show(time) {
        if(time == null ||time ==""){
            return "";
        }
        var time_show = new Date(time);
        var str = time_show.getFullYear()+'年'+(time_show.getMonth()+1)+'月'+time_show.getDate()+'日';
        return str;
    }

    //假条查询流程
    function leave_get_step(id) {
        layer_show(+id+'：审核流程','/teacher/leave_get_leave_number_id_step?leaveId='+id,'400','300');
    }

    //学生信息查询
    function leave_student_data(name, id) {
        layer_show(name+'个人信息','/teacher/leave_student_data?leaveId='+id,'400','300');
    }

    //搜索学号
    $("#numberIdSearch").click(function () {
        type =1;
        seachVar = $("#numberId").val();
        if(seachVar == null || seachVar ==""){
            layer.msg('请输入学号!',{icon:5,time:1000});
        }else{
            $(".table-sort").DataTable().draw();
            layer.msg('搜索完成!',{icon:1,time:1000});
        }
    });

    //搜索假条Id
    $("#leaveIdSearch").click(function () {
        type =2;
        seachVar = $("#leaveId").val();
        if(seachVar == null || seachVar ==""){
            layer.msg('请输入假条Id!',{icon:5,time:1000});
        }else{
            $(".table-sort").DataTable().draw();
            layer.msg('搜索完成!',{icon:1,time:1000});
        }
    });

    //搜索姓名
    $("#studentNameSearch").click(function () {
        type =3;
        seachVar = $("#studentName").val();
        if(seachVar == null || seachVar ==""){
            layer.msg('请输入学生姓名!',{icon:5,time:1000});
        }else{
            $(".table-sort").DataTable().draw();
            layer.msg('搜索完成!',{icon:1,time:1000});
        }
    });
</script>
</body>
</html>