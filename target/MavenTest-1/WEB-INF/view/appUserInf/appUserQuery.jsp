<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>终端用户管理</title>
<link href="${ctx }/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="easyui-panel" title=""  style="padding:10px;">
		<table cellpadding="5">
			<tr>
				<td>手机号码：</td>
				<td><input id="loginName" class="easyui-validatebox textbox" data-options="required:true,novalidate:true"></td>
				<td>真实姓名：</td>
				<td><input id="realName" class="easyui-validatebox textbox" data-options="required:true,novalidate:true"></td>
				<td><a href="#" onclick="getData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:120px">查询</a></td>
				<td><a href="#" onclick="getActivationCode()" class="easyui-linkbutton" data-options="iconCls:'icon-print'" style="width:120px">生成激活码</a></td>
			</tr>
		</table>
	</div>
	<table id="appUserInfDg" class="easyui-datagrid" title="终端用户信息" style="width:auto;height:auto;"
			data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'loginName',width:120,align:'center'">手机号码</th>
				<th data-options="field:'realName',width:120,align:'center'">真实姓名</th>
				<th data-options="field:'idCard',width:170,align:'center'">身份证号</th>
				<th data-options="field:'email',width:240,align:'center'">电子邮箱</th>
				<th data-options="field:'createTime',width:220,align:'center'">注册时间</th>
				<th data-options="field:'_operate',width:120,align:'center',formatter:formatOper">操作</th>
			</tr>
		</thead>
	</table>
	<script>
		function formatOper(val,row,index){  
		    return '<a href="#" onclick="delUser('+index+')">删除</a>';  
		}
		function delUser(index){  
		    $('#appUserInfDg').datagrid('selectRow',index);
		    var row = $('#appUserInfDg').datagrid('getSelected');  
		    if (row){ 
		    	$.messager.confirm('提示', '确定要删除用户['+ row.realName +']吗?', function (r) {
                    if (r) {
                        $.post('${ctx}/delAppUserInf.do', { id: row.id }, function (result) {
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
			var loginName = $('#loginName').val();
			var realName = $('#realName').val();
			var cfg = {
					url:"appUserInfQuery.do",
					type:"post",
					data:{loginName: loginName,realName: realName}, 
					success: function(result) { 
						$('#appUserInfDg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', result);
			        } 
			};
			$.ajax(cfg);
		}
		function getActivationCode(){
			var cfg = {
					url:"getActivationCode.do",
					type:"post",
					success: function(result) { 
			            $.messager.alert('提示','生成的激活码为：' +result.activitionCode);
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