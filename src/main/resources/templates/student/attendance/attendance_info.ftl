<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>考勤详情</title>
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
        <h1 class="title">考勤编号：${(attendance.id)!}</h1>
    </header>
    <!--表单 -->
    <div class="content native-scroll">
        <div class="content">
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">学院</div>
                                <div class="item-input">
                                    ${(attendance.collegeName)!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">班级</div>
                                <div class="item-input">
                                    ${(attendance.eclassName)!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">老师</div>
                                <div class="item-input">
                                    ${(attendance.teacherName)!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">标题</div>
                                <div class="item-input">
                                    ${(attendance.name)!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">备注</div>
                                <div class="item-input">
                                    ${(attendance.beiz)!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">开始时间</div>
                                <div class="item-input">
                                    ${(attendance.timeStart?string("yyyy-MM-dd HH:mm:ss"))!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">结束时间</div>
                                <div class="item-input">
                                    ${(attendance.timeEnd?string("yyyy-MM-dd HH:mm:ss"))!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">发起时间</div>
                                <div class="item-input">
                                    ${(attendance.timeChange?string("yyyy-MM-dd HH:mm:ss"))!}
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">考勤状态</div>
                                <div class="item-input">
                                    <#if (attendanceTime) ?? >
                                        已考勤
                                    <#else>
                                        未考勤
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </li>
                    <#if (attendanceTime) ?? >
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">考勤时间</div>
                                    <div class="item-input">
                                        ${(attendanceTime?string("yyyy-MM-dd HH:mm:ss"))!"未参与考勤"}
                                    </div>
                                </div>
                            </div>
                        </li>
                    </#if>
                </ul>
            </div>
            <div class="content-block">
                <div class="weui-btn-area">
                    <#if (attendanceTime) ?? >
                    <#else>
                        <#if (attendance.timeEnd) ?? && (attendance.timeStart) ?? && (attendance.state) ??>
                            <#if attendance.state =="1" && .now?datetime lte (attendance.timeEnd?datetime) && .now?datetime gte (attendance.timeStart?datetime)>
                                <a class="weui-btn weui-btn_primary external" href="/student/attendance_info_location?attendanceId=${(attendance.id)!}">前往考勤</a>
                            </#if>
                        </#if>
                    </#if>
                    <a href="/student/attendance_index" class="weui-btn weui-btn_default external">返回考勤首页</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/weui/js/jquery.min.js"></script>
<script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>s
<script type="text/javascript" src="/light7/js/light7.min.js"></script>
<!--导入light7中文语言包-->
<script type="text/javascript" src="/light7/js/light7-cn.min.js"></script>

<script>
</script>
</body>
</html>