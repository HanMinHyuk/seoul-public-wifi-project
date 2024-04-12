<%@ page import="Info.WifiInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>위치 기반 와이파이 정보</title>
		<style>
			* {
				text-align:center;
				font-weight: bolder;
			}
		</style>
	</head>
	<body>
		<%
		WifiInfo wifiInfo = new WifiInfo();
		wifiInfo.init();
		wifiInfo.saveInfo();
		%>
	
		<h2><%= wifiInfo.totalCnt() %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h2>
		<a href="index.jsp">홈 으로 가기</a>
	</body>
</html>