package DAO;

import DTO.HistoryDTO;
import MyDb.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;


public class HistoryDAO {
	
	// 테이블에 데이터 삽입
	public int insert(HistoryDTO historyDto) {
		
		try {
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affected = 0;

        try {
        	
            connection = DriverManager.getConnection(Db.URL);

            String sql = "INSERT INTO WIFI_HISTORY (LNT, LAT, SEARCH_DTTM) VALUES (?, ?, datetime('now', 'localtime'));";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, historyDto.getLnt());
            preparedStatement.setDouble(2, historyDto.getLat());

            affected = preparedStatement.executeUpdate();

            if (affected > 0) {
            	System.out.println("히스토리 목록 저장 성공!!!");
            } else {
            	System.out.println("히스토리 목록 저장 실패...");
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
        return affected;
	}

	// 행 제거하기
	public int delete(int id) {
		try {
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affected = 0;

        try {
        	
            connection = DriverManager.getConnection(Db.URL);

            String sql = "DELETE FROM WIFI_HISTORY WHERE ID = ?; ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, id);

            affected = preparedStatement.executeUpdate();

            if (affected > 0) {
            	System.out.println("조회 목록 삭제 성공!!!");
            } else {
            	System.out.println("조회 목록 삭제 실패...");
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
        return affected;
	}

	// 테이블 행 개수 구하기
	public int count() {
		int count = 0;
		
		try {
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection(Db.URL);

            String sql = " SELECT count(*) FROM WIFI_HISTORY; ";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {

            	count = rs.getInt(1);

            }

        } catch (SQLException e) {
        	
            throw new RuntimeException(e);
            
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
        
		return count;
	}

	// 저장된 테이블의 행 웹에 보여주기 위한 List 구현
	public List<HistoryDTO> selectList() {
		List<HistoryDTO> historyDtoList = new ArrayList<>();
		
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

            String sql = "SELECT * FROM WIFI_HISTORY ORDER BY ID DESC";

            preparedStatement = connection.prepareStatement(sql);
            
            rs = preparedStatement.executeQuery();

            while (rs.next()) {

            	int id = rs.getInt("ID");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                String SearchDttm = rs.getString("SEARCH_DTTM");

                HistoryDTO historyDto = new HistoryDTO();
                historyDto.setId(id);
                historyDto.setLat(lat);
                historyDto.setLnt(lnt);
                historyDto.setSearchDttm(SearchDttm);
                
                historyDtoList.add(historyDto);
            }

        } catch (SQLException e) {
        	
            throw new RuntimeException(e);
            
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
		
		return historyDtoList;
	}

}
