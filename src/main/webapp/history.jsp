<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="DAO.HistoryDAO" %>
<%@ page import="DTO.HistoryDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
	<head>
		<title>위치 기반 와이파이 정보</title>
		<style>
			#link-style {
				margin-bottom: 20px;
			}
			
			#table-style {
				border-collapse: collapse;
				width: 100%;
			}
			
			#table-style th {
				border: 1px solid #ddd;
				text-align: center;
				font-size: 15px;
				font-weight: bolder;
				padding: 9px;
			}
			
			#table-style td {
				border: 1px solid #ddd;
				text-align: left;
				font-size: 15px;
				font-weight: bolder;
				padding: 9px;
			}
			
			#td-first {
				padding: 20px;
			}
			
			#table-style tr:nth-child(even) {
				background-color: #f2f2f2;
			}
		
			
			#table-style th {
				padding-top: 10px;
				padding-bottom: 10px;
				background-color: #04AA6D;
				color: white;
			}
			
		</style>
	</head>
	<body>
		<h1>와이파이 정보 구하기</h1>
		
		<div id="link-style">
			<a href="index.jsp">홈</a>
			&#124;
			<a href="history.jsp">위치 히스토리 목록</a>
			&#124;
			<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
		</div>
		
		<table id="table-style">
			<thead>
				<tr>
					<th>ID</th>
			        <th>X좌표</th>
			        <th>Y좌표</th>
			        <th>조회일자</th>
			        <th>비고</th>
				</tr>
			</thead>
			<tbody>
				<%
					HistoryDAO historyDao = new HistoryDAO();
				
					if (historyDao.count() == 0) {
				%>
				<tr>
					<td style="text-align:center; padding: 20px;" colspan="5">조회 이력이 없습니다.</td>
				</tr>
				<%
					} else {
						List<HistoryDTO> historyDaoList = historyDao.selectList();
						
						for (HistoryDTO data : historyDaoList) {
				%>
				<tr>
					<td>
						<%= data.getId() %>
					</td>
					<td>
			            <%= data.getLat() %>
			        </td>
			        <td>
			            <%= data.getLnt() %>
			        </td>
			        <td>
			            <%= data.getSearchDttm() %>
			        </td>
			        <td style="text-align: center">
			        	<button onclick="deleteId(<%= data.getId() %>);">삭제</button>
			        </td>
				</tr>
				<%
						}
					}
				%>
			</tbody>
		</table>
		
		<script>
			function deleteId(id) {
				location.href = "history-delete.jsp?id=" + id;
			}
		</script>
	</body>
</html>