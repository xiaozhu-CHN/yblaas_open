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
    <title>权限管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> <i class="Hui-iconfont">&#xe62d;</i> 用户管理 <span class="c-gray en">&gt;</span> 权限管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="admin_permission_dels()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="admin_permission_add('添加权限节点','admin-permission-add.html','','310')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加权限节点</a></span> <span class="r">用户修改角色的权限。数据无价，谨慎操作。</span> </div>
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

    var user_roles = {
        'admin':'超级管理员',
        'student':'学生',
        'teacher':'老师',
        'fdy':'辅导员',
        'xyld':'学院',
        'xgc':'学工处',
        'black':'黑名单'
    };

    /* datatable参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 1, "asc" ]],//默认第几个排序
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            {"orderable":false,"aTargets":[0,3]}// 制定列不参与排序
        ],
        "ajax":{
            url: "/admin/user_permission_ajax",
            type: 'POST',
            error:function() {
                layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});
            }
        },
        "columns": [
            {	data: 'id',
                title: '多选',
                class: 'text-c',
                width: '25',
                render: function ( data, type, row, meta ) {
                    return '<input type="checkbox" name="del_permission_list" value='+data+'>';
                }
            },
            {	data: 'id',
                title: 'ID',
                class: 'text-c',
                width: '40'
            },
            {	data: 'roleName',
                title: '角色名称',
                class: 'text-c',
                width: '200',
                render: function ( data, type, row, meta ) {
                    return user_roles[data];
                }
            },
            { 	data: 'permission',
                title: '权限名称',
                class: 'text-c',
                width: '200'
            },
            { 	data: 'id',
                title: '操作',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    return "<a title=\"编辑\" href=\"javascript:;\" onclick=\"admin_permission_edit('"+data+"','"+row.roleName+"','"+row.permission+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a> <a title=\"删除\" href=\"javascript:;\" onclick=\"admin_permission_del(this,"+data+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>";
                }
            }
        ]
    });

    /*用户管理-权限-添加*/
    var admin_permission_add_layer;
    function admin_permission_add(){
        admin_permission_add_layer = layer.open({
            type: 1,
            area: ['300px','180px'],
            fix: false, //不固定
            title: '添加权限',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>角色:</td><td>'+
                '<span class="select-box">'+
                '<select class="select roles_add" size="1" >'+
                '<option value="student" selected>学生</option>'+
                '<option value="teacher">老师</option>'+
                '<option value="fdy">辅导员</option>'+
                '<option value="xyld">学院</option>'+
                '<option value="xgc">学工处</option>'+
                '<option value="admin">超级管理员</option>'+
                '<option value="black">黑名单</option>'+
                '</select>'+
                '</span>'+
                '</td></tr>'+
                '<tr class="text-c"><td>权限名称:</td><td><input type="text"  placeholder="请输入权限名称！" id="add_permission" class=".input-text radius" /></td><tr>'+
                '<tr class="text-c"><td colspan="2"><input class="btn btn-success radius" type="button" onclick="admin_permission_add_submit()" value="确定">&nbsp;&nbsp;&nbsp;'+
                '<input class="btn btn-secondary radius" type="button" onclick="admin_permission_add_close()" value="取消"></td></tr></table></div>'
        });
    }

    /*对角色的权限新增提交到服务器 */
    function admin_permission_add_submit() {
        var roleName = $(".roles_add").val();
        var permission = $("#add_permission").val();
        if(permission == ''){
            layer.msg('权限名称不能为空！',{icon:5,time:1000});
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/add_user_permission",
                data: {roleName:roleName,permission:permission},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('添加成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                admin_permission_add_close();
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('添加失败!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        }
    }

    /* 关闭用户管理-权限-新增的弹窗*/
    function admin_permission_add_close() {
        layer.close(admin_permission_add_layer);
    }

    /*用户管理-权限-编辑*/
    var admin_permission_edit_layer;
    function admin_permission_edit(id,roleName,permission){
        admin_permission_edit_layer = layer.open({
            type: 1,
            area: ['300px','180px'],
            fix: false, //不固定
            title: '修改编号：'+id+' 权限',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>角色:</td><td>'+
                '<span class="select-box">'+
                '<select class="select roles_edit" size="1" >'+
                '<option value="student">学生</option>'+
                '<option value="teacher">老师</option>'+
                '<option value="fdy">辅导员</option>'+
                '<option value="xyld">学院</option>'+
                '<option value="xgc">学工处</option>'+
                '<option value="admin">超级管理员</option>'+
                '<option value="black">黑名单</option>'+
                '</select>'+
                '</span>'+
                '</td></tr>'+
                '<tr class="text-c"><td>权限名称:</td><td><input type="text" value="'+permission+'" placeholder="请输入权限名称！" id="edit_permission" class=".input-text radius" /></td><tr>'+
                '<tr class="text-c"><td colspan="2"><input class="btn btn-success radius" type="button" onclick="admin_permission_edit_submit('+id+')" value="确定">&nbsp;&nbsp;&nbsp;'+
                '<input class="btn btn-secondary radius" type="button" onclick="admin_permission_edit_close()" value="取消"></td></tr></table></div>'
        });
        $(".roles_edit option[value='"+roleName+"']").attr("selected", true);
    }

    /* 用户管理-权限-编辑的弹窗*/
    function admin_permission_edit_close() {
        layer.close(admin_permission_edit_layer);
    }

    /*对角色的权限更改提交到服务器 */
    function admin_permission_edit_submit(id) {
        var roleName = $(".roles_edit").val();
        var permission = $("#edit_permission").val();
        if(permission == ''){
            layer.msg('权限名称不能为空！',{icon:5,time:1000});
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/change_user_permission",
                data: {id:id,roleName:roleName,permission:permission},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        layer.msg('修改成功!',{
                            icon:1,
                            time:1000,
                            end: function () {
                                admin_permission_edit_close();
                                $(".table-sort").DataTable().ajax.reload( null, false );
                            }
                        });
                    }else{
                        layer.msg('角色权限没有变化!',{icon:5,time:1000});
                    }
                },
                error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
            });
        }
    }

    /*用户管理-权限-单个删除*/
    function admin_permission_del(obj,id){
        layer.confirm('确认要删除吗？',function(){
            $.ajax({
                type: 'POST',
                url: "/admin/del_user_permission",
                data: {id:id},
                dataType: "text",
                success: function(data){
                    if(data=="success"){
                        $(obj).parents("tr").remove();
                        layer.msg('已删除!',{icon:1, time:1000});
                    }else{
                        layer.msg('删除失败!',{icon:5,time:1000});
                    }
                },
                error:function() {
                    layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});
                }
            });
        });
    }


    /*批量删除*/
    function admin_permission_dels() {
        //多选框的值
        var permission_ids =[];
        $('input[name="del_permission_list"]:checked').each(function(){
            permission_ids.push($(this).val());
        });
        if(permission_ids.length === 0){
            layer.msg('请选择需要批量删除的权限!',{icon:5,time:1000});
        }else{
            layer.confirm('确认要删除吗？',function(){
                $.ajax({
                    type: 'POST',
                    url: "/admin/del_user_permissions",
                    data: {ids:permission_ids},
                    dataType: "text",
                    success: function(data){
                        if(data=="success"){
                            layer.msg('已删除!',{icon:1, time:1000});
                            $(".table-sort").DataTable().ajax.reload( null, false );
                        }else{
                            layer.msg('删除失败!',{icon:5,time:1000});
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