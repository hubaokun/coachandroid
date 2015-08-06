package xiaoba.coach.activity;

import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.umeng.analytics.game.UMGameAgent;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.HomeActivity.NewMsgReceiver;
import xiaoba.coach.common.Settings;
import xiaoba.coach.views.LoadingDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	public CoachApplication mApplication;
	public LoadingDialog mLoadingDialog;// 普通加载对话框
	NewMsgReceiver receiver;
	IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获得CzbApplication实例
		mApplication = CoachApplication.getInstance();
		// 将activity添加到集合中
		mApplication.addCurrentActivity(this);
		// loading对话框
		mLoadingDialog = new LoadingDialog(this, true);
	    UMGameAgent.setDebugMode(true);//设置输出运行时日志
	    UMGameAgent.init( this );

		receiver = new NewMsgReceiver();
		filter = new IntentFilter();
		filter.addAction("xiaoba.newmsg");
		filter.addCategory(Intent.CATEGORY_DEFAULT);
	}
	
	public class NewMsgReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent data) {
			showNewMsgDialog(data.getStringExtra("content"));
			abortBroadcast(); // Receiver接收到广播后中断广播
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			receiver = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		//PgyFeedbackShakeManager.unregister();
		unregisterReceiver(receiver);
		UMGameAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//PgyFeedbackShakeManager.register(BaseActivity.this, Settings.PGY_APP_ID);
		registerReceiver(receiver, filter);
		UMGameAgent.onResume(this);
	}

	private Dialog mServiceDialog;

	public void showNewMsgDialog(String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
		mServiceDialog = builder.create();
		mServiceDialog.show();
		mServiceDialog.setContentView(R.layout.dialog_new_msg);
		mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
		int maxWidth = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(BaseActivity.this, 40);
		int textWidth = DensityUtils.dp2px(BaseActivity.this, 17 * 15);
		if (maxWidth > textWidth)
			params.width = textWidth;
		else
			params.width = maxWidth;
		params.gravity = Gravity.CENTER;
		mServiceDialog.getWindow().setAttributes(params);
		mServiceDialog.setCanceledOnTouchOutside(true);

		TextView contentTv = (TextView) mServiceDialog.findViewById(R.id.new_msg_content);
		TextView mButton = (TextView) mServiceDialog.findViewById(R.id.new_msg_confirm);
		contentTv.setText(content);
		mButton.setOnClickListener(new OnSingleClickListener(){
			@Override
			public void doOnClick(View v) {
				mServiceDialog.dismiss();
			}
		});

	}

}
