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

    <title>班级列表</title>
</head>
<body>
<article class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">注意：学生个人信息审核通过才在如下列表</span> </div>
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

    var eclassId = parseInt(${eclassId!0});

    /* datatable参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 1, "asc" ]],//默认第几个排序
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "aoColumnDefs": [
            {"orderable":false,"aTargets":[3]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/teacher/user_eclass_info_ajax',
            data:{eclassId:eclassId},
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
            { 	data: 'sex',
                title: '性别',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'student',
                title: '操作',
                class: 'text-c',
                width: '60',
                render: function ( data, type, row, meta ) {
                    return "<a title=\"移除\" onclick=\"student_info_del('"+data+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe609;</i></a>";
                }
            }
        ]
    });

    //移除班级学生
    function student_info_del(student) {
        layer.confirm('您确定移除吗?',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/user_eclass_info_del_ajax",
                data: {studentId:student},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('移除完成!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('移除错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        });
    }
</script>
</body>
</html>