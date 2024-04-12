<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="DAO.HistoryDAO" %>
<!DOCTYPE html>
<html>
<head>
<title>와이파이 정보 가져오기</title>
</head>
<body>
	<%
		String id = request.getParameter("id");
	
		HistoryDAO historyDao = new HistoryDAO();
		int affected = historyDao.delete(Integer.valueOf(id));
	%>

<script>
	<%
		String text = affected > 0 ? "히스토리 조회 목록을 제거하였습니다." : "제거 안되었습니다.";
	
	%>
	alert("<%= text %>");
	location.href = "history.jsp";
</script>
	
</body>
</html>