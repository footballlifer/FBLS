package nanofuntas.fbls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
	private static boolean DEBUG = true;
	private static final String TAG = "DataBaseService";
	
	private static Connection conn = null;
	private static final String url = "jdbc:mysql://localhost:3306/db";
	private static final String user = "testuser";
	private static final String password = "123";
	
	private static Connection getDBConnection(){
		if (DEBUG) System.out.println(TAG + ": getDBConnection()");
		
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
		
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	public static long login(String email, String pw){
		if(DEBUG) System.out.println(TAG + ": login()");

		long uid = -1; // return -1 if not registered
		conn = getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select * from USER_LOGIN_INFO");
			
			while(rs.next()){
				if( rs.getString(Config.KEY_EMAIL).equals(email) && 
						rs.getString(Config.KEY_PASSWORD).equals(pw) )
					uid = rs.getLong(Config.KEY_UID); // return uid for successful login
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}		
		return uid;
	}
	
	public static void register(String email, String pw){
		if(DEBUG) System.out.println(TAG + ": register()");
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO USER_LOGIN_INFO (EMAIL, PASSWORD) VALUES(?, ?)");
			ps.setString(1, email);
			ps.setString(2, pw);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("INSERT INTO PLAYER_PROFILE () VALUES()");
			ps.executeUpdate();
			
			ps = conn.prepareStatement("INSERT INTO PLAYER_RATING () VALUES()");
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}	
	}

	public static void setPlayerProfile(long uid, String key, String value) {
		if(DEBUG) System.out.println(TAG + ": setPlayerProfile()");
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE PLAYER_PROFILE SET " + key + " = ? WHERE UID = ?");
			ps.setString(1, value);
			ps.setLong(2, uid);
			ps.executeUpdate();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static String getPlayerProfile(long uid, String key) {
		if(DEBUG) System.out.println(TAG + ": getPlayerProfile()");

		String result = null;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT " + key +" FROM PLAYER_PROFILE WHERE UID = ?");
			ps.setLong(1, uid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = rs.getString(key);					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}		
		
		return result;
		
	}
	
	public static void setPlayerRating(long uid, String key, int value) {
		if(DEBUG) System.out.println(TAG + ": setPlayerRating()");
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE PLAYER_RATING SET " + key + " = ? WHERE UID = ?");
			ps.setInt(1, value);
			ps.setLong(2, uid);
			ps.executeUpdate();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static int getPlayerRating(long uid, String key) {
		if(DEBUG) System.out.println(TAG + ": getPlayerRating()");

		int result = -1;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT " + key +" FROM PLAYER_RATING WHERE UID = ?");
			ps.setLong(1, uid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = Integer.parseInt( rs.getString(key) );					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}		
		return result;
	}

	
}

