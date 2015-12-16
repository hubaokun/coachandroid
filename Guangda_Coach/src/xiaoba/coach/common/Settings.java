package xiaoba.coach.common;

public class Settings {
	public static final boolean DEBUG = true;
	/**
	 * 临时文件路径
	 */
	public static String TEMP_PATH = "";
	/**
	 * 缓存图片路径
	 */
	public static String PIC_PATH = "";
	/**
	 * 保存图片路径
	 */
	public static String PHOTO_PATH = "";
	/**
	 * 缓存语音路径
	 */
	public static String VIDEO_PATH = "";
	/**
	 * 安装包路径
	 */
	public static String APK_PATH = "";
	/**
	 * 屏幕高度
	 */
	public static int DISPLAY_HEIGHT;
	/**
	 * 屏幕宽度
	 */
	public static int DISPLAY_WIDTH;
	/**
	 * 状态栏高度
	 */
	public static int STATUS_BAR_HEIGHT;
	/**
	 * 保存apk路径
	 */
	public static int Width640 = 640; //<720
	public static int Height640 = 1136;
	public static int Width750 = 750;  //>720,<1080
	public static int Height750 = 1333;
	public static int Width1242 = 1242;  //>1080
	public static int Height1242 =2208;
	public static String APK_SAVE = "";
	public static final int MONEY =1;  //余额
	public static final int COUPON = 2;  //小巴券
	public static final int COIN = 3;  //小巴币
	public static final int WITHPAYTYPE = 0; //没有支付方式 
	public static final int COMMIT = 4;
	public static final int CAMERA = 1001;
	public static final int ALBUM = 1002;
	public static final int CROP = 1004;
	public static final int CALCULATOR = 1005;
	public static final String PICTURE_TEMP_EXTENSION = ".tmp";
	//public static final String BASE_URL = "http://192.168.1.144:8080/guangda";
	//public static final String BASE_URL = "http://192.168.1.116:8080/";
	//public static final String BASE_URL = "http://192.168.1.162/dadmin";
	//public static final String BASE_URL = "http://120.25.236.228/dadmin";
	//public static final String BASE_URL = "http://192.168.1.88:8080/xiaoba";
	//public static final String BASE_URL = "http://192.168.1.38:8080/guangda";
	//public static final String BASE_URL = "http://192.168.1.188:8080/xb";
	//public static final String BASE_URL = "http://www.xiaobakaiche.com/dadmin";
	public static final String BASE_URL = "http://xiaobaxueche.com:8080/dadmin2.0.0";
	public static final String PGY_APP_ID = "0b9cee5728dbd943795b67d096be3e85"; //正式环境的Appid
	//public static final String PGY_APP_ID = "8cb0ea072ee0cd493495a691bf07cf2f";  //测试环境的Appid
	//public static final String PGY_APP_ID = "0936f8d01631ca9e8ea4e3955ebb2a03";  //地推环境的Appid
	public static final String BASE_SHARE = "http://www.xiaobakaiche.com";

	public static final String USER_URL = BASE_URL + "/cuser";

	public static final String SUSER_URL = BASE_URL + "/suser";

	public static final String TASK_URL = BASE_URL + "/ctask";
	
	public static final String SORDER_URL = BASE_URL + "/sorder";

	public static final String SCHEDULE_URL = BASE_URL + "/cschedule";

	public static final String CMY_URL = BASE_URL + "/cmy";

	public static final String SYSTEM_URL = BASE_URL + "/system";

	public static final String SSET_URL = BASE_URL + "/sset";

	public static final String SBOOK_URL = BASE_URL + "/sbook";
	
	public static final String CRECOMM = BASE_URL + "/recomm";
	
	public static final String SHARE = BASE_SHARE + "/share.jsp?";
	
	public static final String ADVER_URL = BASE_URL+"/adver";
	
	public static final String LOCATION_URL = BASE_URL + "/location";
}
