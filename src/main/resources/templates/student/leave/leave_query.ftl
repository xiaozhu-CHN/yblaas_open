<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>查看假条</title>
    <link href="/light7/css/light7.min.css" rel="stylesheet">
    <link href="/light7/css/light7-swiper.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/weui/css/weui.min.css">
    <link rel="stylesheet" href="/weui/css/jquery-weui.min.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
</head>
<body>
<div class="page">
    <!-- 标题栏 -->
    <header class="bar bar-nav">
        <a class="icon icon-left pull-left external" href="javascript:history.go(-1)"></a>
        <a class="icon icon-refresh pull-right external" href="javascript:location.reload()"></a>
        <h1 class="title">假条编号：${leave.id!}</h1>
    </header>
    <!--表单 -->
    <div class="content native-scroll">
        <div class="content-block">
            <div class="list-block">
                    <ul>
                        <!-- name -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">姓名</div>
                                    <div class="item-input">
                                        ${leave.studentName!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 学院 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">学院</div>
                                    <div class="item-input">
                                        ${leave.collegeName!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 班级 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">班级</div>
                                    <div class="item-input">
                                        ${leave.eclassName!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 性别 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">性别</div>
                                    <div class="item-input">
                                        ${leave.sex!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 学号 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">学号</div>
                                    <div class="item-input">
                                        ${leave.numberId!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假开始时间 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">开始时间</div>
                                    <div class="item-input">
                                        ${(leave.timeStart?string("yyyy年MM月dd日"))!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假结束时间 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">结束时间</div>
                                    <div class="item-input">
                                        ${(leave.timeEnd?string("yyyy年MM月dd日"))!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假天数 -->
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">请假天数</div>
                                    <div class="item-input">
                                        ${leave.day!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假去向 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">请假去向</div>
                                    <div class="item-input">
                                        ${leave.whereabouts!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 请假事由 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">请假事由</div>
                                    <div class="item-input">
                                        ${leave.cause!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 假条状态 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">假条状态</div>
                                    <div class="item-input">
                                        <#if leave.state ?? &&  leave.state=="0">待辅导员审核</#if>
                                        <#if leave.state ?? &&  leave.state=="1">辅导员审核不通过</#if>
                                        <#if leave.state ?? &&  leave.state=="2">待学院审核</#if>
                                        <#if leave.state ?? &&  leave.state=="3">学院审核不通过</#if>
                                        <#if leave.state ?? &&  leave.state=="4">待学工处审核</#if>
                                        <#if leave.state ?? &&  leave.state=="5">学工处审核不通过</#if>
                                        <#if leave.state ?? &&  leave.state=="6">审核通过待销假</#if>
                                        <#if leave.state ?? &&  leave.state=="7">已完成</#if>
                                        <#if leave.state ?? &&  leave.state=="10">已取消</#if>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <!-- 假条流程 -->
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">假条流程</div>
                                    <div class="item-input">
                                        <div class="weui-cells__tips">
                                            ${(leave.timeChange?string("yyyy-MM-dd HH:mm:ss"))!}：发起假条申请<br/>
                                            <#if (leave.fdyName) ??>${(leave.fdyTime?string("yyyy-MM-dd HH:mm:ss"))!}:辅导员${leave.fdyName!}审核完成<br/></#if>
                                            <#if (leave.xyldName) ??>${(leave.xyldTime?string("yyyy-MM-dd HH:mm:ss"))!}:学院领导${leave.xyldName!}审核完成<br/></#if>
                                            <#if (leave.xgcName) ??>${(leave.xgcTime?string("yyyy-MM-dd HH:mm:ss"))!}:学工处${leave.xgcName!}审核完成<br/></#if>
                                            <#if (leave.xjName) ??>${(leave.xjtTime?string("yyyy-MM-dd HH:mm:ss"))!}:${leave.xjName!}老师已帮销假<br/></#if>
                                            <#if (leave.state) ?? &&  (leave.state)=="7">假条流程结束</#if>
                                            <#if (leave.state) ?? &&  (leave.state)=="10">学生取消申请请假</#if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
            </div>
        </div>
        <#if (leave.state) ?? &&  (leave.state=="0" || leave.state=="1" || leave.state=="4")>
            <div class="content-block">
                <div class="weui-btn-area">
                    <button class="weui-btn weui-btn_warn external" onclick="javascript:$.confirm('您确定要取消请假申请吗?','确认取消', function() {window.location.replace('/student/cancel_leave?leaveId='+${leave.id!0});});">取消</button>
                </div>
            </div>
        </#if>

    </div>
</div>
<script>

</script>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>s
<script type="text/javascript" src="/light7/js/light7.min.js"></script>
<!--导入light7中文语言包-->
<script type="text/javascript" src="/light7/js/light7-cn.min.js"></script>
<script>

</script>
</body>
</html>

