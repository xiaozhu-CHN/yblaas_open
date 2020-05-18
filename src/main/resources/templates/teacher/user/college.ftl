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
    <title>学院管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> <i class="Hui-iconfont">&#xe62c;</i> 用户管理 <span class="c-gray en">&gt;</span> 学院管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20">  <span class="1"><a href="javascript:;" onclick="college_add()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加学院</a></span> <span class="r"></span></div>
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
            {"orderable":false,"aTargets":[2]}// 制定列不参与排序
        ],
        "ajax":{
            url: '/public/get_college_list_ajax',
            dataSrc: '',
            type: 'GET',
            error:function() {
                layer.msg('权限不足!',{icon:5,time:1000});
            }
        },
        "columns":[
            {	data: 'collegeId',
                title: 'id',
                class: 'text-c',
                width: '50'
            },
            {	data: 'name',
                title: '学院/机构',
                class: 'text-c',
                width: '200'
            },
            {
                data: null,
                title: '操作',
                class: 'text-c',
                width: '70',
                render: function ( data, type, row, meta ) {
                    return "<a title=\"学院任命\" onclick=\"college_teacher('"+row.name+"',"+row.collegeId+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe603;</i></a><a title=\"修改学院\" onclick=\"college_change('"+row.name+"',"+row.collegeId+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a><a title=\"删除学院\" onclick=\"college_del("+row.collegeId+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe609;</i></a>";
                }
            }
        ]
    });

    //任命
    function college_teacher(collegeName,collegeId) {
        layer_show(collegeName,'/teacher/user_college_teacher?collegeId='+collegeId,'','550');
    }

    /*
        弹框新增学院
     */
    function college_add(){
        layer.open({
            type: 1,
            area: ['300px','130px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '新增学院',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>学院:</td><td><input type="text" placeholder="请输入学院名称" id="collegeName" class=".input-text radius" style="width: 200px" /></td></tr>'+
                '<tr class="text-c"><td colspan="2"><button class="btn btn-success radius" onclick="college_add_sumbit();return false;">提交</button>&nbsp;&nbsp;&nbsp;<input class="btn btn-secondary radius layui-layer-close" type="button" onclick="javascript:;" value="取消"></td></tr></table></div>'
        });
    }

    //提交新增学院
    function college_add_sumbit() {
        var collegeName = $("#collegeName").val();
        if(collegeName !=null && collegeName !="" ){
            layer.confirm('您确定新增学院吗?',function(){
                $.ajax({
                    type: "POST",
                    url: "/teacher/user_college_add_ajax",
                    data: {collegeName:collegeName},
                    dataType: "text",
                    success: function(data){
                        if(data=="success"){
                            layer.msg('新增学院成功!',{
                                icon:1,
                                time:1000,
                                end: function () {
                                    layer.closeAll('page');
                                    $(".table-sort").DataTable().ajax.reload( null, false );
                                }
                            });
                        }else{
                            layer.msg('新增学院错误!',{icon:5,time:1000});
                        }
                    },
                    error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
                });
            });
        }else{
            layer.msg('请输入学院名称!',{icon:5,time:1000});
        }
    }

    //修改学院名称
    function college_change(collegeName,collegeId) {
        layer.open({
            type: 1,
            area: ['300px','130px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '修改学院',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>学院:</td><td><input type="text" placeholder="请输入班级名称" id="collegeNameChange" class=".input-text radius" style="width: 200px" value="'+collegeName+'" /></td></tr>'+
                '<tr class="text-c"><td colspan="2"><button class="btn btn-success radius" onclick="college_change_sumbit(\''+collegeName+'\','+collegeId+')">修改</button>&nbsp;&nbsp;&nbsp;<input class="btn btn-secondary radius layui-layer-close" type="button" onclick="javascript:;" value="取消"></td></tr></table></div>'
        });
    }

    //提交学院修改
    function college_change_sumbit(collegeName,collegeId) {
        var collegeName2 = $("#collegeNameChange").val();
        if(collegeName2!=null&&collegeName2!=""&&collegeName2!=collegeName){
            layer.confirm('您确定修改班级名称吗?',function(){
                $.ajax({
                    type: "POST",
                    url: "/teacher/user_college_change_ajax",
                    data: {collegeName:collegeName2,collegeId:collegeId},
                    dataType: "text",
                    success: function(data){
                        if(data=="success"){
                            layer.msg('修改学院名称成功!',{
                                icon:1,
                                time:1000,
                                end: function () {
                                    layer.closeAll('page');
                                    $(".table-sort").DataTable().ajax.reload( null, false );
                                }
                            });
                        }else{
                            layer.msg('修改学院名称错误!',{icon:5,time:1000});
                        }
                    },
                    error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
                });
            });
        }else{
            layer.msg('学院名称错误!',{icon:5,time:1000});
        }
    }

    //删除学院
    function college_del(collegeId) {
        layer.confirm('您确定删除吗?请确保学院内没有师生。',function(){
            $.ajax({
                type: "POST",
                url: "/teacher/user_college_del_ajax",
                data: {collegeId:collegeId},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('删除学院成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('删除学院错误!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        });
    }
</script>
</body>
</html>