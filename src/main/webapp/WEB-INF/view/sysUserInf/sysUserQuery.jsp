<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统用户管理</title>
<link href="${ctx }/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="easyui-panel" title=""  style="padding:10px;height:11%;">
		<table cellpadding="5">
			<tr>
				<td>用户名：</td>
				<td><input id="userName" class="easyui-validatebox textbox" data-options="required:true,novalidate:true"></td>
				<td><a href="#" onclick="getData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:120px">查询</a></td>
			</tr>
		</table>
	</div>
	<table id="sysUserInfDg" class="easyui-datagrid" title="系统用户信息" style="width:auto;height:89%;"
			data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:20,toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'userName',width:120,align:'center'">用户名</th>
				<th data-options="field:'lastLoginIP',width:170,align:'center'">上次登录IP</th>
				<th data-options="field:'lastLoginTime',width:170,align:'center'">上次登录时间</th>
				<th data-options="field:'createTime',width:240,align:'center'">创建时间</th>
				<th data-options="field:'_operate',width:120,align:'center',formatter:formatOper">操作</th>
			</tr>
		</thead>
	</table>
	<div id="sysUserInfAdd" class="easyui-window" title="新增系统用户" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:330px;height:270px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'" style="padding:10px;">
				<table  cellpadding="5">
					<tr>
						<td>用户名：</td>
						<td><input id="userNameAdd" class="easyui-validatebox textbox" data-options="required:true"></td>
					</tr>
					<tr>	
						<td>密&nbsp; 码：</td>
						<td><input id="passwordAdd" type="password" class="easyui-validatebox textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="password2Add" type="password" class="easyui-validatebox textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="errorMsg" style="text-align:center;color:red;"></div>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:sysUserInfAdd()" style="width:80px">保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#sysUserInfAdd').window('close')" style="width:80px">取消</a>
			</div>
		</div>
	</div>
	<script>
		var toolbar = [{
			text:'新增用户',
			iconCls:'icon-add',
			handler:function(){$('#sysUserInfAdd').window('open')}
		}];
		function formatOper(val,row,index){  
		    return '<a href="#" onclick="delUser('+index+')">删除</a>';  
		}
		function sysUserInfAdd(){
			var userName = $('#userNameAdd').val();
			var password = $('#passwordAdd').val();
			var password2 = $('#password2Add').val();
			if(userName == ''){
				$.messager.show({title: '错误',msg: '用户名不能为空！'});
	        	$("#errorMsg").html("用户名不能为空");
				return false;
			}
			if(password.length == ''){
				$.messager.show({title: '错误',msg: '密码不能为空！'});
	        	$("#errorMsg").html("密码不能为空");
				return false;
			}
			if(password!=password2){
				$.messager.show({title: '错误',msg: '两次输入密码不一致！'});
				$("#errorMsg").html('两次输入密码不一致！');
				return false;
			}
			var cfg = {
					url:"sysUserInfAdd.do",
					type:"post",
					data:{userName: userName,password: password}, 
					success: function(result) { 
						if(result.RSPCOD!='00000'){
							$.messager.show({title: '错误',msg: result.RSPMSG});
				        	$("#errorMsg").html(result.RSPMSG);
							return false;
						}else {
							$.messager.show({title: '成功',msg: '保存成功！'});
							$('#sysUserInfAdd').window('close')
                        	getData();
						}
			        } 
			};
			$.ajax(cfg);
		}
		
		function delUser(index){  
		    $('#sysUserInfDg').datagrid('selectRow',index);
		    var row = $('#sysUserInfDg').datagrid('getSelected');  
		    if (row){ 
		    	$.messager.confirm('提示', '确定要删除用户['+ row.userName +']吗?', function (r) {
                    if (r) {
                        $.post('${ctx}/sysUserInfDel.do', { id: row.id }, function (result) {
                            if (result.RSPCOD == '00000') {
                            	$.messager.show({   // show error message  
                                    title: '提示',
                                    msg: result.RSPMSG
                                });
                            	getData();
                            } else {
                                $.messager.show({   // show error message  
                                    title: '错误',
                                    msg: result.RSPMSG
                                });
                            }
                        }, 'json');
                    }
                });
		    }  
		}
		function pagerFilter(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
				data = {
					total: data.length,
					rows: data
				}
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				displayMsg:'当前显示从 [{from}] 到 [{to}] 共[{total}]条记录',   
				onSelectPage:function(pageNum, pageSize){
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh',{
						pageNumber:pageNum,
						pageSize:pageSize
					});
					dg.datagrid('loadData',data);
				}
			});
			if (!data.originalRows){
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
		}
		  
		function getData(){
			var userName = $('#userName').val();
			var cfg = {
					url:"sysUserInfQuery.do",
					type:"post",
					data:{userName: userName}, 
					success: function(result) { 
						$('#sysUserInfDg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', result);
			        } 
			};
			$.ajax(cfg);
		}
		$(function(){
			getData();
		});
	</script>
</body>
</html>