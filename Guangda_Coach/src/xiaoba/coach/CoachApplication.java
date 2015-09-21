package xiaoba.coach;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xiaoba.coach.common.Settings;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.easemob.chat.EMChat;
import com.easemob.helpdeskdemo.DemoHXSDKHelper;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pgyersdk.crash.PgyCrashManager;

public class CoachApplication extends Application {

    /*
     * universal tag for logging
     */
    private final static String TAG = "CzbApplication";
    /*
     * list for saving activitys in stack
     */
    public static List<Activity> mActivityList = new ArrayList<Activity>();
    /*
     * self reference
     */
    public static CoachApplication mApplication;
    /*
	 * 
	 */
    public static UserInfo mUserInfo;
    
    public  static int isInvited;
    
    public static  int crewardamount  ;//认证金额
    public static int orewardamount;  //开单金额

    private int isSetAdd = 0;  //判断是否设置了地址，1为初次设置，0为未设置或是已设置
    
    private int maxTays;
    
    private boolean isSaveSet;
    
	private static final String SP_NAME = "ApplicationInfo";
	
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
    

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);

        // Create default ImageLoader configaration params
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        /*
         * 蒲公英(捕获异常)
         */
        PgyCrashManager.register(this, Settings.PGY_APP_ID);

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // 弹出报错并强制退出的对话框
                        if (mActivityList.size() > 0) {
                            Looper.prepare();
                            AlertDialog dialog =
                                    new AlertDialog.Builder(getCurrentActivity()).create();
                            dialog.setTitle(R.string.app_name);
                            dialog.setMessage(getResources().getString(R.string.err_fatal));
                            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources()
                                    .getString(R.string.confirm),
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 强制退出程序
                                            finish();
                                        }
                                    });
                            dialog.show();
                            Looper.loop();
                        }
                    }
                }).start();

            }
        });
        
        EMChat.getInstance().init(getApplicationContext());
		 
		/**
		 * debugMode == true 鏃朵负鎵撳紑锛宻dk 浼氬湪log閲岃緭鍏ヨ皟璇曚俊鎭�
		 * @param debugMode
		 * 鍦ㄥ仛浠ｇ爜娣锋穯鐨勬椂鍊欓渶瑕佽缃垚false
		 */
		EMChat.getInstance().setDebugMode(true);//鍦ㄥ仛鎵撳寘娣锋穯鏃讹紝瑕佸叧闂璬ebug妯″紡锛屽鏋滄湭琚叧闂紝鍒欎細鍑虹幇绋嬪簭鏃犳硶杩愯闂
		hxSDKHelper.onInit(getApplicationContext());
        init();
    }

    /**
     * 初始化信息
     */
    private void init() {
        // 获得屏幕高度（像素）
        Settings.DISPLAY_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        // 获得屏幕宽度（像素）
        Settings.DISPLAY_WIDTH = getResources().getDisplayMetrics().widthPixels;
        // 获得系统状态栏高度（像素）
        Settings.STATUS_BAR_HEIGHT = getStatusBarHeight();
        // 文件路径设置
        String parentPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            parentPath =
                    Environment.getExternalStorageDirectory().getPath()
                            + File.separator + getPackageName();
        } else {
            parentPath = Environment.getDataDirectory().getPath() + "/data/" + getPackageName();
        }
        // 临时文件路径设置
        Settings.TEMP_PATH = parentPath + "/tmp";
        // 图片缓存路径设置
        Settings.PIC_PATH = parentPath + "/pic";
        // 图片保存路径设置
        Settings.PHOTO_PATH = parentPath + "/photo";
        // 更新APK路径设置
        Settings.APK_PATH = parentPath + "/apk";
        // 录音文件
        Settings.VIDEO_PATH = parentPath + "/video";
        // 创建各目录
        new File(Settings.TEMP_PATH).mkdirs();
        new File(Settings.PIC_PATH).mkdirs();
        new File(Settings.PHOTO_PATH).mkdirs();
        new File(Settings.APK_PATH).mkdirs();
        new File(Settings.VIDEO_PATH).mkdirs();
        // 初始化用户信息类
        initUserInfo();

        uploadLocation();
    }

    /*
     * 上传地址和设备id
     */
    // 定位相关
    private LocationClient locationClient;
    private MyLocationListenner myListener;
    private String latitude, longitude;

    private void uploadLocation() {
        getPosition();

    }
    
	public boolean getIsFirst(Context mContext)
	{
		return mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getBoolean("isfirst",true);
	}
	
	public void saveIsFirst(Context mContext)
	{
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean("isfirst",false);
		editor.commit();
	}

    public void getPosition() {
        locationClient = new LocationClient(getApplicationContext());
        myListener = new MyLocationListenner();
        locationClient.registerLocationListener(myListener);
        if (locationClient != null) {
            setLocationOption();
            locationClient.start();
            locationClient.requestLocation();
        } else {
            Log.d("TAG", "locClient is null or not started");
        }
    }

    // 设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (locationClient != null)
                locationClient.stop();
            if (location == null)
                return;
            latitude = location.getLatitude() + "";
            longitude = location.getLongitude() + "";

            new UpdateUserLocationTask(location.getProvince(), location.getCity(),
                    location.getDistrict()).execute();
        }

        public void onReceivePoi(BDLocation poiLocation) {}
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
    }

    private class UpdateUserLocationTask extends AsyncTask<Void, Void, BaseResult> {

        JSONAccessor accessor = new JSONAccessor(CoachApplication.this, JSONAccessor.METHOD_POST);
        String province, city, area;

        public UpdateUserLocationTask(String provice, String city, String area) {

        }

        @Override
        protected BaseResult doInBackground(Void... arg0) {
            HashMap<String, Object> requestParams = new HashMap<String, Object>();
            requestParams.put("action", "UpdateUserLocation");
            requestParams.put("openid", CommonUtils.getDeviceId(getApplicationContext()));
            requestParams.put("devicetype", "0");
            requestParams.put("usertype", "1");
            requestParams.put("appversion", "1.0");
            requestParams.put("province", province);
            requestParams.put("city", city);
            requestParams.put("area", area);

            return accessor.execute(Settings.SYSTEM_URL, requestParams, BaseResult.class);
        }

        @Override
        protected void onPostExecute(BaseResult result) {
            super.onPostExecute(result);
        }
    }

    private void initUserInfo() {
        mUserInfo = new UserInfo();
        mUserInfo = mUserInfo.loadUserInfo(getApplicationContext());
    }

    public UserInfo getUserInfo() {
        if (mUserInfo != null)
            return mUserInfo;
        else{
        	//mUserInfo = new UserInfo().loadUserInfo(getApplicationContext());
        	mUserInfo = new UserInfo();
        }
        //mUserInfo = new UserInfo();
        return mUserInfo;
    }

    /**
     * 获得系统状态栏高度
     * 
     * @return 系统状态栏高度（像素）
     */
    private int getStatusBarHeight() {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object obj = cls.newInstance();
            Field field = cls.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {}
        return 0;
    }

    /**
     * 移除当前的activity
     * 
     * @param activity
     */
    public void removeCurrentActivity(Activity activity) {
        if (activity != null)
            mActivityList.remove(activity);
    }

    /**
     * 获得当前最顶层的activity
     * 
     * @return 当前最顶层的activity
     */
    public Activity getCurrentActivity() {
        if (mActivityList != null && mActivityList.size() > 0) {
            return mActivityList.get(mActivityList.size() - 1);
        }
        return null;
    }

    /**
     * 将Activity存入列表
     * 
     * @param activity
     */
    public void addCurrentActivity(Activity activity) {
        if (activity != null)
            mActivityList.add(activity);
    }

    /**
     * 程序关闭时调用
     */
    public void finish() {
        removeAllActivity();
        System.exit(0);
    }

    /**
     * 清除所有的activity
     */
    public void removeAllActivity() {
        if (mActivityList != null && mActivityList.size() > 0) {
            for (int i = 0; i < mActivityList.size(); i++) {
                Activity activity = mActivityList.get(i);
                if (activity != null)
                    activity.finish();
            }
            mActivityList.clear();
        }
    }

    /**
     * 本身实例
     */
    public static CoachApplication getInstance() {
        return mApplication;
    }

    public void uploadClientId() {
        // 获取clientid，并上传
        BindPushTask bindtask = new BindPushTask(PushManager.getInstance().getClientid(this));
        bindtask.execute();
    }

    public int getIsSetAdd() {
		return isSetAdd;
	}

	public void setIsSetAdd(int isSetAdd) {
		this.isSetAdd = isSetAdd;
	}

	public int getMaxTays() {
		return maxTays;
	}

	public void setMaxTays(int maxTays) {
		this.maxTays = maxTays;
	}

	/*
     * bind tuisong
     */
    public class BindPushTask extends AsyncTask<Void, Void, BaseResult> {

        JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);
        private String clientid;

        public BindPushTask(String clientid) {
            this.clientid = clientid;
        }

        @Override
        protected BaseResult doInBackground(Void... arg0) {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("action", "UpdatePushInfo");
            param.put("userid", CoachApplication.getInstance().getUserInfo().getCoachid());
            param.put("usertype", "1"); // 1: coach
            param.put("devicetype", "0"); // 0: android
            param.put("jpushid", clientid);
            return accessor.execute(Settings.SYSTEM_URL, param, BaseResult.class);
        }

        @Override
        protected void onPostExecute(BaseResult result) {
            super.onPostExecute(result);
        }
    }
    
    public String getVersion() {
    	try {
    	       PackageManager manager = this.getPackageManager();
    	       PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
    	       String version = info.versionName;
    	       return version;
    	   } catch (Exception e) {
    	       e.printStackTrace();
    	       return "";
    	     }
    	 }

	public boolean isSaveSet() {
		return isSaveSet;
	}

	public void setSaveSet(boolean isSaveSet) {
		this.isSaveSet = isSaveSet;
	}
}
