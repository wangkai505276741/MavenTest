<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无界智慧金融管理系统-用户登录</title>
<link type="image/x-icon" href="${ctx}/images/icon.png" rel="icon"/>
<link href="${ctx }/css/login.css" rel="stylesheet" type="text/css" />
<script src="${ctx }/js/jquery.cookie.js"></script>
<script>
	function msgShow(title,msg){
		$.messager.show({
			title:title,
			msg:msg,
			showType:'fade'
		});
	}
	function submitForm(){
		var userName = $('#userName').val();
		if(userName.length == 0){
			msgShow('提示','用户名不能为空！');
        	$("#errorMsg").html("用户名不能为空");
			return false;
		}
		var password = $('#password').val();
		if(password.length == 0){
			msgShow('提示','密码不能为空！');
        	$("#errorMsg").html("密码不能为空");
			return false;
		}
		var codes = $('#codes').val();
		if(codes.length == 0){
			msgShow('提示','验证码不能为空！');
			$("#errorMsg").html('验证码不能为空！');
			return false;
		}

		var cfg = {
				url:"sysLoginCheck.do",
				type:"post",
				data:{userName: userName,password: password,codes:codes}, 
				success: function(result) { 
		            if( result.RSPCOD != '00000'){
		            	$("#errorMsg").html(result.RSPMSG);
		    			msgShow('提示',result.RSPMSG);
		            }else{
		            	location.href = "sysLogin.do?dateStr="+new Date();
		            }
		        } 
		};
		$.ajax(cfg);
	}
	function clearForm(){
		$('#ff')[0].reset();
	}
</script>
</head>
<body>
	<div id="loginDiv">
		<div class="easyui-panel" title="无界智慧金融管理系统" style="width:300px;height:250px;">
			<form id="ff" method="post" action="login.do">
				<table cellpadding="5">
					<tr>
	    				<td>用户名:</td>
	    				<td colspan="2" >
	    					<input class="easyui-textbox" type="text" id="userName" name="userName" style="width:200px;height:25px"></input>
	    				</td>
	    			</tr>
					<tr>
	    				<td>密&nbsp; 码:</td>
	    				<td colspan="2" >
	    					<input class="easyui-textbox" type="password" id="password" name="password" style="width:200px;height:25px"></input>
	    				</td>
	    			</tr>
					<tr>
	    				<td>验证码:</td>
	    				<td >
	    					<input class="easyui-textbox" type="text" id="codes" name="codes" style="width:120px;height:25px"></input>
	    				</td>
	    				<td>
	    					<img src="jsp/validateCode.jsp" onclick="this.src='jsp/validateCode.jsp?id='+Math.random();" alt="点击图片刷新验证码" title="点击图片刷新验证码"/>
	    				</td>
	    			</tr>
				</table>
			</form>
			<div id="errorMsg" style="text-align:center;color:red;">
            </div>
			<div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" style="width:40%;height:32px" onclick="submitForm()">登录</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" style="width:40%;height:32px" onclick="clearForm()">取消</a>
		    </div>
		</div>
	</div>
    <div style=" text-align: center; font-size: 12px; color: white; ">
        Copyright &copy; 2013 - 2020. 无界支付. All Rights Reserved. 联系电话：<label ID="Literal2" ></label>
    </div>
</body>
</html>