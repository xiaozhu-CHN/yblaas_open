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

    <title>学院老师</title>
</head>
<body>
<article class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">注意：只展示“老师”和“学院”权限的老师。</span> </div>
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

    var collegeId = parseInt(${collegeId!0});

    var user_roles = {
        'teacher':'老师',
        'xyld':'学院'
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
            url: '/teacher/user_college_teacher_ajax',
            data:{collegeId:collegeId},
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
                    if(row.role == "xyld"){
                        return "<a title=\"取消任命\" onclick=\"college_teacher_del('" + data + "')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe631;</i></a>";
                    }else{
                        return "<a title=\"任命\" onclick=\"college_teacher_add('" + data + "')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe603;</i></a>";
                    }
                }
            }
        ]
    });

    //任命
    function college_teacher_add(teacher) {
        layer.confirm('您确定任命吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/user_college_teacher_xyld_ajax",
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
    function college_teacher_del(teacher) {
        layer.confirm('您确定取消任命吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/user_college_teacher_teacher_ajax",
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