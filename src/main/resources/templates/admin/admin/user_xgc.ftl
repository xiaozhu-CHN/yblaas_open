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

    <title>学工任命</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> <i class="Hui-iconfont">&#xe62d;</i> 用户管理 <span class="c-gray en">&gt;</span> 学工任命 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<article class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">注意：只展示“老师”和“学工处”权限的老师。</span> </div>
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
<script type="text/javascript">

    var user_roles = {
        'teacher':'老师',
        'xgc':'学工处'
    };

    /* datatable参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 0, "asc" ]],//默认第几个排序
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "aoColumnDefs": [
            {"orderable":false,"aTargets":[3]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/admin/user_xgc_ajax',
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
                width: '150'
            },
            {	data: 'teacherTell',
                title: '电话',
                class: 'text-c',
                width: '200'
            },
            {	data: 'role',
                title: '权限',
                class: 'text-c',
                width: '150',
                render: function ( data, type, row, meta ) {
                    return user_roles[data];
                }
            },
            { 	data: 'teacher',
                title: '操作',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    if(row.role == "xgc"){
                        return "<a title=\"取消任命\" onclick=\"xgc_teacher_del('" + data + "')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe631;</i></a>";
                    }else{
                        return "<a title=\"任命\" onclick=\"xgc_teacher_add('" + data + "')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe603;</i></a>";
                    }
                }
            }
        ]
    });

    //任命
    function xgc_teacher_add(teacher) {
        layer.confirm('您确定任命吗?',function(){
            $.ajax({
                type: "POST",
                url: "/admin/user_xgc_add",
                data: {teacherId:teacher},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('任命成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('任命错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        });
    }

    //取消任命
    function xgc_teacher_del(teacher) {
        layer.confirm('您确定取消任命吗?',function(){
            $.ajax({
                type: "POST",
                url: "/admin/user_xgc_del",
                data: {teacherId:teacher},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('取消任命成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('取消任命错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        });
    }
</script>
</body>
</html>