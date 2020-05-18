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
    <title>学生审核</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> <i class="Hui-iconfont">&#xe62c;</i> 用户管理 <span class="c-gray en">&gt;</span> 学生审核 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="student_dsh_success_list()" class="btn btn-success radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量通过</a> </span> <span class="r">此页面只展示待审核学生信息。</span> </div>
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
        "aaSorting": [[ 1, "asc" ]],//默认第几个排序
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "aoColumnDefs": [
            {"orderable":false,"aTargets":[0,7,8,9,10,11,13]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/teacher/user_student_dsh_ajax',
            dataSrc: '',
            type: 'GET',
            error:function() {
                layer.msg('权限不足!',{icon:5,time:1000});
            }
        },
        "columns":[
            {	data: null,
                title: '<input type="checkbox" id="selectAll" name="selectAll">全选',
                class: 'text-c',
                width: '30',
                render: function ( data, type, row, meta ) {
                    return '<input type="checkbox" name="student_list" value='+row.student+'>';
                }
            },
            {	data: 'student',
                title: 'id',
                class: 'text-c',
                width: '50'
            },
            {	data: 'name',
                title: '姓名',
                class: 'text-c',
                width: '50'
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
            { 	data: 'studentTell',
                title: '电话',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'parenName',
                title: '家长姓名',
                class: 'text-c',
                width: '50'
            },
            { 	data: 'parentTell',
                title: '家长电话',
                class: 'text-c',
                width: '50'
            },
            { 	data: 'city',
                title: '地址区域',
                class: 'text-c',
                width: '100'
            },
            { 	data: 'address',
                title: '详细地址',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'ems',
                title: '邮编',
                class: 'text-c',
                width: '40'
            },
            {	data: null,
                title: '操作',
                class: 'text-c',
                width: '50',
                render: function ( data, type, row, meta ) {
                    return "<a title=\"通过\" onclick=\"student_dsh_success("+row.student+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e1;</i></a><a title=\"不通过\" onclick=\"student_dsh_error("+row.student+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6dd;</i></a>";
                }
            }
        ]
    });

    //全选
    $('input[name="selectAll"]').on("click",function(){
        if($(this).is(':checked')){
            $('input[name="student_list"]').each(function(){
                $(this).prop("checked",true);
            });
        }else{
            $('input[name="student_list"]').each(function(){
                $(this).prop("checked",false);
            });
        }
    });

    //审核通过
    function student_dsh_success(id) {
        layer.confirm('您确定审核通过吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/user_student_dsh_success_ajax",
                data: {studentId:id},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('审核完成!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('审核错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        });
    }

    //审核不通过
    function student_dsh_error(id) {
        layer.confirm('您确定审核不通过吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/user_student_dsh_error_ajax",
                data: {studentId:id},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('审核完成!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('审核错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        });
    }

    //批量通过
    function student_dsh_success_list() {
        //多选框的值
        var student_ids =[];
        $('input[name="student_list"]:checked').each(function(){
            student_ids.push($(this).val());
        });
        if(student_ids.length === 0){
            layer.msg('请选择需要批量同意的学生!',{icon:5,time:1000});
        }else{
            layer.confirm('确认要批量审核通过吗？',function(){
                $.ajax({
                    type: 'POST',
                    url: "/teacher/user_student_dsh_success_list_ajax",
                    data: {studentIds:student_ids},
                    dataType: "text",
                    success: function(data){
                        if(data=="success"){
                            layer.msg('审核完成!',{
                                icon:1,
                                time:1000,
                                end: function () {
                                    $(".table-sort").DataTable().ajax.reload( null, false );
                                }
                            });
                        }else{
                            layer.msg('审核失败!',{icon:5,time:1000});
                        }
                    },
                    error:function() {
                        layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});
                    }
                });
            });
        }
    }
</script>
</body>
</html>