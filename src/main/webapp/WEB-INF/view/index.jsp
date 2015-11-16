<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<%@page import="com.wujiepayment.been.SysUserInf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无界智慧金融管理系统</title>
<link type="image/x-icon" href="${ctx}/images/icon.png" rel="icon"/>
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
		<a href="javascript:$('#sysUserPasswordEdit').window('open')">修改密码</a> |
		<a href="sysLogout.do"  >安全退出</a>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'系统菜单'" style="width:210px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="终端用户管理" >
				<ul class="easyui-tree">
					<li><a rel="appUserInfIndex.do">终端用户管理</a></li>
				</ul>
			</div>
			<div title="在线办卡" >
				<ul class="easyui-tree">
					<li><a rel="onlineCardIndex.do">在线办卡管理</a></li>
				</ul>
			</div>
			<div title="最新资讯" >
				<ul class="easyui-tree">
					<li><a rel="lastestInfIndex.do">最新资讯信息管理</a></li>
					<li><a rel="lastestInfGroupIndex.do">最新资讯组管理</a></li>
				</ul>
			</div>
			<div title="一键贷款" >
				<ul class="easyui-tree">
					<li><a rel="easyLoanInfIndex.do">一键贷款管理</a></li>
				</ul>
			</div>
			<div title="调额助手" >
				<ul class="easyui-tree">
					<li><a rel="creditAdjustmentInfIndex.do">调额助手信息管理</a></li>
					<li><a rel="creditAdjustmentInfGroupIndex.do">调额助手组管理</a></li>
				</ul>
			</div>
			<div title="投资理财" >
				<ul class="easyui-tree">
					<li><a rel="weathManagementIndex.do">投资理财管理</a></li>
				</ul>
			</div>
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
	<div id="sysUserPasswordEdit" class="easyui-window" title="密码修改" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:330px;height:250px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'" style="padding:10px;">
				<input type="hidden" id="sysUserId" value="<%=userInf.getId()%>"/>
				<table  cellpadding="5">
					<tr>
						<td>原密码：</td>
						<td><input id="oldPassword" class="easyui-validatebox textbox" data-options="required:true"></td>
					</tr>
					<tr>	
						<td>新密码：</td>
						<td><input id="newPassword" type="password" class="easyui-validatebox textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="newPassword2" type="password" class="easyui-validatebox textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="errorMsg" style="text-align:center;color:red;"></div>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:sysUserPasswordEdit()" style="width:80px">保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#sysUserPasswordEdit').window('close')" style="width:80px">取消</a>
			</div>
		</div>
	</div>
	<script>
		$(function(){
			$.messager.show({title: '欢迎登陆',msg: '您上次登录的时间为： <br>  <%=userInf.getLastLoginTime() %> <br> 您上次登录的IP为： <br>  <%=userInf.getLastLoginIP()%>',height: '150' });
		});
		
		function sysUserPasswordEdit(){
			var oldPassword = $('#oldPassword').val();
			var newPassword = $('#newPassword').val();
			var newPassword2 = $('#newPassword2').val();
			if(oldPassword == ''){
				$.messager.show({title: '错误',msg: '原密码不能为空！'});
	        	$("#errorMsg").html("原密码不能为空");
				return false;
			}
			if(newPassword.length == ''){
				$.messager.show({title: '错误',msg: '新密码不能为空！'});
	        	$("#errorMsg").html("新密码不能为空");
				return false;
			}
			if(newPassword!=newPassword2){
				$.messager.show({title: '错误',msg: '两次输入密码不一致！'});
				$("#errorMsg").html('两次输入密码不一致！');
				return false;
			}
			var cfg = {
					url:"sysUserPasswordEdit.do",
					type:"post",
					data:{oldPassword: oldPassword,newPassword: newPassword}, 
					success: function(result) { 
						if(result.RSPCOD!='00000'){
							$.messager.show({title: '错误',msg: result.RSPMSG});
				        	$("#errorMsg").html(result.RSPMSG);
							return false;
						}else {
							$.messager.show({title: '成功',msg: '保存成功,重新登录时生效！'});
							$('#sysUserPasswordEdit').window('close');
						}
			        } 
			};
			$.ajax(cfg);
		}
	</script>
</body>
</html>