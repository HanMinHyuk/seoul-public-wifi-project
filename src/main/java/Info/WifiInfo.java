package Info;

import DAO.WifiDAO;
import MyDb.Db;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WifiInfo {
	private static final String BaseUrl = "http://openapi.seoul.go.kr:8088";
	private static final String KEY = "76796b5175616c7335377571715177";
	private static final String DataType = "json";
	private static final String ServiceName = "TbPublicWifiInfo";

	// 서울 공공 와이파이 총 개수를 구한다.
    public int totalCnt() throws IOException {
    	String urlBuilder = BaseUrl + "/" +
                URLEncoder.encode(KEY, "UTF-8") + "/" +
                URLEncoder.encode(DataType, "UTF-8") + "/" +
                URLEncoder.encode(ServiceName, "UTF-8") + "/1/1";

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class); // Json 문자열을 JsonObject 클래스로 변환
        int listTotalCount = jsonObject.getAsJsonObject(ServiceName).get("list_total_count").getAsInt();
        return listTotalCount;
    }

    // 서울 공공 와이파이 초기화 후
    public void init() {
    	WifiDAO wifiDao = new WifiDAO();
    	wifiDao.dbDeleteAll();
    }
    
    
    // 서울 공공 와이파이를 저장한다.
    public void saveInfo() throws IOException {
    	
    	try {
            Class.forName(Db.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	
    	try {
    		
            connection = DriverManager.getConnection(Db.URL);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO WIFI_INFO VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);

            int data = 1;
            for (int i = 0; i < totalCnt() / 1000 + 1; i++) {
                String urlBuilder = BaseUrl + "/" +
                        URLEncoder.encode(KEY, "UTF-8") + "/" +
                        URLEncoder.encode(DataType, "UTF-8") + "/" +
                        URLEncoder.encode(ServiceName, "UTF-8") + "/" +
                        URLEncoder.encode(String.valueOf(data), "UTF-8") + "/" +
                        URLEncoder.encode(String.valueOf(data + 999), "UTF-8"); 

                URL url = new URL(urlBuilder);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                conn.disconnect();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
                JsonArray rowArray = jsonObject.getAsJsonObject(ServiceName).getAsJsonArray("row");

                for (int j = 0; j < rowArray.size(); j++) {
                    JsonObject rowObject = rowArray.get(j).getAsJsonObject();

                    preparedStatement.setString(1, rowObject.get("X_SWIFI_MGR_NO").getAsString());
                    preparedStatement.setString(2, rowObject.get("X_SWIFI_WRDOFC").getAsString());
                    preparedStatement.setString(3, rowObject.get("X_SWIFI_MAIN_NM").getAsString());
                    preparedStatement.setString(4, rowObject.get("X_SWIFI_ADRES1").getAsString());
                    preparedStatement.setString(5, rowObject.get("X_SWIFI_ADRES2").getAsString());
                    preparedStatement.setString(6, rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    preparedStatement.setString(7, rowObject.get("X_SWIFI_INSTL_TY").getAsString());
                    preparedStatement.setString(8, rowObject.get("X_SWIFI_INSTL_MBY").getAsString());
                    preparedStatement.setString(9, rowObject.get("X_SWIFI_SVC_SE").getAsString());
                    preparedStatement.setString(10, rowObject.get("X_SWIFI_CMCWR").getAsString());
                    if ("미확인".equals(rowObject.get("X_SWIFI_CNSTC_YEAR").getAsString())) {
                    	preparedStatement.setString(11, rowObject.get("X_SWIFI_CNSTC_YEAR").getAsString());
                    } else {
                    	preparedStatement.setInt(11, rowObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                    }
                    preparedStatement.setString(12, rowObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                    preparedStatement.setString(13, rowObject.get("X_SWIFI_REMARS3").getAsString());
                    preparedStatement.setDouble(14, rowObject.get("LAT").getAsDouble());
                    preparedStatement.setDouble(15, rowObject.get("LNT").getAsDouble());
                    preparedStatement.setString(16, rowObject.get("WORK_DTTM").getAsString());
                    preparedStatement.addBatch(); // 바로 쿼리 실행을 하지 않고 쿼리를 메모리에 올려두었다가 executeBatch()로 실행하면 메모리에 올려두었던 쿼리를 한번에 날려 보낸다.
                }
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
                connection.commit();

                data += 1000;
            }
            
            System.out.println("이거 뜨면 성공한거다 ㅎ");
            
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
}
