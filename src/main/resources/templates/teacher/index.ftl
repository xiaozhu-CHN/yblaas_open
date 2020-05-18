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
    <title>老师端</title>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/teacher/index">${(yblaas.title)!}</a>
            <a class="logo navbar-logo-m f-l mr-10 visible-xs">${(yblaas.title)!}</a>
            <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>
                        <#if roles ?? &&  roles=="teacher">老师</#if>
                        <#if roles ?? &&  roles=="fdy">辅导员</#if>
                        <#if roles ?? &&  roles=="xyld">学院</#if>
                        <#if roles ?? &&  roles=="xgc">学工处</#if>
                    </li>
                    <li class="dropDown dropDown_hover">
                        <a href="#" class="dropDown_A">${teacherName!} <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a onclick="teacher_data()"><i class="Hui-iconfont">&#xe60d;</i> 个人信息</a></li>
                            <li><a href="/teacher/exit">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <div class="menu_dropdown bk_2">
        <dl id="menu-leave">
            <dt><i class="Hui-iconfont">&#xe639;</i> 请假管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <#if roles ?? &&  roles=="teacher">
                        <li><a data-href="/teacher/leave_get_leave_number_id" data-title="假条查询" href="javascript:void(0)">假条查询</a></li>
                    <#else>
                        <li><a data-href="/teacher/leave_dsh" data-title="待审核" href="javascript:void(0)">待审核</a></li>
                        <li><a data-href="/teacher/leave_dxj" data-title="待销假" href="javascript:void(0)">待销假</a></li>
                        <li><a data-href="/teacher/leave_all" data-title="全部假条" href="javascript:void(0)">全部假条</a></li>
                        <li><a data-href="/teacher/leave_get_leave_number_id" data-title="假条查询" href="javascript:void(0)">假条查询</a></li>
                    </#if>
                </ul>
            </dd>
        </dl>
        <dl id="menu-attendance">
            <dt><i class="Hui-iconfont">&#xe627;</i> 考勤管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a onClick="javascript:window.location.replace('/teacher/attendance_add');" >发起考勤</a></li>
                    <li><a data-href="/teacher/attendance_dkq" data-title="待考勤" href="javascript:void(0)">待考勤</a></li>
                    <li><a data-href="/teacher/attendance_all" data-title="全部考勤" href="javascript:void(0)">全部考勤</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-user">
            <dt><i class="Hui-iconfont">&#xe62c;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <#if roles ?? &&  roles=="fdy">
                        <li><a data-href="/teacher/user_student_dsh" data-title="学生审核" href="javascript:void(0)">学生审核</a></li>
                        <li><a data-href="/teacher/user_eclass_fdy" data-title="班级查看" href="javascript:void(0)">班级查看</a></li>
                    </#if>
                    <#if roles ?? &&  roles=="xyld">
                        <li><a data-href="/teacher/user_eclass_xy" data-title="班级管理" href="javascript:void(0)">班级管理</a></li>
                    </#if>
                    <#if roles ?? &&  roles=="xgc">
                        <li><a data-href="/teacher/user_college" data-title="学院管理" href="javascript:void(0)">学院管理</a></li>
                    </#if>
                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active">
                    <span title="我的桌面" data-href="/teacher/welcome">我的桌面</span>
                    <em></em></li>
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="/teacher/welcome"></iframe>
        </div>
    </div>
</section>

<div class="contextMenu" id="Huiadminmenu">
    <ul>
        <li id="closethis">关闭当前 </li>
        <li id="closeall">关闭全部 </li>
    </ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/hui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/hui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/hui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/hui/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/hui/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">

    /*个人信息*/
    function teacher_data(){
        layer.open({
            type: 2,
            area: ['800px', '460px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '个人信息',
            content: '/teacher/my_data',
            shadeClose:true
            // end: function () {
            //     location.replace(location.href);
            // }
        });
    }
</script>
</body>
</html>