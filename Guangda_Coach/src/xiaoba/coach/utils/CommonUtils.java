package xiaoba.coach.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.activity.LoginActivity_;
import xiaoba.coach.activity.SetActivity;
import xiaoba.coach.common.Settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.daoshun.lib.util.BitmapUtils;
import com.google.gson.Gson;

public class CommonUtils {
	private static long toastTime;

	/**
	 * 
	 * 文字过长时，要按比例缩小字体大小
	 * 
	 * @param text
	 *            要显示出来的文本
	 * @param lenght
	 *            大于该长度时要缩小字体
	 * @param beforeSize
	 *            缩小前的字体大小
	 * @param multiple
	 *            缩放的系数
	 * @param minSize
	 *            缩小到最小的字体大小
	 */
	public static int setReduceSize(String text, int lenght, int beforeSize, int multiple, int minSize) {
		int sizeResult = beforeSize;
		if (!CommonUtils.isEmpty(text)) {
			text = new BigDecimal(text).toPlainString();
			if (text.length() > lenght) {
				sizeResult = beforeSize - (text.length() - lenght) * multiple;
				if (sizeResult < minSize) {
					// 字体大小不能小于1
					sizeResult = minSize;
					if (sizeResult <= 0) {
						// 如果最小大小比0小，强制返回1;
						sizeResult = 1;
					}
				}
				return sizeResult;
			} else {
				return sizeResult;
			}
		} else {
			return sizeResult;
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param text
	 *            被判断的字符串
	 * @return true:字符串为null或者字符串为""
	 */
	public static boolean isEmpty(String text) {
		if (text == null || text.trim().equals("")) {
			return true;
		}
		return false;

	}

	/*
	 * 
	 * 手机号格式check 第一位须为1,长度须为11
	 */
	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern.compile("^(1)\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 清理临时文件
	 */
	public static final void deleteTempFile() {
		File tempFolder = new File(Settings.TEMP_PATH);
		if (tempFolder.exists()) {
			File[] tempFiles = tempFolder.listFiles();
			for (File tempFile : tempFiles) {
				tempFile.delete();
			}
		}
	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

	/**
	 * 
	 * 设置listview 的item一行行从左边飘进来
	 * 
	 * @return
	 */
	public static LayoutAnimationController getListAnim() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(300);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(300);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
		return controller;
	}

	/**
	 * 弹出Toast,避免短时间内多次弹出Toast
	 * 
	 * @param context
	 *            上下文
	 * @param text
	 *            Toast文字内容
	 */
	public static void showToast(Context context, String text) {
		long time = System.currentTimeMillis();
		if (time - toastTime >= 300) { // 原为3000
			toastTime = time;
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 取得图片缓存大小
	 */
	public static final long getCacheSize() {
		File cacheFolder = new File(Settings.PIC_PATH);
		// 文件夹是否存在
		if (cacheFolder.exists()) {
			return getFileSize(cacheFolder);
		} else {
			return 0;
		}
	}

	/**
	 * 取得文件大小
	 * 
	 * @param file
	 *            文件
	 * @return 文件大小
	 */
	public static long getFileSize(File file) {
		long size = 0;
		for (File subFile : file.listFiles()) {
			if (subFile.isDirectory()) {
				size += getFileSize(subFile);
			} else {
				size += subFile.length();
			}
		}
		return size;
	}

	/**
	 * 清理缓存
	 */
	public static final void cleanCache(Context context) {
		File cacheFolder = new File(Settings.PIC_PATH);

		// 清理所有子文件
		for (File file : cacheFolder.listFiles()) {
			if (!file.isDirectory())
				file.delete();
		}

		File cacheFolder1 = new File(Settings.TEMP_PATH);

		// 清理所有子文件
		for (File file : cacheFolder1.listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
	}

	public static Bitmap getBitmapFromFile(File dst, int width, int height) {
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
				//opts.inSampleSize = computeSampleSize(opts, 1000, 1000);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}

			int result = ExifInterface.ORIENTATION_UNDEFINED;
			try {
				ExifInterface exifInterface = new ExifInterface(dst.getPath());
				result = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
			} catch (IOException e) {
				e.printStackTrace();
			}

			int rotate = 0;
			switch (result) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			default:
				break;
			}

			Bitmap bitmap = BitmapFactory.decodeFile(dst.getPath(), opts);
			if (rotate > 0) {
				bitmap = BitmapUtils.rotateBitmap(bitmap, rotate);
			}

			return bitmap;
		}
		return null;
	}

	private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * MD5加密
	 * 
	 * @param 需要加密的String
	 * @return 加密后String
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}

			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	// 检查网络状态
	public static boolean checkNetworkState(Context context) throws SocketException {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);

		State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		// 未连接网络
		if (!mobileState.equals(State.CONNECTED) && !mobileState.equals(State.CONNECTING) && !wifiState.equals(State.CONNECTED) && !wifiState.equals(State.CONNECTING)) {
			return false;
		}

		return true;
	}

	public static final String getTimeDiff2(String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = dateFormat.parse(strDate);
			return getTimeDiff(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String getTimeDiff1(String strDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(strDate);
			SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
			return format.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String getTimeDiff3(String strDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(strDate);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar currentDate = Calendar.getInstance();// 获取当前时间
			return format.format(date).replace(currentDate.get(Calendar.YEAR) + "-", "");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String getTimeDiff4(String strDate) {
		if (strDate != null && strDate.length() > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = dateFormat.parse(strDate);
				SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
				String newdate = format.format(date);
				if (newdate != null && newdate.length() > 0 && newdate.startsWith("0")) {
					return newdate.substring(1);
				} else {
					return newdate;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return "";
		} else {
			return "";
		}
	}

	public static final String getTimeDiffByFormat(String strDate, String formatS) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(strDate);
			SimpleDateFormat format = new SimpleDateFormat(formatS);
			return format.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String getTimeDiff(Date date) {
		Calendar currentDate = Calendar.getInstance();// 获取当前时间
		String year = currentDate.get(Calendar.YEAR) + "";// 获取当前年份
		long diff = currentDate.getTimeInMillis() - date.getTime();
		if (diff < 0)
			return 0 + "秒钟前";
		else if (diff < 60000)
			return diff / 1000 + "秒钟前";
		else if (diff < 3600000)
			return diff / 60000 + "分钟前";
		else if (diff < 86400000)
			return diff / 3600000 + "小时前";
		else {
			String newdate = DateFormat.format("yyyy-MM-dd kk:mm", date).toString();
			if (newdate.contains(year)) {
				return newdate.substring(5);
			} else {
				return newdate;

			}
		}
	}

	/**
	 * 计算两个时间的时间差
	 * 
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @return 间隔时间（秒）
	 */
	public static long getTimeDiff3(String starttime, String endtime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sdate, edate;
		long diff = 0;
		try {
			if (starttime != null && endtime != null) {
				sdate = dateFormat.parse(starttime);
				edate = dateFormat.parse(endtime);
				diff = edate.getTime() - sdate.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Math.abs(diff) / 1000;
	}

	public static final boolean isOnline(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

		if (!mobileState.equals(State.CONNECTED) && !mobileState.equals(State.CONNECTING) && !wifiState.equals(State.CONNECTED) && !wifiState.equals(State.CONNECTING)) {
			return false;
		}
		return true;
	}

	public static String getDisplayName(String username, String loginname) {

		if (username != null) {
			return username;
		} else {
			return loginname;
		}
	}

	/**
	 * 判断来的是哪一天
	 * 
	 * @param pushtime
	 *            输入时间
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getday(String pushtime) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date oldTime = null;
		Date today;
		try {
			oldTime = dateFormat.parse(pushtime);
			// 将下面的 理解成 yyyy-MM-dd 00：00：00 更好理解点
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = format.format(now);
			today = format.parse(todayStr);
			long a = today.getTime() - oldTime.getTime();
			if (a > 0 && a <= 86400000) {
				// 昨天 86400000=24*60*60*1000 一天
				return "昨天";
			} else if (a <= 0) {
				// 至少是今天
				return "今天";
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 至少是前天
		return dateFormat.format(oldTime).substring(5, 11).replace("-", "月");

	}

	// /////////////////////////////////////////////////////////////////////////11.5 xf
	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

	// 检查Wifi状态
	public static boolean isWiFiActive(Context inContext) {
		Context context = inContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 *            文件大小
	 * @return
	 */
	public static String FormatFileSize(long fileS) {
		// DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}

		if (fileSizeString.equals(".00B")) {
			fileSizeString = "0" + fileSizeString;
		}
		return fileSizeString;
	}

	/**
	 * 获得圆形的bitmap
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	// /*
	// * 透明状态栏 4.4以后
	// */
	// public static void transparentStatueBar(Activity mActivity) {
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	// SystemBarTintManager tintManager = new SystemBarTintManager(mActivity);
	// tintManager.setStatusBarTintEnabled(true);
	// // 此处可以重新指定状态栏颜色
	// tintManager.setStatusBarTintResource(R.color.primary_dark);
	// }
	// }

	/*
	 * 取圆型bitmap
	 */
	public static Bitmap getRoundedBitmap(Context context, Bitmap input, int w, int h) {

		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		int r = 0;
		// 取最短边做边长
		if (w > h) {
			r = h;
		} else {
			r = w;
		}

		RectF rectF = new RectF(0, 0, r, r);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, r / 2, r / 2, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(input, null, rectF, paint);

		return output;
	}

	/*
	 * 请求参数转化
	 */
	public static HashMap<String, Object> convertJavaBeanToHashMap(Object obj) throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Class clazz = obj.getClass();
		List<Class> clazzs = new ArrayList<Class>();

		do {
			clazzs.add(clazz);
			clazz = clazz.getSuperclass();
		} while (!clazz.equals(Object.class));

		for (Class iClazz : clazzs) {
			Field[] fields = iClazz.getDeclaredFields();
			for (Field field : fields) {
				Object objVal = null;
				field.setAccessible(true);
				objVal = field.get(obj);
				hashMap.put(field.getName(), objVal);
			}
		}

		return hashMap;
	}

	public static void gotoLogin(Context mContext) {
		CoachApplication.mUserInfo = null;
		Editor editor = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
		CoachApplication.getInstance().removeAllActivity();
		boolean hasLogin = false;
		boolean isTop = false;
		if (CoachApplication.mActivityList != null) {
			int length = CoachApplication.mActivityList.size();
			for (int i = 0; i < length; i++) {
				if (CoachApplication.mActivityList.get(i).getClass().getSimpleName().equals("LoginActivity_")) {
					hasLogin = true;
					if (i == length - 1)
						isTop = true;

					break;
				}
			}
		}
		if (hasLogin) {
			if (isTop) {
				
			} else {
				mContext.startActivity(new Intent(mContext, LoginActivity_.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
			}
		} else {
			mContext.startActivity(new Intent(mContext, LoginActivity_.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
		}
//		} else {
//			mContext.startActivity(new Intent(mContext, LoginActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//		}

	}
	
	public static String getDeviceId(Context mContext) {

        StringBuilder deviceId = new StringBuilder();

        try {

            // wifi mac地址
            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);

                return deviceId.toString();
            }

            // IMEI（imei）
            TelephonyManager tm =
                    (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);

                return deviceId.toString();
            }

            // 序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);

                return deviceId.toString();
            }
            return getUUID(mContext);
        } catch (Exception e) {
            // e.printStackTrace();
            return getUUID(mContext);
        }
    }

    static String getUUID(Context mContext) {
        String uuid = null;
        SharedPreferences mShare = mContext.getSharedPreferences("deviceid", mContext.MODE_PRIVATE);
        if (mShare != null) {
            uuid = mShare.getString("uuid", "");
        }
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString("uuid", uuid);
            mShare.edit().commit();
        }
        return uuid;
    }
}
