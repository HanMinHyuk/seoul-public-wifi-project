package DAO;


import DTO.WifiDTO;
import MyDb.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;


public class WifiDAO {
	
	// 데이터 추출
	public void dbSelect() {
		WifiDTO wifiDto = new WifiDTO();
		
		
		try {
            // 1. 드라이버 로드
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // 2. 커넥션 객체 생성
            connection = DriverManager.getConnection(Db.URL);

            // sql문
            String sql = " SELECT * FROM WIFI_INFO ";

            // 3. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 4. sql 문장 실행
            rs = preparedStatement.executeQuery();

            // 5. 결과 수행
            while (rs.next()) {

            	String mgrNo2 = rs.getString("X_SWIFI_MGR_NO");
                String wrdofc = rs.getString("X_SWIFI_WRDOFC");
                String mainNm = rs.getString("X_SWIFI_MAIN_NM");
                String adres1 = rs.getString("X_SWIFI_ADRES1");
                String adres2 = rs.getString("X_SWIFI_ADRES2");
                String instlFloor = rs.getString("X_SWIFI_INSTL_FLOOR");
                String instlTy = rs.getString("X_SWIFI_INSTL_TY");
                String instlMby = rs.getString("X_SWIFI_INSTL_MBY");
                String svcSe = rs.getString("X_SWIFI_SVC_SE");
                String cmcwr = rs.getString("X_SWIFI_CMCWR");
                int cnstcYear = rs.getInt("X_SWIFI_CNSTC_YEAR");
                String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
                String remars3 = rs.getString("X_SWIFI_REMARS3");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                String workDttm = rs.getString("WORK_DTTM");

                wifiDto.setMgrNo(mgrNo2);
                wifiDto.setWrdofc(wrdofc);
                wifiDto.setMainNm(mainNm);
                wifiDto.setAdres1(adres1);
                wifiDto.setAdres2(adres2);
                wifiDto.setInstlFloor(instlFloor);
                wifiDto.setInstlTy(instlTy);
                wifiDto.setInstlMby(instlMby);
                wifiDto.setSvcSe(svcSe);
                wifiDto.setCmcwr(cmcwr);
                wifiDto.setCnstcYear(cnstcYear);
                wifiDto.setInoutDoor(inoutDoor);
                wifiDto.setRemars3(remars3);
                wifiDto.setLat(lat);
                wifiDto.setLnt(lnt);
                wifiDto.setWorkDttm(workDttm);
                
                System.out.println(mgrNo2);
            }


        } catch (SQLException e) {
        	
            throw new RuntimeException(e);
            
        } finally {

            // 6. 객체 연결 해제
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

        }
	}
	
	// 데이터 삽입하기
	public void dbInsert(WifiDTO wifiDto) {

		try {
            // 1. 드라이버 로드
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affected = 0;

        try {
        	
            connection = DriverManager.getConnection(Db.URL);

            String sql = " INSERT INTO WIFI_INFO VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wifiDto.getMgrNo());
            preparedStatement.setString(2, wifiDto.getWrdofc());
            preparedStatement.setString(3, wifiDto.getMainNm());
            preparedStatement.setString(4, wifiDto.getAdres1());
            preparedStatement.setString(5, wifiDto.getAdres2());
            preparedStatement.setString(6, wifiDto.getInstlFloor());
            preparedStatement.setString(7, wifiDto.getInstlTy());
            preparedStatement.setString(8, wifiDto.getInstlMby());
            preparedStatement.setString(9, wifiDto.getSvcSe());
            preparedStatement.setString(10, wifiDto.getCmcwr());
            preparedStatement.setInt(11, wifiDto.getCnstcYear());
            preparedStatement.setString(12, wifiDto.getInoutDoor());
            preparedStatement.setString(13, wifiDto.getRemars3());
            preparedStatement.setDouble(14, wifiDto.getLat());
            preparedStatement.setDouble(15, wifiDto.getLnt());
            preparedStatement.setString(16, wifiDto.getWorkDttm());

            affected = preparedStatement.executeUpdate();

            if (affected > 0) {
            	System.out.println("저장 성공");
            } else {
            	System.out.println("저장 실패");
            }


        } catch (SQLException e) {
        	
        	e.printStackTrace();
            
        } finally {
        	
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

        }
	}
	
	// 데이터 모두 지우기
	public void dbDeleteAll() {
		try {
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = DriverManager.getConnection(Db.URL);

            String sql = " DELETE FROM WIFI_INFO; ";
            
            preparedStatement = connection.prepareStatement(sql);

            int affected = preparedStatement.executeUpdate();
            
            if (affected > 0) {
                System.out.println("모든 와이파이 데이터 제거 성공");
            } else {
                System.out.println("모든 와이파이 데이터 제거 실패");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
        	try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        	
        }
	}

	// 위치 기반 가까운 곳 20개 추출 후 웹 페이지에 보여주기 위한 List 구현
	public List<WifiDTO> selectList(Double lat, Double lnt) {
		List<WifiDTO> wifiDtoList = new ArrayList<>();
		
		try {
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {

            connection = DriverManager.getConnection(Db.URL);

            String sql = "SELECT *, " +
            		"round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT)-radians(?))" + 
            				"+sin(radians(?))*sin(radians(LAT))), 4) " +
            				"AS distance " +
            				"FROM WIFI_INFO " +
            				"ORDER BY distance " +
            				"LIMIT 20;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setDouble(3, lat);
            
            rs = preparedStatement.executeQuery();

            while (rs.next()) {

            	double dist = rs.getDouble("distance");
            	String mgrNo2 = rs.getString("X_SWIFI_MGR_NO");
                String wrdofc = rs.getString("X_SWIFI_WRDOFC");
                String mainNm = rs.getString("X_SWIFI_MAIN_NM");
                String adres1 = rs.getString("X_SWIFI_ADRES1");
                String adres2 = rs.getString("X_SWIFI_ADRES2");
                String instlFloor = rs.getString("X_SWIFI_INSTL_FLOOR");
                String instlTy = rs.getString("X_SWIFI_INSTL_TY");
                String instlMby = rs.getString("X_SWIFI_INSTL_MBY");
                String svcSe = rs.getString("X_SWIFI_SVC_SE");
                String cmcwr = rs.getString("X_SWIFI_CMCWR");
                int cnstcYear = rs.getInt("X_SWIFI_CNSTC_YEAR");
                String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
                String remars3 = rs.getString("X_SWIFI_REMARS3");
                double lat2 = rs.getDouble("LAT");
                double lnt2 = rs.getDouble("LNT");
                String workDttm = rs.getString("WORK_DTTM");

                WifiDTO wifiDto = new WifiDTO();
                wifiDto.setDist(dist);
                wifiDto.setMgrNo(mgrNo2);
                wifiDto.setWrdofc(wrdofc);
                wifiDto.setMainNm(mainNm);
                wifiDto.setAdres1(adres1);
                wifiDto.setAdres2(adres2);
                wifiDto.setInstlFloor(instlFloor);
                wifiDto.setInstlTy(instlTy);
                wifiDto.setInstlMby(instlMby);
                wifiDto.setSvcSe(svcSe);
                wifiDto.setCmcwr(cmcwr);
                wifiDto.setCnstcYear(cnstcYear);
                wifiDto.setInoutDoor(inoutDoor);
                wifiDto.setRemars3(remars3);
                wifiDto.setLat(lat2);
                wifiDto.setLnt(lnt2);
                wifiDto.setWorkDttm(workDttm);
                
                wifiDtoList.add(wifiDto);
            }


        } catch (SQLException e) {
        	
        	e.printStackTrace();
            
        } finally {

            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }

        }
		
		return wifiDtoList;
	}
}

