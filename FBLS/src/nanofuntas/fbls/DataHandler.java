package nanofuntas.fbls;

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
	@SuppressWarnings("unchecked")
	public static JSONObject handleData(JSONObject jsonReq) {
		String mReqType = (String) jsonReq.get(Config.KEY_REQ_TYPE);
		if (DEBUG) System.out.println(TAG + ", " + mReqType);	
		if (DEBUG) System.out.println(TAG + ", " + jsonReq.toString());	
		
		JSONObject jsonRsp = new JSONObject();
		
		if (mReqType.equals(Config.KEY_REQ_TYPE_LOGIN)) {
			String strEmail = (String) jsonReq.get(Config.KEY_EMAIL);
			String strPassword = (String) jsonReq.get(Config.KEY_PASSWORD);

			JSONObject result = DatabaseService.login(strEmail, strPassword);
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_LOGIN);
			jsonRsp.put(Config.KEY_RESULT, result);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_REGISTER)) {
			String strEmail = (String) jsonReq.get(Config.KEY_EMAIL);
			String strPassword = (String) jsonReq.get(Config.KEY_PASSWORD);
			
			DatabaseService.register(strEmail, strPassword);	
			
			//make sure user is registered and return uid for registering
			JSONObject result = DatabaseService.login(strEmail, strPassword);
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_REGISTER);
			jsonRsp.put(Config.KEY_RESULT, result);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_PLAYER_STATUS)) {
			long uid = (Long) jsonReq.get(Config.KEY_UID);			
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_PLAYER_STATUS);
			
			for (String s: Config.PLAYER_PROFILE_ARRAY) {
				jsonRsp.put(s, DatabaseService.getPlayerProfile(uid, s));
			}
			for (String s: Config.PLAYER_RATING_ALL_ARRAY) { 
				jsonRsp.put(s, DatabaseService.getPlayerRating(uid, s));
			}
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_PLAYER_RATING)) {
			long uid = (Long) jsonReq.get(Config.KEY_UID);			
			JSONObject jsonRating = (JSONObject) jsonReq.get(Config.KEY_RATING);
			long totalRated = DatabaseService.getPlayerRating(uid, Config.KEY_TOTAL_RATED);
			
			for (String s: Config.PLAYER_RATING_WITHOUT_OVERALL_ARRAY) {
				setNewRating(uid, s, jsonRating, totalRated);
			}

			totalRated ++;
					
			DatabaseService.setPlayerRating(uid, Config.KEY_TOTAL_RATED, totalRated);
			setOverallRating(uid);
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_PLAYER_RATING);
			jsonRsp.put(Config.KEY_RESULT, Config.KEY_OK);
			
		} else if (mReqType.equals(Config.KEY_REQ_TYPE_TEAM_STATUS)) {
			long tid = (Long) jsonReq.get(Config.KEY_TID);			
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_TYPE_TEAM_STATUS);
			
			for (String s: Config.TEAM_PROFILE_ARRAY) {
				jsonRsp.put(s, DatabaseService.getTeamProfile(tid, s));
			}
			for (String s: Config.TEAM_RATING_ALL_ARRAY) { 
				jsonRsp.put(s, DatabaseService.getTeamRating(tid, s));
			}
			
			//kakpple test
			if (DEBUG) System.out.println(TAG + "jsonRsp:" + jsonRsp);
			
		}
		return jsonRsp;		
	}
	
	private static void setNewRating(long uid, String key, JSONObject jsonRating, long totalRated) {
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
	
}
