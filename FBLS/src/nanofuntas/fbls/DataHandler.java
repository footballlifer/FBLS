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
		
		if (mReqType.equals(Config.KEY_REQ_LOGIN)) {
			String strEmail = (String) jsonReq.get(Config.KEY_EMAIL);
			String strPassword = (String) jsonReq.get(Config.KEY_PASSWORD);

			long uid = DatabaseService.login(strEmail, strPassword);
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_LOGIN);
			jsonRsp.put(Config.KEY_RESULT, uid);
			
		} else if (mReqType.equals(Config.KEY_REQ_REGISTER)) {
			String strEmail = (String) jsonReq.get(Config.KEY_EMAIL);
			String strPassword = (String) jsonReq.get(Config.KEY_PASSWORD);
			
			DatabaseService.register(strEmail, strPassword);	
			
			//make sure user is registered and return uid for registering
			long uid = DatabaseService.login(strEmail, strPassword);
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_LOGIN);
			jsonRsp.put(Config.KEY_RESULT, uid);
			
		} else if(mReqType.equals(Config.KEY_REQ_STATUS)) {
			long uid = (Long) jsonReq.get(Config.KEY_UID);
			
			if (DEBUG) System.out.println(TAG + ", UID =  " + uid);	
			
			jsonRsp.put(Config.KEY_RSP_TYPE, Config.KEY_RSP_STATUS);
			
			jsonRsp.put(Config.KEY_NAME, DatabaseService.getPlayerProfile(uid, Config.KEY_NAME));
			jsonRsp.put(Config.KEY_POSITION, DatabaseService.getPlayerProfile(uid, Config.KEY_POSITION));
			
			jsonRsp.put(Config.KEY_ATTACK, DatabaseService.getPlayerRating(uid, Config.KEY_ATTACK));
			jsonRsp.put(Config.KEY_DEFENSE, DatabaseService.getPlayerRating(uid, Config.KEY_DEFENSE));
			jsonRsp.put(Config.KEY_TEAMWORK, DatabaseService.getPlayerRating(uid, Config.KEY_TEAMWORK));
			jsonRsp.put(Config.KEY_MENTAL, DatabaseService.getPlayerRating(uid, Config.KEY_MENTAL));
			jsonRsp.put(Config.KEY_POWER, DatabaseService.getPlayerRating(uid, Config.KEY_POWER));
			jsonRsp.put(Config.KEY_SPEED, DatabaseService.getPlayerRating(uid, Config.KEY_SPEED));
			jsonRsp.put(Config.KEY_STAMINA, DatabaseService.getPlayerRating(uid, Config.KEY_STAMINA));
			jsonRsp.put(Config.KEY_BALL_CONTROL, DatabaseService.getPlayerRating(uid, Config.KEY_BALL_CONTROL));
			jsonRsp.put(Config.KEY_PASS, DatabaseService.getPlayerRating(uid, Config.KEY_PASS));
			jsonRsp.put(Config.KEY_SHOT, DatabaseService.getPlayerRating(uid, Config.KEY_SHOT));
			jsonRsp.put(Config.KEY_HEADER, DatabaseService.getPlayerRating(uid, Config.KEY_HEADER));
			jsonRsp.put(Config.KEY_CUTTING, DatabaseService.getPlayerRating(uid, Config.KEY_CUTTING));
			jsonRsp.put(Config.KEY_TEMPER, DatabaseService.getPlayerRating(uid, Config.KEY_TEMPER));
			jsonRsp.put(Config.KEY_OVERALL, DatabaseService.getPlayerRating(uid, Config.KEY_OVERALL));
			
		}
		return jsonRsp;
		
	}

}
