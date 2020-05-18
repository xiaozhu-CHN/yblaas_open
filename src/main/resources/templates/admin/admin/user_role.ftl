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
    <title>角色管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> <i class="Hui-iconfont">&#xe62d;</i> 用户管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
<#--        <form class="Huiform" method="post" action="" target="_self">-->
            <input type="text" class="input-text" style="width:250px" placeholder="易班ID" id="search_var">
            <button type="submit" class="btn btn-success" id="userIdSearch" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
<#--        </form>-->
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">此数据表用于Shiro的角色分配。数据无价，谨慎操作。</span> </div>
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
    /*
        参数解释：
        title	标题
        url		请求的url
        id		需要操作的数据id
        w		弹出层宽度（缺省调默认值）
        h		弹出层高度（缺省调默认值）
    */

    var user_roles = {
        'admin':'超级管理员',
        'student':'学生',
        'teacher':'老师',
        'fdy':'辅导员',
        'xyld':'学院',
        'xgc':'学工处',
        'black':'黑名单'
    };

    /* 表格参数*/
    $(".table-sort").dataTable({
        "aaSorting": [[ 0, "desc" ]],//默认第几个排序
        "searching": false, //是否显示搜索框
        "bStateSave": true,//状态保存
        "autoWidth": true,//自动调整列宽
        "processing": true, //获取过程中出现加载指示
        "serverSide":true,
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            {"orderable":false,"aTargets":[3]}// 制定列不参与排序
        ],
        "ajax":{
            url: "/admin/user_role_ajax",
            type: 'POST',
            data: function (param) {
                param.userId = $("#search_var").val();
                param.column = param.order[0].column;
                param.dir = param.order[0].dir;
                return param;
            },
            error:function() {
                layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});
            }
        },
        "columns": [
            {	data: 'id',
                title: 'ID',
                class: 'text-c',
                width: '40',
            },
            {	data: 'userId',
                title: '易班ID',
                class: 'text-c ybcolum',
                width: '200',
            },
            { 	data: 'roleName',
                title: '角色名称',
                class: 'text-c',
                width: '200',
                render: function ( data, type, row, meta ) {
                    return user_roles[data];
                }
            },
            { 	data: 'id',
                title: '操作',
                class: 'text-c',
                width: '100',
                render: function ( data, type, row, meta ) {
                    return "<a title=\"编辑\" href=\"javascript:;\" onclick=\"admin_roles_edit('"+data+"','"+row.roleName+"','"+row.userId+"')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>";
                }
            },
        ],
    });

    /* 搜索栏 */
    $("#userIdSearch").click(function () {
        $(".table-sort").DataTable().draw();
        layer.msg('搜索完成!',{icon:1,time:1000});
    });

    /*修改角色弹出框 */
    var admin_roles_edit_layer;

    /*
        修改角色弹出框的方法
     */
    function admin_roles_edit(data,roleName,userId) {
        admin_roles_edit_layer = layer.open({
            type: 1,
            area: ['300px','140px'],
            fix: false, //不固定
            title: '修改'+userId+'角色',
            content: '<div><table class="table table-border table-bordered"><tr class="text-c"><td>角色:</td><td>'+
                        '<span class="select-box">'+
                            '<select class="select roles_select" size="1" >'+
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
                '<tr class="text-c"><td colspan="2"><input class="btn btn-success radius" type="button" onclick="admin_roles_edit_submit('+data+')" value="确定">&nbsp;&nbsp;&nbsp;'+
                '<input class="btn btn-secondary radius" type="button" onclick="admin_roles_edit_close()" value="取消"></td></tr></table></div>'
        });
        $(".roles_select option[value='"+roleName+"']").attr("selected", true);
    }

    /*关闭角色修改弹出框 */
    function admin_roles_edit_close() {
        layer.close(admin_roles_edit_layer);
    }

    /*对用户的角色更改提交到服务器 */
    function admin_roles_edit_submit(id) {
        var roleName = $(".roles_select").val();
        $.ajax({
            type: "POST",
            url: "/admin/change_user_role_name",
            data: {id:id,roleName:roleName},
            dataType: "text",
            success: function(data){
                if(data=="success"){
                    layer.msg('修改成功!',{
                        icon:1,
                        time:1000,
                        end: function () {
                            admin_roles_edit_close();
                            $(".table-sort").DataTable().ajax.reload( null, false );
                        }
                    });
                }else{
                    layer.msg('用户角色没有变化!',{icon:5,time:1000});
                }
            },
            error:function() { layer.msg('服务器错误,请稍后再试!',{icon:5,time:1000});}
        });
    }
</script>
</body>
</html>