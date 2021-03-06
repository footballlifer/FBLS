package nanofuntas.fbls;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

/**
 * This DataHandler class receives data from servlet, 
 * and handle data and finally returns data to servlet 
 */
public class DataHandler {
	private static boolean DEBUG = true;
	private static String TAG = "DataHandler";
	
	/**
	 * This handleData function handles data received from servlet and returns result data to servlet
	 * 
	 * @param jsonReq, JSONObject received from servlet
	 * @return JSONObject result data to servlet
	 */
	public static JSONObject handleData(JSONObject jsonReq) {
		String mReqType = (String) jsonReq.get(Config.KEY_REQ_TYPE);
		if (DEBUG) System.out.println(TAG + ", " + mReqType);	
		if (DEBUG) System.out.println(TAG + ", " + jsonReq.toString());	
		
		JSONObject jsonRsp = new JSONObject();
		
		if (mReqType.equals(Config.KEY_REQ_TYPE_LOGIN)) {
			loginHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_REGISTER)) {
			registerHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_PLAYER_STATUS)) {
			reqPlayerStatusHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_PLAYER_RATING)) {
			ratePlayerHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_TEAM_STATUS)) {
			reqTeamStatusHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_CREATE_TEAM)) {
			createTeamHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_JOIN_TEAM)) {
			joinTeamHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_INCRUIT_PLAYER)) {
			incruitPlayerHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_UPDATA_PLAYER_PROFILE)) {
			updatePlayerProfileHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_MEMBERS_PROFILE)) {
			reqTeamProfileHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_MEMBERS_STATUS)) {			
			reqMemberStatusHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_IMG_UPLOAD)) {
			uploadImageHandler(jsonReq, jsonRsp);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_IMG_DOWNLOAD)) {
			return downloadImageHandler(jsonReq, jsonRsp);	
			
		} 
		if (DEBUG) System.out.println(TAG + " jsonRsp:" + jsonRsp);
		return jsonRsp;		
	}

	private static JSONObject downloadImageHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);

		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_IMG_DOWNLOAD);

		String absoluteStuffPath = FBLServlet.PATH;
		String path = absoluteStuffPath + uid + ".png";		
		FileInputStream in = null;
		
		File f = new File(path);
		if (!f.exists()) { 
			jsonRsp.put(Config.KEY_IMAGE, null);
			return jsonRsp;
		} 
		
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		ByteArrayOutputStream byteArrayOutStrm = new ByteArrayOutputStream();

		int lenth;
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		byte[] outbytes = null;
		
		try {
			while ((lenth = in.read(buffer, 0, buffer.length)) != -1) {
				byteArrayOutStrm.write(buffer, 0, lenth);
			}
			byteArrayOutStrm.flush();
			outbytes = byteArrayOutStrm.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)	in.close();
				if (byteArrayOutStrm != null) byteArrayOutStrm.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	    			
		String strImage = Base64.encodeBase64String(outbytes);
		jsonRsp.put(Config.KEY_IMAGE, strImage);
		return jsonRsp;
	}

	private static void uploadImageHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);
		
		String strImage = (String) jsonReq.get(Config.KEY_IMAGE);
		byte[] bytesImage = Base64.decodeBase64(strImage);

		String absoluteStuffPath = FBLServlet.PATH;
		String path = absoluteStuffPath + uid + ".png";
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(bytesImage);	
			BufferedImage bImageFromConvert = ImageIO.read(in);
		
			if (bImageFromConvert != null) {
				ImageIO.write(bImageFromConvert, "png", new File(path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_IMG_UPLOAD);
		jsonRsp.put(Config.KEY_RESULT, Config.KEY_OK);
	}

	private static void reqMemberStatusHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long tid = (Long) jsonReq.get(Config.KEY_TID);
		
		JSONObject jsonMembersStatus = DatabaseService.getMembersStatus(tid);
		
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_MEMBERS_STATUS);
		jsonRsp.put(Config.KEY_RESULT, jsonMembersStatus);
	}

	private static void reqTeamProfileHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long tid = (Long) jsonReq.get(Config.KEY_TID);
		
		JSONObject jsonMembersProfile = DatabaseService.getMembersProfile(tid);
		
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_MEMBERS_PROFILE);
		jsonRsp.put(Config.KEY_RESULT, jsonMembersProfile);
	}

	private static void updatePlayerProfileHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);
		JSONObject myProfile = (JSONObject) jsonReq.get(Config.KEY_PLAYER_PROFILE);
		
		String value = null;
		for (String s : Config.PLAYER_PROFILE_ARRAY) {
			value = (String) myProfile.get(s);
			DatabaseService.setPlayerProfile(uid, s, value);
		}
		
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_UPDATA_PLAYER_PROFILE);
		jsonRsp.put(Config.KEY_RESULT, Config.KEY_OK);
	}

	private static void incruitPlayerHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long tid = (Long) jsonReq.get(Config.KEY_TID);
		String playerName = (String) jsonReq.get(Config.KEY_NAME);

		long result = DatabaseService.incruitPlayer(tid, playerName);
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_INCRUIT_PLAYER);
		jsonRsp.put(Config.KEY_RESULT, result);
	}

	private static void joinTeamHandler(JSONObject jsonReq, JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);
		String teamName = (String) jsonReq.get(Config.KEY_TEAM_NAME);

		long result = DatabaseService.joinTeam(uid, teamName);
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_JOIN_TEAM);
		jsonRsp.put(Config.KEY_RESULT, result);
	}

	private static void createTeamHandler(JSONObject jsonReq, JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);
		String teamName = (String) jsonReq.get(Config.KEY_TEAM_NAME);

		long result = DatabaseService.createTeam(uid, teamName);
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_CREATE_TEAM);
		jsonRsp.put(Config.KEY_RESULT, result);
	}

	private static void reqTeamStatusHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long tid = (Long) jsonReq.get(Config.KEY_TID);			
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_TEAM_STATUS);
		
		for (String s: Config.TEAM_PROFILE_ARRAY) {
			jsonRsp.put(s, DatabaseService.getTeamProfile(tid, s));
		}
		for (String s: Config.TEAM_LEVEL_ALL_ARRAY) { 
			jsonRsp.put(s, DatabaseService.getTeamLevel(tid, s));
		}
		for (String s: Config.TEAM_RATING_ALL_ARRAY) { 
			jsonRsp.put(s, DatabaseService.getTeamRating(tid, s));				
		}
	}

	private static void ratePlayerHandler(JSONObject jsonReq, JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);			
		JSONObject jsonRating = (JSONObject) jsonReq.get(Config.KEY_RATING);
		long totalRated = DatabaseService.getPlayerRating(uid, Config.KEY_TOTAL_RATED);
		
		for (String s: Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY) {
			updatePlayerRating(uid, s, jsonRating, totalRated);
		}

		totalRated ++;
				
		DatabaseService.setPlayerRating(uid, Config.KEY_TOTAL_RATED, totalRated);
		setOverallRating(uid);
		
		int tid = DatabaseService.getTidForUid(uid);
		updateTeamRatingNLevel(tid);
		
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_PLAYER_RATING);
		jsonRsp.put(Config.KEY_RESULT, Config.KEY_OK);
	}

	private static void reqPlayerStatusHandler(JSONObject jsonReq,
			JSONObject jsonRsp) {
		long uid = (Long) jsonReq.get(Config.KEY_UID);			
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_PLAYER_STATUS);
		
		jsonRsp.put(Config.KEY_UID, uid);
		for (String s: Config.PLAYER_PROFILE_ARRAY) {
			jsonRsp.put(s, DatabaseService.getPlayerProfile(uid, s));
		}
		for (String s: Config.PLAYER_RATING_ALL_ARRAY) { 
			jsonRsp.put(s, DatabaseService.getPlayerRating(uid, s));
		}
	}

	private static void registerHandler(JSONObject jsonReq, JSONObject jsonRsp) {
		String strEmail = (String) jsonReq.get(Config.KEY_EMAIL);
		String strPassword = (String) jsonReq.get(Config.KEY_PASSWORD);
		
		DatabaseService.register(strEmail, strPassword);	
		
		//make sure user is registered and return uid for registering
		JSONObject result = DatabaseService.login(strEmail, strPassword);
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_REGISTER);
		jsonRsp.put(Config.KEY_RESULT, result);
	}

	private static void loginHandler(JSONObject jsonReq, JSONObject jsonRsp) {
		String strEmail = (String) jsonReq.get(Config.KEY_EMAIL);
		String strPassword = (String) jsonReq.get(Config.KEY_PASSWORD);

		JSONObject result = DatabaseService.login(strEmail, strPassword);
		jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_LOGIN);
		jsonRsp.put(Config.KEY_RESULT, result);
	}
	
	private static void updatePlayerRating(long uid, String key, JSONObject jsonRating, long totalRated) {
		if (DEBUG) System.out.println(TAG + ", setNewRating()");	
		
		long ratingNow = DatabaseService.getPlayerRating(uid, key);
		long newRating = (Long) jsonRating.get(key);

		if (totalRated != 0) {
			newRating = (ratingNow * totalRated + newRating) / (totalRated + 1);						
		}

		DatabaseService.setPlayerRating(uid, key, newRating);
	}
	
	private static void setOverallRating(long uid) {
		if (DEBUG) System.out.println(TAG + ", setOverallRating()");

		long ratingSum = 0;
		for (String s: Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY) {
			ratingSum += DatabaseService.getPlayerRating(uid, s);
		}
				
		long newOverall = ratingSum / Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY.length;
		DatabaseService.setPlayerRating(uid, Config.KEY_OVERALL, newOverall);		
	}
	
	private static void updateTeamRatingNLevel(long tid) {
		if (DEBUG) System.out.println(TAG + ", updateTeamRating()");

		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		List<Integer> playerIDs = DatabaseService.getUidsForTid(tid);
		
		int counter = 1;
		for (int uid : playerIDs) {
			if (DatabaseService.getPlayerRating(uid, Config.KEY_TOTAL_RATED) == 0) continue;
			for (String key: Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY) {
				int rating = DatabaseService.getPlayerRating(uid, key);
				if (map.get(key) == null) {
					map.put(key, rating);
				} else {
					int mean = ( map.get(key) * counter + rating ) / (counter + 1);
					map.put(key, mean);
				}
			}
			counter++;
		}
		
		int sum = 0;
		for (String key: Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY) {
			DatabaseService.setTeamRating(tid, key, map.get(key));
			sum += map.get(key);
		}
		int overall = (int) ((float)sum / (float)Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY.length);
		DatabaseService.setTeamRating(tid, Config.KEY_TEAM_OVERALL, overall);
		
		// update team level
		float HUNDRED = 100.0f;
		
		int atkRating = map.get(Config.KEY_ATTACK);
    	int dfsRating = map.get(Config.KEY_DEFENSE);
    	int twkRating = map.get(Config.KEY_TEAMWORK);
    	int mtlRating = map.get(Config.KEY_MENTAL);
    	int powRating = map.get(Config.KEY_POWER);
    	int spdRating = map.get(Config.KEY_SPEED);
    	int staRating = map.get(Config.KEY_STAMINA);
    	int blcRating = map.get(Config.KEY_BALL_CONTROL);
    	int pasRating = map.get(Config.KEY_PASS);
    	int shtRating = map.get(Config.KEY_SHOT);
    	int hdrRating = map.get(Config.KEY_HEADER);
    	int cutRating = map.get(Config.KEY_CUTTING);
    	
    	int atk = (int) atkRating;
    	int dfs = (int) ((dfsRating + cutRating) / 2.0f);
    	int twk = (int) twkRating;
    	int mtl = (int) mtlRating;
    	int phy = (int) ((powRating + spdRating + staRating) / 3.0f);
    	int tec = (int) ((blcRating + pasRating + shtRating + hdrRating) / 4.0f);
    	int ove = (int) ((atk + dfs + twk + mtl + phy + tec) / 6.0f);
    	
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_ATK, atk);
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_DFS, dfs);
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_TWK, twk);
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_MTL, mtl);
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_PHY, phy);
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_TEC, tec);
    	DatabaseService.setTeamLevel(tid, Config.KEY_TEAM_OVERALL, ove);
    	
	}
}
