<%@ page import="DTO.WifiDTO" %>
<%@ page import="DAO.WifiDAO" %>
<%@ page import="DTO.HistoryDTO" %>
<%@ page import="DAO.HistoryDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>위치 기반 와이파이 정보</title>
		<style>
			#link-style {
				margin-bottom: 20px;
			}
			
			#location-style {
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
		
		<form id="location-style" method="get" action="index.jsp">
			<label>
				LAT: <input type="text" id="lat" name="lat" value="0.0"> , 
			</label>
			<label>
				LNT: <input type="text" id="lnt" name="lnt" value="0.0">
			</label>
			<input type="button" value="내 위치 가져오기" onclick="getLocation()">
			<input type="submit" value="근처 WIFI 정보 보기">
		</form>
		
		<table id="table-style">
			<thead>
				<tr>
					<th>거리(Km)</th>
					<th>관리번호</th>
			        <th>자치구</th>
			        <th>와이파이명</th>
			        <th>도로명주소</th>
			        <th>상세주소</th>
			        <th>설치위치(층)</th>
			        <th>설치유형</th>
			        <th>설치기관</th>
			        <th>서비스구분</th>
			        <th>망종류</th>
			        <th>설치년도</th>
			        <th>실내외구분</th>
			        <th>WIFI접속환경</th>
			        <th>X좌표</th>
			        <th>Y좌표</th>
			        <th>작업일자</th>
				</tr>
			</thead>
			<tbody>
				<%
					String lat = request.getParameter("lat");
					String lnt = request.getParameter("lnt");
					
					double latValue = 0.0;
					double lntValue = 0.0;
					
					if (lat != null && !lat.isEmpty()) {
						try {
							latValue = Double.parseDouble(lat);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					
					if (lnt != null && !lnt.isEmpty()) {
						try {
							lntValue = Double.parseDouble(lnt);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					
					if (latValue == 0.0 && lntValue == 0.0) {
				%>
				<tr>
					<td style="text-align: center; padding: 20px;" colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
				</tr>
				<%
					} else {
						HistoryDTO historyDto = new HistoryDTO();
						historyDto.setLat(latValue);
						historyDto.setLnt(lntValue);
						
						HistoryDAO historyDao = new HistoryDAO();
						historyDao.insert(historyDto);
						
						WifiDAO wifiDao = new WifiDAO();
						List<WifiDTO> wifiDaoList = wifiDao.selectList(latValue, lntValue);
						
						for (WifiDTO data : wifiDaoList) {
				%>
				<tr>
					<td>
						<%= data.getDist() %>
					</td>
					<td>
			            <%= data.getMgrNo() %>
			        </td>
			        <td>
			            <%= data.getWrdofc() %>
			        </td>
			        <td>
			            <%= data.getMainNm() %>
			        </td>
			        <td>
			            <%= data.getAdres1() %>
			        </td>
			        <td>
			            <%= data.getAdres2() %>
			        </td>
			        <td>
			            <%= data.getInstlFloor() %>
			        </td>
			        <td>
			            <%= data.getInstlTy() %>
			        </td>
			        <td>
			            <%= data.getInstlMby() %>
			        </td>
			        <td>
			            <%= data.getSvcSe() %>
			        </td>
			        <td>
			            <%= data.getCmcwr() %>
			        </td>
			        <td>
			            <%= data.getCnstcYear() %>
			        </td>
			        <td>
			            <%= data.getInoutDoor() %>
			        </td>
			        <td>
			            <%= data.getRemars3() %>
			        </td>
			        <td>
			            <%= data.getLnt() %>
			        </td>
			        <td>
			            <%= data.getLat() %>
			        </td>
			        <td>
			            <%= data.getWorkDttm() %>
			        </td>
				</tr>
				<%
						}
					}
				%>
			</tbody>
		</table>
		
		<script>
			const params = new URLSearchParams(window.location.search);
		    const lnt = params.get("lnt");
		    const lat = params.get("lat");
		
		    if (lnt) {
		        const lntElement = document.getElementById("lnt");
		        lntElement.setAttribute("value", lnt);
		    }
		
		    if (lat) {
		        const latElement = document.getElementById("lat");
		        latElement.setAttribute("value", lat);
		    }
		
		    function getLocation() {
		        if (navigator.geolocation) {
		            navigator.geolocation.getCurrentPosition(showPosition);
		        } else {
		            alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
		        }
		    }
		
		    function showPosition(position) {
		        const lat = position.coords.latitude;
		        const lnt = position.coords.longitude;
		
		        document.getElementById("lat").value = lat;
		        document.getElementById("lnt").value = lnt;
		    }
		</script>
	</body>
</html>