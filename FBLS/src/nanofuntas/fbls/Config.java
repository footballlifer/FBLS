package nanofuntas.fbls;

public class Config {
	public static final String KEY_REQ_TYPE = "REQ_TYPE";
	// request type
	public static final String KEY_REQ_TYPE_LOGIN = "REQ_TYPE_LOGIN";
	public static final String KEY_REQ_TYPE_REGISTER = "REQ_TYPE_REGISTER";
	public static final String KEY_REQ_TYPE_STATUS = "REQ_TYPE_STATUS";	
	public static final String KEY_REQ_TYPE_RATING = "REQ_TYPE_RATING";
	
	public static final String KEY_RSP_TYPE = "RSP_TYPE";
	// response type
	public static final String KEY_RSP_TYPE_LOGIN = "RSP_TYPE_LOGIN";
	public static final String KEY_RSP_TYPE_REGISTER = "RSP_TYPE_REGISTER";
	public static final String KEY_RSP_TYPE_STATUS = "RSP_TYPE_STATUS";
	public static final String KEY_RSP_TYPE_RATING = "RSP_TYPE_RATING";
		
	// player profile 
	public static final String KEY_NAME = "NAME";
	public static final String KEY_POSITION = "POSITION";	

	// player rating
	public static final long NUMBER_OF_RATING_ITEM = 13;
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
	public static final String KEY_TEMPER = "TEMPER";	
	public static final String KEY_OVERALL = "OVERALL";	
	
	public static final String KEY_UID = "UID";
	public static final String KEY_TOTAL_RATED = "TOTAL_RATED";
	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_PASSWORD = "PASSWORD";		
	public static final String KEY_RESULT = "RESULT";
	public static final String KEY_OK = "OK";
	public static final String KEY_RATING = "RATING";
	
	public static final String FBL_SETTINGS = "FBL_SETTINGS";
	
	public static final String[] PROFILE_ARRAY = {
		KEY_NAME,
		KEY_POSITION
	};
	
	public static final String[] RATING_ALL_ARRAY = {
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
		KEY_TEMPER,
		KEY_OVERALL
	};
	
	public static final String[] RATING_WITHOUT_OVERALL_ARRAY = {
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
		KEY_TEMPER
	};
	
}
