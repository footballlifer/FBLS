package nanofuntas.fbls;

public class Config {
	// request type
	public static final String KEY_REQ_TYPE = "REQ_TYPE";
	public static final String KEY_REQ_TYPE_LOGIN = "REQ_TYPE_LOGIN";
	public static final String KEY_REQ_TYPE_REGISTER = "REQ_TYPE_REGISTER";
	public static final String KEY_REQ_TYPE_PLAYER_STATUS = "REQ_TYPE_PLAYER_STATUS";
	public static final String KEY_REQ_TYPE_TEAM_STATUS = "REQ_TYPE_TEAM_STATUS";	
	public static final String KEY_REQ_TYPE_PLAYER_RATING = "REQ_TYPE_PLAYER_RATING";
	public static final String KEY_REQ_TYPE_CREATE_TEAM = "REQ_TYPE_CREATE_TEAM";
	public static final String KEY_REQ_TYPE_JOIN_TEAM = "REQ_TYPE_JOIN_TEAM";
	public static final String KEY_REQ_TYPE_INCRUIT_PLAYER = "REQ_TYPE_INCRUIT_PLAYER";
	public static final String KEY_REQ_TYPE_UPDATA_PLAYER_PROFILE = "REQ_TYPE_UPDATA_PLAYER_PROFILE";
	public static final String KEY_REQ_TYPE_MEMBERS_PROFILE = "REQ_TYPE_MEMBERS_PROFILE";
	public static final String KEY_REQ_TYPE_MEMBERS_STATUS = "REQ_TYPE_MEMBERS_STATUS";
	public static final String KEY_REQ_TYPE_IMG_UPLOAD = "REQ_TYPE_IMG_UPLOAD";
	public static final String KEY_REQ_TYPE_IMG_DOWNLOAD = "REQ_TYPE_IMG_DOWNLOAD";
	
	// response type
	public static final String KEY_RSP_TYPE = "RSP_TYPE";
	public static final String KEY_RSP_TYPE_LOGIN = "RSP_TYPE_LOGIN";
	public static final String KEY_RSP_TYPE_REGISTER = "RSP_TYPE_REGISTER";
	public static final String KEY_RSP_TYPE_PLAYER_STATUS = "RSP_TYPE_PLAYER_STATUS";
	public static final String KEY_RSP_TYPE_TEAM_STATUS = "RSP_TYPE_TEAM_STATUS";
	public static final String KEY_RSP_TYPE_PLAYER_RATING = "RSP_TYPE_PLAYER_RATING";
	public static final String KEY_RSP_TYPE_CREATE_TEAM	= "RSP_TYPE_CREATE_TEAM";
	public static final String KEY_RSP_TYPE_JOIN_TEAM = "RSP_TYPE_JOIN_TEAM";
	public static final String KEY_RSP_TYPE_INCRUIT_PLAYER = "RSP_TYPE_INCRUIT_PLAYER";
	public static final String KEY_RSP_TYPE_UPDATA_PLAYER_PROFILE = "RSP_TYPE_UPDATA_PLAYER_PROFILE";
	public static final String KEY_RSP_TYPE_MEMBERS_PROFILE = "RSP_TYPE_MEMBERS_PROFILE";
	public static final String KEY_RSP_TYPE_MEMBERS_STATUS = "RSP_TYPE_MEMBERS_STATUS";
	public static final String KEY_RSP_TYPE_IMG_UPLOAD = "RSP_TYPE_IMG_UPLOAD";
	public static final String KEY_RSP_TYPE_IMG_DOWNLOAD = "RSP_TYPE_IMG_DOWNLOAD";
	
	// player profile 
	public static final String KEY_NAME = "NAME";
	public static final String KEY_POSITION = "POSITION";	
	public static final String KEY_AGE = "AGE";	
	public static final String KEY_HEIGHT = "HEIGHT";	
	public static final String KEY_WEIGHT = "WEIGHT";	
	public static final String KEY_FOOT = "FOOT";

	// player rating
	public static final String KEY_ATTACK = "ATTACK";	
	public static final String KEY_DEFENSE = "DEFENSE";	
	public static final String KEY_TEAMWORK = "TEAMWORK";	
	public static final String KEY_MENTAL = "MENTAL";
	public static final String KEY_POWER = "POWER";
	public static final String KEY_SPEED = "SPEED";	
	public static final String KEY_STAMINA = "STAMINA";	
	public static final String KEY_BALL_CONTROL = "BALL_CONTROL";	
	public static final String KEY_PASS = "PASS";	
	public static final String KEY_SHOT = "SHOT";	
	public static final String KEY_HEADER = "HEADER";	
	public static final String KEY_CUTTING = "CUTTING";	
	public static final String KEY_OVERALL = "OVERALL";	
	
	// team profile
	public static final String KEY_TEAM_NAME = "TEAM_NAME";
	
	// team rating
	public static final String KEY_TEAM_ATK = "ATK";	
	public static final String KEY_TEAM_DFS = "DFS";	
	public static final String KEY_TEAM_TEC = "TEC";	
	public static final String KEY_TEAM_PHY = "PHY";
	public static final String KEY_TEAM_TWK = "TWK";
	public static final String KEY_TEAM_MTL = "MTL";	
	public static final String KEY_TEAM_OVERALL = "OVERALL";
	
	public static final String KEY_UID = "UID";
	public static final String KEY_TID = "TID";
	public static final String KEY_TOTAL_RATED = "TOTAL_RATED";
	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_PASSWORD = "PASSWORD";		
	public static final String KEY_RESULT = "RESULT";
	public static final String KEY_OK = "OK";
	public static final String KEY_RATING = "RATING";
	public static final String KEY_PLAYER_PROFILE = "RATING";
	public static final String KEY_MEMBERS_COUNT = "MEMBERS_COUNT";
	public static final String KEY_IMAGE = "IMAGE";
	
	public static final String FBL_SETTINGS = "FBL_SETTINGS";
	
	public static final String[] PLAYER_PROFILE_ARRAY = {
		KEY_NAME,
		KEY_POSITION,
		KEY_AGE,
		KEY_HEIGHT,
		KEY_WEIGHT,
		KEY_FOOT
	};
	
	public static final String[] PLAYER_RATING_ALL_ARRAY = {
		KEY_ATTACK,
		KEY_DEFENSE,
		KEY_TEAMWORK,
		KEY_MENTAL,
		KEY_POWER,
		KEY_SPEED,
		KEY_STAMINA,
		KEY_BALL_CONTROL,
		KEY_PASS,
		KEY_SHOT,
		KEY_HEADER,
		KEY_CUTTING,
		KEY_OVERALL
	};
	
	public static final String[] PLAYER_RATING_WITHOUT_OVERALL_ARRAY = {
		KEY_ATTACK,
		KEY_DEFENSE,
		KEY_TEAMWORK,
		KEY_MENTAL,
		KEY_POWER,
		KEY_SPEED,
		KEY_STAMINA,
		KEY_BALL_CONTROL,
		KEY_PASS,
		KEY_SHOT,
		KEY_HEADER,
		KEY_CUTTING
	};
	
	public static final String[] TEAM_PROFILE_ARRAY = {
		KEY_TEAM_NAME
	};
	
	public static final String[] TEAM_LEVEL_ALL_ARRAY = {
		KEY_TEAM_ATK,
		KEY_TEAM_DFS,
		KEY_TEAM_TEC,
		KEY_TEAM_PHY,
		KEY_TEAM_TWK,
		KEY_TEAM_MTL,
		KEY_TEAM_OVERALL
	};
	
	public static final String[] TEAM_LEVEL_WITHOUT_OVERALL_ARRAY = {
		KEY_TEAM_ATK,
		KEY_TEAM_DFS,
		KEY_TEAM_TEC,
		KEY_TEAM_PHY,
		KEY_TEAM_TWK,
		KEY_TEAM_MTL
	};
	
	public static final String[] TEAM_RATING_ALL_ARRAY = {
		KEY_ATTACK,
		KEY_DEFENSE,
		KEY_TEAMWORK,
		KEY_MENTAL,
		KEY_POWER,
		KEY_SPEED,
		KEY_STAMINA,
		KEY_BALL_CONTROL,
		KEY_PASS,
		KEY_SHOT,
		KEY_HEADER,
		KEY_CUTTING,
		KEY_OVERALL
	};
	
}
