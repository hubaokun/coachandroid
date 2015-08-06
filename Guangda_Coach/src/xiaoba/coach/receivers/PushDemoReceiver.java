package xiaoba.coach.receivers;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.HomeActivity;
import xiaoba.coach.activity.LoadingActivity_;
import xiaoba.coach.activity.LoginActivity;

import xiaoba.coach.activity.ShowHandleActivity;

import xiaoba.coach.activity.LoginActivity_;
import xiaoba.coach.common.Settings;


import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.igexin.sdk.PushConsts;

import android.app.Activity;
import android.app.ActivityManager;

import android.app.KeyguardManager;

import android.app.AlertDialog;
import android.app.Dialog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PushDemoReceiver extends BroadcastReceiver {
	String message = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			byte[] payload = bundle.getByteArray("payload");

			if (payload != null) {
				String data = new String(payload);
				Log.e("xfxxxxxxxxxxxxxxx", data);
				JSONObject dataJson = null;
				try {
					dataJson = new JSONObject(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (dataJson != null) {
					int type = 0;
					try {
						type = dataJson.getInt("type");
						message = dataJson.getString("message");
					} catch (JSONException e) {
						e.printStackTrace();
					}

					/*
					 * 区分type
					 */
					Intent tointent = new Intent();
					if (type == 1) {
						// 连接
						tointent.putExtra("FLAG", 1);
					} else {
						// 我的奖励
						tointent.putExtra("FLAG", 2);
					}
					//锁屏弹窗
//				        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);  
//				        if (km.inKeyguardRestrictedInputMode()) {  
//				            Intent alarmIntent = new Intent(context, ShowHandleActivity.class);  
//				            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//				            context.startActivity(alarmIntent);  
//				        }

					if (isAppForground(context)) {
						// 最前 HomeActivity
						if (CoachApplication.mActivityList != null) {
							int length = CoachApplication.mActivityList.size();
							if (CoachApplication.mActivityList.get(length - 1).getClass().getSimpleName().equals("HomeActivity_")) {
								// REFRESH
								((HomeActivity) (CoachApplication.mActivityList.get(length - 1))).refreshMineMsg();
							}
							
							Intent broad = new Intent();
							broad.putExtra("content", message);
							broad.setAction("xiaoba.newmsg");
							context.sendBroadcast(broad);
						}

					} else {
						// 弹出通知，点击启动app
						NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//						Bitmap icon = ((BitmapDrawable)(context.getResources().getDrawable(R.drawable.noticegt))).getBitmap();
						mBuilder.setContentTitle("小巴学车教练端")// 设置通知栏标题
//								.setLargeIcon(icon)
								.setContentText(message == null ? "您有新的消息" : message)// 设置通知栏显示内容
								.setAutoCancel(true)// 设置这个标志当用户单击面板就可以让通知将自动取消
								.setSmallIcon(R.drawable.noticegt);// 设置通知小ICON
						Intent intent1 = new Intent(context, LoadingActivity_.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
						mBuilder.setContentIntent(pendingIntent);
						Notification notification = mBuilder.build();
						notification.defaults = Notification.DEFAULT_SOUND;
						mNotificationManager.notify(56789, notification);// 定义新闻的通知ID为56789
					}
				}
			}
			break;
		default:
			break;
		}

	}

	/**
	 * app是否处于前端
	 * 
	 * @param context
	 * @return
	 */
	private Boolean isAppForground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(context.getPackageName())) {// 不在前端
				return true;
			}
		}
		return false;
	}

	/*
	 * 是否含有该activity
	 */
	private boolean hasSpecificPage(String str) {
		for (Activity activity : CoachApplication.mActivityList) {
			if (activity.getClass().getSimpleName().equals(str))
				return true;
		}
		return false;
	}
}
