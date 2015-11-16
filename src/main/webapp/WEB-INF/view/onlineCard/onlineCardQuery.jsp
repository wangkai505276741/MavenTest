<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线办卡管理</title>
<link href="${ctx }/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<table id="onlineCardDg" class="easyui-datagrid" title="在线办卡信息" style="width:auto;height:100%;">
	</table>
	<div id="onlineCardAdd" class="easyui-window" title="新增在线办卡" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:330px;height:270px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="onlineCardAddForm"  method="post" enctype="multipart/form-data" action="${ctx}/onlineCardAdd.do">
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
							<td>链接：</td>
							<td><input id="urlStrAdd" name="urlStrAdd"  class="easyui-validatebox textbox" data-options="required:true"></td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="errorMsg" style="text-align:center;color:red;"></div>
							</td>
						</tr>
					</table>
				</div>
				<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="onlineCardAdd()" style="width:80px">保存</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#onlineCardAdd').window('close')" style="width:80px">取消</a>
				</div>
			</form>
		</div>
	</div>
	<script>
		$(function(){
			loadGrid();
		});
		var toolbar = [{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){$('#onlineCardAdd').window('open')}
		}];
		function onlineCardAdd(){
			if(!$('#onlineCardAddForm').form('enableValidation').form('validate')){
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
            $("#onlineCardAddForm").submit();     
		}
		function onlineCardDel(index){  
		    $('#onlineCardDg').datagrid('selectRow',index);
		    var row = $('#onlineCardDg').datagrid('getSelected');  
		    if (row){ 
		    	$.messager.confirm('提示', '确定要删['+ row.name +']吗?', function (r) {
                    if (r) {
                        $.post('${ctx}/onlineCardDel.do', { id: row.id }, function (result) {
                            if (result.RSPCOD == '00000') {
                            	$.messager.show({   // show error message  
                                    title: '提示',
                                    msg: result.RSPMSG
                                });
                            	$("#onlineCardDg").datagrid("reload");
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
		function loadGrid(){
			$("#onlineCardDg").datagrid({
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:20,
				toolbar:toolbar,
				url:'onlineCardInfQuery2.do',
				loadMsg:'数据加载中请稍后……',
				columns:[[  
                    {field:'name',title: '标题',align: 'center',width: 120},  
                    {field:'imageUrl',title: '图片',align:'center',width:70, 
                        formatter:function(val,row,index){  
                        	return "<image src='"+val+"' height='45'/>";  
                       }  
                    },  
                    {field:'urlStr',title: '链接',align: 'center',width: 380},  
                    {field:'createTime',title: '创建时间',align:'center',width:170},  
                    {field:'_option',title: '操作',align:'center',width:170,formatter:function(val,row,index){  
                    	return '<a href="#" onclick="onlineCardDel('+index+')">删除</a>';  
                    }}
                ]]  
			});
		}
	</script>
</body>
</html>