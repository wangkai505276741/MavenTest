<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
<script type="text/javascript">
	function loginAjax(){
		var loginName = document.getElementById("loginName").value;
		var loginPassword = document.getElementById("loginPassword").value;
		alert("loginName:" + loginName + ";loginPassword:" + loginPassword);
		alert($.md5(loginPassword));
		loginPassword = $.md5(loginPassword);
		var cfg = {
				url:"applogin.do",
				type:"post",
				data:JSON.stringify({loginName: loginName,loginPassword:'C190F8601A1C6448B28DE8BDBAA5A41A'}), 
				dataType: 'json',
				contentType:'application/json;charset=UTF-8',
				success: function(result) { 
		            alert(result.RSPCOD); 
		            if( result.RSPCOD != '00000'){
			            alert(result.RSPMSG);
		            }
		            alert(result.appUserInf.loginName);
		        } 
		};
		$.ajax(cfg);
	}
	function getActivationCodeAjax(){
		var cfg = {
				url:"getActivationCode.do",
				type:"post",
				contentType:'application/json;charset=UTF-8',
				success: function(result) { 
		            alert(result.RSPMSG);
		            document.getElementById("activationCode").value =result.activitionCode ;
		        }
		};
		$.ajax(cfg);
	}
</script>
</head>
<body>
<label>${welcomeStr }</label>
	<input type="button" value="生成验证码" onclick="getActivationCodeAjax();"/>
	<div id="activationCodeDiv">
		验证码为：<input type="text" id="activationCode" value=""/>
	</div>
	<div id="loginDiv">
		<table>
			<tr>
				<td>手机号：</td>
				<td><input type="text" id="loginName"/></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" id="loginPassword"/></td>
			</tr>
			<tr>
				<td><input type="button" value="登录" onclick="loginAjax();"/></td>
			</tr>
		</table>
	</div>
</body>
</html>