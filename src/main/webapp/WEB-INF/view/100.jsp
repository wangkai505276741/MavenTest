<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%
request.setCharacterEncoding("UTF-8");
String titleStr = request.getParameter("titleStr") != null ? request.getParameter("titleStr") : "";
String contentStr = request.getParameter("contentStr") != null ? request.getParameter("contentStr") : "";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html style="font-size: 40px;"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
 
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Cache-Control" content="no-transform">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=2.0, user-scalable=1"> -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="layoutmode" content="standard">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">


<meta name="wap-font-scale" content="no">

<meta content="telephone=no" name="format-detection">
<meta http-equiv="Pragma" content="no-cache">
<script type="text/javascript">
var _htmlFontSize = (function(){
    var clientWidth = document.documentElement ? document.documentElement.clientWidth : document.body.clientWidth;
    if(clientWidth > 640) clientWidth = 640;
    document.documentElement.style.fontSize = clientWidth * 1/16+"px";
    return clientWidth * 1/16;
})();
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/jsp/images/base.min.css" title="default">
<link rel="stylesheet" id="mobiStyleTemplateCss" type="text/css" href="${ctx }/jsp/images/355.min.css">

<script src="${ctx }/js/jquery-easyui-1.4.2/jquery.min.js"></script>
<meta name="keywords" content="">
<meta name="description" content="">
<style id="mobiStyleModule">
#module4 .formMiddleContent4{padding:0px;}
#module4 .formMiddleContent4{margin-left:0.0rem;}
#module4 .formMiddleContent4{margin-right:0.0rem;}
#module4 .formMiddleContent4{margin-top:0.0rem;}
#module4 .formMiddleContent4{margin-bottom:0.0rem;}
</style>
<style id="mobiStyleNav" type="text/css">
.open .navItem:nth-child(2) {transition-delay: 160ms;} 
.open .navItem:nth-child(4) {transition-delay: 240ms;} 
.open .navItem:nth-child(6) {transition-delay: 320ms;} 
.open .navItem:nth-child(8) {transition-delay: 400ms;} 
.open .navItem:nth-child(10) {transition-delay: 480ms;} 
.open .navItem:nth-child(12) {transition-delay: 560ms;} 
.open .navItem:nth-child(14) {transition-delay: 640ms;} 
.open .navItem:nth-child(16) {transition-delay: 720ms;} 
.open .navItem:nth-child(18) {transition-delay: 800ms;} 
.open .navItem:nth-child(20) {transition-delay: 880ms;} 
.open .navItem:nth-child(22) {transition-delay: 960ms;} 
.open .navItem:nth-child(24) {transition-delay: 1040ms;} 
.open .navItem:nth-child(26) {transition-delay: 1120ms;} 
.open .navItem:nth-child(28) {transition-delay: 1200ms;} 
.open .navItem:nth-child(30) {transition-delay: 1280ms;} 
.open .navItem:nth-child(32) {transition-delay: 1360ms;} 
.open .navItem:nth-child(34) {transition-delay: 1440ms;} 
.open .navItem:nth-child(36) {transition-delay: 1520ms;} 
.open .navItem:nth-child(38) {transition-delay: 1600ms;} 
.open .navItem:nth-child(40) {transition-delay: 1680ms;} 
</style>


<style id="mobiCubeStyleModule">
</style>

<style id="mobiSearchStyleModule">
</style>
</head>
<body faiscomobi="true" id="g_body" class="g_locale2052 mobiCol4">
	<div class="webLeft"></div>
	<div id="g_web" class="g_web" style="padding-top: 0px;">
		<!-- this is loading  -->
		<div id="webLoading" class="loading" style="display: none;">
			<div id="splashscreen" class="splashscreen ui-loader">
			    <span class="ui-icon ui-icon-loading spin"></span>
			</div>
	    </div>
	    <div id="webTopBox" class="webTopBox ">
	    	<div id="webTop" class="webTop">
	    	</div>
	    </div>
		
		<div id="webBannerBox" class="webBannerBox">
		</div>
		<div id="webContainerBox" class="webContainerBox">
			<div id="webModuleContainer" class="webModuleContainer">
					<div id="module4" _headerhiden="1" class="form Handle  formStyle7 " _autoheight="1"><div style="display:none;" class="formBannerTitle formBannerTitle4"><div class="titleLeft titleLeft4">
</div>
<div class="titleCenter titleCenter4">
<div class="titleText titleText4"><div class="titleTextIcon icon-titleText">&nbsp;</div><div class="textContent">æç« è¯¦æ</div></div><div class="formBannerMore formBannerMore4"></div></div>
<div class="titleRight titleRight4">
</div>
</div>
<div class="formMiddle formMiddle4"><div class="middleLeft middleLeft4">
</div>
<div class="middleCenter middleCenter4">
<div class="formMiddleContent formMiddleContent4 moduleContent"><div class="newsDetail" id="newsDetail4">
<div class="title"><span class="titleText">${titleStr}</span></div>
<div class="g_separator separatorLine"></div>
<div class="richContent content">
${contentStr }
</div>
</div></div>
</div>
</div>

			</div>
		</div>
		
	</div>
<script type="text/javascript" charset="utf-8" src="${ctx }/jsp/images/jqmobi.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/jsp/images/jqmobi_ui.min.js"></script>
<script id="mobiOperationJS" type="text/javascript" charset="utf-8" src="${ctx }/jsp/images/mobi.min.js"></script>

<script type="text/javascript" src="${ctx }/jsp/images/2052.min.js"></script>



</body></html>