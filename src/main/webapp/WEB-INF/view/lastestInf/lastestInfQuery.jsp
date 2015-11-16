<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新资讯信息管理</title>
<link href="${ctx }/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="${ctx }/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctx }/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${ctx }/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx }/kindeditor-4.1.10/plugins/code/prettify.js"></script>
</head>
<body>
	<div class="easyui-panel" title=""  style="padding:10px;height:11%">
		<table cellpadding="5">
			<tr>
				<td>所属分组：</td>
				<td>
					<input class="easyui-combobox" id="groupId" name="groupId" data-options="url:'lastestInfGroupQuery2.do',method:'post',valueField:'id',textField:'name',panelHeight:'auto'">
				</td>
				<td><a href="#" onclick="getData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:120px">查询</a></td>
			</tr>
		</table>
		<div id="lastestInfAdd" class="easyui-window" title="新增最新资讯信息" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:780px;height:550px;padding:10px;">
			<div class="easyui-layout" data-options="fit:true">
				<form id="lastestInfAddForm"  method="post" enctype="multipart/form-data" >
					<div data-options="region:'center'" style="padding:10px;">
						<table  cellpadding="5">
							<tr>
								<td width="45px;">标题：</td>
								<td><input id="nameAdd" name="nameAdd" class="easyui-validatebox textbox" data-options="required:true" style="width:100%"></td>
								<!--  td>所属组:</td>
								<td>
									<input class="easyui-combobox" id="groupIdAdd" name="groupIdAdd" data-options="url:'lastestInfGroupQuery2.do',method:'post',valueField:'id',textField:'name',panelHeight:'auto'" style="width:100%">
								</td -->
							</tr>
							<tr>
								<td>链接：</td>
								<td ><input id="urlStrAdd" name="urlStrAdd"  class="easyui-validatebox textbox" style="width:100%"></td>
							</tr>
							<tr>
								<td>简介：</td>
								<td><input id="descStrAdd" name="descStrAdd" class="easyui-validatebox textbox" style="width:100%"></td>
							</tr>
							<tr>
								<td colspan="2">
									<textarea name="content1" cols="100" rows="8" style="width:700px;height:300px;visibility:hidden;"><%=htmlspecialchars(htmlData)%></textarea>
								
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div id="errorMsg" style="text-align:center;color:red;"></div>
								</td>
							</tr>
						</table>
					</div>
					<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="lastestInfAdd()" style="width:80px">保存</a>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#lastestInfAdd').window('close')" style="width:80px">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<table id="lastestInfDg" class="easyui-datagrid" title="最新资讯信息" style="width:auto;height:89%;"
			data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:20,toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'name',width:120,align:'center'">标题</th>
				<th data-options="field:'groupName',width:120,align:'center'">所属组</th>
				<th data-options="field:'urlStr',width:240,align:'center'">链接地址</th>
				<th data-options="field:'createTime',width:220,align:'center'">创建时间</th>
				<th data-options="field:'descStr',width:420,align:'center'">内容简介</th>
				<th data-options="field:'_operate',width:120,align:'center',formatter:formatOper">操作</th>
			</tr>
		</thead>
	</table>
	<script>
		var editor1;
		KindEditor.ready(function(K) {
			editor1 = K.create('textarea[name="content1"]', {
				cssPath : '${ctx }/kindeditor-4.1.10/plugins/code/prettify.css',
				uploadJson : '${ctx }/kindeditor-4.1.10/jsp/upload_json.jsp',
				fileManagerJson : '${ctx }/kindeditor-4.1.10/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
				}
			});
			prettyPrint();
		});
		var toolbar = [{
			text:'新增最新资讯',
			iconCls:'icon-add',
			handler:function(){$('#lastestInfAdd').window('open')}
		}];
		function formatImage(val,row,index){
			return "<image src='"+val+"' height='45'/>";
		}
		function formatOper(val,row,index){  
		    return '<a href="#" onclick="lastestInfDel('+index+')">删除</a>';  
		}
		//新增最新资讯 - 表单提交
		function lastestInfAdd(){
			if(!$('#lastestInfAddForm').form('enableValidation').form('validate')){
				return false;
			}
			var name = $('#nameAdd').val();
			if(name == ''){
				$.messager.show({title: '错误',msg: '标题不能为空！'});
	        	$("#errorMsg").html("标题不能为空");
				return false;
			}
	        //var groupId = $('#groupIdAdd').combobox('getValue');
			var groupId = 1;
			var urlStr = $('#urlStrAdd').val();
			var descStr = $('#descStrAdd').val();
			var contentStr = editor1.html();
            var cfg = {
					url:"lastestInfAdd.do",
					type:"post",
					data:{name: name,groupId: groupId,urlStr: urlStr,descStr: descStr,contentStr: contentStr}, 
					success: function(result) { 
						if(result.RSPCOD!='00000'){
							$.messager.show({title: '错误',msg: result.RSPMSG});
				        	$("#errorMsg").html(result.RSPMSG);
							return false;
						}else {
							$.messager.show({title: '成功',msg: '保存成功！'});
							$('#lastestInfAdd').window('close')
                        	getData();
						}
			        } 
			};
			$.ajax(cfg);
		}
		function lastestInfAdd2(){
			if(!$('#lastestInfAddForm').form('enableValidation').form('validate')){
				return false;
			}
			var name = $('#nameAdd').val();
			if(name == ''){
				$.messager.show({title: '错误',msg: '标题不能为空！'});
	        	$("#errorMsg").html("标题不能为空");
				return false;
			}
	        var groupId = $('#groupIdAdd').combobox('getValue');
	        if(groupId == ''){
				$.messager.show({title: '错误',msg: '请选择所属组！'});
	        	$("#errorMsg").html("请选择所属组！");
				return false;
	        }
			//得到上传文件的全路径  
	        var fileName= $('#imageUrlAdd').filebox('getValue');  
			if(fileName == ''){
				$.messager.show({title: '错误',msg: '请选择要上传的文件！'});
	        	$("#errorMsg").html("请选择要上传的文件");
				return false;
			} 
			var array = new Array('.gif', '.jpeg', '.png', '.jpg', '.bmp'); //可以上传的文件类型  
			var fileContentType=/\.[^\.]+$/.exec(fileName)+""; 
			var isExists = false;  
			//循环判断图片的格式是否正确  
            for (var i in array) {
            	if (fileContentType.toLowerCase() == array[i]){
            		isExists = true;
            	}
            }
			if(!isExists){
				$.messager.show({title: '错误',msg: '文件格式不正确！'});
	        	$("#errorMsg").html("请选择正确的文件格式！");
				return false;
			}
			//提交表单 
            $("#lastestInfAddForm").submit();     
			$.messager.show({title: '成功',msg: '保存成功！'});
			$('#lastestInfAdd').window('close');
        	getData();
		}
		function lastestInfDel(index){  
		    $('#lastestInfDg').datagrid('selectRow',index);
		    var row = $('#lastestInfDg').datagrid('getSelected');  
		    if (row){ 
		    	$.messager.confirm('提示', '确定要删除['+ row.name +']吗?', function (r) {
                    if (r) {
                        $.post('${ctx}/lastestInfDel.do', { id: row.id }, function (result) {
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
			groupId
			var cfg = {
					url:"lastestInfQuery.do",
					type:"post",
					success: function(result) { 
						$('#lastestInfDg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', result.lastestInf);
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
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>