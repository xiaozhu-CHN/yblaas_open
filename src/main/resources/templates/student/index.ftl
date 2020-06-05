<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>请假首页</title>
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
        <button class="button button-link button-nav pull-left" onclick="exit()">
            <span class="icon icon-left"></span>
            退出
        </button>
        <a class="icon icon-edit pull-right external" href="/student/leave_new_leave"></a>
        <h1 class="title">${(yblaas.title)!}</h1>
    </header>
    <!--工具栏 -->
    <nav class="bar bar-tab">
        <a class="tab-item active external" href="/student/index">
            <span class="icon icon-me"></span>
            <span class="tab-label">请假</span>
        </a>
        <a class="tab-item external" href="/student/attendance_index">
            <span class="icon icon-clock"></span>
            <span class="tab-label">考勤</span>
        </a>
        <a class="tab-item" href="/student/my_index">
            <span class="icon icon-menu"></span>
            <span class="tab-label">我的</span>
        </a>
    </nav>
    <div class="content">
        <!-- 这里是页面内容区 -->
        <div class="weui-tab">
            <div class="weui-navbar">
                <div class="weui-navbar__item weui-navbar__item--on" href="#tab1" id="leave_all">
                    全部
                </div>
                <div class="weui-navbar__item" href="#tab2" id="leave_examine">
                    待审核
                </div>
                <div class="weui-navbar__item" href="#tab3" id="leave_xj">
                    待销假
                </div>
            </div>
            <div class="weui-tab__bd">
                <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active weui-pull-to-refresh">
                    <div class="weui-pull-to-refresh__layer">
                        <div class='weui-pull-to-refresh__arrow'></div>
                        <div class='weui-pull-to-refresh__preloader'></div>
                        <div class="down">下拉刷新</div>
                        <div class="up">释放刷新</div>
                        <div class="refresh">正在刷新</div>
                    </div>
                    <br />
                    <div id="card_list_all">

                    </div>
                    <div class="weui-loadmore" id="loadmore_all">
                        <i class="weui-loading"></i>
                        <span class="weui-loadmore__tips">正在加载</span>
                    </div>
                </div>
                <div id="tab2" class="weui-tab__bd-item weui-pull-to-refresh">
                    <div class="weui-pull-to-refresh__layer">
                        <div class='weui-pull-to-refresh__arrow'></div>
                        <div class='weui-pull-to-refresh__preloader'></div>
                        <div class="down">下拉刷新</div>
                        <div class="up">释放刷新</div>
                        <div class="refresh">正在刷新</div>
                    </div>
                    <br />
                    <div id="card_list_examine">

                    </div>
                    <div class="weui-loadmore" id="loadmore_examine">
                        <i class="weui-loading"></i>
                        <span class="weui-loadmore__tips">正在加载</span>
                    </div>
                </div>
                <div id="tab3" class="weui-tab__bd-item weui-pull-to-refresh">
                    <div class="weui-pull-to-refresh__layer">
                        <div class='weui-pull-to-refresh__arrow'></div>
                        <div class='weui-pull-to-refresh__preloader'></div>
                        <div class="down">下拉刷新</div>
                        <div class="up">释放刷新</div>
                        <div class="refresh">正在刷新</div>
                    </div>
                    <br />
                    <div id="card_list_xj">

                    </div>
                    <div class="weui-loadmore" id="loadmore_xj">
                        <i class="weui-loading"></i>
                        <span class="weui-loadmore__tips">正在加载</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>

    </script>
    <script type="text/javascript" src="/weui/js/jquery.min.js"></script>
    <script type="text/javascript" src="/weui/js/jquery-weui.min.js"></script>s
    <script type="text/javascript" src="/light7/js/light7.min.js"></script>
    <!--导入light7中文语言包-->
    <script type="text/javascript" src="/light7/js/light7-cn.min.js"></script>
    <script type="text/javascript" src="/weui/js/jquery-form.min.js"></script>

    <script>

        var start_all=0;
        var start_examine = 0;
        var start_xj=0;
        var length = 10;

        $(function(){
            //初始化滚动插件
            $("#tab1").infinite();
            $("#tab2").infinite();
            $("#tab3").infinite();
            //让全部假条的卡片自适应页面代码
            var browser_width = $(document.body).width()*0.975;
            $("#card_list_all").css("width",browser_width);
            $("#card_list_examine").css("width",browser_width);
            $("#card_list_xj").css("width",browser_width);
            //首次进入加载全部假条
            ajaxLeaveListAll(1);
            setTimeout(function () {
                ajaxLeaveListAll(2);
            },1000);
            setTimeout(function () {
                ajaxLeaveListAll(3);
            },1500);
            $(window).resize(function() {
                browser_width = $(document.body).width()*0.975;
                $("#card_list_all").css("width",browser_width);
                $("#card_list_examine").css("width",browser_width);
                $("#card_list_xj").css("width",browser_width);
            });
        });

        /*全部假条的下拉刷新 */
        $("#tab1").pullToRefresh().on("pull-to-refresh",function () {
            setTimeout(function() {
                start_all = 0;
                $("#card_list_all .card").remove();
                $("#msg_all").remove();
                ajaxLeaveListAll(1);
                $.toast("刷新成功");
                $("#tab1").pullToRefreshDone();
                $("#loadmore_all").show();
                $("#tab1").infinite();
            }, 2000);
        });

        /*全部假条滚动刷新*/
        var loadingAll = false;  //状态标记
        $("#tab1").infinite().on("infinite", function() {
            if(loadingAll) return;
            loadingAll = true;
            setTimeout(function() {
                ajaxLeaveListAll(1);
                loadingAll = false;
            }, 1500);   //模拟延迟
        });

        /*待审核的下拉刷新 */
        $("#tab2").pullToRefresh().on("pull-to-refresh", function() {
            setTimeout(function() {
                start_examine = 0;
                $("#card_list_examine .card").remove();
                $("#msg_examine").remove();
                ajaxLeaveListAll(2);
                $.toast("刷新成功");
                $("#tab2").pullToRefreshDone();
                $("#loadmore_examine").show();
                $("#tab2").infinite();
            }, 2000);
        });

        /*待审核滚动刷新*/
        var loadingExamine = false;  //状态标记
        $("#tab2").infinite().on("infinite", function() {
            if(loadingExamine) return;
            loadingExamine = true;
            setTimeout(function() {
                ajaxLeaveListAll(2);
                loadingExamine = false;
            }, 1500);   //模拟延迟
        });

        /*待销假的下拉刷新 */
        $("#tab3").pullToRefresh().on("pull-to-refresh", function() {
            setTimeout(function() {
                start_xj = 0;
                $("#card_list_xj .card").remove();
                $("#msg_xj").remove();
                ajaxLeaveListAll(3);
                $.toast("刷新成功");
                $("#tab3").pullToRefreshDone();
                $("#loadmore_xj").show();
                $("#tab3").infinite();
            }, 2000);
        });

        /*待销假滚动刷新*/
        var loadingXj = false;  //状态标记
        $("#tab3").infinite().on("infinite", function() {
            if(loadingXj) return;
            loadingXj = true;
            setTimeout(function() {
                ajaxLeaveListAll(3);
                loadingXj = false;
            }, 1500);   //模拟延迟
        });

        /*假条的ajax请求*/
        //tabNumber=1 全部类型
        //tabNumber=2 待审核
        //tabNumber=3 待销假
        function ajaxLeaveListAll(tabNumber) {
            var start = 0;
            if(tabNumber === 1){
                start = start_all;
            }
            if(tabNumber === 2){
                start = start_examine;
            }
            if(tabNumber === 3){
                start = start_xj;
            }
            $.ajax({
                type: "POST",
                url: "/student/index_ajax",
                data: {start:start,length:length,number:tabNumber},
                dataType: "json",
                success: function(data){
                    start = start +length;
                    if(tabNumber === 1){
                        start_all = start;
                        $("#navbar_all").remove();
                        $("#leave_all").append("<span id=\"navbar_all\">("+data["recordsTotal"]+")</span>");
                    }
                    if(tabNumber === 2){
                        start_examine = start;
                        $("#navbar_examine").remove();
                        $("#leave_examine").append("<span id=\"navbar_examine\">("+data["recordsTotal"]+")</span>");
                    }
                    if(tabNumber === 3){
                        start_xj = start;
                        $("#navbar_xj").remove();
                        $("#leave_xj").append("<span id=\"navbar_xj\">("+data["recordsTotal"]+")</span>");
                    }
                    var strHtml='';
                    if(data["data"] !=null && data["data"].length !== 0){
                        for (var i=0;i<data["data"].length;i++){
                            var d1 = new Date(data["data"][i].timeStart);
                            var d2 = new Date(data["data"][i].timeEnd);
                            strHtml+= '<div class="card"><div class="card-header">假条编号：'+data["data"][i].id+'</div>';
                            strHtml+= '<div class="card-content"><div class="card-content-inner">开始时间：  '+d1.getFullYear()+'年'+(d1.getMonth()+1)+'月'+d1.getDate()+'日<br/>';
                            strHtml+= '结束时间：  '+d2.getFullYear()+'年'+(d2.getMonth()+1)+'月'+d2.getDate()+'日<br/>';
                            strHtml+= '请假天数：  '+data["data"][i].day+'<br/>';
                            strHtml+= '</div></div>';
                            strHtml+= '<div class="card-footer"><span>'+data["data"][i].timeChange+'</span><a href="/student/leave_query_leave?leaveId='+data["data"][i].id+'" class="link">更多信息</a></div></div>';
                        }
                        if(tabNumber === 1){
                            $("#card_list_all").append(strHtml);
                        }
                        if(tabNumber === 2){
                            $("#card_list_examine").append(strHtml);
                        }
                        if(tabNumber === 3){
                            $("#card_list_xj").append(strHtml);
                        }
                    }
                    if(start>=data["recordsTotal"]){
                        if(tabNumber === 1){
                            $("#card_list_all").append('<div class="weui-loadmore weui-loadmore_line" id="msg_all"><span class="weui-loadmore__tips">无更多数据</span></div>');
                            $("#tab1").destroyInfinite();
                            $("#loadmore_all").hide();
                        }
                        if(tabNumber === 2){
                            $("#card_list_examine").append('<div class="weui-loadmore weui-loadmore_line" id="msg_examine"><span class="weui-loadmore__tips">无更多数据</span></div>');
                            $("#tab2").destroyInfinite();
                            $("#loadmore_examine").hide();
                        }
                        if(tabNumber === 3){
                            $("#card_list_xj").append('<div class="weui-loadmore weui-loadmore_line" id="msg_xj"><span class="weui-loadmore__tips">无更多数据</span></div>');
                            $("#tab3").destroyInfinite();
                            $("#loadmore_xj").hide();
                        }
                    }
                },
                error:function() { $.toast("服务器错误");}
            });
        }

        function exit() {
            window.location.replace("/student/exit");
        }
    </script>
</body>
</html>