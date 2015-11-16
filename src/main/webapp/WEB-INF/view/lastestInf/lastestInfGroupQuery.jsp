<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新资讯组管理</title>
<link href="${ctx }/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<table id="lastestInfGroupDg" class="easyui-datagrid" title="最新资讯组信息" style="width:auto;height:100%;"
			data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:20,toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'name',width:120,align:'center'">标题</th>
				<th data-options="field:'imageUrl',width:70,align:'center',formatter:formatImage">图片</th>
				<th data-options="field:'createTime',width:240,align:'center'">创建时间</th>
				<th data-options="field:'_operate',width:120,align:'center',formatter:formatOper">操作</th>
			</tr>
		</thead>
	</table>
	<div id="lastestInfGroupAdd" class="easyui-window" title="新增最新资讯组" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:330px;height:270px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="lastestInfGroupAddForm"  method="post" enctype="multipart/form-data" action="${ctx}/lastestInfGroupAdd.do">
				<div data-options="region:'center'" style="padding:10px;">
					<table  cellpadding="5">
						<tr>
							<td>标题：</td>
							<td><input id="nameAdd" name="nameAdd" class="easyui-validatebox textbox" data-options="required:true"></td>
						</tr>
						<tr>
							<td>图片：</td>
							<td><input id="imageUrlAdd" class="easyui-filebox" name="imageUrlAdd" data-options="buttonText:'浏览',prompt:'请选择图片……'" style="width:100%"></td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="errorMsg" style="text-align:center;color:red;"></div>
							</td>
						</tr>
					</table>
				</div>
				<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="lastestInfGroupAdd()" style="width:80px">保存</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#lastestInfGroupAdd').window('close')" style="width:80px">取消</a>
				</div>
			</form>
		</div>
	</div>
	<script>
		var toolbar = [{
			text:'新增最新资讯组',
			iconCls:'icon-add',
			handler:function(){$('#lastestInfGroupAdd').window('open')}
		}];
		function formatImage(val,row,index){
			return "<image src='"+val+"' height='45'/>";
		}
		function formatOper(val,row,index){  
		    return '<a href="#" onclick="lastestInfGroupDel('+index+')">删除</a>';  
		}
		function lastestInfGroupAdd(){
			var name = $('#nameAdd').val();
			//得到上传文件的全路径  
	        var fileName= $('#imageUrlAdd').filebox('getValue');  
			if(name == ''){
				$.messager.show({title: '错误',msg: '标题不能为空！'});
	        	$("#errorMsg").html("标题不能为空");
				return false;
			}
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
            $("#lastestInfGroupAddForm").submit();     
			$.messager.show({title: '成功',msg: '保存成功！'});
			$('#lastestInfGroupAdd').window('close');
        	getData();
		}
		function lastestInfGroupDel(index){  
		    $('#lastestInfGroupDg').datagrid('selectRow',index);
		    var row = $('#lastestInfGroupDg').datagrid('getSelected');  
		    if (row){ 
		    	$.messager.confirm('提示', '确定要删['+ row.name +']吗?', function (r) {
                    if (r) {
                        $.post('${ctx}/lastestInfGroupDel.do', { id: row.id }, function (result) {
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
			var cfg = {
					url:"lastestInfGroupQuery.do",
					type:"post",
					success: function(result) { 
						$('#lastestInfGroupDg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', result.lastestInfGroup);
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