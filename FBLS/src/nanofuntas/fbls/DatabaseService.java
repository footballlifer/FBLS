package nanofuntas.fbls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONObject;

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
	
	public static JSONObject login(String email, String pw){
		if(DEBUG) System.out.println(TAG + ": login(), Email:"+email+",Password:"+pw);

		JSONObject result = new JSONObject();
		long uid = -1; // return -1 if not registered
		long tid = -1; 
		
		conn = getDBConnection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM USER_LOGIN_INFO");
			
			while(rs.next()) {
				if( rs.getString(Config.KEY_EMAIL).equals(email) && 
						rs.getString(Config.KEY_PASSWORD).equals(pw) )
					uid = rs.getLong(Config.KEY_UID); // return uid for successful login
			}			
			
			// get tid
			ps = conn.prepareStatement("SELECT TID FROM TEAM_PLAYER WHERE UID = ?");
			ps.setLong(1, uid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				tid = rs.getLong(Config.KEY_TID);					
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, st, conn);
		}
		
		result.put(Config.KEY_UID, uid);
		result.put(Config.KEY_TID, tid);
		
		return result;
	}
	
	public static void register(String email, String pw){
		if(DEBUG) System.out.println(TAG + ": register(), Email:"+email+",Password:"+pw);
		
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
			close(ps, conn);
		}	
	}

	public static void setPlayerProfile(long uid, String key, String value) {
		if(DEBUG) System.out.println(TAG + ": setPlayerProfile(), UID:"+uid+",KEY:"+key+",VALUE:"+value);
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE PLAYER_PROFILE SET "+key+" = ? WHERE UID = ?");
			ps.setString(1, value);
			ps.setLong(2, uid);
			ps.executeUpdate();				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
	}
	
	public static String getPlayerProfile(long uid, String key) {
		if(DEBUG) System.out.println(TAG + ": getPlayerProfile(), UID:"+uid+",KEY:"+key);

		String result = null;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT "+key+" FROM PLAYER_PROFILE WHERE UID = ?");
			ps.setLong(1, uid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = rs.getString(key);					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}		
		
		if(DEBUG) System.out.println(TAG + ": getPlayerProfile(), UID:"+uid+",KEY:"+key+",RESULT:"+result);
		return result;
	}
	
	public static void setPlayerRating(long uid, String key, long value) {
		if(DEBUG) System.out.println(TAG + ": setPlayerRating(), UID:"+uid+",KEY:"+key+",VALUE:"+value);
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE PLAYER_RATING SET "+key+" = ? WHERE UID = ?");
			ps.setLong(1, value);
			ps.setLong(2, uid);
			ps.executeUpdate();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
	}
	
	public static int getPlayerRating(long uid, String key) {
		if(DEBUG) System.out.println(TAG + ": getPlayerRating(), UID:"+uid+",KEY:"+key);

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
			close(rs, ps, conn);
		}
		
		if(DEBUG) System.out.println(TAG + ": getPlayerRating(), UID:"+uid+",KEY:"+key+",RESULT:"+result);
		return result;
	}

	public static String getTeamProfile(long tid, String key) {
		if(DEBUG) System.out.println(TAG + ": getTeamProfile(), TID:"+tid+",KEY:"+key);

		String result = null;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT "+key+" FROM TEAM_PROFILE WHERE TID = ?");
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = rs.getString(key);					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}		
		
		if(DEBUG) System.out.println(TAG + ": getTeamProfile(), TID:"+tid+",KEY:"+key+",RESULT:"+result);
		return result;
	}
	
	public static int getTeamLevel(long tid, String key) {
		if(DEBUG) System.out.println(TAG + ": getTeamLevel(), TID:"+tid+",KEY:"+key);

		int result = -1;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT " + key +" FROM TEAM_LEVEL WHERE TID = ?");
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = Integer.parseInt( rs.getString(key) );					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		
		if(DEBUG) System.out.println(TAG + ": getTeamLevel(), TID:"+tid+",KEY:"+key+",RESULT:"+result);
		return result;
	}
	
	public static void setTeamLevel(long tid, String key, long value) {
		if(DEBUG) System.out.println(TAG + ": setTeamLevel(), TID:"+tid+",KEY:"+key+",VALUE:"+value);
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE TEAM_LEVEL SET "+key+" = ? WHERE TID = ?");
			ps.setLong(1, value);
			ps.setLong(2, tid);
			ps.executeUpdate();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
	}
	
	public static int getTeamRating(long tid, String key) {
		if(DEBUG) System.out.println(TAG + ": getTeamRating(), TID:"+tid+",KEY:"+key);

		int result = -1;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT " + key +" FROM TEAM_RATING WHERE TID = ?");
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = Integer.parseInt( rs.getString(key) );					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		
		if(DEBUG) System.out.println(TAG + ": getTeamRating(), TID:"+tid+",KEY:"+key+",RESULT:"+result);
		return result;
	}
	
	public static void setTeamRating(long tid, String key, long value) {
		if(DEBUG) System.out.println(TAG + ": setTeamRating(), TID:"+tid+",KEY:"+key+",VALUE:"+value);
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE TEAM_RATING SET "+key+" = ? WHERE TID = ?");
			ps.setLong(1, value);
			ps.setLong(2, tid);
			ps.executeUpdate();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
	}

	public static ArrayList<Integer> getUidsForTid(long tid) {
		if(DEBUG) System.out.println(TAG + ": getPlayerIdsForTeam(), TID:"+tid);
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT UID FROM TEAM_PLAYER WHERE TID = ?");
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				result.add(rs.getInt("UID"));					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		
		if(DEBUG) System.out.println(TAG + ": getPlayerIdsForTeam(), TID:"+tid+",RESULT:"+result);
		return result;
	}
	
	public static int getTidForUid(long uid) {
		if(DEBUG) System.out.println(TAG + ": getTidForUid(), UID:"+uid);

		int tid = -1;
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = conn.prepareStatement("SELECT TID FROM TEAM_PLAYER WHERE UID = ?");
			ps.setLong(1, uid);
			rs = ps.executeQuery();
			
			while(rs.next()){
				tid = rs.getInt("TID");					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}		
		
		if(DEBUG) System.out.println(TAG + ": getTidForUid(), UID:"+uid);
		return tid;
	}
	
	public static long createTeam(long uid, String teamName) {
		if(DEBUG) System.out.println(TAG + ": createTeam(), UID:"+uid+",teamName:"+teamName);
		
		long tid = -1;
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO TEAM_PROFILE (TEAM_NAME) VALUES(?)");
			ps.setString(1, teamName);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("INSERT INTO TEAM_LEVEL (OVERALL) VALUES(?)");
			ps.setInt(1, 0);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("INSERT INTO TEAM_RATING (OVERALL) VALUES(?)");
			ps.setInt(1, 0);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT TID FROM TEAM_PROFILE WHERE TEAM_NAME = ?");
			ps.setString(1, teamName);
			rs = ps.executeQuery();			
			while(rs.next()){
				tid = rs.getLong(Config.KEY_TID);			
			}
			
			ps = conn.prepareStatement("INSERT INTO TEAM_PLAYER (TID, UID) VALUES(?, ?)");
			ps.setLong(1, tid);
			ps.setLong(2, uid);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		
		return tid;
	}
	
	public static long joinTeam(long uid, String teamName) {
		if(DEBUG) System.out.println(TAG + ": joinTeam(), UID:"+uid+",teamName:"+teamName);
		
		long tid = -1;
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {		
			ps = conn.prepareStatement("SELECT TID FROM TEAM_PROFILE WHERE TEAM_NAME = ?");
			ps.setString(1, teamName);
			rs = ps.executeQuery();			
			while(rs.next()){
				tid = rs.getLong(Config.KEY_TID);			
			}
			
			ps = conn.prepareStatement("INSERT INTO TEAM_PLAYER (TID, UID) VALUES(?, ?)");
			ps.setLong(1, tid);
			ps.setLong(2, uid);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return tid;
	}
	
	public static long incruitPlayer(long tid, String playerName) {
		if(DEBUG) System.out.println(TAG + ": incruitPlayer(), TID:"+tid+",playerName:"+playerName);
		
		long uid = -1;
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {		
			ps = conn.prepareStatement("SELECT UID FROM PLAYER_PROFILE WHERE NAME = ?");
			ps.setString(1, playerName);
			rs = ps.executeQuery();			
			while(rs.next()){
				uid = rs.getLong(Config.KEY_UID);
			}
			
			if (uid > 0) {
				ps = conn.prepareStatement("INSERT INTO TEAM_PLAYER (TID, UID) VALUES(?, ?)");
				ps.setLong(1, tid);
				ps.setLong(2, uid);
				ps.executeUpdate();
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return uid;
	}
	
	public static JSONObject getMembersProfile(long tid) {
		if(DEBUG) System.out.println(TAG + ": getMembersProfile(), TID:"+tid);
		
		long uid = -1;
		long count = 0;
		JSONObject profile = null;
		JSONObject jsonMembersProfile = new JSONObject();
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {		
			ps = conn.prepareStatement("SELECT UID FROM TEAM_PLAYER WHERE TID = ?");
			ps.setLong(1, tid);
			rs = ps.executeQuery();			
			while(rs.next()){
				count ++;
				uid = rs.getLong(Config.KEY_UID);
				profile = new JSONObject();
				
				profile.put(Config.KEY_UID, uid);
				
				for (String s : Config.PLAYER_PROFILE_ARRAY) {
					profile.put(s, getPlayerProfile(uid, s));
				}
				jsonMembersProfile.put(Long.toString(count), profile);
			}
			jsonMembersProfile.put(Config.KEY_MEMBERS_COUNT, count);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return jsonMembersProfile;
	}

	public static JSONObject getMembersStatus(long tid) {
		// TODO Auto-generated method stub
		if(DEBUG) System.out.println(TAG + ": getMembersStatus(), TID:"+tid);
		
		long uid = -1;
		long count = 0;
		JSONObject status = null;
		JSONObject jsonMembersStatus = new JSONObject();
		
		conn = getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {		
			ps = conn.prepareStatement("SELECT UID FROM TEAM_PLAYER WHERE TID = ?");
			ps.setLong(1, tid);
			rs = ps.executeQuery();			
			while(rs.next()){
				count ++;
				uid = rs.getLong(Config.KEY_UID);
				status = new JSONObject();
				
				status.put(Config.KEY_UID, uid);
				
				for (String s : Config.PLAYER_PROFILE_ARRAY) {
					status.put(s, getPlayerProfile(uid, s));
				}
				
				for (String s : Config.PLAYER_RATING_ALL_ARRAY) {
					status.put(s, getPlayerRating(uid, s));
				}
				
				jsonMembersStatus.put(Long.toString(count), status);
			}
			jsonMembersStatus.put(Config.KEY_MEMBERS_COUNT, count);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return jsonMembersStatus;
	}
	
	
	private static void close(Statement st, Connection conn) {
		try {
			if (st != null) st.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) rs.close();
			if (st != null) st.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void close(ResultSet rs, Statement st1, Statement st2, Connection conn) {
		try {
			if (rs != null) rs.close();
			if (st1 != null) st1.close();
			if (st2 != null) st2.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

