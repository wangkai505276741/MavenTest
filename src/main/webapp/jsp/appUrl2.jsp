<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function(){
		var ua = navigator.userAgent.toLowerCase();	
		//alert(ua);
		var isAndroid = ua.indexOf('android') > -1 || ua.indexOf('linux') > -1; //android终端或者uc浏览器
		var isiOS = !!ua.match(/\(i[^;]+;( ua;)? cpu.+mac os x/); //ios终端
		//if (/iphone/.test(ua) || /ipad/.test(ua) || /ipod/.test(ua)) {
		//	window.location.href="http://www.pgyer.com/oL6J";
		//} else if (/android/.test(ua)) {
		//	window.location.href="https://fir.im/pz25";
		//}
		//alert("isAndroid="+isAndroid);
		//alert("isiOS="+isiOS);
		if (isiOS){
			//alert('isiOS');
			window.location.href="http://www.pgyer.com/7akS";
		} else {
			//alert("else")
			window.location.href="https://fir.im/9gej";
		}
	})
</script>