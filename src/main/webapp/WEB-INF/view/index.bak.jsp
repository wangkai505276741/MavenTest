<%@page import="com.wujiepayment.been.SysUserInf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>无界智慧金融管理平台</title>
    <link href="scripts/ui/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="images/style.css" rel="stylesheet" type="text/css" />
    <script src="scripts/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="scripts/ui/js/ligerBuild.min.js" type="text/javascript"></script>
    <script src="js/function.js" type="text/javascript"></script>
	
    <script type="text/javascript">
        var tab = null;
        var accordion = null;
        var tree = null;
        $(document).ready(function () {
            //页面布局
            $("#global_layout").ligerLayout({ leftWidth: 180, height: '100%', topHeight: 65, bottomHeight: 24, allowTopResize: false, allowBottomResize: false, allowLeftCollapse: true, onHeightChanged: f_heightChanged });
            var height = $(".l-layout-center").height();
            //Tab
            $("#framecenter").ligerTab({ height: height });
            //左边导航面板
            $("#global_left_nav").ligerAccordion({ height: height - 25, speed: null });
            $(".l-link").hover(function () {
                $(this).addClass("l-link-over");
            }, function () {
                $(this).removeClass("l-link-over");
            });

            ////设置频道菜单
            //$("#global_channel_tree").ligerTree({
            //    url: '../ashx/o-menu.ashx?menu=pos&time=' + Math.random(),
            //    checkbox: false,
            //    nodeWidth: 135,
            //    //attribute: ['nodename', 'url'],
            //    onSelect: function (node) {
            //        if (!node.data.url) return;
            //        var tabid = $(node.target).attr("tabid");
            //        if (!tabid) {
            //            tabid = new Date().getTime();
            //            $(node.target).attr("tabid", tabid)
            //        }
            //        f_addTab(tabid, node.data.text, node.data.url);
            //    }
            //});
            //设置频道菜单
            $("#global_epos_tree").ligerTree({
                //url: '../ashx/o-menu.ashx?menu=epos&time=' + Math.random(),
                url: 'getMenuSet.do?menuId=1',
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });



            //设置虚拟pos
            $("#global_dang_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=dang&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            //设置频道菜单
            $("#global_hetong_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=hetong&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });



            //设置频道菜单
            $("#global_errfina_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=errfina&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            //设置频道菜单
            $("#global_user_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=user&t=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            ////进销存
            //$("#global_goods_tree").ligerTree({
            //    url: '../ashx/o-menu.ashx?menu=goods&time=' + Math.random(),
            //    checkbox: false,
            //    nodeWidth: 135,
            //    //attribute: ['nodename', 'url'],
            //    onSelect: function (node) {
            //        if (!node.data.url) return;
            //        var tabid = $(node.target).attr("tabid");
            //        if (!tabid) {
            //            tabid = new Date().getTime();
            //            $(node.target).attr("tabid", tabid)
            //        }
            //        f_addTab(tabid, node.data.text, node.data.url);
            //    }
            //});

            //设置频道菜单 -- 互联网支付管理
            $("#global_epay_tree").ligerTree({
               url: '../ashx/o-menu.ashx?menu=epay&time=' + Math.random(),
                checkbox: false,
               nodeWidth: 135,
                //attribute: ['nodename', 'url'],
               onSelect: function (node) {
                    if (!node.data.url) return;
                   var tabid = $(node.target).attr("tabid");
                   if (!tabid) {
                       tabid = new Date().getTime();
                     $(node.target).attr("tabid", tabid)
                   }
                   f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            //设置频道菜单 -- 互联网支付管理
            $("#global_vip_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=vip&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });



            //设置频道菜单
            $("#global_mpay_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=mpay&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            //设置频道菜单
            $("#global_fina_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=fina&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            ////设置频道菜单
            $("#global_plug_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=plug&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });


            //设置频道菜单
            $("#global_work_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=work&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            //设置频道菜单
            $("#global_fenrui_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=fenrun',
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });

            //设置频道菜单
            $("#global_sys_tree").ligerTree({
                url: '../ashx/o-menu.ashx?menu=sys&time=' + Math.random(),
                checkbox: false,
                nodeWidth: 135,
                //attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });


            //快捷菜单
            var menu = $.ligerMenu({
                width: 120, items:
                [
                    { text: '管理首页', click: itemclick },
                    { text: '修改密码', click: itemclick },
                    { line: true },
                    { text: '关闭菜单', click: itemclick }
                ]
            });
            $("#tab-tools-nav").bind("click", function () {
                var offset = $(this).offset(); //取得事件对象的位置
                menu.show({ top: offset.top + 27, left: offset.left - 120 });
                return false;
            });

            tab = $("#framecenter").ligerGetTabManager();
            accordion = $("#global_left_nav").ligerGetAccordionManager();
            tree = $("#global_epos_tree").ligerGetTreeManager();
            //tree.expandAll(); //默认展开所有节点
            $("#pageloading_bg,#pageloading").hide();
        });


        //快捷菜单回调函数
        function itemclick(item) {
            switch (item.text) {
                case "管理首页":
                    f_addTab('home', '管理中心', 'center.aspx');
                    break;
                case "快捷导航":
                    //调用函数
                    break;
                case "修改密码":
                    f_addTab('manager_pwd', '修改密码', 'user/sys/useradd.aspx?action=edit&id=1');
                    break;
                default:
                    //关闭窗口
                    break;
            }
        }
        function f_heightChanged(options) {
            if (tab)
                tab.addHeight(options.diff);
            if (accordion && options.middleHeight - 24 > 0)
                accordion.setHeight(options.middleHeight - 24);
        }
        //添加Tab，可传3个参数
        function f_addTab(tabid, text, url, iconcss) {
            if (arguments.length == 4) {
                tab.addTabItem({ tabid: tabid, text: text, url: url, iconcss: iconcss });
            } else {
                tab.addTabItem({ tabid: tabid, text: text, url: url });
            }
        }
        //提示Dialog并关闭Tab
        function f_errorTab(tit, msg) {
            $.ligerDialog.open({
                isDrag: false,
                allowClose: false,
                type: 'error',
                title: tit,
                content: msg,
                buttons: [{
                    text: '确定',
                    onclick: function (item, dialog, index) {
                        //查找当前iframe名称
                        var itemiframe = "#framecenter .l-tab-content .l-tab-content-item";
                        var curriframe = "";
                        $(itemiframe).each(function () {
                            if ($(this).css("display") != "none") {
                                curriframe = $(this).attr("tabid");
                                return false;
                            }
                        });
                        if (curriframe != "") {
                            tab.removeTabItem(curriframe);
                            dialog.close();
                        }
                    }
                }]
            });
        }

    </script>

</head>
<body style="padding: 0px;">
	<%String s = session.getId(); //获取session ID号  %>
	<%SysUserInf sysUserInf = (SysUserInf)session.getAttribute("sysUserInf"); //获取用户信息  %>
    <form id="form1">
        <div class="pageloading_bg" id="pageloading_bg"></div>
        <%--    <div id="pageloading">数据加载中，请稍等...</div>--%>
        <div id="global_layout" class="layout" style="width: 100%">
            <!--头部-->
            <div position="top" class="header">
                <div class="header_box">
                    <div class="header_right">
                        <span>
                        <b>
                            <%=sysUserInf.getUserName()==null ? "" : sysUserInf.getUserName()%></b> ，欢迎光临</span>
                        <br />
                        <a href="javascript:f_addTab('home','管理中心','center.aspx')">管理中心</a> | <a href="javascript:f_addTab('edit','修改密码','user/sys/userpwd.aspx')">修改密码</a> |
                        <a ID="lbtnExit" OnClick="lbtnExit_Click" >安全退出</a>
                    </div>
                    <a class="logo"></a>
                </div>
            </div>
            <!--左边-->
            <div position="left" title="管理菜单" id="global_left_nav">
                 <div title="EPOS管理" class="l-scroll">
                    <ul id="global_epos_tree" style="margin-top: 3px;"></ul>
                </div>
               <%-- <div title="互联网支付管理" class="l-scroll">
                    <ul id="global_epay_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                
                <%--<div title="预付卡管理系统" class="l-scroll">
                    <ul id="global_vip_tree" style="margin-top: 3px;"></ul>
                </div>
                <div title="移动支付管理系统" class="l-scroll">
                    <ul id="global_mpay_tree" style="margin-top: 3px;"></ul>
                </div>--%>
              <%--  <div title="风控管理" class="l-scroll">
                    <ul id="global_dang_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                <div title="分润管理" class="l-scroll">
                    <ul id="global_fenrui_tree" style="margin-top: 3px;"></ul>
                </div>
               <%-- <div title="差错帐管理" class="l-scroll">
                    <ul id="global_errfina_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                <%--  <div title="合同管理" class="l-scroll" style="display:none">
                    <ul id="global_hetong_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                <div title="用户管理" class="l-scroll">
                    <ul id="global_user_tree" style="margin-top: 3px;"></ul>
                </div>
              <%--  <div title="工作流" class="l-scroll">
                    <ul id="global_work_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                <%--<div title="在线插件" class="l-scroll">
                    <ul id="global_plug_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                <%--<div title="进销存管理" class="l-scroll">
                    <ul id="global_goods_tree" style="margin-top: 3px;"></ul>
                </div>--%>
                <div title="财务管理" class="l-scroll">
                    <ul id="global_fina_tree" style="margin-top: 3px;"></ul>
                </div>
                <div title="系统设置" class="l-scroll">
                    <ul id="global_sys_tree" style="margin-top: 3px;"></ul>
                </div>
            </div>
            <div position="center" id="framecenter" toolsid="tab-tools-nav">
                <div tabid="home" title="管理中心" iconcss="tab-icon-home" style="height: 300px; -webkit-overflow-scrolling: touch; overflow: scroll;">
                    <iframe frameborder="0" name="sysMain" src="center-epos.jsp"></iframe>
                </div>
            </div>
            <div position="bottom" class="footer">
                <div class="copyright">
                    Copyright &copy; 2013 - 2020.无界支付  All Rights Reserved. 联系电话：
                </div>
            </div>
        </div>
    </form>
    </body>
</html>
