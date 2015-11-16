<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<%@page import="com.wujiepayment.been.SysUserInf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无界智慧金融管理系统</title>
<script src="${ctx }/js/index.js"></script>
<link href="${ctx }/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body class="easyui-layout">
	<%String s = session.getId(); //获取session ID号  %>
	<%SysUserInf userInf = (SysUserInf)session.getAttribute("sysUserInf"); //获取用户信息  %>
	<div data-options="region:'north',border:false" style="height:65px;background:url(${ctx }/images/head_bg.gif);padding:10px">
		<div class="header_left">
		
		无界智慧金融管理系统
		
		</div>
		<div class="header_right">
		    <span>
		    <b>
		        <%=userInf==null ? "" : userInf.getUserName()%></b> ，欢迎光临</span>
				<br />
		<a href="javascript:f_addTab('home','管理中心','center.aspx')">管理中心</a> | <a href="javascript:f_addTab('edit','修改密码','user/sys/userpwd.aspx')">修改密码</a> |
		<a ID="lbtnExit" OnClick="lbtnExit_Click" >安全退出</a>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'系统菜单'" style="width:210px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="终端用户管理" >
				<ul class="easyui-tree">
					<li><a rel="appUserInfIndex.do">终端用户管理</a></li>
				</ul>
			</div>
			<!-- 
			<div title="卡号登记" >
				<ul class="easyui-tree">
					<li><a >卡号登记管理</a></li>
				</ul>
			</div>
			<div title="在线办卡" >
				<ul class="easyui-tree">
					<li><a >在线办卡管理</a></li>
				</ul>
			</div>
			<div title="最新资讯" >
				<ul class="easyui-tree">
					<li><a >最新资讯管理</a></li>
				</ul>
			</div>
			<div title="一键贷款" >
				<ul class="easyui-tree">
					<li><a >一键贷款管理</a></li>
				</ul>
			</div>
			<div title="调额助手" >
				<ul class="easyui-tree">
					<li><a >调额助手管理</a></li>
				</ul>
			</div>
			<div title="投资理财" >
				<ul class="easyui-tree">
					<li><a >投资理财管理</a></li>
				</ul>
			</div>
			 -->
			<div title="系统账号管理" >
				<ul class="easyui-tree">
					<li><a rel="sysUserInfIndex.do">系统账号管理</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false"">
		<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
			
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:30px;background:rgb(210, 224, 242);padding:5px;text-align:center;">版权所有：</div>
</body>
</html>